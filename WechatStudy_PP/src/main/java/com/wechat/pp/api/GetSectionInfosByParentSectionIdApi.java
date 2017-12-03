package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.SectionInfoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GetSectionInfosByParentSectionIdApi {

	@Resource
	private SectionInfoService sectionInfoService;

	@RequestMapping(value="/api/getSectionInfosByParentSectionId.do",method=RequestMethod.POST)
	public JSONObject getSectionInfosByParentSectionId(@RequestBody String json){
		log.info(" method is /api/getSectionInfosByParentSectionId.do to customer input parameter message :  {}",json);
		JSONObject result=sectionInfoService.getSectionInfosByParentSectionId(json);
		log.info(" /api/getSectionInfosByParentSectionId.do  to result message :  {}",result);
		return result;
	};
}
