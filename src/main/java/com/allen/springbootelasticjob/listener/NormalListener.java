package com.allen.springbootelasticjob.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 普通作业监听器，每个节点均会执行，无需考虑分布式（官方推荐）
 *
 * @author allen
 * @date 2020/6/26 21:36
 */
@Slf4j
public class NormalListener implements ElasticJobListener {

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info("--- Normal listener executor before jobName:{} ---", shardingContexts.getJobName());
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info("--- Normal listener executor after jobName:{} ---", shardingContexts.getJobName());
    }
}
