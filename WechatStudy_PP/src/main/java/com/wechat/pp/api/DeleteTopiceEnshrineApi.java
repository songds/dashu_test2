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
 * 删除收藏题目
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class DeleteTopiceEnshrineApi {
	@Resource
	private TopicEnshrineInfoService topicEnshrineInfoService;
	
	@RequestMapping(value="/api/deleteTopiceEnshrine.do",method=RequestMethod.POST)
	public JSONObject deleteTopiceEnshrine(@RequestBody String json){
		log.info(" method is /api/deleteTopiceEnshrine.do to customer input parameter message :  {}",json);
		JSONObject result=topicEnshrineInfoService.deleteTopiceEnshrine(json);
		log.info(" /api/deleteTopiceEnshrine.do  to result message :  {}",result);
		return result;
	};
}
