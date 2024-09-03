package com.hdu.orange_recruitment.controller;

import com.hdu.orange_recruitment.entity.R;
import com.hdu.orange_recruitment.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author alpha
 * @since 2024-08-30
 */
@RestController
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @PutMapping("/submit/{id}")
    public R submit(@PathVariable Integer id) {

        progressService.submit(id);
        return R.ok();
    }

//    @GetMapping
//    public R getProgressByStatus(int status, int pageNum, int pageSize) {
//
//        return R.success(progressService.getProgressByStatus(status, pageNum, pageSize));
//    }

    @GetMapping
    public R getProgress(int qualification , int basicSalary, int status, int pageNum, int pageSize) {

        //根据状态码筛选后，再根据学历和底薪筛选
        return R.success(progressService.getProgress(qualification, basicSalary, progressService.getProgressByStatus(status, pageNum, pageSize)));
    }


}
