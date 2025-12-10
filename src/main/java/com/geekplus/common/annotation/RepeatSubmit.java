package com.geekplus.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解防止表单重复提交
 *
 * @author
 *
 */
@Inherited
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit
{
    /**
     * 防重复操作限时标记数值(存储redis限时标记数值)
     */
    String value() default "repeat_submit_key";

    /**
     * 默认失效时间3秒
     *
     * @return
     */
    long seconds() default 3;
}
