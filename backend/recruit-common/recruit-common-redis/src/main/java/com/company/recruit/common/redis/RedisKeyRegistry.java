package com.company.recruit.common.redis;

public final class RedisKeyRegistry {

    public static final String CAPTCHA = "recruit:captcha:";
    public static final String TOKEN_BLACKLIST = "recruit:token:blacklist:";
    public static final String HOT_JOBS = "recruit:jobs:hot";
    public static final String HOT_JOB_RANKING = "recruit:job:hot:ranking";
    public static final String JOB_DETAIL = "recruit:job:detail:";
    public static final String JOB_SEARCH_PAGE = "recruit:job:search:";
    public static final String COMPANY_DETAIL = "recruit:company:detail:";
    public static final String DICT_DATA = "recruit:dict:";
    public static final String DUPLICATE_SUBMIT = "recruit:idempotency:";
    public static final String JOB_VIEW_COUNTER = "recruit:job:view:counter";
    public static final String JOB_APPLY_COUNTER = "recruit:job:apply:counter";
    public static final String RESUME_ATTACHMENT_HASH = "recruit:resume:attachment:hash:";
    public static final String RESUME_PARSE_TASK = "recruit:resume:parse:task:";
    public static final String RECOMMEND_PROFILE = "recruit:recommend:profile:";
    public static final String RECOMMEND_PAGE = "recruit:recommend:page:";
    public static final String RECOMMEND_BEHAVIOR_JOB = "recruit:recommend:behavior:job:";
    public static final String RECOMMEND_BEHAVIOR_CATEGORY = "recruit:recommend:behavior:category:";
    public static final String RECOMMEND_BEHAVIOR_CITY = "recruit:recommend:behavior:city:";
    public static final String USER_UNREAD_MESSAGE = "recruit:message:unread:";

    private RedisKeyRegistry() {
    }
}