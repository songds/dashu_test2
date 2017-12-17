package com.wechat.pp.api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wechat.pp.service.WeixinPayService;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信回调接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class WeixinPayCallBackNotifyUrlApi {

	@Resource
	private WeixinPayService weixinPayService;
	
	@RequestMapping(value="/api/pay/callBackNotifyUrl.do",method=RequestMethod.POST)
	public String callBackNotifyUrl(HttpServletRequest request){
		log.info(" load /api/pay/callBackNotifyUrl.do ..... ");
		String result=weixinPayService.payCallBackNotifyUrl(request);
		log.info(" /api/pay/callBackNotifyUrl.do  to result message :  {}",result);
		return result;
	};
}
