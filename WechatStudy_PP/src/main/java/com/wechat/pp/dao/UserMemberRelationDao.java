package com.wechat.pp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wechat.pp.po.UserMemberRelationPo;

public interface UserMemberRelationDao extends JpaRepository<UserMemberRelationPo, Integer>{

	public UserMemberRelationPo isExistByUserNameAndMemberIdAndMemberType(String userName,int memberId,String memberType);
	
	public List<UserMemberRelationPo> queryByUserName(String userName);
}
