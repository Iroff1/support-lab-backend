package com.iroff.supportlab.adapter.config.global.swagger;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import com.iroff.supportlab.adapter.common.in.web.dto.ResponseDTO;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components())
			.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info()
			.title("Iroff API")
			.description("Iroff API")
			.version("1.0.0");
	}

	@Bean
	public GroupedOpenApi api() {
		return GroupedOpenApi.builder()
			.group("v1")
			.pathsToMatch("/api/**")
			.addOperationCustomizer((operation, handlerMethod) -> {

				Method method = handlerMethod.getMethod();
				Type returnType = method.getGenericReturnType();

				if (returnType instanceof ParameterizedType entityPt
					&& ResponseEntity.class.equals(entityPt.getRawType())) {
					returnType = entityPt.getActualTypeArguments()[0];
				}

				if (returnType instanceof ParameterizedType dtoPt
					&& ResponseDTO.class.equals(dtoPt.getRawType())) {
					Type inner = dtoPt.getActualTypeArguments()[0];
					String ref = "#/components/schemas/" + ((Class<?>)inner).getSimpleName();

					operation.getResponses().values().forEach(resp ->
						resp.getContent().values().forEach(content ->
							content.setSchema(new Schema<>().$ref(ref))
						)
					);
				}
				return operation;
			})
			.addOpenApiCustomizer(registerResponseDtoSchema())
			.build();
	}

	@Bean
	public OpenApiCustomizer registerResponseDtoSchema() {
		return openApi -> {
			Components comps = openApi.getComponents();
			Map<String, Schema> schemas = ModelConverters.getInstance().readAll(ResponseDTO.class);
			Optional<Map.Entry<String, Schema>> modelEntry = schemas.entrySet().stream().findFirst();
			modelEntry.ifPresent(e ->
				comps.addSchemas(e.getKey(), e.getValue())
			);
		};
	}
}
