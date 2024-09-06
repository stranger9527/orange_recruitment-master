package com.hdu.orange_recruitment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hdu.orange_recruitment.entity.R;
import com.hdu.orange_recruitment.entity.User;
import com.hdu.orange_recruitment.mapper.UserMapper;
import com.hdu.orange_recruitment.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.orange_recruitment.utils.FileUtils;
import com.hdu.orange_recruitment.utils.JwtUtil;
import com.hdu.orange_recruitment.utils.Md5Util;
import com.hdu.orange_recruitment.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alpha
 * @since 2024-08-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

//    @Autowired
//    public IUserService userService;

    @Value("${orange.uploadPath.avatar}")
    private String avatarBasePath;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public User findByPhone(String phone) {


        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPhone, phone);
        User user = getOne(lambdaQueryWrapper); // 调用 getOne 方法
        if (user != null) {
            System.out.println("User found: " + user);
        } else {
            System.out.println("User not found.");
        }
        return user;
    }

    @Override
    public R login(User user) {

        //根据用户名查询用户
        User loginUser = findByPhone(user.getPhone());


        //判断该用户是否存在
        if (loginUser == null) {
            return R.error("用户不存在");
        }

        //判断密码是否正确  loginUser对象中的password是密文
        if (Md5Util.checkPassword(user.getPassword(), loginUser.getPassword())) {
            //登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("role", loginUser.getRole());
            String token = JwtUtil.genToken(claims);
            //把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
            System.out.println(operations.get(token));
//            stringRedisTemplate.delete(token);
//            System.out.println(operations.get(token));
            return R.success(token, loginUser);
//            return R.success(user);
        }
        return R.error("密码错误");
    }

    @Override
    public void register(User user) {

        user.setPassword(Md5Util.getMD5String(user.getPassword()));
        System.out.println(user.getPassword());
        boolean result = save(user); // 调用 save 方法
        if (result) {
            System.out.println("User saved successfully.");
        } else {
            System.out.println("Failed to save user.");
        }
    }

    @Override
    public R logout(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        stringRedisTemplate.delete(token);
        return R.ok("退出成功");
    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        //注册后才能上传头像

        String path = FileUtils.upload(file, avatarBasePath);

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, ThreadLocalUtils.getId()).set(User::getAvatar, path);
        boolean result = update(updateWrapper); // 调用 update 方法
        if (result) {
            System.out.println("Record updated successfully.");
        } else {
            System.out.println("Failed to update record.");
        }


        return path;
    }

    @Override
    public void downloadAvatar(HttpServletResponse response) {

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId, ThreadLocalUtils.getId());
        User user = getOne(lambdaQueryWrapper); // 调用 getOne 方法

        FileUtils.download(user.getAvatar(), avatarBasePath, response);



    }

}
