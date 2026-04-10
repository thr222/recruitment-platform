package com.company.recruit.common.security.jwt;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenStore {

    private final Map<Long, StoredRefreshToken> store = new ConcurrentHashMap<>();

    public void save(Long userId, String refreshToken, Duration duration) {
        store.put(userId, new StoredRefreshToken(refreshToken, Instant.now().plus(duration)));
    }

    public boolean matches(Long userId, String refreshToken) {
        StoredRefreshToken storedRefreshToken = store.get(userId);
        if (storedRefreshToken == null) {
            return false;
        }
        if (storedRefreshToken.expireAt().isBefore(Instant.now())) {
            store.remove(userId);
            return false;
        }
        return storedRefreshToken.token().equals(refreshToken);
    }

    public void remove(Long userId) {
        store.remove(userId);
    }

    private record StoredRefreshToken(String token, Instant expireAt) {
    }
}
