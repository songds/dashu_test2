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
@Table(name="MEMBER_INFO")
@Data
@EqualsAndHashCode(callSuper=false)
public class MemberInfoPo extends BasePo{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID",length=11) 
	private int id;
	
	@Column (name="MEMBER_NAME") 
	private String memberName;
	

}
