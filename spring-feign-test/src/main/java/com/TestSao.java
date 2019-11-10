package com;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="TestSaoService",url="https://47.96.163.204",configuration=FeignClientConf.class)
public interface TestSao {

	
		
		@RequestMapping(value="/api/audit/auditFindAll.do",method=RequestMethod.POST,headers={"Coikie={coikie}"})
		String auditFindAll(@RequestBody String json,@RequestHeader("coikie")String coikie);
}
