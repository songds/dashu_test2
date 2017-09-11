package com.deshun.springboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deshun.springboot.Po.SysRole;


public interface SysRoleDao extends JpaRepository<SysRole, Integer>{

	List<SysRole> findByName(String name);
}
