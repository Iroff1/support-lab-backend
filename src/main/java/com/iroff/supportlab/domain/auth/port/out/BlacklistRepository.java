package com.iroff.supportlab.domain.auth.port.out;

import java.time.Duration;

public interface BlacklistRepository {
    void save(String token, Duration ttl);
    boolean exists(String token);
}
