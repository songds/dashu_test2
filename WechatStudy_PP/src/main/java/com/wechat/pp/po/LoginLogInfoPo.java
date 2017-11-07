package com.wechat.pp.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="LOGIN_LOG_INFO")
@Data
public class LoginLogInfoPo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID",length=11) 
	private int id;
	
	@Column (name="USER_NAME") 
	private String userName;
	
	@Column (name="TO_KEN") 
	private String toKen;
	
	@Column (name="DEVICE_NUMBER")
	private String deviceNumber;

	@Column (name="IP_ADDR")
	private String ipAddr;
	
	@Column (name="LOGIN_STATUS")
	private String loginStatus;
	
	@Column (name="LOGIN_DATE")
	private Date loginDate;
	
	@Column (name="EXPIRATION_TIME")
	private Date expirationTime;
	
	
	
	
}
