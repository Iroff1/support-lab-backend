package com.iroff.supportlab.adapter.user.out.persistence;

import org.springframework.stereotype.Component;

import com.iroff.supportlab.domain.user.model.User;

@Component
public class UserMapper {

	public UserEntity mapToEntity(User user) {
		return UserEntity.builder()
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
			.createdAt(user.getCreatedAt())
			.modifiedAt(user.getModifiedAt())
			.build();
	}

	public User mapToDomain(UserEntity entity) {
		return User.builder()
			.id(entity.getId())
			.password(entity.getPassword())
			.email(entity.getEmail())
			.name(entity.getName())
			.phone(entity.getPhone())
			.role(entity.getRole())
			.termsOfServiceAgreed(entity.getTermsOfServiceAgreed())
			.privacyPolicyAgreed(entity.getPrivacyPolicyAgreed())
			.marketingAgreed(entity.getMarketingAgreed())
			.active(entity.getActive())
			.version(entity.getVersion())
			.createdAt(entity.getCreatedAt())
			.modifiedAt(entity.getModifiedAt())
			.build();
	}
}
