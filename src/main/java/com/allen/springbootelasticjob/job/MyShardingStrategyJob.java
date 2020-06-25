package com.allen.springbootelasticjob.job;

import com.allen.autoconfig.sharding.ShardingStrategy;
import com.allen.autoconfig.simple.ElasticSimpleJob;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * shardingStrategy 自定义分片策略的 Job
 *
 * @author allen
 * @date 2020/6/25 16:10
 */
@Slf4j
@ElasticSimpleJob(
        jobName = "myShardingStrategyJob",
        cron = "0/10 * * * * ?",
        shardingTotalCount = 10,
        overwrite = true,
        jobStrategy = ShardingStrategy.class
)
public class MyShardingStrategyJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("jobName:{} , shardingItem:{} , shardingTotal:{}",
                // job名称
                shardingContext.getJobName(),
                // 当前分片
                shardingContext.getShardingItem(),
                // 分片总数
                shardingContext.getShardingTotalCount());
    }
}
