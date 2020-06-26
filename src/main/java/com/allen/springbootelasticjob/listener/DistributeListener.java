package com.allen.springbootelasticjob.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 分布式场景中，仅单一节点执行，整个任务只有一头一尾（谨慎使用）
 * <p>
 * 1.该监听器，存在并发安全问题
 * 2.设计的初衷应该是不存在并发问题，但是 创建 和 检查所有节点均启动 两个操作并不是原子性的
 * 3.如果考虑到并发问题，在以上情况上加锁，JVM的锁并不适合，只能使用分布式锁
 * 4.如使用了分布式锁解决以上问题，任务执行的性能必定会受到影响
 * 5.以上问题 在 version:2.1.5 中并未得到解决，所以官方不推荐使用
 * </p>
 *
 * @author allen
 * @date 2020/6/26 22:36
 */
@Slf4j
public class DistributeListener extends AbstractDistributeOnceElasticJobListener {

    public DistributeListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }

    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
        log.info("--- Distribute listener executor before jobName:{} ---", shardingContexts.getJobName());
    }

    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
        log.info("--- Distribute listener executor after jobName:{} ---", shardingContexts.getJobName());

    }
}
