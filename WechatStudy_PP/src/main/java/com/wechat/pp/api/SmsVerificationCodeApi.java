package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.SMSService;

import lombok.extern.slf4j.Slf4j;

/**
 * 短信验证码验证
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class SmsVerificationCodeApi {


	@Resource
	private SMSService smsService;
	
	@RequestMapping(value="/api/SMSVerificationCode.do",method=RequestMethod.POST)
	public JSONObject SMSVerificationCode(@RequestBody String json){
		log.info(" method is /api/SMSVerificationCode.do to customer input parameter message :  {}",json);
		JSONObject result=smsService.SMSVerificationCode(json);
		log.info(" /api/SMSVerificationCode.do  to result message :  {}",result);
		return result;
	};
}
