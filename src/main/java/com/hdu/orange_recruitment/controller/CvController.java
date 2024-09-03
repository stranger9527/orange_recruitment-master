package com.hdu.orange_recruitment.controller;

import com.hdu.orange_recruitment.entity.Cv;
import com.hdu.orange_recruitment.entity.R;
import com.hdu.orange_recruitment.service.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 个人简历 前端控制器
 * </p>
 *
 * @author alpha
 * @since 2024-08-30
 */
@RestController
@RequestMapping("/user/cv")
public class CvController {

    @Autowired
    private CvService cvService;

    @GetMapping
    public R getCv() {

        Cv cv = cvService.getCv();
        return R.success(cv);
    }

    @PutMapping
    private R update(@RequestBody Cv cv){

        cvService.update(cv);
        return R.ok();
    }
}
