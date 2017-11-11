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
 * 注册接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class RegisterApi {

	@Resource
	private UserInfoService userInfoService;
	
	@RequestMapping(value="/api/register.do",method=RequestMethod.POST)
	public JSONObject register(@RequestBody String json){
		log.info(" method is /api/register.do to customer input parameter message :  {}",json);
		JSONObject result=userInfoService.register(json);
		log.info(" /api/register.do  to result message :  {}",result);
		return result;
	};
}
