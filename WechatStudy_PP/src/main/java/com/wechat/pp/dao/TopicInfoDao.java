package com.wechat.pp.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.TopicInfoPo;

public interface TopicInfoDao extends JpaRepository<TopicInfoPo, Integer>{

	@Query(value="SELECT T1.* FROM topic_info T1"
			+ " left join topic_status_info T2 on T1.topic_id = T2.topic_id and T2.user_name=?2 "
			+ " where T1.section_Id=?1 and T2.id is null order by T1.topic_id limit ?3,?4 ",nativeQuery=true)
	//public Page<TopicInfoPo> findBySectionId(int sectionId,String userName,Pageable pageable);
	public List<TopicInfoPo> findBySectionId(int sectionId,String userName,int startSize,int pageSize);
	
	@Query(value="SELECT T1.* FROM topic_info T1"
			+ " left join topic_status_info T2 on T1.topic_id = T2.topic_id and T2.user_name=?2 "
			+ " where T1.section_Id=?1 and T1.topice_type=?3 and T2.id is null order by T1.topic_id limit ?4,?5 ",nativeQuery=true)
	//public Page<TopicInfoPo> findBySectionId(int sectionId,String userName,Pageable pageable);
	public List<TopicInfoPo> findBySectionId(int sectionId,String userName,String topicType,int startSize,int pageSize);
	
	@Query(value="SELECT count(T1.topic_id) FROM topic_info T1"
					+ " left join topic_status_info T2 on T1.topic_id = T2.topic_id and T2.user_name=?2 "
					+ " where T1.section_Id=?1 and T2.id is null ",nativeQuery=true)
	public int countByUserNameNotTopic(int sectionId,String userName);
	
	public Page<TopicInfoPo> findByTopicNameContaining(String topicName,Pageable pageable);
	
	
	@Query(value="SELECT count(T1.topic_id) FROM topic_info T1"
			+ " inner join topic_status_info T2 on T1.topic_id = T2.topic_id and T2.user_name=?2"
			+ " where T1.section_Id=?1 ",nativeQuery=true)
	public int countBySectionIdAndUserName(int sectionId,String userName);
	
	
}
