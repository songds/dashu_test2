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
 * QQ快捷登陆接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class QqLoginApi {

	@Resource
	private UserInfoService userInfoService;
	
	@RequestMapping(value="/api/qqLogin.do",method=RequestMethod.POST)
	public JSONObject qqLogin(@RequestBody String json){
		log.info(" method is /api/qqLogin.do to customer input parameter message :  {}",json);
		JSONObject result=userInfoService.qqLogin(json);
		log.info(" /api/qqLogin.do  to result message :  {}",result);
		return result;
	};
}
