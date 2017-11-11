package com.wechat.pp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.wechat.pp.po.UserInfoPo;

public interface UserInfoDao extends JpaRepository<UserInfoPo, Integer>{
	
	public UserInfoPo loginByUserNameAndPassword(String userName,String password);
	
	public UserInfoPo isUserExistByUserName(String userName);
	
	public UserInfoPo isUserExistByMobile(String mobile);
	
	@Modifying
	public int update(UserInfoPo userInfo);
	
	
}
