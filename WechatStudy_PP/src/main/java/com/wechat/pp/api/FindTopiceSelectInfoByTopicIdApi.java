package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.TopiceSelectInfoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class FindTopiceSelectInfoByTopicIdApi {
	@Resource
	private TopiceSelectInfoService topiceSelectInfoService;
	
	@RequestMapping(value="/api/findTopiceSelectInfoByTopicId.do",method=RequestMethod.POST)
	public JSONObject findTopiceSelectInfoByTopicId(@RequestBody String json){
		log.info(" method is /api/findTopiceSelectInfoByTopicId.do to customer input parameter message :  {}",json);
		JSONObject result=topiceSelectInfoService.findTopiceSelectInfoByTopicId(json);
		log.info(" /api/findTopiceSelectInfoByTopicId.do  to result message :  {}",result);
		return result;
	};
}
