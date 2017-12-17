package com.wechat.pp.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.SectionInfoDao;
import com.wechat.pp.po.SectionInfoPo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SectionInfoService {

	@Resource
	private SectionInfoDao sectionInfoDao;
	
	/**
	 * 添加章节
	 * @param json
	 * @return
	 */
	@Transactional
	public JSONObject saveSectionInfo(String json){
		JSONObject result=new JSONObject();
		try {
			SectionInfoPo sectionInfo=JSONObject.parseObject(json,SectionInfoPo.class);
			if(StringUtils.isEmpty(sectionInfo.getSectionName())){
				result.put("code", "F00001");
				result.put("message", "添加章节失败,参数章节名称不能为空值!");
				return result;
			}else if(StringUtils.isEmpty(sectionInfo.getIsLeafSection())){
				result.put("code", "F00002");
				result.put("message", "添加章节失败,参数是否叶子章节不能为空值!");
				return result;
			}else if(sectionInfo.getParentSectionId()<0){
				result.put("code", "F00005");
				result.put("message", "添加章节失败,参数父章节编号不能为空值!");
				return result;
			}else{
				int sectionId=sectionInfoDao.save(sectionInfo).getSectionId();
				result.put("code", "SUC000");
				result.put("message", "成功");
				result.put("data", sectionId);
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			result.put("code", "F00000");
			result.put("message", "添加章节异常,请您联系系统管理员!");
			return result;
		}
		
		
	}
	
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
