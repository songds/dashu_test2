package com.wechat.pp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wechat.pp.po.TopiceSelectInfoPo;

public interface TopicSelectInfoDao extends JpaRepository<TopiceSelectInfoPo, Integer>{

	public List<TopiceSelectInfoPo> findByTopicId(int topicId);
}
