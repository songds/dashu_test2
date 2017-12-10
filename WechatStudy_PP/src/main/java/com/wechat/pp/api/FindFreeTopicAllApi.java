package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.FreeTopicInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * 免费练习题目查询
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class FindFreeTopicAllApi {

	@Resource
	private FreeTopicInfoService freeTopicInfoService;
	
	@RequestMapping(value="/api/findFreeTopicAll.do",method=RequestMethod.POST)
	public JSONObject findFreeTopicAll(@RequestBody String json){
		log.info(" method is /api/findFreeTopicAll.do to customer input parameter message :  {}",json);
		JSONObject result=freeTopicInfoService.findFreeTopicAll(json);
		log.info(" /api/findFreeTopicAll.do  to result message :  {}",result);
		return result;
	};
}
