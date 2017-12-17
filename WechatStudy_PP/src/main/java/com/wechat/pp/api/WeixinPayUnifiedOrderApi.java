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
 * 微信统一下单接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class WeixinPayUnifiedOrderApi {

	@Resource
	private WeixinPayService weixinPayService;
	
	@RequestMapping(value="/api/pay/unifiedOrder.do",method=RequestMethod.POST)
	public JSONObject payUnifiedOrder(@RequestBody String json){
		log.info(" method is /api/pay/unifiedOrder.do to customer input parameter message :  {}",json);
		JSONObject result=weixinPayService.payUnifiedOrder(json);
		log.info(" /api/pay/unifiedOrder.do  to result message :  {}",result);
		return result;
	};
}
