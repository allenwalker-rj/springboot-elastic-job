package com.allen.springbootelasticjob.project;

import com.allen.springbootelasticjob.project.model.JobProject;
import com.allen.springbootelasticjob.project.service.JobProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author allen
 * @date 2020/6/25 17:59
 */
@SpringBootTest
public class JobProjectTest {

    @Autowired
    private JobProjectService jobProjectService;

    @Test
    public void testAddProject(){
        JobProject project = new JobProject();
        project.setName("springboot整合ElasticJob");
        project.setVersion("ver 1.0.1");
        Date now = new Date();
        project.setCreateTime(now);
        project.setUpdateTime(now);
        jobProjectService.addProject(project);
    }
}
