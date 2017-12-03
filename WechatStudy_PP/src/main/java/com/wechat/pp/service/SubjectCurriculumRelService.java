package com.wechat.pp.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.SubjectCurriculumRelDao;
import com.wechat.pp.po.SubjectCurriculumRelPo;

@Service
public class SubjectCurriculumRelService {

	@Resource
	private SubjectCurriculumRelDao subjectCurriculumRelDao;
	 
	/**
	 * 添加科目与课程关系
	 * @param json
	 */
	public JSONObject saveSubjectCurriculumRel(String json){
		JSONObject result=new JSONObject();
		SubjectCurriculumRelPo subjectCurriculumRel=JSONObject.parseObject(json,SubjectCurriculumRelPo.class);
		if(subjectCurriculumRel.getCurriculumId()<=0){
			result.put("code", "F00001");
			result.put("message", "添加科目与课程关系失败,参数课程编号不能为空值!");
			return result;
		}else if(subjectCurriculumRel.getSubjectId()<=0){
			result.put("code", "F00001");
			result.put("message", "添加科目与课程关系失败,参数科目编号不能为空值!");
			return result;
		}else{
			subjectCurriculumRelDao.save(subjectCurriculumRel);
			result.put("code", "SUC000");
			result.put("message", "成功");
			return result;
		}
		
	}
}
