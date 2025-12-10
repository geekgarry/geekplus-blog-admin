package com.geekplus.common.annotation;

import java.lang.annotation.*;

/**
 * author     : geekplus
 * description: 自定义注解防止重复登录
 */
@Inherited
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatLogin {

    /**
     * 默认失效时间3秒
     *
     * @return
     */
    long seconds() default 3;
}
