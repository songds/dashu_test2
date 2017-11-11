package com.wechat.pp.po;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="TO_KEN")
	private String token;
	
	@Column(name="DEVICE_NUMBER")
	private String deviceNumber;
	
	@Column(name="IP_ADDR")
	private String ipAddr;
}
