package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.PhotoSheetInfoService;

import lombok.extern.slf4j.Slf4j;
/**
 * 统计用户当天拍照次数接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class CountUserNamePhotoNumberApi {

	@Resource
	private PhotoSheetInfoService photoSheetInfoService;
	
	@RequestMapping(value="/api/countUserNamePhotoNumber.do",method=RequestMethod.POST)
	public JSONObject countUserNamePhotoNumber(@RequestBody String json){
		log.info(" method is /api/countUserNamePhotoNumber.do to customer input parameter message :  {}",json);
		JSONObject result=photoSheetInfoService.countUserNamePhotoNumber(json);
		log.info(" /api/countUserNamePhotoNumber.do  to result message :  {}",result);
		return result;
	};
}
