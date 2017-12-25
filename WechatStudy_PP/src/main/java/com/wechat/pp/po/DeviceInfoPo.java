package com.wechat.pp.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="DEVICE_INFO")
@EqualsAndHashCode(callSuper=false)
@Data
public class DeviceInfoPo extends BasePo{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID",length=11) 
	private int id;
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="TO_KEN")
	private String token;
	
	@Column(name="DEVICE_NUMBER")
	private String deviceNumber;
	
	@Column(name="IP_ADDR")
	private String ipAddr;
	
	@Column(name="device_status")
	private String deviceStatus;
}
