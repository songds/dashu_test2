package com.wechat.pp.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.service.TradingRecordInfoService;

import lombok.extern.slf4j.Slf4j;
/**
 * 添加用户交易记录接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class SaveTradingRecordApi {

	@Resource
	private TradingRecordInfoService tradingRecordInfoService;
	
	@RequestMapping(value="/api/saveTradingRecord.do",method=RequestMethod.POST)
	public JSONObject saveTradingRecord(@RequestBody String json){
		log.info(" method is /api/saveTradingRecord.do to customer input parameter message :  {}",json);
		JSONObject result=tradingRecordInfoService.saveTradingRecord(json);
		log.info(" /api/saveTradingRecord.do  to result message :  {}",result);
		return result;
	};
}
