package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.UserMemberRelationService;

import lombok.extern.slf4j.Slf4j;
/**
 * 根据用户查询该用户下的所有会员接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class QueryUserToMeberApi {

	@Resource
	private UserMemberRelationService userMemberRelationService;
	
	@RequestMapping(value="/api/queryUserToMeber.do",method=RequestMethod.POST)
	public JSONObject queryUserToMeber(@RequestBody String json){
		log.info(" method is /api/queryUserToMeber.do to customer input parameter message :  {}",json);
		JSONObject result=userMemberRelationService.queryUserToMeber(json);
		log.info(" /api/queryUserToMeber.do  to result message :  {}",result);
		return result;
	};
}
