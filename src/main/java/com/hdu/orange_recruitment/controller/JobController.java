package com.hdu.orange_recruitment.controller;

import com.hdu.orange_recruitment.entity.R;
import com.hdu.orange_recruitment.service.JobService;
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
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public R search(int pageNum, int pageSize, String kw) {

        return R.success(jobService.getPage(pageNum, pageSize, kw));
    }

    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {

        return R.success(jobService.getDetail(id));
    }


}
