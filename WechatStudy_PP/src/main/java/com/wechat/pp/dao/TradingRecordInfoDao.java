package com.wechat.pp.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wechat.pp.po.TradingRecordInfoPo;


public interface TradingRecordInfoDao extends JpaRepository<TradingRecordInfoPo, Integer>{

	public Page<TradingRecordInfoPo> findAll(Pageable pageable);
}
