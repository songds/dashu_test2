package com.wechat.pp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wechat.pp.po.FreeTopicInfoPo;

public interface FreeTopicInfoDao extends JpaRepository<FreeTopicInfoPo, Integer>{

	public List<FreeTopicInfoPo> findBySubjectId(int subjectId);
}
