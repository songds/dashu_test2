package com.wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.util.WeixinUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageSerevice {

	private static final String SEND_MESSAGE_URL="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	@Autowired
	private UserService userService;
	
	public JSONObject sendMessage(String json){
		String accessToken=userService.getAccessToken();
		String requestUrl=SEND_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject result=WeixinUtil.httpRequest(requestUrl, "POST", json);
		log.info(" result : {} ",result);
		return result;
	}
	
	

}
