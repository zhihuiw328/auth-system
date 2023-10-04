package com.biyou.auth_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biyou.auth_system.model.SysMenu;
import com.biyou.auth_system.model.SysRoleMenu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    public void assignMenu(Integer roleId, List<Integer> sysMenuIds);
}
