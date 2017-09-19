package com.deshun.springboot.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@FeignClient(value="MicroExpressionService",url="${zuul.routes.MicroExpressionService.url}")
public interface HttpTestService {

	@RequestMapping(value = "/api/getCustomerInfoList.do", method = RequestMethod.POST)
	String httpClientTest(String json);
	
	
	@RequestMapping(value = "/api/getCustomerInfoList.do", method = RequestMethod.POST)
	public String getCustomerInfoList(@RequestBody String json);
	
	
	@RequestMapping(value = "/api/getCustomerInfoDetails.do", method = RequestMethod.POST)
	public String getCustomerInfoDetails(@RequestBody String json);
	
	@RequestMapping(value="/api/createDSPMProfile.do",method=RequestMethod.POST)
	public String createDSPMProfile(@RequestBody String json);
	
	@RequestMapping(value="/api/getDSPMStatus.do",method=RequestMethod.POST)
	public String getDSPMStatus(@RequestBody String json);
	
	@RequestMapping(value="/api/getDSPMDebitInfo.do",method=RequestMethod.POST)
	public String getDSPMDebitInfo(@RequestBody String json);
	
	@RequestMapping(value="/api/getDSPMRepaymentInfo.do",method=RequestMethod.POST)
	public String getDSPMRepaymentInfo(@RequestBody String json);
	
	@RequestMapping(value = "/api/getAccountListInfo.do", method = RequestMethod.POST)
	public String getAccountListInfo(@RequestBody String json);
	
	@RequestMapping(value = "/api/getProductinfo.do", method = RequestMethod.POST)
	public String getProductinfo(@RequestBody String json);
	
	
	
	/**
	 * 证件类型查询接口
	 * @return
	 */
	@RequestMapping(value = "/api/CertTypeInfo.do", method = RequestMethod.POST)
	public String CertTypeInfo();
	/**
	 * 地区编号查询接口
	 * @return
	 */
	@RequestMapping(value = "/api/InputOrgInfo.do", method = RequestMethod.POST)
	public String InputOrgInfo();
	/**
	 * 意向合作机构查询接口
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/api/PartnerInfo.do", method = RequestMethod.POST)
	public String PartnerInfo(@RequestBody String json);
	/**
	 * 授权原因查询接口
	 * @return
	 */
	@RequestMapping(value = "/api/AuthorReason.do", method = RequestMethod.POST)
	public String AuthorReason();
	/**
	 * 客户类型查询接口
	 * @return
	 */
	@RequestMapping(value = "/api/EmployeeType.do", method = RequestMethod.POST)
	public String EmployeeType();
	/**
	 * 获取省份信息接口
	 * @return
	 */
	@RequestMapping(value = "/api/PronviceInformation.do", method = RequestMethod.POST)
	public String PronviceInformation();
	/**
	 * 获取市区信息接口
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/api/CityInformation.do", method = RequestMethod.POST)
	public String CityInformation(@RequestBody String json);
	/**
	 * 获取区县信息接口
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/api/FocountyInformation.do", method = RequestMethod.POST)
	public String FocountyInformation(@RequestBody String json);
	
	/**
	 * 渠道名称信息接口
	 * @return
	 */
	@RequestMapping(value = "/api/ChannelInfo.do", method = RequestMethod.POST)
	public String ChannelInfo();
	/**
	 * 归属业务助理查询接口
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/api/SalesAssistantInfo.do", method = RequestMethod.POST)
	public String SalesAssistantInfo(@RequestBody String json);
	/**
	 * 个贷服务中心查询接口
	 * @return
	 */
	@RequestMapping(value = "/api/SignAddressInfo.do", method = RequestMethod.POST)
	public String SignAddressInfo();
	/**
	 * 归属客户经理查询接口
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/api/salerManagerInfo.do", method = RequestMethod.POST)
	public String salerManagerInfo(@RequestBody String json) throws Exception;
	
}
