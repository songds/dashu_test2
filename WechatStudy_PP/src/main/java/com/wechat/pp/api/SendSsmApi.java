package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.SsmVerificationService;

import lombok.extern.slf4j.Slf4j;
/**
 * 发送短信验证码接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class SendSsmApi {

	@Resource
	private SsmVerificationService ssmVerificationService;
	
	@RequestMapping(value="/api/sendSsm.do",method=RequestMethod.POST)
	public JSONObject sendSsm(@RequestBody String json){
		log.info(" method is /api/sendSsm.do to customer input parameter message :  {}",json);
		JSONObject result=ssmVerificationService.sendSsm(json);
		log.info(" /api/sendSsm.do  to result message :  {}",result);
		return result;
	};
}
