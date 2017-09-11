package com.deshun.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deshun.springboot.Po.SysUser;


public interface SysUserDao  extends JpaRepository<SysUser, Integer>{

	SysUser findByName(String name);
	
}
