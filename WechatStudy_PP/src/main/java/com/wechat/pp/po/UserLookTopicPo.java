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
@Table(name="USER_LOOK_TOPIC")
@Data
@EqualsAndHashCode(callSuper=false)
public class UserLookTopicPo extends BasePo{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID",length=11) 
	private int id;
	
	@Column (name="USER_NAME")
	private String userName;
	
	@Column (name="TOPIC_ID") 
	private int topicId;
}
