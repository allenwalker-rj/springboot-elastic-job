package com.allen.springbootelasticjob.project.model;

import java.util.Date;

/**
 * @author allen
 * @date 2022/9/10 13:28
 */
public class JobProject {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_project.id
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_project.name
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_project.version
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    private String version;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_project.create_time
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column job_project.update_time
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_project.id
     *
     * @return the value of job_project.id
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_project.id
     *
     * @param id the value for job_project.id
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_project.name
     *
     * @return the value of job_project.name
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_project.name
     *
     * @param name the value for job_project.name
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_project.version
     *
     * @return the value of job_project.version
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    public String getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_project.version
     *
     * @param version the value for job_project.version
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_project.create_time
     *
     * @return the value of job_project.create_time
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_project.create_time
     *
     * @param createTime the value for job_project.create_time
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column job_project.update_time
     *
     * @return the value of job_project.update_time
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column job_project.update_time
     *
     * @param updateTime the value for job_project.update_time
     *
     * @mbg.generated Thu Jun 25 17:47:34 GMT+08:00 2020
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}