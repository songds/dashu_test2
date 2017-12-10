package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.TopicInfoService;

import lombok.extern.slf4j.Slf4j;
/**
 * 根据题目获取题目选项以及答案
 * @author Administrator
 *
 */
@RestController
@Slf4j
public class GetByTopicIdApi {

	@Resource
	private TopicInfoService topicInfoService;
	
	@RequestMapping(value="/api/getByTopicId.do",method=RequestMethod.POST)
	public JSONObject getByTopicId(@RequestBody String json){
		log.info(" method is /api/getByTopicId.do to customer input parameter message :  {}",json);
		JSONObject result=topicInfoService.getByTopicId(json);
		log.info(" /api/getByTopicId.do  to result message :  {}",result);
		return result;
	};
}
