package com.deshun.springboot.api;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deshun.springboot.service.HttpTestService;


@RestController
public class DSPMTestAPI {
	
	@Resource
	private HttpTestService httpTestService;
	
	/** 客户信息查询列表接口--用于客户信息列表获取，至少设置一个传入参数。
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/api/getCustomerInfoList.do",method=RequestMethod.GET)
	public String getCustomerInfoList(){
		Map<String, String> map=new HashMap<>();
		map.put("mobile","13560249210");
		map.put("customerName", null);
		map.put("certId", null);
		return httpTestService.getCustomerInfoList(JSONObject.toJSONString(map));
	}
	
	
	/**
	 * 客户信息查询详情接口---获取指定客户的详细信息
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/api/getCustomerInfoDetails.do",method=RequestMethod.GET)
	public String getCustomerInfoDetails(){
		Map<String, String> map=new HashMap<>();
		map.put("certId", "411324198704140513");
		return httpTestService.getCustomerInfoDetails(JSONObject.toJSONString(map));
	}
	
	
	/**
	 * 预约建档接口--信贷系统中建档。
	 */
	@RequestMapping(value="/api/createDSPMProfile.do",method=RequestMethod.GET)
	public String createDSPMProfile(){
		Map<String, String> map=new HashMap<>();
		map.put("crmApplyNo", "RL20150828000080");
		map.put("customerName", "汤九");
		map.put("mobile", "13000009781");
		map.put("authRreason", "010");
		map.put("inputOrgId", "100100");
		map.put("certType", "Ind01");
		map.put("certNo", "110102199603042870");
		map.put("customerType", "020");
		map.put("lineId", "RL20150105000002");
		map.put("partnerName", "廊坊银行");
		map.put("signAddressCode", "100001");
		map.put("createdBy", "FANYU");
		map.put("createdDate", new Timestamp(System.currentTimeMillis())+"");
		map.put("regChannel", "019036");
		map.put("salesAssistantNo", "0030025");
		map.put("salesAssistantId", "WUSHANSHAN");
		map.put("accountManagerID", "LIJINGMEI");
		map.put("accountManagerNO", "0190002");
		map.put("accountManagerPhone", "18612817183");
		map.put("accountManagerOrg", "019001");
		map.put("workAddress", "440000");
		map.put("residentialAddress", "440000市");
		return httpTestService.createDSPMProfile(JSONObject.toJSONString(map));
	}
	
