package com.iroff.supportlab.domain.user.model;

import com.iroff.supportlab.domain.common.model.BaseTime;

public interface User extends BaseTime {
	Long getId();

	String getPassword();

	String getEmail();

	String getName();

	String getPhone();

	Role getRole();

	Boolean getTermsOfServiceAgreed();

	Boolean getPrivacyPolicyAgreed();

	Boolean getMarketingAgreed();

	void changePassword(String password);
}
