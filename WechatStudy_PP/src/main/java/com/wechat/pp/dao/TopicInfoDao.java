package com.wechat.pp.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.TopicInfoPo;

public interface TopicInfoDao extends JpaRepository<TopicInfoPo, Integer>{

	public List<TopicInfoPo> findBySectionId(int sectionId);
	
	@Query("from TopicInfoPo where topicName like ?1")
	public Page<TopicInfoPo> findLikeByTopicName(String topicName,Pageable pageable);
}