	/**
	 * 状态同步接口--获取信贷系统中的业务状态信息。
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/api/getDSPMStatus.do",method=RequestMethod.GET)
	public String getDSPMStatus(){
		Map<String, String> map=new HashMap<>();
		map.put("dspmApplyNo", "RL20170729000736");
		Map<String, String> map2=new HashMap<>();
		map2.put("dspmApplyNo", "RL20170729000734");
		Map<String, String> map3=new HashMap<>();
		map3.put("dspmApplyNo", "RL20160104000214");
		Map<String, String> map4=new HashMap<>();
		map4.put("dspmApplyNo", "RL20141231000009");
		Map<String, String> map5=new HashMap<>();
		map5.put("dspmApplyNo", "RL20161117000074");
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		list.add(map);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		Map<String, Object>	listMap=new HashMap<String, Object>();
		listMap.put("dspmApplyNoList", list);
		listMap.put("dspmApplyNo", "RL20180516000280");
		System.out.println(JSONArray.toJSONString(listMap));
		return httpTestService.getDSPMStatus(JSONArray.toJSONString(listMap));
	}
	
	/**
	 * 信贷信息查询接口--通过信贷系统编号查询信贷信息(已申请的)
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/api/getDSPMDebitInfo.do",method=RequestMethod.GET)
	public String getDSPMDebitInfo(){
		Map<String, String> map=new HashMap<>();
		map.put("dspmApplyNo", null);
		return httpTestService.getDSPMDebitInfo(JSONObject.toJSONString(map));
	}
	
	/**
	 * 还款信息查询接口--查询还款信息(放款成功)
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/api/getDSPMRepaymentInfo.do",method=RequestMethod.GET)
	public String getDSPMRepaymentInfo(){
		Map<String, String> map=new HashMap<>();
		map.put("customerName", null);
		map.put("certNo", null);
		map.put("customerId", null);
		return httpTestService.getDSPMRepaymentInfo(JSONObject.toJSONString(map));
	}
	
	

	/**
	 * 1.9台账信息同步接口--查询台账信息(已申请)
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/api/getAccountListInfo.do",method=RequestMethod.GET)
	public String getAccountListInfo(){
		Map<String, String> map=new HashMap<>();
		map.put("customerName", null);
		map.put("certNo", null);
		map.put("customerId", null);
		return httpTestService.getAccountListInfo(JSONObject.toJSONString(map));
	}
	/**
	 * 1.10产品信息接口 --查询产品信息(已申请)
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/api/getProductinfo.do",method=RequestMethod.GET)
	public String getProductinfo(){
		Map<String, String> map=new HashMap<>();
		map.put("certNo", null);
		return httpTestService.getProductinfo(JSONObject.toJSONString(map));
	}
	
	
	
	/**
	 * 证件类型查询接口
	 * @return
	 */
	@RequestMapping(value = "/api/CertTypeInfo.do", method = RequestMethod.GET)
	public String CertTypeInfo(){
		return httpTestService.CertTypeInfo();
	};
	/**
	 * 地区编号查询接口
	 * @return
	 */
	@RequestMapping(value = "/api/InputOrgInfo.do", method = RequestMethod.GET)
	public String InputOrgInfo(){
		return httpTestService.InputOrgInfo();
	};
	/**
	 * 意向合作机构查询接口
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/api/PartnerInfo.do", method = RequestMethod.GET)
	public String PartnerInfo(){
		Map<String, String> map=new HashMap<>();
		map.put("orgid", "100100");
		return httpTestService.PartnerInfo(JSONObject.toJSONString(map));
	};
	/**
	 * 授权原因查询接口
	 * @return
	 */
	@RequestMapping(value = "/api/AuthorReason.do", method = RequestMethod.GET)
	public String AuthorReason(){
		return httpTestService.AuthorReason();
	};
	/**
	 * 客户类型查询接口
	 * @return
	 */
	@RequestMapping(value = "/api/EmployeeType.do", method = RequestMethod.GET)
	public String EmployeeType(){
		return httpTestService.EmployeeType();
	};
	/**
	 * 获取省份信息接口
	 * @return
	 */
	@RequestMapping(value = "/api/PronviceInformation.do", method = RequestMethod.GET)
	public String PronviceInformation(){
		return httpTestService.PronviceInformation();
	};
	/**
	 * 获取市区信息接口
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/api/CityInformation.do", method = RequestMethod.GET)
	public String CityInformation(){
		Map<String, String> map=new HashMap<>();
		map.put("pronviceno", "10");
		return httpTestService.CityInformation(JSONObject.toJSONString(map));
	};
	/**
	 * 获取区县信息接口
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/api/FocountyInformation.do", method = RequestMethod.GET)
	public String FocountyInformation(){
		Map<String, String> map=new HashMap<>();
		map.put("cityno", "1000");
		return httpTestService.FocountyInformation(JSONObject.toJSONString(map));
	};
	
	/**
	 * 渠道名称信息接口
	 * @return
	 */
	@RequestMapping(value = "/api/ChannelInfo.do", method = RequestMethod.GET)
	public String ChannelInfo(){
		return httpTestService.ChannelInfo();
	};
	/**
	 * 归属业务助理查询接口
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/api/SalesAssistantInfo.do", method = RequestMethod.GET)
	public String SalesAssistantInfo(){
		Map<String, String> map=new HashMap<>();
		map.put("assistantid", null);
		map.put("assistantname", null);
		return httpTestService.SalesAssistantInfo(JSONObject.toJSONString(map));
	};
	/**
	 * 个贷服务中心查询接口
	 * @return
	 */
	@RequestMapping(value = "/api/SignAddressInfo.do", method = RequestMethod.GET)
	public String SignAddressInfo(){
		return httpTestService.SignAddressInfo();
	};
	/**
	 * 归属客户经理查询接口
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/api/salerManagerInfo.do", method = RequestMethod.GET)
	public String salerManagerInfo(){
		try {
			Map<String, String> map=new HashMap<>();
			map.put("managerid", null);
			map.put("managername", null);
			return httpTestService.salerManagerInfo(JSONObject.toJSONString(map));
		} catch (Exception e) {
			e.printStackTrace();
			return "报错了。。。请联系管理员";
		}
		
	};
}
