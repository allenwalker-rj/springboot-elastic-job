package com.allen.autoconfig.zookeeper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * zk 配置属性类
 *
 * @author allen
 * @date 2020/5/30 15:52
 */
@ConfigurationProperties(prefix = "elasticjob.zookeeper")
@Data
public class ZookeeperProperties {
    /**
     * 地址列表
     */
    private String serverList;
    /**
     * 命名空间
     */
    private String namespace;
}