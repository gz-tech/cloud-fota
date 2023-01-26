package com.upuphone.cloudplatform.fota.util;

import java.lang.annotation.*;

/**
 * @author guangzheng.ding
 * @date 2021/11/29 10:05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    String operationModule() default "";

    String operationType() default "";

    String operationDescription() default "";

    String content() default "";

    String relationId() default "";

    String relationType() default "";

    String objName() default "";
}
