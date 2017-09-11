package com.deshun.springboot.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deshun.springboot.Po.SysResource;



public interface SysResourceDao extends JpaRepository<SysResource, Integer>{
	
	List<SysResource> findByResourceIdIn(List<String> ids); 
}
