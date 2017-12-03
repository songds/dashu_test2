package com.wechat.pp.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.TopicInfoDao;
import com.wechat.pp.po.TopicInfoPo;

@Service
public class TopicInfoService {

	@Resource
	private TopicInfoDao topicInfoDao;
	
	/**
	 * 根据章节获取题目
	 * @param sectionId
	 * @return
	 */
	public JSONObject findTopicInfoBySectionId(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("sectionId"))){
			result.put("code", "F00001");
			result.put("message", "查询子科目失败,参数父科目编号不能为空值!");
			return result;
		}else{
			int sectionId=jsonParameter.getIntValue("sectionId");
			List<TopicInfoPo> topicInfos=topicInfoDao.findBySectionId(sectionId);
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", topicInfos);
			return result;
		}
	}
	
	/**
	 * 添加题目
	 * @param topicInfo
	 */
	public JSONObject saveTopicInfo(String json){
		JSONObject result=new JSONObject();
		TopicInfoPo topicInfo=JSONObject.parseObject(json,TopicInfoPo.class);
		if(StringUtils.isEmpty(topicInfo.getTopicName())){
			result.put("code", "F00001");
			result.put("message", "添加题目失败,参数题目内容不能为空值!");
			return result;
		}else if(topicInfo.getSectionId()<=0){
			result.put("code", "F00002");
			result.put("message", "添加题目失败,参数章节编号不能为空值!");
			return result;
		}
		if(StringUtils.isEmpty(topicInfo.getTopiceType())){
			result.put("code", "F00003");
			result.put("message", "添加题目失败,参数题目类型不能为空值!");
			return result;
		}else{
			if(!topicInfo.getTopiceType().equals("1")&&
			!topicInfo.getTopiceType().equals("2")&&
			!topicInfo.getTopiceType().equals("3")&&
			!topicInfo.getTopiceType().equals("5")){
				if(StringUtils.isEmpty(topicInfo.getTopicContent())){
					result.put("code", "F00003");
					result.put("message", "添加题目失败,参数题目内容不能为空值!");
					return result;
				}
			}
		}
		
		int topicId=topicInfoDao.save(topicInfo).getTopicId();
		result.put("code", "SUC000");
		result.put("message", "成功");
		result.put("data", topicId);
		return result;
	}
	
	
}
