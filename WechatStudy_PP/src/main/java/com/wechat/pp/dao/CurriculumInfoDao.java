package com.wechat.pp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.CurriculumInfoPo;

public interface CurriculumInfoDao extends JpaRepository<CurriculumInfoPo, Integer>{

	@Query(value="select * from Curriculum_Info T1 inner join Subject_Curriculum_Rel T2"
			+ " on T1.curriculum_id=T2.curriculum_id and T2.subject_id=?1 ",nativeQuery=true)
	public List<CurriculumInfoPo> getBySubjectId(int subjectId);
}
