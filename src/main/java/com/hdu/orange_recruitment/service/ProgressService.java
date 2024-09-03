package com.hdu.orange_recruitment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hdu.orange_recruitment.entity.Job;
import com.hdu.orange_recruitment.entity.Progress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hdu.orange_recruitment.entity.vo.JobVo;
import com.hdu.orange_recruitment.entity.vo.ProgressVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author alpha
 * @since 2024-08-30
 */
public interface ProgressService extends IService<Progress> {

    void submit(Integer id);

    IPage<ProgressVo> getProgressByStatus(int status, int pageNum, int pageSize);


    IPage<ProgressVo> getProgress(int qualification, int basicSalary, IPage<ProgressVo> progressVoByStatus);
}
