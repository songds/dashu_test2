package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.CurriculumInfoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SaveCurriculumInfoApi {

	@Resource
	private CurriculumInfoService curriculumInfoService;
	
	@RequestMapping(value="/api/saveCurriculumInfo.do",method=RequestMethod.POST)
	public JSONObject saveCurriculumInfo(@RequestBody String json){
		log.info(" method is /api/saveCurriculumInfo.do to customer input parameter message :  {}",json);
		JSONObject result=curriculumInfoService.saveCurriculumInfo(json);
		log.info(" /api/saveCurriculumInfo.do  to result message :  {}",result);
		return result;
	};

}
