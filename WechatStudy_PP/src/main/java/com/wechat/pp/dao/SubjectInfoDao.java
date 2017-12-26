package com.wechat.pp.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.SubjectInfoPo;

public interface SubjectInfoDao extends JpaRepository<SubjectInfoPo, Integer>{

	public List<SubjectInfoPo> getByParentSubjectId(int parentSubjectId);
	
	public Page<SubjectInfoPo> findBySubjectNameContaining(String subjectName,Pageable pageable);
	
	@Query(value="from SubjectInfoPo where subjectName like %?1% ")
	public List<SubjectInfoPo> findBySubjectNameLike(String subjectName);
}
