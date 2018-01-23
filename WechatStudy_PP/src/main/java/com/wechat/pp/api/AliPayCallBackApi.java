package com.wechat.pp.api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wechat.pp.service.AliPayService;

import lombok.extern.slf4j.Slf4j;

/**
 * 支付宝回调接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class AliPayCallBackApi {
	@Resource
	private AliPayService aliPayService;
	
	@RequestMapping(value="/api/pay/aliPayCallBack.do",method=RequestMethod.POST)
	public String aliPayCallBack(HttpServletRequest request){
		log.info(" load /api/pay/aliPayCallBack.do ..... ");
		String result=aliPayService.aliPayCallBack(request);
		log.info(" /api/pay/aliPayCallBack.do  to result message :  {}",result);
		return result;
	};
}
