package com.wechat.pp.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.SectionInfoDao;
import com.wechat.pp.po.SectionInfoPo;

@Service
public class SectionInfoService {

	@Resource
	private SectionInfoDao sectionInfoDao;
	
	/**
	 * 根据科目获取章节
	 * @param subjectId
	 * @return
	 */
	public JSONObject  getSectionInfosBySubjectId(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("subjectId"))){
			result.put("code", "F00001");
			result.put("message", "查询章节失败,参数科目编号不能为空值!");
			return result;
		}else{
			int subjectId=jsonParameter.getIntValue("subjectId");
			List<SectionInfoPo> sectionInfos=sectionInfoDao.getSectionInfosBySubjectId(subjectId);
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", sectionInfos);
			return result;
		}
	}
	
	/**
	 * 根据课程编号获取章节
	 * @param subjectId
	 * @return
	 */
	public JSONObject getSectionInfosByCurriculumId(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("curriculumId"))){
			result.put("code", "F00001");
			result.put("message", "查询章节失败,参数课程编号不能为空值!");
			return result;
		}else{
			int curriculumId=jsonParameter.getIntValue("curriculumId");
			List<SectionInfoPo> sectionInfos=sectionInfoDao.getSectionInfosByCurriculumId(curriculumId);
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", sectionInfos);
			return result;
		}
	}
	
	/**
	 * 根据父章节获取子章节
	 * @param subjectId
	 * @return
	 */
	public JSONObject getSectionInfosByParentSectionId(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("parentSectionId"))){
			result.put("code", "F00001");
			result.put("message", "查询章节失败,参数父章节编号不能为空值!");
			return result;
		}else{
			int parentSectionId=jsonParameter.getIntValue("parentSectionId");
			List<SectionInfoPo> sectionInfos=sectionInfoDao.getSectionInfosByParentSectionId(parentSectionId);
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", sectionInfos);
			return result;
		}
	}
	
	
}
