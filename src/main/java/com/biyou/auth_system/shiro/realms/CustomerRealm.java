package com.biyou.auth_system.shiro.realms;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.biyou.auth_system.model.SysMenu;
import com.biyou.auth_system.model.SysRole;
import com.biyou.auth_system.model.SysRoleUser;
import com.biyou.auth_system.model.SysUser;
import com.biyou.auth_system.service.SysRoleService;
import com.biyou.auth_system.service.SysRoleUserService;
import com.biyou.auth_system.service.SysUserService;
import com.biyou.auth_system.util.ApplicationContextUtils;
import com.biyou.auth_system.util.SaltUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.List;

@Slf4j
public class CustomerRealm extends AuthorizingRealm {

//    @Resource
//    private SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = (String) principalCollection.getPrimaryPrincipal();

        log.info("primaryPrincipal is {}", username);

        // get user object
        SysUserService sysUserService = (SysUserService) ApplicationContextUtils.getBean("sysUserService");
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(SysUser::getUsername, username);
        SysUser user = sysUserService.getOne(lambdaQueryWrapper);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermission("system:menu:add");
        return simpleAuthorizationInfo;
//        if (user != null) {
//            // get roles
//            List<SysRole> roles = sysUserService.getRoles(user);
//
//            SysRoleService sysRoleService = (SysRoleService) ApplicationContextUtils.getBean("sysRoleService");
//            for (SysRole role : roles) {
//                simpleAuthorizationInfo.addRole(role.getRoleName());
//
//                // get perms from role
//                List<SysMenu> menus = sysRoleService.getMenus(role);
//                for (SysMenu menu : menus) {
//                    simpleAuthorizationInfo.addStringPermission(menu.getMenuCode());
//                }
//            }
//            return simpleAuthorizationInfo;
//        }







//        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {

        String principal = (String) token.getPrincipal();

        SysUserService sysUserService = (SysUserService) ApplicationContextUtils.getBean("sysUserService");

        // 2. query username from db
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, principal);
        SysUser user = sysUserService.getOne(queryWrapper);

        if (user != null) {
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(SaltUtils.getSalt()), this.getName());
        }
        return null;
    }
}
