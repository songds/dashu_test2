package com.deshun.springboot.Util;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import com.deshun.springboot.service.CustomInvocationSecurityMetadataSourceService;

/** 
 * 该过滤器的主要作用就是通过spring著名的IoC生成securityMetadataSource。 
 * securityMetadataSource相当于本包中自定义的MyInvocationSecurityMetadataSourceService。 
 * 该MyInvocationSecurityMetadataSourceService的作用提从数据库提取权限和资源，装配到HashMap中， 
 * 供Spring Security使用，用于权限校验。 
 * @author sparta 11/3/29 
 * 
 */

@Component
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor  
	implements Filter{
	
	@Autowired  
    private CustomInvocationSecurityMetadataSourceService  mySecurityMetadataSource;  
      
    @Autowired  
    private CustomAccessDecisionManager myAccessDecisionManager;  
      
    @Autowired  
    private AuthenticationManager authenticationManager;  
    
    @PostConstruct  
    public void init(){  
        super.setAuthenticationManager(authenticationManager);  
        super.setAccessDecisionManager(myAccessDecisionManager);  
    }  
    
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		 System.out.println("filter===========================");  
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		 FilterInvocation fi = new FilterInvocation( request, response, chain );  
	     invoke(fi);
	}

	public void invoke( FilterInvocation fi ) throws IOException, ServletException{  
        System.out.println("filter..........................");  
        InterceptorStatusToken  token = super.beforeInvocation(fi);  
        try{  
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());  
        }finally{  
            super.afterInvocation(token, null);  
        }  
          
    }  
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		  System.out.println("filter===========================end");
	}

	@Override
	public Class<?> getSecureObjectClass() {
		// TODO Auto-generated method stub
		return FilterInvocation.class; 
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		// TODO Auto-generated method stub
		System.out.println("filtergergetghrthetyetyetyetyj");  
        return this.mySecurityMetadataSource;  
	}

}
