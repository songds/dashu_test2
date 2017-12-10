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
 * 删除用户错题
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class DeleteTopiceErrorApi {

	@Resource
	private TopicErrorInfoService topicErrorInfoService;
	
	@RequestMapping(value="/api/deleteTopiceError.do",method=RequestMethod.POST)
	public JSONObject deleteTopiceError(@RequestBody String json){
		log.info(" method is /api/deleteTopiceError.do to customer input parameter message :  {}",json);
		JSONObject result=topicErrorInfoService.deleteTopiceError(json);
		log.info(" /api/deleteTopiceError.do  to result message :  {}",result);
		return result;
	};
}
