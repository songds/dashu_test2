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
@Table(name="SUBJECT_SECTION_REL")
@EqualsAndHashCode(callSuper=false)
@Data
public class SubjectSectionRelPo  extends BasePo{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID") 
	private int id;
	
    @Column (name="SUBJECT_ID") 
	private int subjectId;
    
    @Column (name="SECTION_ID") 
	private int sectionId;
    
}
