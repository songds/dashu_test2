package com.wechat.pp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wechat.pp.dao.TradingRecordInfoDao;

@Service
public class TradingRecordInfoService {

	@Resource
	private TradingRecordInfoDao tradingRecordInfoDao;
}
