package com.deshun.springboot.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.deshun.springboot.dao.SysUserRoleDao;

@Service
public class SysUserRoleService {

	@Resource
	private SysUserRoleDao sysUserRoleDao;
	
	List<Integer> findByUserId(Integer userId){
		return sysUserRoleDao.findByUserId(userId);
	}
}
