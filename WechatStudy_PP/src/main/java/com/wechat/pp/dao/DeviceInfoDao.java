package com.wechat.pp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.DeviceInfoPo;


public interface DeviceInfoDao extends JpaRepository<DeviceInfoPo, String>{

	public DeviceInfoPo getByUserName(String userName);
	
	@Modifying
	@Query("update DeviceInfoPo set token=?2 where userName=?1")
	public int updateByUserNameAndToken(String userName,String token);
	
	public boolean isExistByUserNameAndToken(String userName,String token);
}
