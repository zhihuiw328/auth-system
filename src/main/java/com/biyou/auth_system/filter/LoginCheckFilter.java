//package com.biyou.auth_system.filter;
//
//
//import com.alibaba.fastjson.JSON;
//import com.biyou.auth_system.common.BaseContext;
//import com.biyou.auth_system.common.ResultUtil;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.util.AntPathMatcher;
//
//import java.io.IOException;
//
//
//
//@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
//@Slf4j
//public class LoginCheckFilter implements Filter {
//
//    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        // 1. get url
//        String requestURI = request.getRequestURI();
//
//        log.info("request: {}", requestURI);
//
//        String[] urls = new String[] {
//                "/login",
//                "/logout",
//                "/modifyPassword",
//                "/template/**",
//                "/static/**"
//        };
//
//        // 2. check request
//        boolean check = check(requestURI, urls);
//
//        // 3. check == true, approve
//        if (check) {
//            log.info("approve request");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // 4. check login status
//        Subject subject = SecurityUtils.getSubject();
//        Object sysUser = subject.getSession().getAttribute("sysUser");
//        if (sysUser != null) {
//            log.info("user already login, id : {}", request.getSession().getAttribute("sysUser"));
//
//            Integer id = (Integer)request.getSession().getAttribute("sysUser");
//            BaseContext.setCurrentId(id);
//
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//
//        log.info("user haven't login");
//        response.getWriter().write(JSON.toJSONString(ResultUtil.fail("notLogin")));
//
//        return;
//    }
//
//
//    public boolean check(String requestURI, String[] urls) {
//        for (String url : urls) {
//            boolean match = PATH_MATCHER.matches(url, requestURI);
//            if (match) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
