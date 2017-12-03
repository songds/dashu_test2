package com.wechat.pp.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.wechat.pp.po.SubjectInfoPo;

public interface SubjectInfoDao extends JpaRepository<SubjectInfoPo, Integer>{

	public List<SubjectInfoPo> getByParentSubjectId(int parentSubjectId);
}
