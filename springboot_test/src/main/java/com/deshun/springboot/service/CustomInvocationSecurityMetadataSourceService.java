package com.deshun.springboot.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import com.deshun.springboot.Po.SysResource;
import com.deshun.springboot.Po.SysResourceRole;
import com.deshun.springboot.Po.SysRole;
import com.deshun.springboot.Po.SysUser;
import com.deshun.springboot.dao.SysResourceDao;
import com.deshun.springboot.dao.SysResourceRoleDao;
import com.deshun.springboot.dao.SysRoleDao;


@Service
public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

	@Autowired  
    private SysResourceDao sysResourceDao;
	
	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Autowired
	private SysResourceRoleDao sysResourceRoleDao;
	
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	//@PostConstruct
	//被@PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行。
    private void loadResourceDefine() { 
//一定要加上@PostConstruct注解
		// 在Web服务器启动时，提取系统中的所有权限。
        List<SysRole> list =sysRoleDao.findAll();  
        List<String> query = new ArrayList<String>();  
        if(list!=null && list.size()>0) {  
            for(SysRole srole :list){  
                //String name = sr.get("name")    
                //Object value = srole.getName();
                //String name = String.valueOf(value);  
                //query.add(name);  
            	
            	Object value = srole.getId();
                String id = String.valueOf(value);  
                query.add(id); 
            }  
        }  
        /* 
         * 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。 
         * sparta 
         */
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();  
  
        for (String auth : query) {  
            ConfigAttribute ca = new SecurityConfig(auth);  
            //List<Map<String,Object>> query1 = sResourceVODao.findByRoleName(auth);  
           List<String> query1 = new ArrayList<String>();
           List<SysResourceRole> SysResourceRoles=sysResourceRoleDao.findByRoleId(auth);
           List<String>  SysResourceRoleIds= new ArrayList<String>();
           for (SysResourceRole sysResourceRole : SysResourceRoles) {
        	   SysResourceRoleIds.add(String.valueOf(sysResourceRole.getId()));
           }
            List<SysResource>  list1 = sysResourceDao.findByResourceIdIn(SysResourceRoleIds);  
            if(list1!=null && list1.size()>0) {  
                for(SysResource map :list1){  
                   // Object value = map.get("resource_string");
                	Object value=map.getResourceString();
                    String url = String.valueOf(value);  
                    query1.add(url);  
                }  
            }  
            for (String res : query1) {  
                String url = res;  
                  /* 
                 * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中。 
                 * sparta 
                 */
                if (resourceMap.containsKey(url)) {  
  
                    Collection<ConfigAttribute> value = resourceMap.get(url);  
                    value.add(ca);  
                    resourceMap.put(url, value);  
                } else {  
                    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();  
                    atts.add(ca);  
                    resourceMap.put(url, atts);  
                }  
  
            }  
        }  
  
    }  
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		 System.out.println("nwuidhwuiehdfu");  
	        // object 是一个URL，被用户请求的url。
	        FilterInvocation filterInvocation = (FilterInvocation) object;  
	        if (resourceMap == null) {  
	            loadResourceDefine();  
	        }  
	        Iterator<String> ite = resourceMap.keySet().iterator();  
	        while (ite.hasNext()) {  
	            String resURL = ite.next();  
	             RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL);  
	                if(requestMatcher.matches(filterInvocation.getHttpRequest())) {  
	                return resourceMap.get(resURL);  
	            }  
	        }  
	  
	        return null; 
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return new ArrayList<ConfigAttribute>();
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}
