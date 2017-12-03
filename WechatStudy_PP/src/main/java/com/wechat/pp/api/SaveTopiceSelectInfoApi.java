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
public class SaveTopiceSelectInfoApi {

	@Resource
	private TopiceSelectInfoService topiceSelectInfoService;
	
	@RequestMapping(value="/api/saveTopiceSelectInfo.do",method=RequestMethod.POST)
	public JSONObject saveTopiceSelectInfo(@RequestBody String json){
		log.info(" method is /api/saveTopiceSelectInfo.do to customer input parameter message :  {}",json);
		JSONObject result=topiceSelectInfoService.saveTopiceSelectInfo(json);
		log.info(" /api/saveTopiceSelectInfo.do  to result message :  {}",result);
		return result;
	};
}
