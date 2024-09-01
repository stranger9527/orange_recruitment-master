package com.hdu.orange_recruitment.controller;


import com.hdu.orange_recruitment.entity.R;
import com.hdu.orange_recruitment.entity.User;

import com.hdu.orange_recruitment.service.UserService;
import com.hdu.orange_recruitment.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author alpha
 * @since 2024-08-30
 */
@RestController
@RequestMapping("/user")
//@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public R register(@RequestBody User user) {

        //查询用户
        User u = userService.findByPhone(user.getPhone());
        if (u == null) {
//            没有占用
//            注册
            userService.register(user);
            return R.ok("注册成功");
        } else {
            //占用
            return R.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
//    public R login(@RequestBody @Pattern(regexp = "^\\S{5,16}$") String phone, @RequestBody @Pattern(regexp = "^\\S{5,16}$") String password) {
    public R login(@RequestBody User user) {

        return userService.login(user);
    }

    @PostMapping("/logout")
    public R logout(HttpServletRequest request) {

        return userService.logout(request);
    }

    @PostMapping("/upload")
    public R upload(MultipartFile file){

        //上传（修改）头像，返回地址
        return R.success(userService.uploadAvatar(file));
    }

    /**
     * 文件下载
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response){

        userService.downloadAvatar(response);


    }

}
