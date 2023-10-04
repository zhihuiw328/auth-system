package com.biyou.auth_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biyou.auth_system.mapper.SysRoleUserMapper;
import com.biyou.auth_system.model.SysRoleUser;
import com.biyou.auth_system.service.SysRoleUserService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements SysRoleUserService {
}
