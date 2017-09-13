package com.deshun.springboot.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.deshun.springboot.Po.SysRole;
import com.deshun.springboot.Po.SysUser;
import com.deshun.springboot.Po.SysUserRole;
import com.deshun.springboot.Util.SecurityUser;

@Component
public class CustomUserDetailsService  implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Resource
	private SysUserRoleService sysUserRoleService;
	
	@Resource
	private SysRoleService sysRoleService;
	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		SysUser user=userService.findByName(username);
		if(user == null){
			throw new UsernameNotFoundException("UserName " +username +" not found");
		}
		System.out.println(user.getId());
		List<Integer> roleIds=sysUserRoleService.findByUserId(user.getId());
		System.out.println("roleIds:"+roleIds);
		List<SysRole> sysRoles=sysRoleService.findByIdIn(roleIds);
		System.out.println("sysRoles:"+sysRoles);
		user.setSysRoles(sysRoles);
		SecurityUser securityUser=new SecurityUser(user);
		//System.out.println(bCryptPasswordEncoder.encode(user.getPassword()));
		//securityUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return securityUser;
	}

}
