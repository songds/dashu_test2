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
 * 记录用户拍照信息接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class SavePhotoInfoPo {

	@Resource
	private PhotoSheetInfoService photoSheetInfoService;
	
	@RequestMapping(value="/api/savePhotoInfo.do",method=RequestMethod.POST)
	public JSONObject savePhotoInfo(@RequestBody String json){
		log.info(" method is /api/savePhotoInfo.do to customer input parameter message :  {}",json);
		JSONObject result=photoSheetInfoService.savePhotoInfo(json);
		log.info(" /api/savePhotoInfo.do  to result message :  {}",result);
		return result;
	};
}
