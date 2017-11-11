package com.wechat.pp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.TradingRecordInfoPo;


public interface TradingRecordInfoDao extends JpaRepository<TradingRecordInfoPo, Integer>{

	@Query("from TradingRecordInfoPo limit ?1,?2")
	public List<TradingRecordInfoPo> queryPage(long startSize,long endSize);
}
