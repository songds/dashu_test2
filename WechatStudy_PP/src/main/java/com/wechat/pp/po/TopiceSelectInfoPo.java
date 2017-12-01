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
@Table(name="TOPICE_SELECT_INFO")
@EqualsAndHashCode(callSuper=false)
@Data
public class TopiceSelectInfoPo  extends BasePo{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID") 
	private int id;
	
	@Column (name="TOPIC_ID") 
	private int topicId;
	
	@Column (name="SELECT_ID")
	private String selectId;
	
	@Column (name="SELECT_CONTENT")
	private String selectContent;
	
	@Column (name="IS_CORRECT_SELECT")
	private String isCorrectSelect;
	
}
