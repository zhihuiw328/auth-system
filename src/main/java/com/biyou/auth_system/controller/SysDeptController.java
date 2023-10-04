package com.biyou.auth_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biyou.auth_system.common.Result;
import com.biyou.auth_system.common.ResultUtil;
import com.biyou.auth_system.model.SysDept;
import com.biyou.auth_system.model.SysMenu;
import com.biyou.auth_system.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("/sysDept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;


    @RequiresPermissions("system:dept:add")
    @PostMapping
    public Result<String> save(@RequestBody SysDept sysDept) {
        log.info("sysDept: {}", sysDept);
        sysDeptService.save(sysDept);
        return ResultUtil.ok("save department success");
    }

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize) {

        // pagination constructor
        Page<SysDept> pageInfo = new Page<>(page, pageSize);

        // conditional constructor
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysDept::getId);

        // query
        sysDeptService.page(pageInfo, queryWrapper);

        return ResultUtil.ok(pageInfo);
    }

    @RequiresPermissions("system:dept:remove")
    @DeleteMapping
    public Result<String> delete(Integer id) {
        sysDeptService.removeById(id);
        return ResultUtil.ok("delete department success");
    }

    @RequiresPermissions("system:dept:edit")
    @PutMapping
    public Result<String> update(@RequestBody SysDept sysDept) {
        sysDeptService.updateById(sysDept);
        return ResultUtil.ok("update menu success");
    }
}
