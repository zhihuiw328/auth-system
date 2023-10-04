package com.biyou.auth_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biyou.auth_system.model.SysMenu;
import com.biyou.auth_system.model.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    public List<SysMenu> getMenus(SysRole sysRole);
}
