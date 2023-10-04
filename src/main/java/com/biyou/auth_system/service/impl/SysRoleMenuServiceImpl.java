package com.biyou.auth_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biyou.auth_system.mapper.SysRoleMenuMapper;
import com.biyou.auth_system.model.SysMenu;
import com.biyou.auth_system.model.SysRoleMenu;
import com.biyou.auth_system.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Transactional
    public void assignMenu(Integer roleId, List<Integer> sysMenuIds) {
        // get all Menus of roleId
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> oldMenuList = sysRoleMenuMapper.selectList(queryWrapper);
        // new menu list
        List<SysRoleMenu> newMenuList = new ArrayList<>();
        for (Integer id : sysMenuIds) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(id);
            newMenuList.add(sysRoleMenu);
        }

        // remove oldMenuList
        for (SysRoleMenu old : oldMenuList) {
            sysRoleMenuMapper.deleteById(old.getId());
        }
        // add new MenuList
        for (SysRoleMenu newMenu : newMenuList) {
            sysRoleMenuMapper.insert(newMenu);
        }
    }
}
