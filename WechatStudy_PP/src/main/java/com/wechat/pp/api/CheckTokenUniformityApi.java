package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.DeviceInfoService;

import lombok.extern.slf4j.Slf4j;
/**
 * 校验用户token值一致性接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class CheckTokenUniformityApi {

	@Resource
	private DeviceInfoService deviceInfoService;
	
	@RequestMapping(value="/api/checkTokenUniformity.do",method=RequestMethod.POST)
	public JSONObject checkTokenUniformity(@RequestBody String json){
		log.info(" method is /api/updateHeadPortrait.do to customer input parameter message :  {}",json);
		JSONObject result=deviceInfoService.checkTokenUniformity(json);
		log.info(" /api/checkTokenUniformity.do  to result message :  {}",result);
		return result;
	};
}
