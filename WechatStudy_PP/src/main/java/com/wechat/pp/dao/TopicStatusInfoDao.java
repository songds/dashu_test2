package com.wechat.pp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.TopicStatusInfoPo;

public interface TopicStatusInfoDao extends JpaRepository<TopicStatusInfoPo, Integer>{

	@Modifying
	@Query("delete from TopicStatusInfoPo where userName=?1 and topiceId=?2")
	public void deleteByUserNameAndTopicId(String userName,int topicId);
	
	public TopicStatusInfoPo getByUserNameAndTopicId(String userName,int topicId);
	
}
