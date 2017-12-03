package com.wechat.pp.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.SubjectInfoDao;
import com.wechat.pp.po.SubjectInfoPo;

@Service
public class SubjectInfoService {

	@Resource
	private SubjectInfoDao subjectInfoDao;
	/**
	 * 科目添加接口
	 * @param subjectInfo
	 */
	public JSONObject saveSubjectInfo(String json){
		JSONObject result=new JSONObject();
		SubjectInfoPo subjectInfo=JSONObject.parseObject(json,SubjectInfoPo.class);
		if(StringUtils.isEmpty(subjectInfo.getSubjectName())){
			result.put("code", "F00001");
			result.put("message", "添加科目失败,参数科目名称不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(subjectInfo.getSubjectNameLetter())){
			result.put("code", "F00002");
			result.put("message", "添加科目失败,参数科目名称对应字母不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(subjectInfo.getIsLeafSubject())){
			result.put("code", "F00003");
			result.put("message", "添加科目失败,参数是否叶子科目不能为空值!");
			return result;
		}else if(StringUtils.isEmpty(subjectInfo.getSubjectType())){
			result.put("code", "F00004");
			result.put("message", "添加科目失败,参数科目类型不能为空值!");
			return result;
		}else if(subjectInfo.getParentSubjectId()<0){
			result.put("code", "F00005");
			result.put("message", "添加科目失败,参数父科目编号不能为空值!");
			return result;
		}else{
			int subjectId=subjectInfoDao.save(subjectInfo).getSubjectId();
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", subjectId);
			return result;
		}
		
	}
	
	/**
	 * 查询所有科目
	 * @return
	 */
	public List<SubjectInfoPo> findPage(){
		return subjectInfoDao.findAll();
	}
	
	/**
	 * 根据上级科目获取子科目数据
	 * @param parentSubjectId
	 * @return
	 */
	
	public JSONObject getByParentSubjectId(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("parentSubjectId"))){
			result.put("code", "F00001");
			result.put("message", "查询子科目失败,参数父科目编号不能为空值!");
			return result;
		}else{
			int parentSubjectId=jsonParameter.getIntValue("parentSubjectId");
			List<SubjectInfoPo> SubjectInfos=subjectInfoDao.getByParentSubjectId(parentSubjectId);
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", SubjectInfos);
			return result;
		}
	}
	
	
	
}
