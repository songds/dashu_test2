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
 * 批量保存做题状态
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class BatchSaveTopicStatusApi {
	@Resource
	private TopicStatusInfoService topicStatusInfoService;
	
	@RequestMapping(value="/api/batchSaveTopicStatus.do",method=RequestMethod.POST)
	public JSONObject batchSaveTopicStatus(@RequestBody String json){
		log.info(" method is /api/batchSaveTopicStatus.do to customer input parameter message :  {}",json);
		JSONObject result=topicStatusInfoService.batchSaveTopicStatus(json);
		log.info(" /api/batchSaveTopicStatus.do  to result message :  {}",result);
		return result;
	};
}
