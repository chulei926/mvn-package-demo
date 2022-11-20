package com.example.mvn.pkg.demo.mapper;

import com.example.mvn.pkg.demo.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

	User getById(@Param("id") Long id);
}
