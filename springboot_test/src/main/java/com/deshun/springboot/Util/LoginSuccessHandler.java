package com.deshun.springboot.Util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.deshun.springboot.Po.SysUser;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//获得授权后可得到用户信息  可使用SUserService进行数据库操作
		SysUser userDetails=(SysUser) authentication.getPrincipal();
		//输出登录提示信息
		System.out.println("管理员 " +userDetails.getName()+"登录");
		System.out.println("IP : "+getIpAddress(request));
		super.onAuthenticationSuccess(request, response, authentication);
		
	}

	public String getIpAddress(HttpServletRequest request){
		String ip=request.getHeader("x-forwaded-for");
		return ip;
	}
}
