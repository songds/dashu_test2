package com.deshun.springboot.dto;

import lombok.Data;

@Data
public class CustomerMessage {

	/**
	 * 客户姓名
	 */
	private String customerName;
	
	/**
	 * 证件类型
	 */
	private String certType;
	
	/**
	 * 证件号码
	 */
	private String certNo;
	
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 联系电话
	 */
	private String mobile;
}
