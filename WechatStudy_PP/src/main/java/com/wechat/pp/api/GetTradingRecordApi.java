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
 * 用户交易记录分页查询接口
 * @author ex-songdeshun
 *
 */
@RestController
@Slf4j
public class GetTradingRecordApi {

	@Resource
	private TradingRecordInfoService tradingRecordInfoService;
	
	@RequestMapping(value="/api/getTradingRecord.do",method=RequestMethod.POST)
	public JSONObject getTradingRecord(@RequestBody String json){
		log.info(" method is /api/getTradingRecord.do to customer input parameter message :  {}",json);
		JSONObject result=tradingRecordInfoService.getTradingRecord(json);
		log.info(" /api/getTradingRecord.do  to result message :  {}",result);
		return result;
	};
}

