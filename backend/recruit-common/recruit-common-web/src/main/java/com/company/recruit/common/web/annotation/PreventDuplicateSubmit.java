package com.company.recruit.common.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks write endpoints that must reject repeated submissions in a short time window.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreventDuplicateSubmit {

    /**
     * Business scene name, used when composing the idempotency key.
     */
    String scene() default "";

    /**
     * Lock interval in seconds. When not configured, the global default is used.
     */
    long intervalSeconds() default -1L;

    /**
     * When true, requests must carry an X-Idempotency-Key style header.
     */
    boolean requireIdempotencyKey() default false;
}