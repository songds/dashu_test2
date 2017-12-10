package com.wechat.pp.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.TopicInfoPo;

public interface TopicInfoDao extends JpaRepository<TopicInfoPo, Integer>{

	@Query(value="SELECT T1.* FROM topic_info T1"
			+ " left join topic_status_info T2 on T1.topic_id = T2.topic_id and T2.user_name=?2 "
			+ " where T1.section_Id=?1 and T2.id is null ",nativeQuery=true)
	public Page<TopicInfoPo> findBySectionId(int sectionId,String userName,Pageable pageable);
	
	@Query("from TopicInfoPo where topicName like ?1")
	public Page<TopicInfoPo> findLikeByTopicName(String topicName,Pageable pageable);
	
	@Query(value="SELECT count(T1.topic_id) FROM topic_info T1"
			+ " inner join topic_status_info T2 on T1.topic_id = T2.topic_id and T2.user_name=?2"
			+ " where T1.section_Id=?1 ",nativeQuery=true)
	public int countBySectionIdAndUserName(int sectionId,String userName);
	
	
}
