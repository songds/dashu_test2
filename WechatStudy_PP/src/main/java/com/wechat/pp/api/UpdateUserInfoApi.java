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
 * 修改用户基本信息接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class UpdateUserInfoApi {

	@Resource
	private UserInfoService userInfoService;
	
	@RequestMapping(value="/api/updateUserInfo.do",method=RequestMethod.POST)
	public JSONObject updateUserInfo(@RequestBody String json){
		log.info(" method is /api/updateUserInfo.do to customer input parameter message :  {}",json);
		JSONObject result=userInfoService.updateUserInfo(json);
		log.info(" /api/updateUserInfo.do  to result message :  {}",result);
		return result;
	};
}
