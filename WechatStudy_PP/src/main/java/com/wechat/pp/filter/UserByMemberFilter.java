package com.wechat.pp.filter;

import java.io.IOException;
import java.util.Date;

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
import com.wechat.pp.dao.UserLookTopicDao;
import com.wechat.pp.dao.UserMemberRelationDao;
import com.wechat.pp.po.UserLookTopicPo;
import com.wechat.pp.po.UserMemberRelationPo;
import com.wechat.pp.util.MAPIHttpServletRequestWrapper;

import lombok.extern.slf4j.Slf4j;
@WebFilter(filterName="UserByMemberFilter",urlPatterns={"/api/getByTopicId.do","/api/findTopicInfoBySectionId.do"})
@Slf4j
public class UserByMemberFilter extends CompositeFilter{

	@Resource
	private UserMemberRelationDao userMemberRelationDao;
	@Resource
	private UserLookTopicDao userLookTopicDao;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		MAPIHttpServletRequestWrapper httpServletRequestWrapper=new MAPIHttpServletRequestWrapper((HttpServletRequest)request);
		String json=new String(httpServletRequestWrapper.getBody(),"UTF-8");
		log.info(" method is UserByMemberFilter to customer input parameter message :  {}",json);
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
			Date currentTime=new  Date(System.currentTimeMillis());
			UserMemberRelationPo userMemberRelation=userMemberRelationDao.isExistByUserNameAndMemberIdAndMemberTypeAndValidEndTime(userName, memberId, memberType,currentTime);
			if(userMemberRelation!=null){
				super.doFilter(httpServletRequestWrapper, response, chain);
			}else{
				if(httpServletRequestWrapper.getRequestURI().equals("/api/getByTopicId.do")){
					if(StringUtils.isEmpty(paramter.getString("topicId"))){
						//response.getWriter().print(x);
						ServletOutputStream out=response.getOutputStream();
						byte[] b="{\"code\":\"F00004\",\"message\":\"查询题目失败,参数题目编号不能空\"}".getBytes("utf-8");
						out.write(b);
						out.flush();
						return;
					}
					int LookTopicCount=userLookTopicDao.countByUserName(userName);
					if(LookTopicCount>=10){
						ServletOutputStream out=response.getOutputStream();
						byte[] b="{\"code\":\"SUC001\",\"message\":\"查询题目处理成功,只能免费查询10个题目,请购买该科目会员!\"}".getBytes("utf-8");
						out.write(b);
						out.flush();
						return;
					}else{
						int topicId=paramter.getIntValue("topicId");
						UserLookTopicPo userLookTopic=new UserLookTopicPo();
						userLookTopic.setTopicId(topicId);
						userLookTopic.setUserName(userName);
						userLookTopicDao.save(userLookTopic);
						super.doFilter(httpServletRequestWrapper, response, chain);
					}
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

}
