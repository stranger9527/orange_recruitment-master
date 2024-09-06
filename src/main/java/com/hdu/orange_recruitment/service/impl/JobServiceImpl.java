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

                    jobVo.setUserHrName(user.getName());
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
    public void addJob(Job job) {

        if (ThreadLocalUtils.getRole().equals("2")){
            job.setUserHrId(ThreadLocalUtils.getId());
            save(job);
        }else {
            System.out.println("权限不足");
        }
    }

    @Override
    public void updateJob(Job job) {

        if (ThreadLocalUtils.getRole().equals("2")){
            job.setUserHrId(ThreadLocalUtils.getId());
            updateById(job);
        }else {
            System.out.println("权限不足");
        }

    }

    @Override
    public void deleteJob(Job job) {

        if (ThreadLocalUtils.getRole().equals("2")){
            removeById(job);
        }else {
            System.out.println("权限不足");
        }
    }


}
