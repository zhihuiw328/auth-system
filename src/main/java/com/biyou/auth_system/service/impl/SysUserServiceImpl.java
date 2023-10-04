package com.biyou.auth_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biyou.auth_system.mapper.SysRoleMapper;
import com.biyou.auth_system.mapper.SysRoleUserMapper;
import com.biyou.auth_system.mapper.SysUserMapper;
import com.biyou.auth_system.model.SysRole;
import com.biyou.auth_system.model.SysRoleMenu;
import com.biyou.auth_system.model.SysRoleUser;
import com.biyou.auth_system.model.SysUser;
import com.biyou.auth_system.service.SysRoleUserService;
import com.biyou.auth_system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    @Transactional
    public void saveRole(Integer userId, List<Integer> roleIds) {

        // get all roles of userId
        LambdaQueryWrapper<SysRoleUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleUser::getUserId, userId);
        List<SysRoleUser> oldRoleList = sysRoleUserMapper.selectList(queryWrapper);

        // new role list
        List<SysRoleUser> newRoleUser = new ArrayList<>();
        for (Integer id : roleIds) {
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setRoleId(id);
            sysRoleUser.setUserId(userId);
            newRoleUser.add(sysRoleUser);
        }

        // remove old list
        for (SysRoleUser old : oldRoleList) {
            sysRoleUserMapper.deleteById(old);
        }


        // add new list
        for (SysRoleUser newRole : newRoleUser) {
            sysRoleUserMapper.insert(newRole);
        }
    }

    @Override
    public List<SysRole> getRoles(SysUser user) {
        List<SysRole> list = new ArrayList<>();

        //get role-user relation
        LambdaQueryWrapper<SysRoleUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleUser::getUserId, user.getId());
        List<SysRoleUser> roleUserList = sysRoleUserMapper.selectList(queryWrapper);

        // get role and add to list
        for (SysRoleUser roleUser : roleUserList) {
            SysRole sysRole = sysRoleMapper.selectById(roleUser.getRoleId());
            list.add(sysRole);
        }

        return list;
    }
}
