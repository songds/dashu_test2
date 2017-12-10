package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.TopicSelectInfoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class FindTopicSelectInfoByTopicIdApi {
	@Resource
	private TopicSelectInfoService topiceSelectInfoService;
	
	@RequestMapping(value="/api/findTopicSelectInfoByTopicId.do",method=RequestMethod.POST)
	public JSONObject findTopiceSelectInfoByTopicId(@RequestBody String json){
		log.info(" method is /api/findTopicSelectInfoByTopicId.do to customer input parameter message :  {}",json);
		JSONObject result=topiceSelectInfoService.findTopicSelectInfoByTopicId(json);
		log.info(" /api/findTopicSelectInfoByTopicId.do  to result message :  {}",result);
		return result;
	};
}
