package com.allen.springbootelasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * DistributeListener 实现分布式监听器
 *
 * @author allen
 * @date 2020/6/26 22:55
 */
//@ElasticSimpleJob(
//        jobName = "MyDistributeListenerJob",
//        cron = "0/10 * * * * ?",
//        shardingTotalCount = 10,
//        overwrite = true,
//        jobStrategy = ShardingStrategy.class,
//        openJobEvent = false,
//        jobListener = {DistributeListener.class}
//)
@Slf4j
public class MyDistributeListenerJob implements SimpleJob {
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
