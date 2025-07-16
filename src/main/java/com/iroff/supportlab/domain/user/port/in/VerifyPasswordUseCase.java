package com.iroff.supportlab.domain.user.port.in;

public interface VerifyPasswordUseCase {
    void verifyPassword(Long userId, String password);
}
