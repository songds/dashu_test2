package com.deshun.springboot.api;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deshun.springboot.Po.SysUser;
import com.deshun.springboot.Util.ResponseInfo;
import com.deshun.springboot.dto.CustomerMessage;
import com.deshun.springboot.service.HttpTestService;
import com.deshun.springboot.service.UserService;

@Controller
@RequestMapping("/html")
public class SysCommonApi {
	
	@Resource
	private UserService userService;

	@RequestMapping("/page/{path}")
	String index(@PathVariable String path){
		return path;
	}
	
	@RequestMapping(value="/saveUser",method=RequestMethod.POST)
    String saveUser(SysUser user){
    	userService.save(user);
    	return "login";
    }
	
	@Resource
	private HttpTestService HttpTestService;
	
	@RequestMapping("/page/httpTest.do")
	@ResponseBody
	String httpTest(String customerName,String mobile,String certId){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("customerName", customerName);
		map.put("mobile", mobile);
		map.put("certId", certId);
		System.out.println(map);
		System.out.println("ssssss");
		return HttpTestService.httpClientTest(JSONObject.toJSONString(map));
	}
							
	@RequestMapping(value="/select/testApi.do",method=RequestMethod.POST)
	@ResponseBody
	ResponseInfo<CustomerMessage> testApi(@RequestBody String json){
		CustomerMessage customerMessage=JSONObject.parseObject(json,CustomerMessage.class);
		ResponseInfo<CustomerMessage> responseInfo=new ResponseInfo<CustomerMessage>();
		responseInfo.setCode("SUC000");
		responseInfo.setMessage("成功");
		responseInfo.setData(customerMessage);
		return responseInfo;
	}
	
}
