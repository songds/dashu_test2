package com.wechat.pp.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.DeviceInfoPo;


public interface DeviceInfoDao extends JpaRepository<DeviceInfoPo, String>{

	public DeviceInfoPo getByUserName(String userName);
	
	public DeviceInfoPo getByUserNameAndDeviceStatus(String userName,String deviceStatus);
	
	@Modifying
	@Query("update DeviceInfoPo set token=?2 where userName=?1 and deviceStatus='0' ")
	public int updateByUserNameAndToken(String userName,String token);
	
	@Query("from DeviceInfoPo where userName=?1 and token=?2")
	public boolean isExistByUserNameAndToken(String userName,String token);
	
	@Query("from DeviceInfoPo where userName=?1 and deviceStatus='1' and createdDate>=?2 and createdDate<=?3 ")
	public List<DeviceInfoPo> findByUserNameAndMinDtAndMaxDt(String userName,Date minDt,Date maxDt);
}
