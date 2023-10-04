package com.biyou.auth_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biyou.auth_system.common.CustomException;
import com.biyou.auth_system.common.Result;
import com.biyou.auth_system.common.ResultUtil;
import com.biyou.auth_system.dto.roleMenuDto;
import com.biyou.auth_system.model.SysMenu;
import com.biyou.auth_system.model.SysRole;
import com.biyou.auth_system.model.SysRoleMenu;
import com.biyou.auth_system.model.SysRoleUser;
import com.biyou.auth_system.service.SysMenuService;
import com.biyou.auth_system.service.SysRoleMenuService;
import com.biyou.auth_system.service.SysRoleService;
import com.biyou.auth_system.service.SysRoleUserService;
import com.biyou.auth_system.util.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleUserService sysRoleUserService;

    @RequiresPermissions("system:role:add")
    @PostMapping
    public Result<String> save(@RequestBody SysRole sysRole) {
        log.info("sysMenu: {}", sysRole);
        sysRoleService.save(sysRole);
        return ResultUtil.ok("save Menu success");
    }

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize) {

        // pagination constructor
        Page<SysRole> pageInfo = new Page<>(page, pageSize);

        // conditional constructor
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysRole::getId);

        // query
        sysRoleService.page(pageInfo, queryWrapper);

        return ResultUtil.ok(pageInfo);
    }


    @RequiresPermissions("system:role:edit")
    @PutMapping
    public Result<String> update(@RequestBody SysRole sysRole) {

        sysRoleService.updateById(sysRole);
        return ResultUtil.ok("update menu success");
    }

    @RequiresPermissions("system:role:remove")
    @DeleteMapping
    public Result<String> delete(Integer id) {
        log.info("delete role, id is : {}", id);

        // check if this role contains user
        LambdaQueryWrapper<SysRoleUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleUser::getRoleId, id);
        long count = sysRoleUserService.count(queryWrapper);
        if(count > 0) {
            return ResultUtil.fail("this role contains user");
        }

        // delete role in SysRoleMenu table
        sysRoleMenuService.removeByMap(ConvertUtil.toMap("role_id", id));

        // delete role in SysRole table
        sysRoleService.removeById(id);


        return ResultUtil.ok("delete menu success");
    }

    /**
     *  query all menus of role
     */
    @GetMapping("/listMenu")
    @ResponseBody
    public Result<List<SysMenu>> listMenu(@RequestBody SysRole sysRole) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, sysRole.getId());
        // get Role - Menu relation
        List<SysRoleMenu> list = sysRoleMenuService.list(queryWrapper);
        // get all menus
        List<SysMenu> menuList = sysMenuService.listByIds(ConvertUtil.toListId(list));

        return ResultUtil.ok(menuList);
    }

    @RequiresPermissions("system:role:addMenu")
    @PostMapping("/assignMenu")
    @ResponseBody
    public Result<String> assignMenu(@RequestBody roleMenuDto data) {
        log.info("data is : {}", data.toString());
        sysRoleMenuService.assignMenu(data.getRoleId(), data.getMenuIds());
        return ResultUtil.ok("assign Menu success");
    }
}
