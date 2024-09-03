package com.hdu.orange_recruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hdu.orange_recruitment.constants.ProgressStatusConstants;
import com.hdu.orange_recruitment.entity.Progress;
import com.hdu.orange_recruitment.mapper.ProgressMapper;
import com.hdu.orange_recruitment.service.ProgressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.orange_recruitment.utils.ThreadLocalUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alpha
 * @since 2024-08-30
 */
@Service
public class ProgressServiceImpl extends ServiceImpl<ProgressMapper, Progress> implements ProgressService {

    @Override
    public void submit(Integer jobId) {

        int userCandidateId = ThreadLocalUtils.getId();
        int userHrId;
        LambdaQueryWrapper<Progress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Progress::getUserCandidateId, userCandidateId).eq(Progress::getJobId, jobId); //获取当前用户和当前岗位的关系的记录
        Progress progress = getOne(lambdaQueryWrapper);
        if (progress == null) {
            //如果当前用户和当前岗位没有关系，在查询时新建空白简历
            Progress progress1 = new Progress();
            progress1.setUserCandidateId(userCandidateId); //id、userHrId与jobId在hr设置岗位时已产生
            progress1.setJobId(jobId);
            progress1.setStatus(ProgressStatusConstants.getIdByStatus("简历已投递")); //简历已投递
            save(progress1);
        }else {
            //如果当前用户和当前岗位存在关系
            LambdaUpdateWrapper<Progress> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            Set<String> statusSet = Arrays.stream(progress.getStatus().split(","))
                    .collect(Collectors.toSet());
            statusSet.add(ProgressStatusConstants.getIdByStatus("简历已投递")); //set去重
            String status = StringUtils.join(statusSet, ",");
            lambdaUpdateWrapper.set(Progress::getStatus, status);

        }

    }
}
