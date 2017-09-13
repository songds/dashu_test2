package com.deshun.springboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deshun.springboot.Po.SysUserRole;


public interface SysUserRoleDao extends JpaRepository<SysUserRole, Integer>{
	
	@Query("select distinct roleId from SysUserRole where userId=:userId")
	List<Integer> findByUserId(@Param("userId") Integer userId);
	
	
}
