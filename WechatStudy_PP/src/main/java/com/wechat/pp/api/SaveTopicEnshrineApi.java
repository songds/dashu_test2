package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.TopicEnshrineInfoService;

import lombok.extern.slf4j.Slf4j;
/**
 * 用户收藏题目
 * @author Administrator
 *
 */
@RestController
@Slf4j
public class SaveTopicEnshrineApi {

	@Resource
	private TopicEnshrineInfoService topicEnshrineInfoService;
	
	@RequestMapping(value="/api/saveTopicEnshrine.do",method=RequestMethod.POST)
	public JSONObject saveTopicEnshrine(@RequestBody String json){
		log.info(" method is /api/saveTopicEnshrine.do to customer input parameter message :  {}",json);
		JSONObject result=topicEnshrineInfoService.saveTopicEnshrine(json);
		log.info(" /api/saveTopicEnshrine.do  to result message :  {}",result);
		return result;
	};
}
