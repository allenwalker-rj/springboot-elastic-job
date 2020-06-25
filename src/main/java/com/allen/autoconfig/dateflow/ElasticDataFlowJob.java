package com.allen.autoconfig.dateflow;

import com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启 DateFlowJob 流式任务
 *
 * @author allen
 * @date 2020/5/30 17:46
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ElasticDataFlowJob {

    String jobName() default "";

    String cron() default "";

    int shardingTotalCount() default 1;

    boolean overwrite() default false;

    // 是否开启流式处理，默认关闭
    boolean steamingProcess() default false;

    // 分片策略
    Class<?> jobStrategy() default AverageAllocationJobShardingStrategy.class;

    // 是否开启时间追踪
    boolean openJobEvent() default false;
}
