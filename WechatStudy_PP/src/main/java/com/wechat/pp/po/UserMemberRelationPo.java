package com.wechat.pp.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="USER_MEMBER_RELATION")
@EqualsAndHashCode(callSuper=false)
@Data
public class UserMemberRelationPo extends BasePo{

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
	private int id;
	
	@Column(name="USER_NAME")
	private String userName;
	@Column(name="MEMBER_ID")
	private int memberId;
	@Column(name="MEMBER_TYPE")
	private String memberType;
	
	@Column(name="VALID_START_TIME")
	private Date validStartTime;
	
	@Column(name="VALID_END_TIME")
	private Date validEndTime;
	
}
