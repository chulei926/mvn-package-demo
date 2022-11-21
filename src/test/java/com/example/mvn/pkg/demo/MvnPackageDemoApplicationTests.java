package com.example.mvn.pkg.demo;

import com.example.mvn.pkg.demo.domain.User;
import com.example.mvn.pkg.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MvnPackageDemoApplicationTests {

	@Resource
	private UserService userService;

	@Test
	void getTest(){
		User user = userService.get(1L);
		System.out.println(user);
	}

	@Test
	void contextLoads() {
	}

}
