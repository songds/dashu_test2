package com.wechat.pp.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.FreeTopicInfoDao;
import com.wechat.pp.dao.TopicSelectInfoDao;
import com.wechat.pp.po.FreeTopicInfoPo;

@Service
public class FreeTopicInfoService {

	@Resource
	private FreeTopicInfoDao freeTopicInfoDao;
	
	@Resource
	private TopicSelectInfoDao topicSelectInfoDao;
	
	/**
	 * 免费练习题目查询
	 * @param json
	 * @return
	 */
	public JSONObject findFreeTopicAll(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("subjectId"))){
			result.put("code", "F00001");
			result.put("message", "免费练习题查询失败,参数科目编号不能为空值!");
			return result;
		}
		int subjectId=jsonParameter.getIntValue("subjectId");
		List<FreeTopicInfoPo> results=new ArrayList<FreeTopicInfoPo>();
		List<FreeTopicInfoPo> freeTopicInfos=freeTopicInfoDao.findBySubjectId(subjectId);
		for (FreeTopicInfoPo freeTopicInfoPo : freeTopicInfos) {
			freeTopicInfoPo.setTopiceSelectInfos(topicSelectInfoDao.findByTopicId(freeTopicInfoPo.getTopicId()));
			results.add(freeTopicInfoPo);
		}
		result.put("code", "SUC000");
		result.put("message", "成功");
		result.put("data", results);
		return result;
	}
}
