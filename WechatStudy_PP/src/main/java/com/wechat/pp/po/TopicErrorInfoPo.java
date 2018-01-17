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
@Table(name="TOPIC_ERROR_INFO")
@EqualsAndHashCode(callSuper=false)
@Data
public class TopicErrorInfoPo extends BasePo{

	
	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="id") 
	private int id;
	
	/**
	 * 用户名
	 */
	@Column (name="USER_NAME") 
	private String userName;
	
	/**
	 * 题目编号
	 */
	@Column (name="TOPIC_ID") 
	private int topicId;
	/**
	 * 章节编号
	 */
	@Column (name="SECTION_ID") 
	private int sectionId;
	
	/**
	 * 题目名称
	 */
	@Column (name="TOPIC_NAME") 
	private String topicName;
	
	/**
	 * 题目内容
	 */
	@Column (name="TOPIC_CONTENT") 
	private String topicContent;
	
	/**
	 * 题目类型
	 */
	@Column (name="TOPIC_TYPE") 
	private String topicType;
	
	@Column (name="ANLITXT",columnDefinition="text")
	private String anlitxt;
	
	@Column (name="ANLILIST",columnDefinition="text")
	private String anliList;
	
	@Column (name="ANSWER_EXP",columnDefinition="text")
	private String answerExp;
	
	@Column (name="IS_CORRECT_SELECT")
	private String isCorrectSelect;
}
