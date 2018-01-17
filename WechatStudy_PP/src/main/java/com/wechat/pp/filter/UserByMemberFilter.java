package com.wechat.pp.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.CompositeFilter;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.UserMemberRelationDao;
import com.wechat.pp.po.UserMemberRelationPo;
import com.wechat.pp.util.MAPIHttpServletRequestWrapper;
@WebFilter(filterName="UserByMemberFilter",urlPatterns={"/api/getByTopicId.do","/api/findTopicInfoBySectionId.do"})
public class UserByMemberFilter extends CompositeFilter{

	@Resource
	private UserMemberRelationDao userMemberRelationDao;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		MAPIHttpServletRequestWrapper httpServletRequestWrapper=new MAPIHttpServletRequestWrapper((HttpServletRequest)request);
		String json=new String(httpServletRequestWrapper.getBody(),"UTF-8");
		if(StringUtils.isEmpty(json)){
			ServletOutputStream out=response.getOutputStream();
			byte[] b="{\"code\":\"F00001\",\"message\":\"查询题目失败,参数会员编号不能空!\"}".getBytes("utf-8");
			out.write(b);
			out.flush();
		}else{
			JSONObject paramter=JSONObject.parseObject(json);
			if(StringUtils.isEmpty(paramter.getString("userName"))){
				//response.getWriter().print(x);
				ServletOutputStream out=response.getOutputStream();
				byte[] b="{\"code\":\"F00002\",\"message\":\"查询题目失败,参数用户名不能空\"}".getBytes("utf-8");
				out.write(b);
				out.flush();
				return;
			}
			if(StringUtils.isEmpty(paramter.getString("memberId"))){
				//response.getWriter().print(x);
				ServletOutputStream out=response.getOutputStream();
				byte[] b="{\"code\":\"F00003\",\"message\":\"查询题目失败,参数会员编号不能空\"}".getBytes("utf-8");
				out.write(b);
				out.flush();
				return;
			}
			if(StringUtils.isEmpty(paramter.getString("memberType"))){
				ServletOutputStream out=response.getOutputStream();
				byte[] b="{\"code\":\"F00004\",\"message\":\"查询题目失败,参数会员类型不能空\"}".getBytes("utf-8");
				out.write(b);
				out.flush();
				return;
			}
			String userName=paramter.getString("userName");
			int memberId=paramter.getIntValue("memberId");
			String memberType=paramter.getString("memberType");
			UserMemberRelationPo userMemberRelation=userMemberRelationDao.isExistByUserNameAndMemberIdAndMemberType(userName, memberId, memberType);
			if(userMemberRelation!=null){
				super.doFilter(httpServletRequestWrapper, response, chain);
			}else{
				ServletOutputStream out=response.getOutputStream();
				byte[] b="{\"code\":\"SUC001\",\"message\":\"查询题目处理成功,该用户未购买该科目!\"}".getBytes("utf-8");
				out.write(b);
				out.flush();
				return;
			}
			
		}
		
		
	}

}
