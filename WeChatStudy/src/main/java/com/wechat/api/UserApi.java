package com.wechat.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.service.UserService;

@RestController
public class UserApi {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/api/user/getAccessToken.do",method=RequestMethod.GET)
	public String getAccessToken(){
		return userService.getAccessToken();
	}
	
	@RequestMapping(value="/api/user/getUserInfoList.do",method=RequestMethod.GET)
	public JSONObject getUserInfoList(){
		return userService.getUserInfoList();
	}
	
	@RequestMapping(value="/api/user/getUserInfo.do",method=RequestMethod.GET)
	public JSONObject getUserInfo(@RequestParam("openId")String openId){
		return userService.getUserInfo(openId);
	}
	@RequestMapping(value="/api/user/createCode.do",method=RequestMethod.POST)
	public JSONObject createCode(@RequestBody String json){
		return userService.createCode(json);
	}
	@RequestMapping(value="/api/user/wxAttention.do",method=RequestMethod.POST)
	public JSONObject wxAttention(@RequestBody String json){
		Map<String, String> params=JSONObject.parseObject(json, Map.class);
		params.put("CreateTime", String.valueOf(System.currentTimeMillis()));
		return userService.wxAttention(params);
	}
}
