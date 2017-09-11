package com.deshun.springboot.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.deshun.springboot.Po.SysRole;
import com.deshun.springboot.Po.SysUser;

public class SecurityUser extends SysUser implements UserDetails {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SecurityUser(SysUser suser) {
		// TODO Auto-generated constructor stub
		if(suser !=null){
			this.setId(suser.getId());
			this.setName(suser.getName());
			this.setEmail(suser.getEmail());
			this.setPassword(suser.getPassword());
			this.setDob(suser.getDob());
			this.setSRoles(suser.getSysRoles());
		}
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();  
        Set<SysRole> userRoles = this.getSysRoles();  
          
        if(userRoles != null)  
        {  
            for (SysRole role : userRoles) {  
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());  
                authorities.add(authority);  
            }  
        }  
        return authorities;  
	}

    public String getPassword() {  
        return super.getPassword();  
    }  
  
    public String getUsername() {  
        return super.getName();  
    }  
  
    public boolean isAccountNonExpired() {  
        return true;  
    }  
  
    public boolean isAccountNonLocked() {  
        return true;  
    }  
  
    public boolean isCredentialsNonExpired() {  
        return true;  
    }  
  
    public boolean isEnabled() {  
        return true;  
    }  

	
	
}
