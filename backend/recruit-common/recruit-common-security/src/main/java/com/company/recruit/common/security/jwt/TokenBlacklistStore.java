package com.company.recruit.common.security.jwt;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class TokenBlacklistStore {

    private final Map<String, Instant> store = new ConcurrentHashMap<>();

    public void save(String token, Duration duration) {
        store.put(token, Instant.now().plus(duration));
    }

    public boolean contains(String token) {
        Instant expireAt = store.get(token);
        if (expireAt == null) {
            return false;
        }
        if (expireAt.isBefore(Instant.now())) {
            store.remove(token);
            return false;
        }
        return true;
    }
}
