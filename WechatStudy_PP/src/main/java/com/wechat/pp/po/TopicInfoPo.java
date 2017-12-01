package com.wechat.pp.po;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="TOPIC_INFO")
@EqualsAndHashCode(callSuper=false)
@Data
public class TopicInfoPo  extends BasePo{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="TOPIC_ID") 
	private int topicId;
	
	@Column (name="TOPIC_NAME")
	private String topicName;
	
	@Column (name="TOPIC_CONTENT")
	private String topicContent;
	
	@Column (name="TOPICE_TYPE")
	private String topiceType;
	
	@OneToMany(targetEntity=TopiceSelectInfoPo.class)
	@JoinColumn(name="TOPIC_ID",referencedColumnName="TOPIC_ID")
	private List<TopiceSelectInfoPo> topiceSelectInfoPos;
	
	
	
}
