package com.iroff.supportlab.domain.auth.port.in;

public interface LogoutUseCase {
    void logout(String accessToken);
}