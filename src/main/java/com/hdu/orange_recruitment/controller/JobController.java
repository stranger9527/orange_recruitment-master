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

    @GetMapping("/page")
    public R search(int pageNum, int pageSize, String kw) {

        return R.success(jobService.getPage(pageNum, pageSize, kw));
    }

    @GetMapping("/page/chatted")
    public R getChatted(int pageNum, int pageSize) {

        return R.success(jobService.getChattedPage(pageNum, pageSize));
    }
    @GetMapping("/page/submitted")
    public R getSubmitted(int pageNum, int pageSize) {

        return R.success(jobService.getSubmittedPage(pageNum, pageSize));
    }

//    @GetMapping("/page/interviews")
//    public R getInterviews(int pageNum, int pageSize) {
//
//        return R.success(jobService.getInterviewsPage(pageNum, pageSize));
//    }

    @GetMapping("/page/favorites")
    public R getFavorites(int pageNum, int pageSize) {

        return R.success(jobService.getFavoritesPage(pageNum, pageSize));
    }

    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {

        return R.success(jobService.getDetail(id));
    }


}
