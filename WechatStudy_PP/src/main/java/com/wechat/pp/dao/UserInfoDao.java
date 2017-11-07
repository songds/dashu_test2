package com.wechat.pp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wechat.pp.po.UserInfoPo;

public interface UserInfoDao extends JpaRepository<UserInfoPo, Integer>{

}
