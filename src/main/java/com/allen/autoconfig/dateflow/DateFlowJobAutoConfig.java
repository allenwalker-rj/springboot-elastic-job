package com.allen.autoconfig.dateflow;

import com.allen.autoconfig.zookeeper.ZookeeperAutoConfig;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author allen
 * @date 2020/5/30 16:58
 */
@Configuration
@ConditionalOnBean(CoordinatorRegistryCenter.class)
@AutoConfigureAfter(ZookeeperAutoConfig.class)
public class DateFlowJobAutoConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CoordinatorRegistryCenter zkCenter;

    @PostConstruct
    public void initSimpleJob(){
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ElasticDataFlowJob.class);
        for (Map.Entry<String,Object> entry :beans.entrySet()){
            Object instance = entry.getValue();
            Class<?>[] interfaces = instance.getClass().getInterfaces();
            for (Class<?> superInterface:interfaces){
                if (superInterface == DataflowJob.class){
                    ElasticDataFlowJob annotation = instance.getClass().getAnnotation(ElasticDataFlowJob.class);
                    String jobName = annotation.jobName();
                    String cron = annotation.cron();
                    int shardingTotalCount = annotation.shardingTotalCount();
                    boolean overwrite = annotation.overwrite();
                    boolean steamingProcess = annotation.steamingProcess();
                    JobCoreConfiguration coreConfiguration = JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount).build();
//                    JobTypeConfiguration typeConfiguration = new SimpleJobConfiguration(coreConfiguration,instance.getClass().getCanonicalName());
                    JobTypeConfiguration typeConfiguration = new DataflowJobConfiguration(coreConfiguration,instance.getClass().getCanonicalName(),steamingProcess);
                    LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(typeConfiguration).overwrite(overwrite).build();
                    new JobScheduler(zkCenter,liteJobConfiguration).init();
                }
            }
        }

    }
}