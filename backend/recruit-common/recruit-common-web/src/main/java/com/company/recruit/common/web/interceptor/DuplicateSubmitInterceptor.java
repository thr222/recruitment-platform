package com.company.recruit.common.web.interceptor;

import com.company.recruit.common.core.exception.BusinessException;
import com.company.recruit.common.security.context.SecurityUtils;
import com.company.recruit.common.web.annotation.PreventDuplicateSubmit;
import com.company.recruit.common.web.config.IdempotencyProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Rejects duplicate write requests within a short interval.
 */
@Component
public class DuplicateSubmitInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;
    private final IdempotencyProperties idempotencyProperties;

    public DuplicateSubmitInterceptor(StringRedisTemplate stringRedisTemplate, IdempotencyProperties idempotencyProperties) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.idempotencyProperties = idempotencyProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!idempotencyProperties.isEnabled() || !(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        if (HttpMethod.GET.matches(request.getMethod())
            || HttpMethod.HEAD.matches(request.getMethod())
            || HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        PreventDuplicateSubmit annotation = handlerMethod.getMethodAnnotation(PreventDuplicateSubmit.class);
        if (annotation == null) {
            return true;
        }

        String idempotencyKey = request.getHeader(idempotencyProperties.getHeaderName());
        if (annotation.requireIdempotencyKey() && !StringUtils.hasText(idempotencyKey)) {
            throw new BusinessException("IDEMPOTENCY_KEY_REQUIRED", "Missing idempotency key header");
        }

        String businessScene = StringUtils.hasText(annotation.scene())
            ? annotation.scene()
            : request.getRequestURI();
        String actor = resolveActor();
        String requestFingerprint = StringUtils.hasText(idempotencyKey)
            ? idempotencyKey
            : request.getMethod() + ':' + request.getRequestURI() + ':' + resolveClientIp(request);
        String digest = DigestUtils.md5DigestAsHex(requestFingerprint.getBytes(StandardCharsets.UTF_8));
        String redisKey = idempotencyProperties.getKeyPrefix() + businessScene + ':' + actor + ':' + digest;

        long intervalSeconds = annotation.intervalSeconds() > 0
            ? annotation.intervalSeconds()
            : idempotencyProperties.getDefaultIntervalSeconds();
        Boolean locked = stringRedisTemplate.opsForValue()
            .setIfAbsent(redisKey, "1", Duration.ofSeconds(intervalSeconds));
        if (Boolean.TRUE.equals(locked)) {
            return true;
        }
        throw new BusinessException("DUPLICATE_SUBMIT", "Duplicate submission detected, please retry later");
    }

    private String resolveActor() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return currentUserId == null ? "anonymous" : "user-" + currentUserId;
    }

    private String resolveClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }
        String realIp = request.getHeader("X-Real-IP");
        if (StringUtils.hasText(realIp)) {
            return realIp;
        }
        return request.getRemoteAddr();
    }
}