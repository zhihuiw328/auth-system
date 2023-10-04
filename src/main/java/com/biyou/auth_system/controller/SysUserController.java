package com.biyou.auth_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biyou.auth_system.common.Result;
import com.biyou.auth_system.common.ResultUtil;
import com.biyou.auth_system.dto.roleMenuDto;
import com.biyou.auth_system.dto.roleUserDto;
import com.biyou.auth_system.model.SysUser;
import com.biyou.auth_system.service.SysUserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/sysUser")
public class SysUserController {


    @Autowired
    private SysUserService sysUserService;


    @RequiresPermissions("system:user:add")
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody SysUser sysUser) {
        log.info("new user : {}", sysUser.toString());

        // md5 encrypt password
        sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));

//        sysUser.setCreateTime(new Date());
//        sysUser.setUpdateTime(new Date());
//
//        Integer sysUserId = (Integer) request.getSession().getAttribute("sysUser");

//        sysUser.setCreateUser(sysUserId.toString());
//        sysUser.setUpdateUser(sysUserId.toString());

        sysUserService.save(sysUser);

        return ResultUtil.ok();
    }

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);

        Page pageInfo = new Page(page, pageSize);


        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper();

        // filter
        lambdaQueryWrapper.like(!StringUtils.isEmpty(name), SysUser::getTruename, name);
        lambdaQueryWrapper.orderByDesc(SysUser::getCreateTime);

        sysUserService.page(pageInfo, lambdaQueryWrapper);

        return ResultUtil.ok(pageInfo);
    }

    @RequiresPermissions("system:user:edit")
    @PutMapping
    public Result<String> update(HttpServletRequest request, @RequestBody SysUser sysUser) {
        log.info(sysUser.toString());

//        sysUser.setUpdateTime(new Date());
//        Integer id = (Integer)request.getSession().getAttribute("sysUser");
//        sysUser.setUpdateUser(id.toString());


        sysUserService.updateById(sysUser);

        return ResultUtil.ok();
    }

    @RequiresPermissions("system:user:remove")
    @DeleteMapping
    public Result<String> delete(Integer id) {

        boolean res = sysUserService.removeById(id);
        if (!res) {
            return ResultUtil.fail("delete user fail");
        }

        return ResultUtil.ok("delete user success");
    }

    @RequiresPermissions("system:user:addRole")
    @PostMapping("/saveRole")
    public Result<String> saveRole(@RequestBody roleUserDto data) {
        log.info("data is : {}", data.toString());
        sysUserService.saveRole(data.getUserId(), data.getRoleIds());
        return ResultUtil.ok("save role success");
    }


    @GetMapping("/{id}")
    public Result<SysUser> getById(@PathVariable Integer id) {
        log.info("query sysUser by id");

        SysUser user = sysUserService.getById(id);

        if(user != null) {
            return ResultUtil.ok(user);
        }

        return ResultUtil.fail();
    }




}
