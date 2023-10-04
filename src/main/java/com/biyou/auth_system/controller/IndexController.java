package com.biyou.auth_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.biyou.auth_system.common.Result;
import com.biyou.auth_system.common.ResultUtil;
import com.biyou.auth_system.model.SysUser;
import com.biyou.auth_system.service.SysUserService;
import com.biyou.auth_system.util.SaltUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class IndexController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public Result<SysUser> login(HttpServletRequest request, @RequestBody SysUser sysUser) {

//        // 1. use md5 handle password
//        String password = sysUser.getPassword();
//        password = DigestUtils.md5DigestAsHex(password.getBytes());


//        // 2. query username from db
//        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SysUser::getUsername, sysUser.getUsername());
//        SysUser user = sysUserService.getOne(queryWrapper);
//
//        // 3. check if user is null
//        if (user == null) {
//            return ResultUtil.fail("login failed");
//        }
//
//        // 4. compare password
//        if (!user.getPassword().equals(password)) {
//            return ResultUtil.fail("login failed");
//        }
//
//        // login success
//        request.getSession().setAttribute("sysUser", user.getId());
//        return ResultUtil.ok(user);

        // use shiro to login

        Subject subject = SecurityUtils.getSubject();
        String msg = "";
        log.info(sysUser.toString());
        try {
            subject.login(new UsernamePasswordToken(sysUser.getUsername(), sysUser.getPassword()));
            subject.getSession().setAttribute("sysUser", sysUser.getId());
            return ResultUtil.ok();
        } catch (UnknownAccountException e) {
            msg = "account not exist";
        } catch (IncorrectCredentialsException e) {
            msg = "password wrong";
        } catch (AuthenticationException e) {
            msg = "login error";
        }
        return ResultUtil.fail(msg);
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
//        request.getSession().removeAttribute("sysUser");
//        return ResultUtil.ok("logout Success");

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return  ResultUtil.ok("logout Success");
    }

    @PutMapping("/modifyPassword")
    public Result<String> update(@RequestBody SysUser sysUser) {
        String password = sysUser.getPassword();

        Md5Hash md5Hash = new Md5Hash(password, SaltUtils.getSalt(), 1024);
        sysUser.setPassword(md5Hash.toHex());

        sysUserService.updateById(sysUser);
        return ResultUtil.ok("update user info success!");
    }


}
