package com.hdu.orange_recruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hdu.orange_recruitment.constants.ProgressStatusConstants;
import com.hdu.orange_recruitment.entity.*;
import com.hdu.orange_recruitment.entity.vo.JobVo;
import com.hdu.orange_recruitment.entity.vo.ProgressVo;
import com.hdu.orange_recruitment.mapper.ProgressMapper;
import com.hdu.orange_recruitment.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.orange_recruitment.utils.ThreadLocalUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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

    @Autowired
    private UserService userService;
    @Autowired
    private CvService cvService;
    @Autowired
    private JobService jobService;
    @Autowired
    private EnterpriseStaffService enterpriseStaffService;
    @Autowired
    private EnterpriseService enterpriseService;

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

    @Override
    public IPage<ProgressVo> getProgressByStatus(int status, int pageNum, int pageSize) {

        int id = ThreadLocalUtils.getId();
        //获取当前用户和当前岗位的存在status关系的列表
        LambdaQueryWrapper<Progress> progressLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (ThreadLocalUtils.getRole().equals("1")) {
            progressLambdaQueryWrapper.eq(Progress::getUserCandidateId, id)
                    .like(Progress::getStatus, status);
        }else {
            progressLambdaQueryWrapper.eq(Progress::getUserHrId, id)
                    .like(Progress::getStatus, status);
        }
        List<Progress> progressList = list(progressLambdaQueryWrapper);
        if (progressList.isEmpty()) { return new Page<ProgressVo>(pageNum, pageSize); }

        List<ProgressVo> progressVoList = progressList.stream()
                .map(progress -> {

                    ProgressVo progressVo = new ProgressVo();
                    BeanUtils.copyProperties(progress, progressVo);

                    //EnterpriseStaff
                    LambdaQueryWrapper<EnterpriseStaff> enterpriseStaffLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    enterpriseStaffLambdaQueryWrapper.eq(EnterpriseStaff::getUserId, progress.getUserHrId());
                    EnterpriseStaff enterpriseStaff = enterpriseStaffService.getOne(enterpriseStaffLambdaQueryWrapper);
                    //Enterprise
                    LambdaQueryWrapper<Enterprise> enterpriseLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    enterpriseLambdaQueryWrapper.eq(Enterprise::getId, enterpriseStaff.getEnterpriseId());
                    Enterprise enterprise = enterpriseService.getOne(enterpriseLambdaQueryWrapper);
                    //User_Hr
                    LambdaQueryWrapper<User> userHrLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    userHrLambdaQueryWrapper.eq(User::getId, progress.getUserHrId());
                    User userHr = userService.getOne(userHrLambdaQueryWrapper);
                    //User_Candidate
                    LambdaQueryWrapper<User> userCandidateLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    userCandidateLambdaQueryWrapper.eq(User::getId, progress.getUserCandidateId());
                    User userCandidate = userService.getOne(userCandidateLambdaQueryWrapper);
                    //Cv
                    LambdaQueryWrapper<Cv> cvLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    cvLambdaQueryWrapper.eq(Cv::getUserId, progress.getUserCandidateId());
                    Cv cv = cvService.getOne(cvLambdaQueryWrapper);
                    //Job
                    LambdaQueryWrapper<Job> jobLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    jobLambdaQueryWrapper.eq(Job::getId, progress.getJobId());
                    Job job = jobService.getOne(jobLambdaQueryWrapper);

                    progressVo.setUserCandidateName(userCandidate.getName());
                    progressVo.setJobTitle(job.getTitle());
                    progressVo.setAddress(job.getAddress());
                    progressVo.setQualification(cv.getQualification());
                    progressVo.setBasicSalary(cv.getBasicSalary());
                    progressVo.setEnterpriseId(enterprise.getId());
                    progressVo.setEnterpriseName(enterprise.getName());
                    progressVo.setSalary(job.getSalary());
                    progressVo.setTag1(job.getTag1());
                    progressVo.setTag2(job.getTag2());
                    progressVo.setTag3(job.getTag3());
                    progressVo.setTag4(job.getTag4());
                    progressVo.setTag5(job.getTag5());
                    progressVo.setUserHrName(userHr.getName());
                    progressVo.setAvatar(userHr.getAvatar());
                    progressVo.setPosition(enterpriseStaff.getPosition());

                    return progressVo;
                }).collect(Collectors.toList());

        IPage<ProgressVo> progressVoPage = new Page<>(pageNum, pageSize);
        progressVoPage.setRecords(progressVoList);
        progressVoPage.setTotal(progressVoList.size());

        return progressVoPage;
    }

    @Override
    public IPage<ProgressVo> getProgress(int qualification, int basicSalary, IPage<ProgressVo> progressVoByStatus) {

        List<ProgressVo> progressVoList = progressVoByStatus.getRecords().stream()
                .map(progressVo -> {
                    if (Integer.parseInt(progressVo.getQualification()) >= qualification
                            && Integer.parseInt(progressVo.getBasicSalary()) <= basicSalary) {

                        return progressVo;
                    }
                    return null;
                }).collect(Collectors.toList());

        IPage<ProgressVo> progressVoIPage = new Page<>();
        progressVoIPage.setTotal(progressVoList.size());
        progressVoIPage.setRecords(progressVoList);
        return progressVoIPage;
    }

    @Override
    public void updateProgress(int userCandidateId, int jobId, String status, String action) { //action  add：添加  delete：删除

//        ThreadLocalUtils.getId();
        LambdaQueryWrapper<Progress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Progress::getUserCandidateId, userCandidateId).eq(Progress::getJobId, jobId); //获取候选人用户和当前岗位的关系的记录
        Progress progress = getOne(lambdaQueryWrapper);
        if (progress == null) {

            LambdaQueryWrapper<Job> jobLambdaQueryWrapper = new LambdaQueryWrapper<>();
            jobLambdaQueryWrapper.eq(Job::getId, jobId);
            Job job = jobService.getOne(jobLambdaQueryWrapper);
            //如果候选人用户和当前岗位没有关系，在查询时新建空白简历
            Progress progress1 = new Progress();
            progress1.setUserCandidateId(userCandidateId); //id、userHrId与jobId在hr设置岗位时已产生
            progress1.setUserHrId(job.getUserHrId());
            progress1.setJobId(jobId);
            progress1.setStatus(status); //添加action的值作为状态码
            save(progress1);
        }else {
            //如果候选人用户和当前岗位存在关系
            LambdaUpdateWrapper<Progress> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            Set<String> statusSet = Arrays.stream(progress.getStatus().split(","))
                    .collect(Collectors.toSet());
            if (action.equals("add")) {
                statusSet.add(status); //set去重

                if (Integer.parseInt(status) >= Integer.parseInt(ProgressStatusConstants.getIdByStatus("待评价"))) { // >=待评价-5
                    //待面试-4 待评价-5 已发offer-6 确认入职-7 只能存在一个
                    statusSet.remove(Integer.toString(Integer.parseInt(status)-1));
                }
            }else {
                statusSet.remove(status);
            }
            String newStatus = StringUtils.join(statusSet, ",");
            update(lambdaUpdateWrapper.eq(Progress::getUserCandidateId, userCandidateId).eq(Progress::getJobId, jobId).set(Progress::getStatus, newStatus));

        }
    }


