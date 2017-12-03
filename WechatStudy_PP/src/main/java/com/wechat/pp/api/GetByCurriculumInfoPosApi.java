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
public class GetByCurriculumInfoPosApi {

	@Resource
	private CurriculumInfoService curriculumInfoService;
	
	@RequestMapping(value="/api/getByCurriculumInfoPos.do",method=RequestMethod.POST)
	public JSONObject getByCurriculumInfoPos(@RequestBody String json){
		log.info(" method is /api/getByCurriculumInfoPos.do to customer input parameter message :  {}",json);
		JSONObject result=curriculumInfoService.getByCurriculumInfoPos(json);
		log.info(" /api/getByCurriculumInfoPos.do  to result message :  {}",result);
		return result;
	};
	
}
