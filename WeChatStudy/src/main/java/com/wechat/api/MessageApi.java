package com.wechat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.service.MessageSerevice;

@RestController
public class MessageApi {

	@Autowired
	private MessageSerevice messageSerevice;
	
	@RequestMapping(value="/api/message/sendMessage.do",method=RequestMethod.POST)
	public JSONObject sendMessage(@RequestParam("accessToken")String accessToken,@RequestBody String json){
		return messageSerevice.sendMessage(accessToken, json);
	}
	
}
