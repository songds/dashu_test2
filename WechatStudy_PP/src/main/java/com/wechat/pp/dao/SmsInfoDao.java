package com.wechat.pp.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wechat.pp.po.SmsInfoPo;




public interface SmsInfoDao extends CrudRepository<SmsInfoPo, Integer>{
	
	@Query("from SmsInfoPo where status=0 and mobileNo=?1  and expirationTime>=?2 ")
	public SmsInfoPo getSmsInfo(String mobileNo,Date expirationTime);
	
	@Query("from SmsInfoPo where status=0 and mobileNo=?1 and expirationTime>=?2 and verificationCode=?3")
	public SmsInfoPo getSmsInfo(String mobileNo,Date expirationTime,String verificationCode);
	
	
}
