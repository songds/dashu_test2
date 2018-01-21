package com.wechat.pp.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			String tradingType=jsonParameter.getString("tradingType");
			String tradingId=jsonParameter.getString("tradingId");
			TradingRecordInfoPo tradingRecordInfo=new TradingRecordInfoPo();
			tradingRecordInfo.setTradingId(tradingId);
			tradingRecordInfo.setTradingStatus(tradingStatus);
			tradingRecordInfo.setTradingTime(sdf.format(tradingTime));
			tradingRecordInfo.setTradingType(tradingType);
			tradingRecordInfo.setUserName(userName);
			tradingRecordInfoDao.save(tradingRecordInfo);
			result.put("code", "SUC000");
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
	 * {userName/用户名,page/页数,size/每页条数}
	 * @return
	 */
	public JSONObject getTradingRecord(String json){
		JSONObject result=new JSONObject();
		try{
			JSONObject jsonParameter=JSONObject.parseObject(json);
			String userName=jsonParameter.getString("userName");
			int page=jsonParameter.getIntValue("page");
			int size=jsonParameter.getIntValue("size");
			Sort sort=new Sort(Direction.DESC,"id");
			Pageable pageable=new PageRequest(page-1, size,sort);
			Page<TradingRecordInfoPo> list=tradingRecordInfoDao.findByUserName(userName,pageable);
			result.put("code", "SUC000");
			result.put("message", "交易记录查询成功");
			result.put("data", list.getContent());
			result.put("total", list.getTotalElements());
			result.put("page", list.getTotalPages());
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "交易记录查询异常，请您联系客服！");
		}
		return result;
	}
	
	
}
