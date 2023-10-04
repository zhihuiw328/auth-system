package com.biyou.auth_system;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static java.lang.System.*;

@Slf4j
@MapperScan("com.biyou.auth_system.mapper")
@ServletComponentScan
@EnableTransactionManagement
@SpringBootApplication
public class AuthSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthSystemApplication.class, args);
		log.info("start project successfully...");
	}

}
