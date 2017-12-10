package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.TopicStatusInfoService;

import lombok.extern.slf4j.Slf4j;
/**
 * 保存用户做题状态
 * @author Administrator
 *
 */
@RestController
@Slf4j
public class SaveTopicStatusApi {

	@Resource
	private TopicStatusInfoService topicStatusInfoService;
	
	@RequestMapping(value="/api/saveTopicStatus.do",method=RequestMethod.POST)
	public JSONObject saveTopicStatus(@RequestBody String json){
		log.info(" method is /api/saveTopicStatus.do to customer input parameter message :  {}",json);
		JSONObject result=topicStatusInfoService.saveTopicStatus(json);
		log.info(" /api/saveTopicStatus.do  to result message :  {}",result);
		return result;
	};
}
