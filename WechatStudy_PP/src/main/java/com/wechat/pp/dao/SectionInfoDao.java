package com.wechat.pp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.SectionInfoPo;

public interface SectionInfoDao extends JpaRepository<SectionInfoPo, Integer>{

	@Query(value="select T1.* from Section_Info T1 inner join Subject_Section_Rel T2"
			+ " on T1.section_id=T2.section_id and T2.subject_id=?1 ",nativeQuery=true)
	public List<SectionInfoPo> getSectionInfosBySubjectId(int subjectId);
	
	@Query(value="select T1.* from Section_Info T1 inner join curriculum_section_rel T2"
			+ " on T1.section_id=T2.section_id and T2.curriculum_id=?1 ",nativeQuery=true)
	public List<SectionInfoPo> getSectionInfosByCurriculumId(int subjectId);
	
	public List<SectionInfoPo> getSectionInfosByParentSectionId(int parentSectionId);
}
