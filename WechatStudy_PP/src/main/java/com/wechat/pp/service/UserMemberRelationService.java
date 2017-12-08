package com.wechat.pp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.UserMemberRelationDao;
import com.wechat.pp.po.UserMemberRelationPo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserMemberRelationService {
	
	@Resource
	private UserMemberRelationDao userMemberRelationDao;
	
	/**
	 * 购买会员
	 * @param json
	 * @return
	 */
	public JSONObject buyMember(String json){
		JSONObject result=new JSONObject();
		try {
			JSONObject jsonParamter=JSONObject.parseObject(json);
			String userName=jsonParamter.getString("userName");
			int memberId=jsonParamter.getIntValue("memberId");
			String memberType=jsonParamter.getString("memberType");
			UserMemberRelationPo userMemberRelation=userMemberRelationDao.isExistByUserNameAndMemberIdAndMemberType(userName, memberId, memberType);
			if(userMemberRelation!=null){
				result.put("code", "SUC001");
				result.put("message", "您已购买该会员！");
			}else{
				userMemberRelation=new UserMemberRelationPo();
				userMemberRelation.setMemberId(memberId);
				userMemberRelation.setMemberType(memberType);
				userMemberRelation.setUserName(userName);
				userMemberRelationDao.save(userMemberRelation);
				result.put("code", "SUC000");
				result.put("message", "购买会员成功");
				result.put("data", userMemberRelation);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "购买会员异常，请您联系客服！");
		}
		
		return result;
	}
	
	/**
	 * 根据用户查询该用户下的所有会员
	 * @param userName 用户名
	 * @return
	 */
	public JSONObject queryUserToMeber(String userName){
		JSONObject result=new JSONObject();
		try {
			List<UserMemberRelationPo> list=userMemberRelationDao.queryByUserName(userName);
			result.put("code", "SUC000");
			result.put("message", "查询成功！");
			result.put("data", list);
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "会员查询异常，请您联系客服！");
		}
		
		return result;
	}
	
	
	
}
