package com.iroff.supportlab.adapter.user.out.persistence;

import org.springframework.stereotype.Component;

import com.iroff.supportlab.domain.user.model.User;

@Component
public class UserMapper {

	public UserEntity mapToEntity(User user) {
		UserEntity entity = UserEntity.builder()
			.id(user.getId())
			.password(user.getPassword())
			.email(user.getEmail())
			.name(user.getName())
			.phone(user.getPhone())
			.role(user.getRole())
			.termsOfServiceAgreed(user.getTermsOfServiceAgreed())
			.privacyPolicyAgreed(user.getPrivacyPolicyAgreed())
			.marketingAgreed(user.getMarketingAgreed())
			.active(user.getActive())
			.version(user.getVersion())
			.build();
		entity.setCreatedAt(user.getCreatedAt());
		entity.setModifiedAt(user.getModifiedAt());
		return entity;
	}

	public User mapToDomain(UserEntity entity) {
		return new User(
			entity.getId(),
			entity.getPassword(),
			entity.getEmail(),
			entity.getName(),
			entity.getPhone(),
			entity.getRole(),
			entity.getTermsOfServiceAgreed(),
			entity.getPrivacyPolicyAgreed(),
			entity.getMarketingAgreed(),
			entity.getActive(),
			entity.getCreatedAt(),
			entity.getModifiedAt(),
			entity.getVersion()
		);
	}
}
