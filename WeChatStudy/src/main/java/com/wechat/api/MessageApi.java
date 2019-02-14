package com.wechat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.service.MessageSerevice;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MessageApi {

	@Autowired
	private MessageSerevice messageSerevice;
	
	@RequestMapping(value="/api/message/sendMessage.do",method=RequestMethod.POST)
	public JSONObject sendMessage(@RequestBody String json){
		log.info(" method is /api/message/sendMessage.do to customer input parameter message : {}",json);
		return messageSerevice.sendMessage(json);
	}
	
}
