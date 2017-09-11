package com.deshun.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deshun.springboot.Po.TUser;

public interface UserDao  extends JpaRepository<TUser, Integer>{

}
