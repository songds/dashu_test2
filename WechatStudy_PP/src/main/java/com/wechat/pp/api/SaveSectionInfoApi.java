package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.SectionInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * 添加章节
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class SaveSectionInfoApi {

	@Resource
	private SectionInfoService sectionInfoService;

	@RequestMapping(value="/api/saveSectionInfo.do",method=RequestMethod.POST)
	public JSONObject saveSectionInfo(@RequestBody String json){
		log.info(" method is /api/saveSectionInfo.do to customer input parameter message :  {}",json);
		JSONObject result=sectionInfoService.saveSectionInfo(json);
		log.info(" /api/saveSectionInfo.do  to result message :  {}",result);
		return result;
	};
}
