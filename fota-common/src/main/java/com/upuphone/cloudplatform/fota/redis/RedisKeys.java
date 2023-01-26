package com.upuphone.cloudplatform.fota.redis;

/**
 * @Classname RedisKeys
 * @Description
 * @Date 2022/3/7 3:34 下午
 * @Created by gz-d
 */
public class RedisKeys {
    private static final String REDIS_KEY_BASE = "FOTA_";

    private static final String RELEASE_TASK_KEY = REDIS_KEY_BASE + "release_task_lock";

    public static String getReleaseTakeLockKey() {
        return RELEASE_TASK_KEY;
    }
}
