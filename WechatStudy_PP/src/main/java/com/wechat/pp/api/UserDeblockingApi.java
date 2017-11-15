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
 * 用户解锁接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class UserDeblockingApi {

	@Resource
	private UserInfoService userInfoService;
	
	@RequestMapping(value="/api/userDeblocking.do",method=RequestMethod.POST)
	public JSONObject userDeblocking(@RequestBody String json){
		log.info(" method is /api/userDeblocking.do to customer input parameter message :  {}",json);
		JSONObject result=userInfoService.userDeblocking(json);
		log.info(" /api/userDeblocking.do  to result message :  {}",result);
		return result;
	};
}