//    @Override
//    public IPage<ProgressVo> getProgress(int pageNum, int pageSize) {
//
//        LambdaQueryWrapper<Progress> progressLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        progressLambdaQueryWrapper.eq(Progress::getUserHrId, ThreadLocalUtils.getId());
//        List<Progress> progressList = list(progressLambdaQueryWrapper);
//        if (progressList.isEmpty()) { return new Page<ProgressVo>(pageNum, pageSize); }
//
//        List<ProgressVo> progressVoList = progressList.stream().map(progress -> {
//            ProgressVo progressVo = new ProgressVo();
//            BeanUtils.copyProperties(progress, progressVo);
//
//            LambdaQueryWrapper<Job> jobLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            jobLambdaQueryWrapper.eq(Job::getId, progress.getJobId());
//
//            Job job = jobService.getOne(jobLambdaQueryWrapper);
//
//            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            userLambdaQueryWrapper.eq(User::getId, progress.getUserCandidateId());
//            User user = userService.getOne(userLambdaQueryWrapper);
//
//            LambdaQueryWrapper<Cv> cvLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            cvLambdaQueryWrapper.eq(Cv::getId, user.getId());
//            Cv cv = cvService.getOne(cvLambdaQueryWrapper);
//
//            progressVo.setUserName(user.getName());
//            progressVo.setJobTitle(job.getTitle());
//            progressVo.setQualification(cv.getQualification());
//
//
//
//            return progressVo;
//        }).collect(Collectors.toList());
//
//        IPage<ProgressVo> progressVoIPage = new Page<>(pageNum, pageSize);
//        progressVoIPage.setTotal(progressVoList.size());
//        progressVoIPage.setRecords(progressVoList);
//        return progressVoIPage;
//    }
}
