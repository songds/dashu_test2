package com.deshun.springboot.service;


import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.deshun.springboot.Po.SysUser;
import com.deshun.springboot.Po.TUser;
import com.deshun.springboot.dao.SysUserDao;
import com.deshun.springboot.dao.UserDao;


@Service
public class UserService {

	@Resource
	private UserDao userDao;
	
	@Resource
	private SysUserDao sysUserDao;
	
	public List<TUser> findUserAll(){
		List<TUser> ites=userDao.findAll();
		return ites;
	}
	
	public SysUser findByName(String name){
		return sysUserDao.findByName(name);
	}
}
