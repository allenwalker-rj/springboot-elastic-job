package com.allen.springbootelasticjob.job;

import com.allen.autoconfig.dateflow.ElasticDataFlowJob;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
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
    private static List<Integer> a = new ArrayList<>();
    static {
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
    }

    @Override
    public List<Integer> fetchData(ShardingContext shardingContext) {
        List<Integer> b = new ArrayList<>();
        for (Integer index : a){
            if (index % shardingContext.getShardingTotalCount() == shardingContext.getShardingItem()){
                b.add(index);

                break;
            }
        }
        log.info("分片: "+shardingContext.getShardingItem()+" 获取的数据 :"+b);
        return b;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Integer> list) {
        a.removeAll(list);
        log.info("分片: "+shardingContext.getShardingItem()+" 处理的数据 :"+list);
    }
}