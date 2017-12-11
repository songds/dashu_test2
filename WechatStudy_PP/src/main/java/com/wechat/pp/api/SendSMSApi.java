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
 * 发送短信验证码接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class SendSMSApi {

	@Resource
	private SMSService smsService;
	
	@RequestMapping(value="/api/sendSMS.do",method=RequestMethod.POST)
	public JSONObject sendSMS(@RequestBody String json){
		log.info(" method is /api/sendSMS.do to customer input parameter message :  {}",json);
		JSONObject result=smsService.sendSMS(json);
		log.info(" /api/sendSMS.do  to result message :  {}",result);
		return result;
	};
}
