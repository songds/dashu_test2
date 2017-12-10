package com.wechat.pp.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.TopicErrorInfoPo;

public interface TopicErrorInfoDao extends JpaRepository<TopicErrorInfoPo, Integer>{

	@Modifying
	@Query("delete from TopicErrorInfoPo where userName=?1 and topiceId=?2")
	public void deleteByUserNameAndTopicId(String userName,int topicId);
	
	public List<TopicErrorInfoPo> getByUserNameAndTopicId(String userName,int topicId);
	
	public Page<TopicErrorInfoPo> findByUserNameAndSectionId(String userName,int sectionId,Pageable pageable);
}
