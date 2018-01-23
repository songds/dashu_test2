package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.AliPayService;

import lombok.extern.slf4j.Slf4j;

/**
 * 支付宝支付接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class AlibabaPayApi {
	@Resource
	private AliPayService aliPayService;
	
	@RequestMapping(value="/api/pay/alibabaPay.do",method=RequestMethod.POST)
	public JSONObject alibabaPay(@RequestBody String json){
		log.info(" method is /api/pay/alibabaPay.do to customer input parameter message :  {}",json);
		JSONObject result=aliPayService.alibabaPay(json);
		log.info(" /api/pay/alibabaPay.do  to result message :  {}",result);
		return result;
	};
}
