package com.wechat.pp.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.TopicEnshrineInfoPo;

public interface TopicEnshrineInfoDao extends JpaRepository<TopicEnshrineInfoPo, Integer>{

	@Modifying
	@Query("delete from TopicEnshrineInfoPo where userName=?1 and topiceId=?2")
	public void deleteByUserNameAndTopicId(String userName,int topicId);
	
	public TopicEnshrineInfoPo getByUserNameAndTopicIdAndSectionId(String userName,int topicId,int sectionId);
	
	public List<TopicEnshrineInfoPo> getByUserNameAndTopicId(String userName,int topicId);
	
	public Page<TopicEnshrineInfoPo> findByUserNameAndSectionId(String userName,int sectionId,Pageable pageable);
}
