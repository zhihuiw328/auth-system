package com.biyou.auth_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biyou.auth_system.model.SysRole;
import com.biyou.auth_system.model.SysUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface SysUserService extends IService<SysUser> {
    public void saveRole(Integer userId, List<Integer> roleIds);

    public List<SysRole> getRoles(SysUser user);

}
