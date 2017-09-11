package com.deshun.springboot.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deshun.springboot.Po.TUser;
import com.deshun.springboot.service.UserService;

@Controller
public class SpringBootApi {
	@Resource
	private UserService userService;
	//设置访问的url
    @RequestMapping("/home")
    //表示返回JSON格式的结果，如果前面使用的是@RestController可以不用写
    @ResponseBody
    List<TUser> home() {
        return userService.findUserAll();//返回结果为字符串
    }
}
