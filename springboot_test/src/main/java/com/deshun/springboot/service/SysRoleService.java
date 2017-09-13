package com.deshun.springboot.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.deshun.springboot.Po.SysRole;
import com.deshun.springboot.dao.SysRoleDao;

@Service
public class SysRoleService {

	@Resource
	private SysRoleDao sysRoleDao;
	
	List<SysRole> findByIdIn(List<Integer> ids){
		return sysRoleDao.findByIdIn(ids);
	}
}
