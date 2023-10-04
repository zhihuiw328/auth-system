package com.biyou.auth_system.service;

import com.biyou.auth_system.model.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysUserServiceTest {
    @Autowired
    private SysUserService sysUserService;

    @Test
    void testQueryById() {
        SysUser user = sysUserService.getById(3);
        System.out.println("user is : " + user.toString());
    }



}
