package com.allen.springbootelasticjob.job;

import com.allen.autoconfig.simple.ElasticSimpleJob;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * SimpleJob 简单任务测试
 *
 * @author allen
 * @date 2020/5/30 16:38
 */
@Slf4j
@ElasticSimpleJob(
        jobName = "mySimpleJob",
        cron = "0/10 * * * * ?",
        shardingTotalCount = 2,
        overwrite = true
)
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("jobName:{} , shardingItem:{} , shardingTotal:{}",shardingContext.getJobName(),
                shardingContext.getShardingItem(), shardingContext.getShardingTotalCount());
    }
}