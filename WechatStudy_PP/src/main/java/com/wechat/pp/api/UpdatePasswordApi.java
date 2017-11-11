package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.UserInfoService;

import lombok.extern.slf4j.Slf4j;
/**
 * 修改密码接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class UpdatePasswordApi {

	@Resource
	private UserInfoService userInfoService;
	
	@RequestMapping(value="/api/updatePassword.do",method=RequestMethod.POST)
	public JSONObject updatePassword(@RequestBody String json){
		log.info(" method is /api/updatePassword.do to customer input parameter message :  {}",json);
		JSONObject result=userInfoService.updatePassword(json);
		log.info(" /api/updatePassword.do  to result message :  {}",result);
		return result;
	};
}
