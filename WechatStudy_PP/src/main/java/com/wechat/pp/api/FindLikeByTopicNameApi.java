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
 * 根据题目标题模糊搜索所用题目接口分页查询
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class FindLikeByTopicNameApi {
	@Resource
	private TopicInfoService topicInfoService;
	
	@RequestMapping(value="/api/findLikeByTopicName.do",method=RequestMethod.POST)
	public JSONObject findLikeByTopicName(@RequestBody String json){
		log.info(" method is /api/findLikeByTopicName.do to customer input parameter message :  {}",json);
		JSONObject result=topicInfoService.findLikeByTopicName(json);
		log.info(" /api/findLikeByTopicName.do  to result message :  {}",result);
		return result;
	};
}
