package com.biyou.auth_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biyou.auth_system.mapper.SysMenuMapper;
import com.biyou.auth_system.mapper.SysRoleMapper;
import com.biyou.auth_system.mapper.SysRoleMenuMapper;
import com.biyou.auth_system.model.SysMenu;
import com.biyou.auth_system.model.SysRole;
import com.biyou.auth_system.model.SysRoleMenu;
import com.biyou.auth_system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getMenus(SysRole sysRole) {
        List<SysMenu> list = new ArrayList<>();

        // get role-menu relation
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, sysRole.getId());
        List<SysRoleMenu> roleMenuList = sysRoleMenuMapper.selectList(queryWrapper);

        // get menu and add to list
        for (SysRoleMenu roleMenu : roleMenuList) {
            SysMenu sysMenu = sysMenuMapper.selectById(roleMenu.getMenuId());
            list.add(sysMenu);
        }

        return list;
    }
}
