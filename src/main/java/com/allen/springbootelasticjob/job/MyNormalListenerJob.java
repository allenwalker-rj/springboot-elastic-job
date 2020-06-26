package com.allen.springbootelasticjob.job;

import com.allen.springbootelasticjob.listener.NormalListener;
import com.allen.springbootelasticjob.sharding.ShardingStrategy;
import com.allen.autoconfig.simple.ElasticSimpleJob;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * NormalListener 实现普通作业监听
 *
 * @author allen
 * @date 2020/6/26 22:28
 */
@ElasticSimpleJob(
        jobName = "MyNormalListenerJob",
        cron = "0/10 * * * * ?",
        shardingTotalCount = 10,
        overwrite = true,
        jobStrategy = ShardingStrategy.class,
        openJobEvent = false,
        jobListener = {NormalListener.class}
)
@Slf4j
public class MyNormalListenerJob implements SimpleJob {

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
