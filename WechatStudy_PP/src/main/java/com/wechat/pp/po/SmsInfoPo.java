package com.wechat.pp.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "SMS_INFO")
public class SmsInfoPo extends BasePo{
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@Column(name="MOBILE_NO")
	private String mobileNo;
	
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="VERIFICATION_CODE")
	private String verificationCode;
	
	@Column(name="EXPIRATION_TIME")
	private Date expirationTime;
	

}
