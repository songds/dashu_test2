package com;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="TestSaoService",url="https://47.96.163.204")
public interface TestSao {

	
		
		@RequestMapping(value="/api/audit/auditFindAll.do",method=RequestMethod.POST)
		String auditFindAll(@RequestBody String json);
}
