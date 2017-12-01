package com.wechat.pp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.UserInfoPo;

public interface UserInfoDao extends JpaRepository<UserInfoPo, Integer>{
	
	@Query("from UserInfoPo where userName=?1 and password=?2")
	public UserInfoPo loginByUserNameAndPassword(String userName,String password);
	
	@Query("from UserInfoPo where userName=?1")
	public UserInfoPo isUserExistByUserName(String userName);
	
	@Query("from UserInfoPo where mobile=?1")
	public UserInfoPo isUserExistByMobile(String mobile);
	
	
}
