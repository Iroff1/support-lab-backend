package com.iroff.supportlab.adapter.auth.out;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Base64;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.iroff.supportlab.adapter.auth.out.config.NcpProperties;
import com.iroff.supportlab.adapter.auth.out.config.SmsProperties;
import com.iroff.supportlab.adapter.auth.out.dto.NcpSmsRequest;
import com.iroff.supportlab.adapter.auth.out.dto.NcpSmsResponse;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.auth.port.out.SmsClient;
import com.iroff.supportlab.domain.common.port.in.exception.DomainException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class NcpSmsClient implements SmsClient {

	private final SmsProperties smsProperties;
	private final NcpProperties ncpProperties;

	@Override
	public void sendCode(String phone, String code) {
		WebClient webClient = initWebClient();
		NcpSmsRequest request = makeRequest(phone, code);

		NcpSmsResponse ncpSmsResponse = webClient.post()
			.uri(uriBuilder -> uriBuilder
				.path("/services/{serviceId}/messages")
				.build(ncpProperties.getServiceId()))
			.bodyValue(request)
			.retrieve()
			.onStatus(status -> !status.is2xxSuccessful(),
				response -> response.bodyToMono(String.class)
					.flatMap(body -> {
						log.error("SMS send failed: {}", body);
						return Mono.error(new DomainException(AuthError.SEND_CODE_FAILED));
					}))
			.bodyToMono(NcpSmsResponse.class)
			.block(Duration.ofSeconds(10));
	}

	private WebClient initWebClient() {
		String baseUrl = ncpProperties.getApiUrl();
		String timestamp = String.valueOf(System.currentTimeMillis());
		String accessKey = ncpProperties.getAccessKey();
		String signature = makeSignature(timestamp);

		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

		return WebClient.builder()
			.uriBuilderFactory(factory)
			.defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
			.defaultHeader("x-ncp-apigw-timestamp", timestamp)
			.defaultHeader("x-ncp-iam-access-key", accessKey)
			.defaultHeader("x-ncp-apigw-signature-v2", signature)
			.build();
	}

	private NcpSmsRequest makeRequest(String phone, String code) {
		String content = new StringBuilder()
			.append("[")
			.append(code)
			.append("] ")
			.append(smsProperties.getAuthTemplate())
			.toString();

		NcpSmsRequest request = NcpSmsRequest.builder()
			.from(ncpProperties.getFrom())
			.content(content)
			.messages(
				List.of(
					NcpSmsRequest.Message.builder()
						.to(phone)
						.build()
				)
			)
			.build();
		log.info("SMS request: {}", request);
		return request;
	}

	private String makeSignature(String timestamp) {
		String url = String.format("/sms/v2/services/%s/messages", ncpProperties.getServiceId());
		String space = " ";
		String newLine = "\n";
		String method = "POST";
		String accessKey = ncpProperties.getAccessKey();
		String secretKey = ncpProperties.getSecretKey();
		String algorithm = "HmacSHA256";

		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(timestamp)
			.append(newLine)
			.append(accessKey)
			.toString();

		log.info("Signature message: {}", message);

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), algorithm);
		Mac mac;
		try {
			mac = Mac.getInstance(algorithm);
			mac.init(signingKey);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}

		byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
		String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

		return encodeBase64String;
	}
}
