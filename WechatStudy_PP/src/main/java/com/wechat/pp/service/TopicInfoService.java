package com.wechat.pp.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.TopicInfoDao;
import com.wechat.pp.dao.TopicSelectInfoDao;
import com.wechat.pp.po.TopicInfoPo;

@Service
public class TopicInfoService {

	@Resource
	private TopicInfoDao topicInfoDao;
	
	@Resource
	private TopicSelectInfoDao topicSelectInfoDao;
	
	/**
	 * 根据章节分页查询题目
	 * @param sectionId
	 * @return
	 */
	public JSONObject findTopicInfoBySectionId(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("sectionId"))){
			result.put("code", "F00001");
			result.put("message", "查询题目失败,参数父科目编号不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("userName"))){
			result.put("code", "F00003");
			result.put("message", "查询题目失败,参数用户名不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("page"))){
			result.put("code", "F00004");
			result.put("message", "查询题目失败,参数页数不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("size"))){
			result.put("code", "F00005");
			result.put("message", "查询题目失败,参数每页条数不能为空值!");
			return result;
		}else{
			int sectionId=jsonParameter.getIntValue("sectionId");
			String userName=jsonParameter.getString("userName");
			Sort sort=new Sort(Direction.ASC, "id");
			Pageable pageable=new PageRequest(jsonParameter.getIntValue("page"), jsonParameter.getIntValue("size"),sort);
			Page<TopicInfoPo> topicInfos=topicInfoDao.findBySectionId(sectionId,userName,pageable);
			List<TopicInfoPo> results=new ArrayList<TopicInfoPo>();
			for (TopicInfoPo topicInfoPo : topicInfos) {
				topicInfoPo.setTopiceSelectInfos(topicSelectInfoDao.findByTopicId(topicInfoPo.getTopicId()));
				results.add(topicInfoPo);
			}
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", results);
			return result;
		}
	}
	
	/**
	 * 添加题目
	 * @param topicInfo
	 */
	@Transactional
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
	
	/**
	 * 根据题目标题模糊搜索所用题目接口分页查询
	 * @param json
	 * @return
	 */
	public JSONObject findLikeByTopicName(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("topicName"))){
			result.put("code", "F00001");
			result.put("message", "查询题目失败,参数父科目编号不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("page"))){
			result.put("code", "F00002");
			result.put("message", "查询题目失败,参数页数不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("size"))){
			result.put("code", "F00003");
			result.put("message", "查询题目失败,参数每页条数不能为空值!");
			return result;
		}else{
			String topicName=jsonParameter.getString("topicName");
			Sort sort=new Sort(Direction.ASC, "id");
			Pageable pageable=new PageRequest(jsonParameter.getIntValue("page"), jsonParameter.getIntValue("size"),sort);
			Page<TopicInfoPo> topicInfos=topicInfoDao.findLikeByTopicName("'%"+topicName+"%'",pageable);
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", topicInfos);
			return result;
		}
	}
	/**
	 * 根据题目获取题目选项以及答案
	 * @param json
	 * @return
	 */
	public JSONObject getByTopicId(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("topicId"))){
			result.put("code", "F00001");
			result.put("message", "查询题目失败,参数父科目编号不能为空值!");
			return result;
		}else{
			int topicId=jsonParameter.getIntValue("topicId");
			TopicInfoPo topicInfo=topicInfoDao.getOne(topicId);
			if(topicInfo!=null){
				topicInfo.setTopiceSelectInfos(topicSelectInfoDao.findByTopicId(topicInfo.getTopicId()));
			}
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", topicInfo);
			return result;
		}
	}
	
	/**
	 * 根据章节统计该用户下已做题目
	 * @param json
	 * @return
	 */
	public JSONObject countBySectionIdAndUserName(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("sectionId"))){
			result.put("code", "F00001");
			result.put("message", "统计用户做题情况失败,参数章节编号不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("userName"))){
			result.put("code", "F00002");
			result.put("message", "统计用户做题情况失败,参数用户名称不能为空值!");
			return result;
		}else{
			int sectionId=jsonParameter.getIntValue("sectionId");
			String userName=jsonParameter.getString("userName");
			int count=topicInfoDao.countBySectionIdAndUserName(sectionId, userName);
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", count);
			return result;
		}
	}
}
