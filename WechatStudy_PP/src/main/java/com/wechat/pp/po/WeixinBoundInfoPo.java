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
@Table(name="WEIXIN_BOUND_INFO")
@Data
@EqualsAndHashCode(callSuper=false)
public class WeixinBoundInfoPo extends BasePo{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column (name="ID",length=11) 
	private int id;
	
	@Column (name="weixin_id")
	private String weixinId;
	
	@Column (name="USER_NAME")
	private String userName;
	
	@Column (name="weixin_name")
	private String weixinName;
	
	@Column (name="weixin_image_url")
	private String weixinImageUrl;
	
	@Column (name="OPEN_ID")
	private String openId;
	
	@Column (name="UNION_ID")
	private String unionId;
	
}
