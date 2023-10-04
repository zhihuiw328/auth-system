package com.biyou.auth_system;

import com.biyou.auth_system.mapper.SysUserMapper;
import com.biyou.auth_system.model.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthSystemApplicationTests {

	@Autowired
	private SysUserMapper userMapper;

	@Test
	void testQueryById() {
		SysUser user = userMapper.selectById(3);
		System.out.println(user);
	}

}
