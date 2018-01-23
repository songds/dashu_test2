package com.wechat.pp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wechat.pp.po.AlibabaPayInfoPo;

public interface AlibabaPayInfoDao extends JpaRepository<AlibabaPayInfoPo, Integer>{

	public AlibabaPayInfoPo getByOutTradeNo(String outTradeNo);
}
