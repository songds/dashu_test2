package com.wechat.pp.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.TopicErrorInfoDao;
import com.wechat.pp.dao.TopicStatusInfoDao;
import com.wechat.pp.po.TopicErrorInfoPo;
import com.wechat.pp.po.TopicStatusInfoPo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TopicStatusInfoService {

	@Resource
	private TopicStatusInfoDao topicStatusInfoDao;
	
	@Resource
	private TopicErrorInfoDao topicErrorInfoDao;
	
	@Resource
	private TopicErrorInfoService topicErrorInfoService;
	
	/**
	 * 保存做题状态
	 * @param json
	 */
	@Transactional
	public JSONObject saveTopicStatus(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("userName"))){
			result.put("code", "F00001");
			result.put("message", "保存用户做题状态失败,参数用户名称不能为空值!");
			result.put("error", jsonParameter);
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("topicStatus"))){
			result.put("code", "F00002");
			result.put("message", "保存用户做题状态失败,参数题目状态不能为空值!");
			result.put("error", jsonParameter);
			return result;
		}else if(StringUtils.isEmpty(jsonParameter.getString("topicId"))){
			result.put("code", "F00003");
			result.put("message", "保存用户做题状态失败,参数题目编号不能为空值!");
			result.put("error", jsonParameter);
			return result;
		}
		String userName=jsonParameter.getString("userName");
		int topicId=jsonParameter.getIntValue("topicId");
		String topicStatus=jsonParameter.getString("topicStatus");
		TopicStatusInfoPo topicStatusInfo=topicStatusInfoDao.getByUserNameAndTopicId(userName, topicId);
		if(topicStatusInfo==null){
			topicStatusInfo=JSONObject.parseObject(json, TopicStatusInfoPo.class);
		}else{
			topicStatusInfo.setTopicStatus(Integer.parseInt(topicStatus));
		}
		topicStatusInfo.setCreatedBy(userName);
		topicStatusInfo.setUpdatedBy(userName);
		if(topicStatus.equals("1")){
			int id=topicStatusInfoDao.save(topicStatusInfo).getId();
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", id);
			return result;
		}else if(topicStatus.equals("0")){
			if(StringUtils.isEmpty(jsonParameter.getString("sectionId"))){
				result.put("code", "F00005");
				result.put("message", "保存用户做题状态失败,参数题目编号不能为空值!");
				result.put("error", jsonParameter);
				return result;
			}else if(StringUtils.isEmpty(jsonParameter.getString("topicName"))){
				result.put("code", "F00006");
				result.put("message", "保存用户做题状态失败,参数题目名称不能为空值!");
				result.put("error", jsonParameter);
				return result;
			}else if(StringUtils.isEmpty(jsonParameter.getString("topicType"))){
				result.put("code", "F00007");
				result.put("message", "保存用户做题状态失败,参数题目类型不能为空值!");
				result.put("error", jsonParameter);
				return result;
			}
			/*TopicStatusInfoPo topicStatusInfo=new TopicStatusInfoPo();
			topicStatusInfo.setTopicId(jsonParameter.getIntValue("topicId"));
			topicStatusInfo.setTopicStatus(0);
			topicStatusInfo.setUserName(jsonParameter.getString("userName"));
			topicStatusInfo.setCreatedBy(jsonParameter.getString("userName"));
			topicStatusInfo.setUpdatedBy(jsonParameter.getString("userName"));*/
			/*TopicErrorInfoPo topicErrorInfo=new TopicErrorInfoPo();
			topicErrorInfo.setTopicId(jsonParameter.getIntValue("topicId"));
			topicErrorInfo.setSectionId(jsonParameter.getIntValue("sectionId"));
			topicErrorInfo.setTopicName(jsonParameter.getString("topicName"));
			topicErrorInfo.setTopiceType(jsonParameter.getString("topicType"));
			topicErrorInfo.setTopicContent(jsonParameter.getString("topicContent"));
			topicErrorInfo.setAnswerExp(jsonParameter.getString("answerExp"));
			topicErrorInfo.setIsCorrectSelect(jsonParameter.getString("isCorrectSelect"));
			topicErrorInfo.setAnlitxt(jsonParameter.getString("anlitxt"));
			topicErrorInfo.setAnliList(jsonParameter.getString("anliList"));
			topicErrorInfo.setUserName(jsonParameter.getString("userName"));
			topicErrorInfo.setCreatedBy(jsonParameter.getString("userName"));
			topicErrorInfo.setUpdatedBy(jsonParameter.getString("userName"));*/
			JSONObject errResult=topicErrorInfoService.saveTopicError(json);
			log.info(" method saveTopicStatus to topicErrorInfoService-->saveTopicError result is message : {} ",errResult);
			int id=topicStatusInfoDao.save(topicStatusInfo).getId();
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", id);
			return result;
		}else{
			result.put("code", "F00004");
			result.put("message", "保存用户做题状态失败,参数题目状态不存在不能为空值!");
			result.put("error", jsonParameter);
			return result;
		}
	}
	
	
	
	/**
	 * 批量保存做题状态
	 * @param json
	 */
	@Transactional
	public JSONObject batchSaveTopicStatus(String json){
		JSONObject info=new JSONObject();
		List listParameter=JSONObject.parseObject(json,List.class);
		if(listParameter!=null&&listParameter.size()>0){
			List<JSONObject> topicList=new ArrayList<JSONObject>();
			for (int i = 0; i < listParameter.size(); i++) {
				topicList.add(saveTopicStatus(listParameter.get(i).toString()));
			}
			info.put("code", "SUC000");
			info.put("message", "批量处理成功");
			info.put("data", topicList);
			return info;
		}else{
			info.put("code", "F00004");
			info.put("message", "批量保存用户做题状态失败,参数题目状态不存在不能为空值!");
			return info;
		}	
	}
	
	
}
