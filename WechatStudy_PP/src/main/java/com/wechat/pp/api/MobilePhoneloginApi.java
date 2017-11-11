package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.UserInfoService;

import lombok.extern.slf4j.Slf4j;
/**
 * 手机号码登陆接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class MobilePhoneloginApi {

	@Resource
	private UserInfoService userInfoService;
	
	@RequestMapping(value="/api/mobilePhonelogin.do",method=RequestMethod.POST)
	public JSONObject mobilePhonelogin(@RequestBody String json){
		log.info(" method is /api/mobilePhonelogin.do to customer input parameter message :  {}",json);
		JSONObject result=userInfoService.mobilePhonelogin(json);
		log.info(" /api/mobilePhonelogin.do  to result message :  {}",result);
		return result;
	};
}
