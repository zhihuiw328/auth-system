package com.biyou.auth_system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biyou.auth_system.common.CustomException;
import com.biyou.auth_system.common.Result;
import com.biyou.auth_system.common.ResultUtil;
import com.biyou.auth_system.model.SysMenu;
import com.biyou.auth_system.service.SysMenuService;
import com.biyou.auth_system.util.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @RequiresPermissions("system:menu:add")
    @PostMapping
    public Result<String> save(@RequestBody SysMenu sysMenu) {
        log.info("sysMenu: {}", sysMenu);
        sysMenuService.save(sysMenu);
        return ResultUtil.ok("save Menu success");
    }

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize) {

        // pagination constructor
        Page<SysMenu> pageInfo = new Page<>(page, pageSize);

        // conditional constructor
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SysMenu::getMenuType);

        // query
        sysMenuService.page(pageInfo, queryWrapper);

        return ResultUtil.ok(pageInfo);
    }

    @RequiresPermissions("system:menu:remove")
    @DeleteMapping
    public Result<String> delete(Integer id) {
        log.info("delete menu, id is : {}", id);
        // List<SysMenu> children = (List<SysMenu>) menuService.listByMap(ConvertUtil.toMap("pid",(Object)id));


        List<SysMenu> children = sysMenuService.listByMap(ConvertUtil.toMap("pid", (Object) id));
        if(children != null && children.size() > 0) {
            throw new CustomException("menu contain submenu");
        }

        sysMenuService.removeById(id);
        return ResultUtil.ok("delete menu success");
    }

    @RequiresPermissions("system:menu:edit")
    @PutMapping
    public Result<String> update(@RequestBody SysMenu sysMenu) {
        log.info("sysMenu is {}", sysMenu);
        sysMenuService.updateById(sysMenu);
        return ResultUtil.ok("update menu success");
    }

}
