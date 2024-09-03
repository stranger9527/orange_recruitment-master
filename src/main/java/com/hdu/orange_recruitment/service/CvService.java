package com.hdu.orange_recruitment.service;

import com.hdu.orange_recruitment.entity.Cv;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 个人简历 服务类
 * </p>
 *
 * @author alpha
 * @since 2024-08-30
 */
public interface CvService extends IService<Cv> {

    Cv getCv();

    void update(Cv cv);
}
