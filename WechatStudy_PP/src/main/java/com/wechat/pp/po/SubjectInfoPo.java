package com.wechat.pp.po;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="SUBJECT_INFO")
@EqualsAndHashCode(callSuper=false)
@Data
public class SubjectInfoPo  extends BasePo{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="SUBJECT_ID") 
	private int subjectId;
	
	@Column (name="SUBJECT_NAME") 
	private String subjectName;
	
	@Column (name="SUBJECT_TYPE") 
	private String subjectType;
	
	@Column (name="IS_LEAF_SUBJECT") 
	private String isLeafSubject;
	
	@Column (name="PARENT_SUBJECT_ID") 
	private int parentSubjectId;
	
	@Column (name="SUBJECT_NAME_LETTER") 
	private String subjectNameLetter;
	
	@ManyToMany(targetEntity=CurriculumInfoPo.class)
	@JoinTable(name="SUBJECT_CURRICULUM_REL",joinColumns={
			@JoinColumn(name="SUBJECT_ID",referencedColumnName="SUBJECT_ID")
	},inverseJoinColumns={
			@JoinColumn(name="CURRICULUM_ID",referencedColumnName="CURRICULUM_ID")
	})
	private List<CurriculumInfoPo> curriculumInfoPos;
	
	
	@ManyToMany(targetEntity=SectionInfoPo.class)
	@JoinTable(name="SUBJECT_SECTION_REL",joinColumns={
			@JoinColumn(name="SUBJECT_ID",referencedColumnName="SUBJECT_ID")
	},inverseJoinColumns={
			@JoinColumn(name="SECTION_ID",referencedColumnName="SECTION_ID")
	})
	private List<SectionInfoPo> sectionInfoPos;
	
	
}
