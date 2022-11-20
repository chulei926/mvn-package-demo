package com.example.mvn.pkg.demo.web;

import com.example.mvn.pkg.demo.domain.User;
import com.example.mvn.pkg.demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Resource
	private UserService userService;

	@GetMapping("/{id}")
	private User get(@PathVariable("id") Long id) {
		return userService.get(id);
	}
}
