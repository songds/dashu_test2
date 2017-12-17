package com.wechat.pp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wechat.pp.po.WeixinPayInfoPo;

public interface WeixinPayInfoDao extends JpaRepository<WeixinPayInfoPo, Integer>{

	public WeixinPayInfoPo getByOutTradeNo(String outTradeNo);
}
