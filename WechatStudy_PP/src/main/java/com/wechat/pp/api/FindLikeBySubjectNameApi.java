package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.SubjectInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * 根据科目名称模糊搜索科目分页查询
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class FindLikeBySubjectNameApi {

	@Resource
	private SubjectInfoService subjectInfoService;
	
	@RequestMapping(value="/api/findLikeBySubjectName.do",method=RequestMethod.POST)
	public JSONObject findLikeBySubjectName(@RequestBody String json){
		log.info(" method is /api/findLikeBySubjectName.do to customer input parameter message :  {}",json);
		JSONObject result=subjectInfoService.findLikeBySubjectName(json);
		log.info(" /api/findLikeBySubjectName.do  to result message :  {}",result);
		return result;
	};
}
