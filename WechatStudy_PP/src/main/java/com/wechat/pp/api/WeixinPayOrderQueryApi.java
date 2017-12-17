package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.WeixinPayService;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信回调接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class WeixinPayOrderQueryApi {

	@Resource
	private WeixinPayService weixinPayService;
	
	@RequestMapping(value="/api/pay/orderQuery.do",method=RequestMethod.POST)
	public JSONObject orderQuery(@RequestBody String json){
		log.info(" method is /api/pay/orderQuery.do to customer input parameter message :  {}",json);
		JSONObject result=weixinPayService.payOrderQuery(json);
		log.info(" /api/pay/orderQuery.do  to result message :  {}",result);
		return result;
	};
}
