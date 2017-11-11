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
 * 微信快捷登陆接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class WeixinLoginApi {

	@Resource
	private UserInfoService userInfoService;
	
	@RequestMapping(value="/api/weixinLogin.do",method=RequestMethod.POST)
	public JSONObject weixinLogin(@RequestBody String json){
		log.info(" method is /api/weixinLogin.do to customer input parameter message :  {}",json);
		JSONObject result=userInfoService.weixinLogin(json);
		log.info(" /api/weixinLogin.do  to result message :  {}",result);
		return result;
	};
}
