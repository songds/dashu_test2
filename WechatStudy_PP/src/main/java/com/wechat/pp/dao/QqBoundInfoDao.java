package com.wechat.pp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wechat.pp.po.QqBoundInfoPo;

public interface QqBoundInfoDao extends JpaRepository<QqBoundInfoPo, Integer>{

	public QqBoundInfoPo getQQPoByOpenId(String openId);
}
