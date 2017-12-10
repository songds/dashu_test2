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
 * 根据章节统计该用户下已做题目
 * @author Administrator
 *
 */
@RestController
@Slf4j
public class CountBySectionIdAndUserNameApi {

	@Resource
	private TopicInfoService topicInfoService;
	
	@RequestMapping(value="/api/countBySectionIdAndUserName.do",method=RequestMethod.POST)
	public JSONObject countBySectionIdAndUserName(@RequestBody String json){
		log.info(" method is /api/countBySectionIdAndUserName.do to customer input parameter message :  {}",json);
		JSONObject result=topicInfoService.countBySectionIdAndUserName(json);
		log.info(" /api/countBySectionIdAndUserName.do  to result message :  {}",result);
		return result;
	};
}
