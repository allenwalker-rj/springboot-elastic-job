package com.allen.autoconfig.simple;

import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 开启 SimpleJob 简单任务
 *
 * @author allen
 * @date 2020/5/30 16:55
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ElasticSimpleJob {

    String jobName() default "";

    String cron() default "";

    int shardingTotalCount() default 1;

    boolean overwrite() default false;

    // 使用自定义分片策略
    Class<?> jobStrategy() default AverageAllocationJobShardingStrategy.class;

    // 是否开启时间追踪
    boolean openJobEvent() default false;
}
