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

@Entity
@Table(name="USER_INFO")
@Data
@EqualsAndHashCode(callSuper=false)
public class UserInfoPo extends BasePo{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID",length=11) 
	private int id;
	
	@Column (name="name")
	private String name;
	
	@Column (name="sex")
	private String sex;
	
	@Column (name="BIRTH_DATE")
	private Date birthDate;
	
	@Column (name="education")
	private String education;
	
	@Column (name="USER_NAME")
	private String userName;
	
	@Column (name="PASSWORD")
	private String password;
	
	@Column (name="MOBILE")
	private String mobile;
	
	@Column (name="IMAGE_URL")
	private String imageUrl;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="USER_STATUS")
	private String userStatus;
	
	@Column (name="REGISTRATION_DATE")
	private Date registrationDate;
	
	@Column (name="BALANCE_ACCOUNT")
	private double balanceAccount;	
	
}
