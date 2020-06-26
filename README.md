# springboot-elastic-job
SpringBoot 整合 Elastic-Job

#### Zookeeper 配置
```properties
elasticjob.zookeeper.server-list=localhost:2181（可输入自己的地址）
elasticjob.zookeeper.namespace=springboot-elasticjob（可自定义命名空间）
```
如未开启zk，启动项目会失败，注释zk的配置，方可正常启动

#### mybatis generator 配置
引入 jar 包
```xml
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.4.0</version>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>8.0.15</version>
                    </dependency>
                </dependencies>
            </plugin>
```
编写 generatorConfig.xml 文件
```xml
        <!-- mysql 连接 -->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/job_test?serverTimezone=Asia/Shanghai&amp;userSSL=false"
                        userId="root"
                        password="root">

            <property name="nullCatalogMeansCurrent" value="true" />
        </jdbcConnection>

        <!-- 配置 实体类 生成位置 -->
        <javaModelGenerator targetPackage="com.allen.springbootelasticjob.project.model" targetProject="src\main\java">
            <property name="enableSubPackages" value="true" />
            <property name="trimStrings" value="true" />
        </javaModelGenerator>

        <!-- 配置 SQL文件 生成位置 -->
        <sqlMapGenerator targetPackage="mybatis"  targetProject="src\main\resources">
            <property name="enableSubPackages" value="true" />
        </sqlMapGenerator>

        <!-- 配置 Mapper类 生成位置 -->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.allen.springbootelasticjob.project.dao" targetProject="src\main\java">
            <property name="enableSubPackages" value="true" />
        </javaClientGenerator>

        <!-- 指定数据库名称，指定表名称，指定映射类名称 -->
        <table schema="job_test" tableName="job_project" domainObjectName="JobProject">
        </table>
```

相关配置可查询官网 [Mybatis Generator 相关设置](http://mybatis.org/generator/configreference/xmlconfig.html)

#### mysql配置
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/job_test?serverTimezone=Asia/Shanghai&userSSL=false&characterEncoding=UTF-8
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root
```
具体配置可依据自己环境

---
#### version 1.0.1
1. 添加 `自定义分片策略` ShardingStrategy（轮询）
2. 实现 `自定义分片策略` MyShardingStrategyJob（轮询）


#### version 1.0.2
1. 整合 mybatis 
2. 引入 mybatis generator 自动生成工具

#### version 1.0.3
1. 添加 `事件追踪功能` 
2. 事件追踪功能依赖于数据库，请先保证数据库连接正常可用
3. 如数据库连接正常，会在 库：job_test 下自动生成两张表
    * job_execution_log ：记录每次作业的执行历史
    * job_status_trace_log ：记录作业状态变更痕迹表 
4. 因事件追踪功能生成数据较多，暂不建议开启

#### version 1.0.4
1. 添加 `作业监听功能`
2. 监听作业执行前和作业执行后
3. 监听器分为两种类型
    * 每个作业节点均执行，无需考虑分布式（官方推荐）
    * 分布式场景中，仅单一节点执行，整个任务只有一头一尾（谨慎使用）

#### 未完待续
