package com.wechat.pp.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.TradingRecordInfoDao;
import com.wechat.pp.po.TradingRecordInfoPo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TradingRecordInfoService {

	@Resource
	private TradingRecordInfoDao tradingRecordInfoDao;
	/**
	 * 添加用户交易记录
	 * @param json json格式字符串
	 * {userName/用户名,tradingStatus/交易状态,tradingTime/交易时间,tradingType/交易类型,tradingId/交易编号}
	 * @return
	 */
	public JSONObject saveTradingRecord(String json){
		JSONObject result=new JSONObject();
		try{
			JSONObject jsonParameter=JSONObject.parseObject(json);
			String userName=jsonParameter.getString("userName");
			String tradingStatus=jsonParameter.getString("tradingStatus");
			Date tradingTime=jsonParameter.getDate("tradingTime");
			String tradingType=jsonParameter.getString("tradingType");
			String tradingId=jsonParameter.getString("tradingId");
			TradingRecordInfoPo tradingRecordInfo=new TradingRecordInfoPo();
			tradingRecordInfo.setTradingId(tradingId);
			tradingRecordInfo.setTradingStatus(tradingStatus);
			tradingRecordInfo.setTradingTime(tradingTime);
			tradingRecordInfo.setTradingType(tradingType);
			tradingRecordInfo.setUserName(userName);
			tradingRecordInfoDao.save(tradingRecordInfo);
			result.put("code", "SUS000");
			result.put("message", "交易记录添加成功！");
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "交易记录添加异常，请您联系客服！");
		}
		return result;
	};
	
	/**
	 * 用户交易记录分页查询
	 * @param json 
	 * {startSize/开始条数,endSize/结束条数}
	 * @return
	 */
	public JSONObject getTradingRecord(String json){
		JSONObject result=new JSONObject();
		try{
			JSONObject jsonParameter=JSONObject.parseObject(json);
			int startSize=jsonParameter.getIntValue("startSize");
			int endSize=jsonParameter.getIntValue("endSize");
			Pageable pageable=new PageRequest(startSize, endSize);
			Page<TradingRecordInfoPo> list=tradingRecordInfoDao.findAll(pageable);
			result.put("code", "SUS000");
			result.put("message", "交易记录查询成功");
			result.put("data", list);
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "交易记录查询异常，请您联系客服！");
		}
		return result;
	}
	
	
}
