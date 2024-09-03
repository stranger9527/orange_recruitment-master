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

    IPage<JobVo> getChattedPage(int pageNum, int pageSize);

    IPage<JobVo> getSubmittedPage(int pageNum, int pageSize);

    IPage<JobVo> getFavoritesPage(int pageNum, int pageSize);
}
