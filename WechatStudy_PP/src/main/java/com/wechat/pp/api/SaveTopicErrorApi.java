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
 * 保存用户错题信息
 * @author Administrator
 *
 */
@RestController
@Slf4j
public class SaveTopicErrorApi {

	@Resource
	private TopicErrorInfoService topicErrorInfoService;
	
	@RequestMapping(value="/api/saveTopicError.do",method=RequestMethod.POST)
	public JSONObject saveTopicError(@RequestBody String json){
		log.info(" method is /api/saveTopicError.do to customer input parameter message :  {}",json);
		JSONObject result=topicErrorInfoService.saveTopicError(json);
		log.info(" /api/saveTopicError.do  to result message :  {}",result);
		return result;
	};
}
