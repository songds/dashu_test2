package com.wechat.pp.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class SsmVerificationService {

	/**
	 * 短信验证接口
	 * @param mobilePhone 手机号码
	 * @param checkCode 验证码
	 * @return
	 */
	public boolean checkSsm(String mobilePhone,String checkCode){
		return false;
	}
	
	
	/**
	 * 发送短信接口
	 * @param mobilePhone 手机号码
	 * @return
	 */
	public JSONObject sendSsm(String mobilePhone){
		return null;
	}
}
