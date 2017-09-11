package com.deshun.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.deshun.springboot.Po.SysUser;
import com.deshun.springboot.Util.SecurityUser;

@Component
public class CustomUserDetailsService  implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		SysUser user=userService.findByName(username);
		if(user == null){
			throw new UsernameNotFoundException("UserName " +username +" not found");
		}
		SecurityUser securityUser=new SecurityUser(user);
		
		return securityUser;
	}

}
