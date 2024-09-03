package com.hdu.orange_recruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdu.orange_recruitment.constants.ProgressStatusConstants;
import com.hdu.orange_recruitment.entity.*;
import com.hdu.orange_recruitment.entity.vo.JobVo;
import com.hdu.orange_recruitment.mapper.JobMapper;
import com.hdu.orange_recruitment.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.orange_recruitment.utils.ThreadLocalUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {


    @Autowired
    private EnterpriseStaffService enterpriseStaffService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProgressService progressService;


    /**
     * 获取分页
     * @param pageNum
     * @param pageSize
     * @param kw
     * @return
     */
    @Override
    public IPage<JobVo> getPage(int pageNum, int pageSize, String kw) {

        IPage<Job> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Job> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Job::getTitle, kw);
        IPage<Job> jobPage = page(page, lambdaQueryWrapper); // 调用 page 方法
        jobPage.setTotal(jobPage.getRecords().size()); //获取总数

        List<JobVo> jobVos = jobPage.getRecords().stream()
                .map(job -> {
                    JobVo jobVo = new JobVo();
                    BeanUtils.copyProperties(job, jobVo);

                    //EnterpriseStaff
                    LambdaQueryWrapper<EnterpriseStaff> enterpriseStaffLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    enterpriseStaffLambdaQueryWrapper.eq(EnterpriseStaff::getUserId, job.getUserHrId());
                    EnterpriseStaff enterpriseStaff = enterpriseStaffService.getOne(enterpriseStaffLambdaQueryWrapper);
                    //Enterprise
                    LambdaQueryWrapper<Enterprise> enterpriseLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    enterpriseLambdaQueryWrapper.eq(Enterprise::getId, enterpriseStaff.getEnterpriseId());
                    Enterprise enterprise = enterpriseService.getOne(enterpriseLambdaQueryWrapper);
                    //User
                    LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    userLambdaQueryWrapper.eq(User::getId, job.getUserHrId());
                    User user = userService.getOne(userLambdaQueryWrapper);

                    jobVo.setEnterpriseId(enterpriseStaff.getEnterpriseId());
                    jobVo.setEnterpriseName(enterprise.getName());
                    jobVo.setAvatar(user.getAvatar());
                    jobVo.setPosition(enterpriseStaff.getPosition());

                    return jobVo;
                }).collect(Collectors.toList());

        IPage<JobVo> jobVoPage = new Page<>(pageNum, pageSize);
        jobVoPage.setRecords(jobVos);
        jobVoPage.setTotal(jobPage.getTotal());

        return jobVoPage;


    }


    /**
     * 获取岗位详情
     * @param id
     * @return
     */
    @Override
    public Job getDetail(Long id) {

        LambdaQueryWrapper<Job> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Job::getId, id);
        return getOne(lambdaQueryWrapper);
    }

    @Override
    public IPage<JobVo> getChattedPage(int pageNum, int pageSize) {

        return getPageByStatus(pageNum, pageSize, "沟通过");
    }

    @Override
    public IPage<JobVo> getSubmittedPage(int pageNum, int pageSize) {

        return getPageByStatus(pageNum, pageSize, "简历已投递");
    }

    @Override
    public IPage<JobVo> getFavoritesPage(int pageNum, int pageSize) {
        return getPageByStatus(pageNum, pageSize, "收藏");
    }

    private IPage<JobVo> getPageByStatus(int pageNum, int pageSize, String status) {

        int id = ThreadLocalUtils.getId();
        IPage<Job> page = new Page<>(pageNum, pageSize);
        //获取当前用户沟通过的岗位的id列表
        LambdaQueryWrapper<Progress> progressLambdaQueryWrapper = new LambdaQueryWrapper<>();
        progressLambdaQueryWrapper.select(Progress::getJobId)
                .eq(Progress::getUserCandidateId, id)
                .like(Progress::getStatus, ProgressStatusConstants.getIdByStatus(status));
        List<Progress> progressList = progressService.list(progressLambdaQueryWrapper);
        if (progressList.isEmpty()) { return new Page<JobVo>(pageNum, pageSize); }
        List<Integer> jobIdList = progressList.stream()
                .map(Progress::getJobId).collect(Collectors.toList());

        LambdaQueryWrapper<Job> jobLambdaQueryWrapper = new LambdaQueryWrapper<>();
        jobLambdaQueryWrapper.in(Job::getId, jobIdList);
        IPage<Job> jobPage = page(page, jobLambdaQueryWrapper); // 调用 page 方法
        jobPage.setTotal(jobPage.getRecords().size()); //获取总数

        List<JobVo> jobVos = jobPage.getRecords().stream()
                .map(job -> {
                    JobVo jobVo = new JobVo();
                    BeanUtils.copyProperties(job, jobVo);

                    //EnterpriseStaff
                    LambdaQueryWrapper<EnterpriseStaff> enterpriseStaffLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    enterpriseStaffLambdaQueryWrapper.eq(EnterpriseStaff::getUserId, job.getUserHrId());
                    EnterpriseStaff enterpriseStaff = enterpriseStaffService.getOne(enterpriseStaffLambdaQueryWrapper);
                    //Enterprise
                    LambdaQueryWrapper<Enterprise> enterpriseLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    enterpriseLambdaQueryWrapper.eq(Enterprise::getId, enterpriseStaff.getEnterpriseId());
                    Enterprise enterprise = enterpriseService.getOne(enterpriseLambdaQueryWrapper);
                    //User
                    LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    userLambdaQueryWrapper.eq(User::getId, job.getUserHrId());
                    User user = userService.getOne(userLambdaQueryWrapper);

                    jobVo.setEnterpriseId(enterpriseStaff.getEnterpriseId());
                    jobVo.setEnterpriseName(enterprise.getName());
                    jobVo.setAvatar(user.getAvatar());
                    jobVo.setPosition(enterpriseStaff.getPosition());

                    return jobVo;
                }).collect(Collectors.toList());

        IPage<JobVo> jobVoPage = new Page<>(pageNum, pageSize);
        jobVoPage.setRecords(jobVos);
        jobVoPage.setTotal(jobPage.getTotal());

        return jobVoPage;
    }
}
