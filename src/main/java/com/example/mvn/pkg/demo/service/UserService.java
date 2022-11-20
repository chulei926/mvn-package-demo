package com.example.mvn.pkg.demo.service;

import com.example.mvn.pkg.demo.domain.User;
import com.example.mvn.pkg.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

	@Resource
	private UserMapper userMapper;

	public User get(Long id) {
		return userMapper.getById(id);
	}
}
