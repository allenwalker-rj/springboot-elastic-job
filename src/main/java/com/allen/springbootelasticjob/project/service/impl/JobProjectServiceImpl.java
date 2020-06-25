package com.allen.springbootelasticjob.project.service.impl;

import com.allen.springbootelasticjob.project.dao.JobProjectMapper;
import com.allen.springbootelasticjob.project.model.JobProject;
import com.allen.springbootelasticjob.project.service.JobProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author allen
 * @date 2020/6/25 17:55
 */
@Service
public class JobProjectServiceImpl implements JobProjectService {

    @Autowired
    private JobProjectMapper mapper;

    @Override
    public void addProject(JobProject jobProject) {
        mapper.insert(jobProject);
    }
}
