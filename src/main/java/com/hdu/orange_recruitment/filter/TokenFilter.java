//package com.hdu.orange_recruitment.filter;
//
//import com.hdu.orange_recruitment.utils.ThreadLocalUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import static com.hdu.orange_recruitment.constants.ErrorConstants.TOKEN_IS_NULL;
//
///**
// * 主要是检查前端是否传了id
// * 并且将该id存入ThreadLocal中
// */
//@WebFilter
//public class TokenFilter implements Filter {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        String token = request.getHeader("Authorization");
//
//        if (!StringUtils.isEmpty(token)) {
//
//            ThreadLocalUtils.save(Long.parseLong(id));
//
//            filterChain.doFilter(servletRequest, servletResponse);
//        }else{
//            response.setStatus(401);
//            response.getWriter().println(TOKEN_IS_NULL);
//        }
//    }
//}
