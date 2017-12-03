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
public class GetSectionInfosByCurriculumIdApi {

	@Resource
	private SectionInfoService sectionInfoService;

	@RequestMapping(value="/api/getSectionInfosByCurriculumId.do",method=RequestMethod.POST)
	public JSONObject getSectionInfosByCurriculumId(@RequestBody String json){
		log.info(" method is /api/getSectionInfosByCurriculumId.do to customer input parameter message :  {}",json);
		JSONObject result=sectionInfoService.getSectionInfosByCurriculumId(json);
		log.info(" /api/getSectionInfosByCurriculumId.do  to result message :  {}",result);
		return result;
	};
}
