package com.hdu.orange_recruitment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hdu.orange_recruitment.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hdu.orange_recruitment.entity.vo.JobVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author alpha
 * @since 2024-08-30
 */
public interface JobService extends IService<Job> {

    IPage<JobVo> getPage(int pageNum, int pageSize, String kw);

    Job getDetail(Long id);


    void addJob(Job job);

    void updateJob(Job job);

    void deleteJob(Job job);
}
