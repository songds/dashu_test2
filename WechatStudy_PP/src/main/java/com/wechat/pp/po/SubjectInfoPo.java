package com.wechat.pp.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
}
