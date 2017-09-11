package com.deshun.springboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deshun.springboot.Po.SysResourceRole;

public interface SysResourceRoleDao  extends JpaRepository<SysResourceRole, Integer>{

	List<SysResourceRole> findByRoleId(String roleId);
}
