package com.wechat.pp.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

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
	 * cardType {月卡/M,季卡/Q,年卡/Y}
	 * @return
	 */
	@Transactional
	public JSONObject buyMember(String json){
		JSONObject result=new JSONObject();
		try {
			JSONObject jsonParamter=JSONObject.parseObject(json);
			String userName=jsonParamter.getString("userName");
			int memberId=jsonParamter.getIntValue("memberId");
			String memberType=jsonParamter.getString("memberType");
			String cardType=jsonParamter.getString("cardType");
			UserMemberRelationPo userMemberRelation=userMemberRelationDao.isExistByUserNameAndMemberIdAndMemberType(userName, memberId, memberType);
			if(userMemberRelation!=null){
				Date currentTime=new Date(System.currentTimeMillis());
				if(currentTime.getTime()<userMemberRelation.getValidEndTime().getTime()){
					userMemberRelation.setValidEndTime(createDate(userMemberRelation.getValidEndTime(), cardType));
				}else{
					userMemberRelation.setValidEndTime(createDate(currentTime, cardType));
				}
				userMemberRelationDao.save(userMemberRelation);
				result.put("code", "SUC000");
				result.put("message", "会员升级成功");
				result.put("data", userMemberRelation);
			}else{
				userMemberRelation=new UserMemberRelationPo();
				userMemberRelation.setMemberId(memberId);
				userMemberRelation.setMemberType(memberType);
				userMemberRelation.setUserName(userName);
				Date currentTime=new Date(System.currentTimeMillis());
				userMemberRelation.setValidStartTime(currentTime);
				userMemberRelation.setValidEndTime(createDate(currentTime, cardType));
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
	 * 根据卡类型创建有效时间
	 * @param currentTime
	 * @param cardType
	 * @return
	 */
	public Date createDate(Date currentTime,String cardType){
		Calendar calendar=Calendar.getInstance();
		if(cardType.equals("Q")){
			calendar.add(Calendar.MONTH, 3);
			Date validEndTime=calendar.getTime();
			return validEndTime;
		}else if(cardType.equals("Y")){
			calendar.add(Calendar.YEAR, 1);
			Date validEndTime=calendar.getTime();
			return validEndTime;
		}else{
			calendar.add(Calendar.MONTH, 1);
			Date validEndTime=calendar.getTime();
			return validEndTime;
		}
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
