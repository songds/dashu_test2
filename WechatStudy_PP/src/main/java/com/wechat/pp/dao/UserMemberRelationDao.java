package com.wechat.pp.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wechat.pp.po.UserMemberRelationPo;

public interface UserMemberRelationDao extends JpaRepository<UserMemberRelationPo, Integer>{

	@Query("from UserMemberRelationPo where userName=?1 and memberId=?2 and memberType=?3")
	public UserMemberRelationPo isExistByUserNameAndMemberIdAndMemberType(String userName,int memberId,String memberType);
	
	@Query("from UserMemberRelationPo where userName=?1 and memberId=?2 and memberType=?3 and ?4<validEndTime")
	public UserMemberRelationPo isExistByUserNameAndMemberIdAndMemberTypeAndValidEndTime(String userName,int memberId,String memberType,Date validEndTime);
	
	public List<UserMemberRelationPo> queryByUserName(String userName);
}
