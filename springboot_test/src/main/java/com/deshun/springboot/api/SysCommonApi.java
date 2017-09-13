package com.deshun.springboot.api;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deshun.springboot.Po.SysUser;
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
}
