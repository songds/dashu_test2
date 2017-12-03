package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.SubjectInfoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SaveSubjectInfoApi {

	@Resource
	private SubjectInfoService subjectInfoService;
	
	@RequestMapping(value="/api/saveSubjectInfo.do",method=RequestMethod.POST)
	public JSONObject saveSubjectInfo(@RequestBody String json){
		log.info(" method is /api/saveSubjectInfo.do to customer input parameter message :  {}",json);
		JSONObject result=subjectInfoService.saveSubjectInfo(json);
		log.info(" /api/saveSubjectInfo.do  to result message :  {}",result);
		return result;
	};
}
