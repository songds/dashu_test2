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
import com.wechat.pp.dao.TopicErrorInfoDao;
import com.wechat.pp.dao.TopicStatusInfoDao;
import com.wechat.pp.po.TopicErrorInfoPo;
import com.wechat.pp.po.TopicStatusInfoPo;


@Service
public class TopicErrorInfoService {

	@Resource
	private TopicErrorInfoDao topicErrorInfoDao;
	
	@Resource
	private TopicStatusInfoDao topicStatusInfoDao;
	
	/**
	 * 保存错题信息
	 * @param json
	 */
	@Transactional
	public JSONObject saveTopicError(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("userName"))){
			result.put("code", "F00001");
			result.put("message", "保存用户做题状态失败,参数用户名称不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("topicStatus"))){
			result.put("code", "F00002");
			result.put("message", "保存用户做题状态失败,参数题目状态不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("topicId"))){
			result.put("code", "F00003");
			result.put("message", "保存用户做题状态失败,参数题目编号不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("sectionId"))){
			result.put("code", "F00004");
			result.put("message", "保存用户做题状态失败,参数题目编号不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("topicName"))){
			result.put("code", "F00005");
			result.put("message", "保存用户做题状态失败,参数题目名称不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("topicType"))){
			result.put("code", "F00006");
			result.put("message", "保存用户做题状态失败,参数题目类型不能为空值!");
			return result;
		}
		int topicId=jsonParameter.getIntValue("topicId");
		int sectionId=jsonParameter.getIntValue("sectionId");
		String userName=jsonParameter.getString("userName");
		TopicErrorInfoPo topicErrorInfo=topicErrorInfoDao.getByUserNameAndTopicIdAndSectionId(userName, topicId, sectionId);
		if(topicErrorInfo==null){
			topicErrorInfo=new TopicErrorInfoPo();
		}
		topicErrorInfo.setTopicId(jsonParameter.getIntValue("topicId"));
		topicErrorInfo.setSectionId(jsonParameter.getIntValue("sectionId"));
		topicErrorInfo.setTopicName(jsonParameter.getString("topicName"));
		topicErrorInfo.setTopicType(jsonParameter.getString("topicType"));
		topicErrorInfo.setTopicContent(jsonParameter.getString("topicContent"));
		topicErrorInfo.setAnliList(jsonParameter.getString("anliList"));
		topicErrorInfo.setAnlitxt(jsonParameter.getString("anlitxt"));;
		topicErrorInfo.setAnswerExp(jsonParameter.getString("answerExp"));
		topicErrorInfo.setIsCorrectSelect(jsonParameter.getString("isCorrectSelect"));
		topicErrorInfo.setUserName(jsonParameter.getString("userName"));
		int id=topicErrorInfoDao.save(topicErrorInfo).getId();
		result.put("code", "SUC000");
		result.put("message", "成功");
		result.put("data", id);
		return result;
	}
	
	/**
	 * 删除错题
	 * @param json
	 * @return
	 */
	@Transactional
	public JSONObject deleteTopiceError(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("id"))){
			result.put("code", "F00002");
			result.put("message", "删除错题失败,参数错题编号不能为空值!");
			result.put("error", jsonParameter);
			return result;
		}else{
			//String userName=jsonParameter.getString("userName");
			int id=jsonParameter.getIntValue("id");
			//List<TopicErrorInfoPo> topicErrorInfos=topicErrorInfoDao.getByUserNameAndTopicId(userName, topicId);
			TopicErrorInfoPo topicErrorInfo=topicErrorInfoDao.getById(id);
			if(topicErrorInfo!=null){
				TopicStatusInfoPo topicStatusInfo =topicStatusInfoDao.getByUserNameAndTopicId(topicErrorInfo.getUserName(), topicErrorInfo.getTopicId());
				topicErrorInfoDao.delete(topicErrorInfo);
				if(topicStatusInfo!=null){
					topicStatusInfoDao.delete(topicStatusInfo);	
				}
			}
			result.put("code", "SUC000");
			result.put("message", "删除错题成功!");
			return result;
		}
	}
	
	
	/**
	 * 批量删除错题
	 * @param json
	 * @return
	 */
	@Transactional
	public JSONObject batchDeleteTopicError(String json){
		JSONObject info=new JSONObject();
		if(!StringUtils.isEmpty(json)){
			List listParameter=JSONObject.parseObject(json, List.class);
			if(listParameter!=null&&listParameter.size()>0){
				List<JSONObject> topicErrorList=new ArrayList<JSONObject>();
				for (int i = 0; i < listParameter.size(); i++) {
					topicErrorList.add(deleteTopiceError(listParameter.get(i).toString()));
				}
				info.put("code", "SUC000");
				info.put("message", "批量删除成功");
				info.put("data", topicErrorList);
				return info;
			}else{
				info.put("code", "SUC001");
				info.put("message", "批量错题删除成功,您没有要删除的记录!");
				return info;
			}
		}else{
			info.put("code", "F00001");
			info.put("message", "批量错题删除失败,参数不能为空!");
			return info;
		}
	}
	
	/**
	 * 我的错题分页查询
	 * @param json
	 * @return
	 */
	public JSONObject pageTopicErr(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("userName"))){
			result.put("code", "F00001");
			result.put("message", "我的错题分页查询题目失败,参数用户名称不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("sectionId"))){
			result.put("code", "F00002");
			result.put("message", "我的错题分页查询题目失败,参数章节编号不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("page"))){
			result.put("code", "F00003");
			result.put("message", "我的错题分页查询题目失败,参数页数不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("size"))){
			result.put("code", "F00004");
			result.put("message", "我的错题分页查询题目失败,参数每页条数不能为空值!");
			return result;
		}else{
			String userName=jsonParameter.getString("userName");
			int sectionId=jsonParameter.getIntValue("sectionId");
			int page=jsonParameter.getIntValue("page");
			int size=jsonParameter.getIntValue("size");
			Sort sort=new Sort(Direction.ASC, "id");
			Pageable pageable=new PageRequest(page-1, size,sort);
			Page<TopicErrorInfoPo> topicErrorInfos=topicErrorInfoDao.findByUserNameAndSectionId(userName, sectionId, pageable);
			result.put("code", "SUC000");
			result.put("message", "我的错题分页查询题目成功!");
			result.put("data", topicErrorInfos.getContent());
			result.put("total", topicErrorInfos.getTotalElements());
			result.put("page", topicErrorInfos.getTotalPages());
			return result;
		}
	}
}
