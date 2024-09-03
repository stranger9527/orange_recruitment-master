package com.hdu.orange_recruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hdu.orange_recruitment.entity.Cv;
import com.hdu.orange_recruitment.mapper.CvMapper;
import com.hdu.orange_recruitment.service.CvService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.orange_recruitment.utils.ThreadLocalUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 个人简历 服务实现类
 * </p>
 *
 * @author alpha
 * @since 2024-08-30
 */
@Service
public class CvServiceImpl extends ServiceImpl<CvMapper, Cv> implements CvService {

    @Override
    public Cv getCv() {

        int id = ThreadLocalUtils.getId();
        LambdaQueryWrapper<Cv> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Cv::getUserId, id);
        Cv cv = getOne(lambdaQueryWrapper);
        if (cv == null) {
            //如果该用户没有简历，在查询时新建空白简历
            Cv emptyCv = new Cv();
            emptyCv.setUserId(id);
            save(emptyCv);
        }else {

            return cv;
        }
        //返回新建的空白简历
        LambdaQueryWrapper<Cv> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Cv::getUserId, id);

        return getOne(lambdaQueryWrapper);
    }

    @Override
    public void update(Cv cv) {

        int id = ThreadLocalUtils.getId();
        LambdaUpdateWrapper<Cv> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Cv::getUserId, id);
        boolean result = update(cv, lambdaUpdateWrapper);
        if (result) {
            System.out.println("Record updated successfully.");
        } else {
            System.out.println("Failed to update record.");
        }
    }
}
