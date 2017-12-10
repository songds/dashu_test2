package com.wechat.pp.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.MemberPriceInfoDao;
import com.wechat.pp.po.MemberPriceInfoPo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberPriceInfoService {

	@Resource
	private MemberPriceInfoDao memberPriceInfoDao;
	
	/**
	 * 获取会员价格
	 * {memberId/会员id}
	 * @param json
	 * @return
	 */
	public JSONObject getMemberPriceInfo(String json){
		JSONObject result=new JSONObject();
		try {
			JSONObject jsonParameter=JSONObject.parseObject(json);
			int memberId=jsonParameter.getIntValue("memberId");
			List<MemberPriceInfoPo> data=memberPriceInfoDao.getMemberPriceByMemberId(memberId);
			if(data.size()>0){
				result.put("code", "SUC000");
				result.put("message", "查询成功");
				result.put("data", data);
				return result;
			}else{
				result.put("code", "SUC001");
				result.put("message", "查询成功,未查询到数据！");
				result.put("data", new ArrayList<MemberPriceInfoPo>());
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "查询异常，请您联系客服！");
			return result;
		}
	}
}
