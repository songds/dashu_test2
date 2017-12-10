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
 * 我的收藏分页查询
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class PageTopicEnApi {

	@Resource
	private TopicEnshrineInfoService topicEnshrineInfoService;
	
	@RequestMapping(value="/api/pageTopicEn.do",method=RequestMethod.POST)
	public JSONObject pageTopicEn(@RequestBody String json){
		log.info(" method is /api/pageTopicEn.do to customer input parameter message :  {}",json);
		JSONObject result=topicEnshrineInfoService.pageTopicEn(json);
		log.info(" /api/pageTopicEn.do  to result message :  {}",result);
		return result;
	};
}
