package com.wechat.pp.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.TopicSelectInfoDao;
import com.wechat.pp.po.TopiceSelectInfoPo;

@Service
public class TopicSelectInfoService {

	@Resource
	private TopicSelectInfoDao topiceSelectInfoDao;
	/**
	 * 根据题目编号获取选项
	 * @param topicId
	 * @return
	 */
	public JSONObject  findTopicSelectInfoByTopicId(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("topicId"))){
			result.put("code", "F00001");
			result.put("message", "查询题目选项失败,参数题目编号不能为空值!");
			return result;
		}else{
			int topicId=jsonParameter.getIntValue("topicId");
			List<TopiceSelectInfoPo> topiceSelectInfos=topiceSelectInfoDao.findByTopicId(topicId);
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", topiceSelectInfos);
			return result;
		}
	}
	
	/**
	 * 添加题目选项
	 * @param topiceSelectInfo
	 */
	@Transactional
	public JSONObject saveTopiceSelectInfo(String json){
		JSONObject result=new JSONObject();
		TopiceSelectInfoPo topiceSelectInfo=JSONObject.parseObject(json,TopiceSelectInfoPo.class);
		if(StringUtils.isEmpty(topiceSelectInfo.getSelectContent())){
			result.put("code", "F00001");
			result.put("message", "添加题目选项失败,参数题目选项内容不能为空值!");
			return result;
		}else if(topiceSelectInfo.getTopicId()<=0){
			result.put("code", "F00002");
			result.put("message", "添加题目选项失败,参数题目编号不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(topiceSelectInfo.getIsCorrectSelect())){
			result.put("code", "F00003");
			result.put("message", "添加题目选项失败,参数是否正确选项不能为空值!");
			return result;
		}else{
			int id=topiceSelectInfoDao.save(topiceSelectInfo).getId();
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", id);
			return result;
		}
		
	}
}
