package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.SubjectCurriculumRelService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SaveSubjectCurriculumRelApi {

	@Resource
	private SubjectCurriculumRelService subjectCurriculumRelService;
	
	@RequestMapping(value="/api/SaveSubjectCurriculumRel.do",method=RequestMethod.POST)
	public JSONObject saveSubjectCurriculumRel(@RequestBody String json){
		log.info(" method is /api/saveSubjectCurriculumRel.do to customer input parameter message :  {}",json);
		JSONObject result=subjectCurriculumRelService.saveSubjectCurriculumRel(json);
		log.info(" /api/saveSubjectCurriculumRel.do  to result message :  {}",result);
		return result;
	};
}
