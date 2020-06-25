package com.allen.springbootelasticjob.job;

import com.allen.autoconfig.dateflow.ElasticDataFlowJob;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * DateFlow 流式任务测试
 *
 * @author allen
 * @date 2020/5/30 17:41
 */
@Slf4j
@ElasticDataFlowJob(
        jobName = "myDataFlowJob",
        cron = "0/10 * * * * ?",
        shardingTotalCount = 2,
        overwrite = true,
        steamingProcess = true
)
public class MyDataFlowJob implements DataflowJob<Integer> {

    private static List<Integer> testList = new ArrayList<>();

    static {
        testList.add(1);
        testList.add(2);
        testList.add(3);
        testList.add(4);
        testList.add(5);
        testList.add(6);
        testList.add(7);
        testList.add(8);
        testList.add(9);
        testList.add(10);
    }

    @Override
    public List<Integer> fetchData(ShardingContext shardingContext) {
        List<Integer> fetchList = new ArrayList<>();
        for (Integer index : testList) {
            if (index % shardingContext.getShardingTotalCount() == shardingContext.getShardingItem()) {
                fetchList.add(index);
                break;
            }
        }
        log.info("jobName:{} , shardingItem:{}, fetchList:{}",
                shardingContext.getJobName(),
                shardingContext.getShardingItem(),
                fetchList);

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            // 测试使用，生产环境慎用
            e.printStackTrace();
//            log.error("fetchData error",e);

        }

        return fetchList;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Integer> processList) {
        testList.removeAll(processList);
        log.info("jobName:{} , shardingItem:{}, processList:{}",
                shardingContext.getJobName(),
                shardingContext.getShardingItem(),
                processList);
    }
}