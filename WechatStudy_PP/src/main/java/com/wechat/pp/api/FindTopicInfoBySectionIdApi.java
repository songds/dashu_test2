package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.TopicInfoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class FindTopicInfoBySectionIdApi {

	@Resource
	private TopicInfoService topicInfoService;
	
	@RequestMapping(value="/api/findTopicInfoBySectionId.do",method=RequestMethod.POST)
	public JSONObject findTopicInfoBySectionId(@RequestBody String json){
		log.info(" method is /api/findTopicInfoBySectionId.do to customer input parameter message :  {}",json);
		JSONObject result=topicInfoService.findTopicInfoBySectionId(json);
		log.info(" /api/findTopicInfoBySectionId.do  to result message :  {}",result);
		return result;
	};
}
