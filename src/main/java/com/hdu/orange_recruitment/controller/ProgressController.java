package com.hdu.orange_recruitment.controller;

import com.hdu.orange_recruitment.entity.R;
import com.hdu.orange_recruitment.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
}
