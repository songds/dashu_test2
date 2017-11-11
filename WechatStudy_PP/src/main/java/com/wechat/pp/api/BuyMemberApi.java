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
 * 购买会员接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class BuyMemberApi {

	@Resource
	private UserMemberRelationService userMemberRelationService;
	
	@RequestMapping(value="/api/buyMember.do",method=RequestMethod.POST)
	public JSONObject buyMember(@RequestBody String json){
		log.info(" method is /api/buyMember.do to customer input parameter message :  {}",json);
		JSONObject result=userMemberRelationService.buyMember(json);
		log.info(" /api/buyMember.do  to result message :  {}",result);
		return result;
	};
}
