package com.company.recruit.common.web.config;

import com.company.recruit.common.redis.RedisKeyRegistry;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Global settings for duplicate submit prevention.
 */
@ConfigurationProperties(prefix = "app.idempotency")
public class IdempotencyProperties {

    private boolean enabled = true;
    private long defaultIntervalSeconds = 3L;
    private String headerName = "X-Idempotency-Key";
    private String keyPrefix = RedisKeyRegistry.DUPLICATE_SUBMIT;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getDefaultIntervalSeconds() {
        return defaultIntervalSeconds;
    }

    public void setDefaultIntervalSeconds(long defaultIntervalSeconds) {
        this.defaultIntervalSeconds = defaultIntervalSeconds;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}