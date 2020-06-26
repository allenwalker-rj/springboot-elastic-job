package com.allen.autoconfig.simple;

import com.allen.autoconfig.zookeeper.ZookeeperAutoConfig;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;

/**
 * SimpleJob 自动化配置
 *
 * @author allen
 * @date 2020/5/30 16:58
 */
@Slf4j
@Configuration
@ConditionalOnBean(CoordinatorRegistryCenter.class)
@AutoConfigureAfter(ZookeeperAutoConfig.class)
public class SimpleJobAutoConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CoordinatorRegistryCenter zkCenter;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initSimpleJob() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ElasticSimpleJob.class);
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();
            Class<?>[] interfaces = instance.getClass().getInterfaces();
            for (Class<?> superInterface : interfaces) {
                if (superInterface == SimpleJob.class) {
                    this.generateSimpleJob(instance);
                }
            }
        }
    }

    private void generateSimpleJob(Object instance) {
        ElasticSimpleJob annotation = instance.getClass().getAnnotation(ElasticSimpleJob.class);
        String jobName = annotation.jobName();
        String cron = annotation.cron();
        int shardingTotalCount = annotation.shardingTotalCount();
        boolean overwrite = annotation.overwrite();
        // 获取分片策略
        Class<?> jobStrategy = annotation.jobStrategy();
        // 开启事件追踪
        boolean openJobEvent = annotation.openJobEvent();
        // 获取作业监听器
        Class<? extends ElasticJobListener>[] listeners = annotation.jobListener();
        ElasticJobListener[] jobListeners = this.buildListener(listeners);

        // job核心配置
        JobCoreConfiguration core =
                JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount)
                        .build();
        // job类型配置
        JobTypeConfiguration type =
                new SimpleJobConfiguration(core, instance.getClass().getCanonicalName());
        // job根配置
        LiteJobConfiguration liteJob =
                LiteJobConfiguration.newBuilder(type)
                        // 设置 overwrite 属性，是否支持刷新
                        .overwrite(overwrite)
                        // 设置分片策略
                        .jobShardingStrategyClass(jobStrategy.getCanonicalName())
                        .build();
        // 根据是否开启事件追踪，使用不同的构造方法
        if (openJobEvent) {
            JobEventConfiguration jec = new JobEventRdbConfiguration(dataSource);
            // 注册 jobSchedule
            new JobScheduler(zkCenter, liteJob, jec, jobListeners).init();
        } else {
            new JobScheduler(zkCenter, liteJob, jobListeners).init();
            // 分布式作业监听器实现（不支持，不提供，可了解）
//            DistributeListener distributeListener =
//                    new DistributeListener(5000,5000);
//            new JobScheduler(zkCenter, liteJob, distributeListener).init();
        }
    }

    private ElasticJobListener[] buildListener(Class<? extends ElasticJobListener>[] listeners) {
        ElasticJobListener[] jobListeners = null;
        int length = listeners.length;
        if (length > 0) {
            try {
                jobListeners = new ElasticJobListener[length];
                int index = 0;
                for (Class<? extends ElasticJobListener> listener : listeners) {
                    ElasticJobListener listenerInstance = listener.getDeclaredConstructor().newInstance();
                    jobListeners[index] = listenerInstance;
                    index++;
                }
            } catch (Exception e) {
                log.error("[SimpleJobAutoConfig]#generateSimpleJob error", e);
            }
        } else {
            jobListeners = new ElasticJobListener[0];
        }
        return jobListeners;
    }
}