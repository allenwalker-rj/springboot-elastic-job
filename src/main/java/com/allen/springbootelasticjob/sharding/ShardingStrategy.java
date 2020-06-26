package com.allen.springbootelasticjob.sharding;

import com.dangdang.ddframe.job.lite.api.strategy.JobInstance;
import com.dangdang.ddframe.job.lite.api.strategy.JobShardingStrategy;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 自定义分片策略
 *
 * @author allen
 * @date 2020/6/25 10:31
 */
public class ShardingStrategy implements JobShardingStrategy {
    @Override
    public Map<JobInstance, List<Integer>> sharding(List<JobInstance> jobInstances,
                                                    String jobName,
                                                    int shardingTotalCount) {
        Map<JobInstance, List<Integer>> result = new HashMap<>();
        // 设置一个队列
        ArrayDeque<Integer> deque = IntStream.range(0, shardingTotalCount).boxed().
                collect(Collectors.toCollection(() -> new ArrayDeque<>(shardingTotalCount)));
        // 策略：轮询
        while (deque.size() > 0) {
            for (JobInstance instance : jobInstances) {
                // 防止取空
                if (deque.size() > 0) {
                    Integer shardingItem = deque.pop();
                    List<Integer> list = result.get(instance);
                    if (!CollectionUtils.isEmpty(list)) {
                        list.add(shardingItem);
                    } else {
                        List<Integer> initList = new ArrayList<>();
                        initList.add(shardingItem);
                        result.put(instance, initList);
                    }
                }
            }
        }
        return result;
    }
}
