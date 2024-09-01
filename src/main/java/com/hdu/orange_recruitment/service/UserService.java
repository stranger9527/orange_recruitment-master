package com.hdu.orange_recruitment.service;

import com.hdu.orange_recruitment.entity.R;
import com.hdu.orange_recruitment.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author alpha
 * @since 2024-08-30
 */
public interface UserService extends IService<User> {

    User findByPhone(String phone);

    R login(User user);

    void register(User user);

    String uploadAvatar(MultipartFile file);

    void downloadAvatar(HttpServletResponse response);

    R logout(HttpServletRequest request);
}
