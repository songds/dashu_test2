package com.wechat.pp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.UserLookTopicPo;

public interface UserLookTopicDao extends JpaRepository<UserLookTopicPo, Integer>{

	@Query("select count(id) from UserLookTopicPo where userName=?1")
	public int countByUserName(String userName);
}
