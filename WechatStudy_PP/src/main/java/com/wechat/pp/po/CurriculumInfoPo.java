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
@Table(name="CURRICULUM_INFO")
@EqualsAndHashCode(callSuper=false)
@Data
public class CurriculumInfoPo  extends BasePo{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="CURRICULUM_ID") 
	private int curriculumId;
	
	@Column(name="CURRICULUM_NAME")
	private String curriculumName;
	
}
