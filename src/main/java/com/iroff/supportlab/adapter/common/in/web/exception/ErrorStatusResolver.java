package com.iroff.supportlab.adapter.common.in.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.iroff.supportlab.adapter.auth.in.web.exception.AuthErrorStatus;
import com.iroff.supportlab.adapter.email.in.exception.EmailErrorStatus;
import com.iroff.supportlab.adapter.user.in.web.exception.UserErrorStatus;
import com.iroff.supportlab.domain.auth.port.in.exception.AuthError;
import com.iroff.supportlab.domain.common.port.in.exception.ErrorInfo;
import com.iroff.supportlab.domain.email.port.in.exception.EmailError;
import com.iroff.supportlab.domain.user.port.in.exception.UserError;

@Component
public class ErrorStatusResolver {
	private final Map<Class<? extends ErrorInfo>, ErrorStatus> errorStatusMap = new HashMap<>();
	private final ErrorStatus unknownErrorStatus = new UnknownErrorStatus();

	public ErrorStatusResolver() {
		errorStatusMap.put(UserError.class, new UserErrorStatus());
		errorStatusMap.put(AuthError.class, new AuthErrorStatus());
		errorStatusMap.put(EmailError.class, new EmailErrorStatus());
	}

	public ErrorStatus resolve(ErrorInfo errorInfo) {
		return errorStatusMap.getOrDefault(errorInfo.getClass(), unknownErrorStatus);
	}
}
