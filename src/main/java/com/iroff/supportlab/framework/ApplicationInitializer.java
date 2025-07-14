package com.iroff.supportlab.framework;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.iroff.supportlab.adapter.user.out.persistence.UserEntity;
import com.iroff.supportlab.adapter.user.out.persistence.UserJpaRepository;
import com.iroff.supportlab.domain.user.model.Role;

@Configuration
@Profile("local")
public class ApplicationInitializer {

	@Bean
	public CommandLineRunner loadTestData(UserJpaRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.count() > 0) {
				return;
			}
			String password = passwordEncoder.encode("qwer1234!@");

			UserEntity user = UserEntity.builder()
				.email("asdf1234@naver.com")
				.password(password)
				.name("지원사업연구소")
				.phone("01012345678")
				.role(Role.USER)
				.termsOfServiceAgreed(true)
				.marketingAgreed(true)
				.privacyPolicyAgreed(true)
				.build();

			userRepository.save(user);
		};
	}
}
