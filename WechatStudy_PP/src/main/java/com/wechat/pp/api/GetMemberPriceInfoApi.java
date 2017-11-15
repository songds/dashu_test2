package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.MemberPriceInfoService;

import lombok.extern.slf4j.Slf4j;
/**
 * 获取会员价格接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class GetMemberPriceInfoApi {

	@Resource
	private MemberPriceInfoService memberPriceInfoService;
	
	@RequestMapping(value="/api/getMemberPriceInfo.do",method=RequestMethod.POST)
	public JSONObject getMemberPriceInfo(@RequestBody String json){
		log.info(" method is /api/getMemberPriceInfo.do to customer input parameter message :  {}",json);
		JSONObject result=memberPriceInfoService.getMemberPriceInfo(json);
		log.info(" /api/getMemberPriceInfo.do  to result message :  {}",result);
		return result;
	};
}
