package com.wechat.pp.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.CurriculumInfoDao;
import com.wechat.pp.po.CurriculumInfoPo;



@Service
public class CurriculumInfoService {

	@Resource
	private CurriculumInfoDao curriculumInfoDao;
	/**
	 * 根据科目id获取课程
	 * @param subjectId
	 * @return
	 */
	public JSONObject getByCurriculumInfoPos(String json){
		JSONObject result=new JSONObject();
		JSONObject jsonParameter=JSONObject.parseObject(json);
		if(StringUtils.isEmpty(jsonParameter.getString("subjectId"))){
			result.put("code", "F00001");
			result.put("message", "查询课程失败,参数科目编号不能为空值!");
			return result;
		}else{
			int subjectId=jsonParameter.getIntValue("subjectId");
			List<CurriculumInfoPo> curriculumInfos=curriculumInfoDao.getBySubjectId(subjectId);
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", curriculumInfos);
			return result;
		}
	}
	
	/**
	 * 添加课程
	 * @param curriculumInfo
	 */
	@Transactional
	public JSONObject saveCurriculumInfo(String json){
		CurriculumInfoPo curriculumInfo=JSONObject.parseObject(json,CurriculumInfoPo.class);
		JSONObject result=new JSONObject();
		if(StringUtils.isEmpty(curriculumInfo.getCurriculumName())){
			result.put("code", "F00001");
			result.put("message", "添加课程失败,参数课程名称不能为空值!");
			return result;
		}else{			
			int curriculumId=curriculumInfoDao.save(curriculumInfo).getCurriculumId();
			result.put("code", "SUC000");
			result.put("message", "成功");
			result.put("data", curriculumId);
			return result;
		}
	}
	
	
}
