package com.iroff.supportlab.adapter.auth.out.persistence;

import com.iroff.supportlab.domain.auth.port.out.BlacklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisBlacklistRepository implements BlacklistRepository {

    private final StringRedisTemplate redisTemplate;
    private static final String BLACKLIST_PREFIX = "blacklist:";

    @Override
    public void save(String token, Duration ttl) {
        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "logout", ttl);
    }

    @Override
    public boolean exists(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + token));
    }
}
