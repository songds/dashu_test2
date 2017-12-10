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
@Table(name="TOPIC_STATUS_INFO")
@EqualsAndHashCode(callSuper=false)
@Data
public class TopicStatusInfoPo extends BasePo{

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
	 * 题目状态
	 */
	@Column (name="TOPIC_STATUS") 
	private int topicStatus;
}
