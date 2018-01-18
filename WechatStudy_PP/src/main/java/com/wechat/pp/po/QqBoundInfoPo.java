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
@Table(name="QQ_BOUND_INFO")
@Data
@EqualsAndHashCode(callSuper=false)
public class QqBoundInfoPo extends BasePo{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID",length=11) 
	private int id;
	
	@Column (name="qq_id")
	private String qqId;
	
	@Column (name="USER_NAME")
	private String userName;
	
	@Column (name="QQ_NAME")
	private String qqName;
	
	@Column (name="QQ_IMAGE_URL")
	private String qqImageUrl;
	
	@Column (name="OPEN_ID")
	private String openId;
	
	@Column (name="UNION_ID")
	private String unionId;
	
	
}
