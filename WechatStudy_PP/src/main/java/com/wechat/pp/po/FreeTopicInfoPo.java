package com.wechat.pp.po;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="FREE_TOPIC_INFO")
@EqualsAndHashCode(callSuper=false)
@Data
public class FreeTopicInfoPo extends BasePo{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column (name="ID") 
	private int id;
	
    @Column (name="TOPIC_ID") 
	private int topicId;
	
	
	@Column (name="TOPIC_NAME")
	private String topicName;
	
	@Column (name="TOPIC_CONTENT")
	private String topicContent;
	
	@Column (name="TOPICE_TYPE")
	private String topiceType;
	

	@Column(name="SUBJECT_ID")
	private int subjectId;
	
	@Column (name="ANLITXT",columnDefinition="text")
	private String anlitxt;
	
	@Column (name="ANLILIST",columnDefinition="text")
	private String anliList;
	
	@Column (name="ANSWER_EXP",columnDefinition="text")
	private String answerExp;
	
	@Column (name="IS_CORRECT_SELECT")
	private String isCorrectSelect;
	
	@Transient
	private List<TopiceSelectInfoPo> topiceSelectInfos;
}
