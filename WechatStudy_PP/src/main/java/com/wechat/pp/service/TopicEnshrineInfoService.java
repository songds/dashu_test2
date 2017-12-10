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
import com.wechat.pp.dao.TopicEnshrineInfoDao;
import com.wechat.pp.po.TopicEnshrineInfoPo;

@Service
public class TopicEnshrineInfoService {

	@Resource
	private TopicEnshrineInfoDao topicEnshrineInfoDao;
	
	/**
	 * 用户收藏题目
	 * @param json
	 */
	@Transactional
	public JSONObject saveTopicEnshrine(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("userName"))){
			result.put("code", "F00001");
			result.put("message", "用户收藏失败,参数用户名称不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("topicStatus"))){
			result.put("code", "F00002");
			result.put("message", "用户收藏失败,参数题目状态不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("topicId"))){
			result.put("code", "F00003");
			result.put("message", "用户收藏失败,参数题目编号不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("sectionId"))){
			result.put("code", "F00004");
			result.put("message", "用户收藏失败,参数题目编号不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("topicName"))){
			result.put("code", "F00005");
			result.put("message", "用户收藏失败,参数题目名称不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("topicType"))){
			result.put("code", "F00006");
			result.put("message", "用户收藏失败,参数题目内容不能为空值!");
			return result;
		}
		TopicEnshrineInfoPo topicEnshrineInfo=new TopicEnshrineInfoPo();
		topicEnshrineInfo.setTopicId(jsonParameter.getIntValue("topicId"));
		topicEnshrineInfo.setSectionId(jsonParameter.getIntValue("sectionId"));
		topicEnshrineInfo.setTopicName(jsonParameter.getString("topicName"));
		topicEnshrineInfo.setTopiceType(jsonParameter.getString("topiceType"));
		topicEnshrineInfo.setTopicContent(jsonParameter.getString("topicContent"));
		topicEnshrineInfo.setAnliList(jsonParameter.getString("anliList"));
		topicEnshrineInfo.setAnlitxt(jsonParameter.getString("anlitxt"));
		topicEnshrineInfo.setUserName(jsonParameter.getString("userName"));
		topicEnshrineInfo.setCreatedBy(jsonParameter.getString("userName"));
		topicEnshrineInfo.setUpdatedBy(jsonParameter.getString("userName"));
		int id=topicEnshrineInfoDao.save(topicEnshrineInfo).getId();
		result.put("code", "SUC000");
		result.put("message", "成功");
		result.put("data", id);
		return result;
	}
	
	/**
	 * 删除收藏题目
	 * @param json
	 * @return
	 */
	@Transactional
	public JSONObject deleteTopiceEnshrine(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("id"))){
			result.put("code", "F00003");
			result.put("message", "删除收藏题目失败,参数编号不能为空值!");
			result.put("error", jsonParameter);
			return result;
		}else{
			//String userName=jsonParameter.getString("userName");
			int id=jsonParameter.getIntValue("id");
			TopicEnshrineInfoPo topicEnshrineInfo=topicEnshrineInfoDao.getOne(id);
			topicEnshrineInfoDao.delete(topicEnshrineInfo);
			result.put("code", "SUC000");
			result.put("message", "删除收藏题目成功!");
			return result;
		}
	}
	
	
	/**
	 * 批量删除收藏题目
	 * @param json
	 * @return
	 */
	@Transactional
	public JSONObject batchDeleteTopicEnshrine(String json){
		JSONObject info=new JSONObject();
		if(StringUtils.isEmpty(json)){
			List listParameter=JSONObject.parseObject(json, List.class);
			if(listParameter!=null&&listParameter.size()>0){
				List<JSONObject> topicErrorList=new ArrayList<JSONObject>();
				for (int i = 0; i < listParameter.size(); i++) {
					topicErrorList.add(deleteTopiceEnshrine(listParameter.get(i).toString()));
				}
				info.put("code", "SUC000");
				info.put("message", "批量删收藏除题目成功");
				info.put("data", topicErrorList);
				return info;
			}else{
				info.put("code", "SUC001");
				info.put("message", "批量删除收藏题目成功,您没有要删除的记录!");
				return info;
			}
		}else{
			info.put("code", "F00001");
			info.put("message", "批量删除收藏题目失败,参数不能为空!");
			return info;
		}
	}
	
	/**
	 * 我的收藏分页查询
	 * @param json
	 * @return
	 */
	public JSONObject pageTopicEn(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("userName"))){
			result.put("code", "F00001");
			result.put("message", "我的收藏分页查询题目失败,参数用户名称不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("topicId"))){
			result.put("code", "F00002");
			result.put("message", "我的收藏分页查询题目失败,参数题目编号不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("page"))){
			result.put("code", "F00003");
			result.put("message", "我的收藏分页查询题目失败,参数页数不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("size"))){
			result.put("code", "F00004");
			result.put("message", "我的收藏分页查询题目失败,参数每页条数不能为空值!");
			return result;
		}else{
			String userName=jsonParameter.getString("userName");
			int sectionId=jsonParameter.getIntValue("sectionId");
			int page=jsonParameter.getIntValue("page");
			int size=jsonParameter.getIntValue("size");
			Sort sort=new Sort(Direction.ASC, "id");
			Pageable pageable=new PageRequest(page, size,sort);
			Page<TopicEnshrineInfoPo> topicEnshrineInfos=topicEnshrineInfoDao.findByUserNameAndSectionId(userName, sectionId, pageable);
			result.put("code", "SUC000");
			result.put("message", "我的收藏分页查询题目成功!");
			result.put("data", topicEnshrineInfos);
			return result;
		}
	}
}
