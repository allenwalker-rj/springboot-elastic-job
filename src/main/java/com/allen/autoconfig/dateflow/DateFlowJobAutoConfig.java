package com.allen.autoconfig.dateflow;

import com.allen.autoconfig.zookeeper.ZookeeperAutoConfig;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
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
 * DateFlowJob 自动化配置
 *
 * @author allen
 * @date 2020/5/30 16:58
 */
@Slf4j
@Configuration
@ConditionalOnBean(CoordinatorRegistryCenter.class)
@AutoConfigureAfter(ZookeeperAutoConfig.class)
public class DateFlowJobAutoConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CoordinatorRegistryCenter zkCenter;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initDateFlowJob() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ElasticDataFlowJob.class);
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();
            Class<?>[] interfaces = instance.getClass().getInterfaces();
            for (Class<?> superInterface : interfaces) {
                if (superInterface == DataflowJob.class) {
                    this.generateDateFlowJob(instance);
                }
            }
        }

    }

    private void generateDateFlowJob(Object instance) {
        ElasticDataFlowJob annotation = instance.getClass().getAnnotation(ElasticDataFlowJob.class);
        String jobName = annotation.jobName();
        String cron = annotation.cron();
        int shardingTotalCount = annotation.shardingTotalCount();
        boolean overwrite = annotation.overwrite();
        // 流式处理开关
        boolean steamingProcess = annotation.steamingProcess();
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
                new DataflowJobConfiguration(core, instance.getClass().getCanonicalName(), steamingProcess);
        // job根配置
        LiteJobConfiguration liteJob =
                LiteJobConfiguration.newBuilder(type)
                        .overwrite(overwrite)
                        .jobShardingStrategyClass(jobStrategy.getCanonicalName())
                        .build();
        // 根据是否开启事件追踪，使用不同的构造方法
        if (openJobEvent){
            JobEventConfiguration jec = new JobEventRdbConfiguration(dataSource);
            // 注册 jobSchedule
            new JobScheduler(zkCenter, liteJob, jec).init();
        }else {
            new JobScheduler(zkCenter, liteJob).init();
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
                log.error("[DateFlowJobAutoConfig]#generateSimpleJob error", e);
            }
        } else {
            jobListeners = new ElasticJobListener[0];
        }
        return jobListeners;
    }
}