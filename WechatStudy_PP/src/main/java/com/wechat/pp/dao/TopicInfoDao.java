package com.wechat.pp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wechat.pp.po.TopicInfoPo;

public interface TopicInfoDao extends JpaRepository<TopicInfoPo, Integer>{

	public List<TopicInfoPo> findBySectionId(int sectionId);
}
