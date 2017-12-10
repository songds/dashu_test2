package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.TopicErrorInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * 我的错题分页查询
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class PageTopicErrApi {

	@Resource
	private TopicErrorInfoService topicErrorInfoService;
	
	@RequestMapping(value="/api/pageTopicErr.do",method=RequestMethod.POST)
	public JSONObject pageTopicErr(@RequestBody String json){
		log.info(" method is /api/pageTopicErr.do to customer input parameter message :  {}",json);
		JSONObject result=topicErrorInfoService.pageTopicErr(json);
		log.info(" /api/pageTopicErr.do  to result message :  {}",result);
		return result;
	};
}
