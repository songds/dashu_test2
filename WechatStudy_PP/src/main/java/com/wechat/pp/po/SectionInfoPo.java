package com.wechat.pp.po;

import java.util.List;

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
@Table(name="SECTION_INFO")
@EqualsAndHashCode(callSuper=false)
@Data
public class SectionInfoPo extends BasePo{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="SECTION_ID") 
	private int sectionId;
	
	@Column (name="SECTION_NAME") 
	private String sectionName;
	
	@Column (name="PARENT_SECTION_ID") 
	private int parentSectionId;
	
	@Column (name="IS_LEAF_SECTION") 
	private String isLeafSection;
	
	
	@ManyToMany(targetEntity=TopicInfoPo.class)
	@JoinTable(name="SECTION_TOPIC_REL",joinColumns={
			@JoinColumn(name="SECTION_ID",referencedColumnName="SECTION_ID")
	},inverseJoinColumns={
			@JoinColumn(name="TOPIC_ID",referencedColumnName="TOPIC_ID")
	})
	private List<TopicInfoPo> topicInfoPos;
	
	
	
}
