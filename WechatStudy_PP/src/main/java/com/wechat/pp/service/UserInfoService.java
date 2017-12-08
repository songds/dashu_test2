package com.wechat.pp.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.DeviceInfoDao;
import com.wechat.pp.dao.QqBoundInfoDao;
import com.wechat.pp.dao.UserInfoDao;
import com.wechat.pp.dao.WeixinBoundInfoDao;
import com.wechat.pp.po.DeviceInfoPo;
import com.wechat.pp.po.QqBoundInfoPo;
import com.wechat.pp.po.UserInfoPo;
import com.wechat.pp.po.WeixinBoundInfoPo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserInfoService {

	@Resource
	private UserInfoDao userInfoDao;
	
	@Resource
	private DeviceInfoDao deviceInfoDao;
	
	@Resource
	private WeixinBoundInfoDao weixinBoundInfoDao;
	
	@Resource
	private QqBoundInfoDao qqBoundInfoDao;
	
	@Resource
	private SsmVerificationService ssmVerificationService;
	
	/**
	 * 注册接口
	 * @param json json格式字符串
	 * {mobilePhone/手机号码,password/密码,authCode/验证码}
	 */
	public JSONObject register(String json){
		JSONObject result=new JSONObject();
		try {
			JSONObject jsonParameter=JSONObject.parseObject(json);
			String mobilePhone=jsonParameter.getString("mobilePhone");
			String password=jsonParameter.getString("password");
			String authCode=jsonParameter.getString("authCode");
			//String ipAddr=jsonParameter.getString("ipAddr");
			UserInfoPo userInfo=userInfoDao.isUserExistByMobile(mobilePhone);
			if(userInfo!=null){
				result.put("code", "F00002");
				result.put("message", "注册失败，该手机号已使用，请重新输入！");
			}else{
				userInfo=new UserInfoPo();
				String userName="Q"+System.currentTimeMillis();
				userInfo.setMobile(mobilePhone);
				userInfo.setPassword(MD5Encoder.encode(password.getBytes()));
				userInfo.setCreatedBy(userName);
				userInfo.setUserName(userName);
				userInfo.setRegistrationDate(new Date(System.currentTimeMillis()));
				userInfo.setUpdatedBy(userName);
				userInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
				userInfoDao.save(userInfo);
				result.put("code", "SUC000");
				result.put("message", "注册成功！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "注册异常，请您联系客服！");
		}
		return result;
	}
	
	
	/**
	 * 修改密码
	 * @param json json格式字符串
	 * {mobilePhone/手机号码,password/密码,authCode/验证码}
	 */
	public JSONObject updatePassword(String json){
		JSONObject result=new JSONObject();
		try {
			JSONObject jsonParameter=JSONObject.parseObject(json);
			String mobilePhone=jsonParameter.getString("mobilePhone");
			String password=jsonParameter.getString("password");
			String authCode=jsonParameter.getString("authCode");
			//String ipAddr=jsonParameter.getString("ipAddr");
			UserInfoPo userInfo=userInfoDao.isUserExistByMobile(mobilePhone);
			if(userInfo!=null){
				userInfo=new UserInfoPo();
				userInfo.setMobile(mobilePhone);
				userInfo.setPassword(MD5Encoder.encode(password.getBytes()));
				userInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
				userInfoDao.save(userInfo);
				result.put("code", "SUC000");
				result.put("message", "修改密码成功！");
			}else{
				result.put("code", "F00002");
				result.put("message", "修改密码失败，该手机号码未注册！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "修改密码异常，请您联系客服！");
		}
		return result;
	}
	
	/**
	 * 用户名登陆接口
	 */
	public String login(String userName,String password){
		JSONObject result=new JSONObject();
		boolean userExistBool=isUserExist(userName, "3");
		if(userExistBool){
			UserInfoPo userInfo=userInfoDao.loginByUserNameAndPassword(userName, password);
			if(userInfo!=null){
				result.put("code", "SUC000");
				result.put("message", "登陆成功");
				result.put("data", userInfo);
			}else{
				result.put("code", "F00002");
				result.put("message", "登录失败，密码错误，请您重新输入！");
			}
		}else{
			result.put("code", "F00001");
			result.put("message", "登录失败，用户名不存在，请您先注册用户！");
			
		}
		return JSONObject.toJSONString(result);
	}
	
	/**
	 * 手机号码登陆接口
	 * 	@param json
	 *  {mobilePhone/手机号码
	 *  password/密码
	 * 	deviceNumber/设备号
	 * 	ipAddr/IP地址
	 * }
	 */
	public JSONObject mobilePhonelogin(String json){
		JSONObject result=new JSONObject();
		try {
			JSONObject jsonParameter=JSONObject.parseObject(json);
			String mobilePhone=jsonParameter.getString("mobilePhone");
			String password=jsonParameter.getString("password");
			String deviceNumber=jsonParameter.getString("deviceNumber");
			//String ipAddr=jsonParameter.getString("ipAddr");
			UserInfoPo userInfo=userInfoDao.isUserExistByMobile(mobilePhone);
			if(userInfo!=null){
				if(userInfo.getPassword().equals(password)){
					DeviceInfoPo deviceInfo=deviceInfoDao.getByUserName(userInfo.getUserName());
					if(deviceInfo!=null){
						if(StringUtils.isEmpty(deviceInfo.getDeviceNumber())||deviceInfo.getDeviceNumber().equals(deviceNumber)){
							String token=MD5Encoder.encode(("Q"+System.currentTimeMillis()).getBytes());
							deviceInfoDao.updateByUserNameAndToken(userInfo.getUserName(), token);
							result.put("code", "SUC000");
							result.put("message", "成功");
							result.put("data", userInfo);
							result.put("toke", token);
						}else{
							result.put("code", "SUC001");
							result.put("message", "登陆异常，设备号异常！");
						}
					}else{
						String token=MD5Encoder.encode(("Q"+System.currentTimeMillis()).getBytes());
						 deviceInfo=new DeviceInfoPo();
						 deviceInfo.setUserName(userInfo.getUserName());
						 deviceInfo.setToken(token);
						 deviceInfo.setDeviceNumber(deviceNumber);
						 deviceInfo.setIpAddr(jsonParameter.getString("ipAddr"));
						deviceInfoDao.save(deviceInfo);
						result.put("code", "SUC000");
						result.put("message", "成功");
						result.put("data", userInfo);
						result.put("toke", token);
					}
				}else{
					result.put("code", "F00003");
					result.put("message", "登录失败，密码错误，请重新输入！");
				}
			}else{
				result.put("code", "F00002");
				result.put("message", "登录失败，用户名不存在，请您先注册用户！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "登录异常，请您联系客服！");
		}
		
		return result;
	}
	
	
	/**
	 * 微信快捷登陆接口
	 * @param json json格式的字段串
	 * {openId,unionId,token,weixinImageUrl,weixinName,weixinId}
	 */
	public JSONObject weixinLogin(String json){
		JSONObject result=new JSONObject();
		try {
			JSONObject jsonParameter=JSONObject.parseObject(json);
			String openId=jsonParameter.getString("openId");
			String unionId=jsonParameter.getString("unionId");
			String token=jsonParameter.getString("token");
			//String ipAddr=jsonParameter.getString("ipAddr");
			WeixinBoundInfoPo weixinBound=weixinBoundInfoDao.getWeixinPoByOpenId(openId);
			if(weixinBound!=null){
				DeviceInfoPo deviceInfo=deviceInfoDao.getByUserName(weixinBound.getUserName());
				if(deviceInfo!=null){
					deviceInfoDao.updateByUserNameAndToken(weixinBound.getUserName(), token);
					result.put("code", "SUC000");
					result.put("message", "成功");
					result.put("data", weixinBound);
					result.put("toke", token);
				}else{
					DeviceInfoPo deviceInfoPo=new DeviceInfoPo();
					deviceInfoPo.setUserName(weixinBound.getUserName());
					deviceInfoPo.setToken(token);
					deviceInfoDao.save(deviceInfoPo);
					result.put("code", "SUC000");
					result.put("message", "成功");
					result.put("data", weixinBound);
					result.put("toke", token);
				}
			}else{
				weixinBound=new WeixinBoundInfoPo();
				String userName="Q"+System.currentTimeMillis();
				weixinBound.setUserName(userName);
				weixinBound.setCreatedBy(userName);
				weixinBound.setOpenId(openId);
				weixinBound.setUnionId(unionId);
				weixinBound.setWeixinImageUrl(jsonParameter.getString("weixinImageUrl"));
				weixinBound.setWeixinName(jsonParameter.getString("weixinName"));
				weixinBound.setWeixinId(jsonParameter.getString("weixinId"));
				weixinBound.setCreatedDate(new Date(System.currentTimeMillis()));
				weixinBound.setUpdatedBy(userName);
				weixinBound.setUpdatedDate(new Date(System.currentTimeMillis()));
				weixinBoundInfoDao.save(weixinBound);
				UserInfoPo userInfo=new UserInfoPo();
				userInfo.setUserName(userName);
				userInfo.setRegistrationDate(new Date(System.currentTimeMillis()));
				userInfo.setPassword(MD5Encoder.encode("Q_admin_123456".getBytes()));
				userInfo.setCreatedBy(userName);
				userInfo.setUpdatedBy(userName);
				userInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
				userInfoDao.save(userInfo);
				DeviceInfoPo deviceInfoPo=new DeviceInfoPo();
				deviceInfoPo.setUserName(userName);
				deviceInfoPo.setToken(token);
				deviceInfoDao.save(deviceInfoPo);
				result.put("code", "SUC000");
				result.put("message", "成功");
				result.put("data", jsonParameter);
				result.put("toke", token);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "登录异常，请您联系客服！");
		}
		
		return result;
	}
	
	
	/**
	 * QQ快捷登陆接口
	 * @param json json格式的字段串
	 * {openId,unionId,token,qqId,qqName,qqImageUrl}
	 */
	public JSONObject qqLogin(String json){
		 
		JSONObject result=new JSONObject();
		try {
			JSONObject jsonParameter=JSONObject.parseObject(json);
			String openId=jsonParameter.getString("opendId");
			String unionId=jsonParameter.getString("unionId");
			String token=jsonParameter.getString("token");
			//String ipAddr=jsonParameter.getString("ipAddr");
			QqBoundInfoPo qqBoundInfo=qqBoundInfoDao.getQQPoByOpenId(openId);
			if(qqBoundInfo!=null){
				DeviceInfoPo deviceInfo=deviceInfoDao.getByUserName(qqBoundInfo.getUserName());
				if(deviceInfo!=null){
					deviceInfoDao.updateByUserNameAndToken(qqBoundInfo.getUserName(), token);
					result.put("code", "SUC000");
					result.put("message", "成功");
					result.put("data", qqBoundInfo);
					result.put("toke", token);
				}else{
					DeviceInfoPo deviceInfoPo=new DeviceInfoPo();
					deviceInfoPo.setUserName(qqBoundInfo.getUserName());
					deviceInfoPo.setToken(token);
					deviceInfoDao.save(deviceInfoPo);
					result.put("code", "SUC000");
					result.put("message", "成功");
					result.put("data", qqBoundInfo);
					result.put("toke", token);
				}
			}else{
				qqBoundInfo=new QqBoundInfoPo();
				String userName="Q"+System.currentTimeMillis();
				qqBoundInfo.setUserName(userName);
				qqBoundInfo.setCreatedBy(userName);
				qqBoundInfo.setOpenId(openId);
				qqBoundInfo.setUnionId(unionId);
				qqBoundInfo.setQqImageUrl(jsonParameter.getString("qqImageUrl"));
				qqBoundInfo.setQqName(jsonParameter.getString("qqName"));
				qqBoundInfo.setQqId(jsonParameter.getString("qqId"));
				qqBoundInfo.setCreatedDate(new Date(System.currentTimeMillis()));
				qqBoundInfo.setUpdatedBy(userName);
				qqBoundInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
				qqBoundInfoDao.save(qqBoundInfo);
				UserInfoPo userInfo=new UserInfoPo();
				userInfo.setUserName(userName);
				userInfo.setRegistrationDate(new Date(System.currentTimeMillis()));
				userInfo.setPassword(MD5Encoder.encode("Q_admin_123456".getBytes()));
				userInfo.setCreatedBy(userName);
				userInfo.setUpdatedBy(userName);
				userInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
				userInfoDao.save(userInfo);
				DeviceInfoPo deviceInfoPo=new DeviceInfoPo();
				deviceInfoPo.setUserName(userName);
				deviceInfoPo.setToken(token);
				deviceInfoDao.save(deviceInfoPo);
				result.put("code", "SUC000");
				result.put("message", "成功");
				result.put("data", jsonParameter);
				result.put("toke", token);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "登录异常，请您联系客服！");
		}
		
		return result;
	}
	
	/**
	 * 判断用户是否存在
	 * @param obj
	 * @param type 1/QQ 2/weixin 3/userName 4/手机号码
	 * @return
	 */
	public boolean isUserExist(String obj,String type){
		if (type.equals("1")){
			
		}else if(type.equals("2")){
			
		}else if(type.equals("3")){
			//return userInfoDao.isUserExistByUserName(obj);
		}else if(type.equals("4")){
			//return userInfoDao.isUserExistByMobile(obj);
		}
		return false;
	}
	
	/**
	 * 修改用户个人信息
	 * {userName/用户名,imageUrl/图片路劲}
	 * @param json
	 * @return
	 */
	public JSONObject updateHeadPortrait(String json){
		JSONObject result=new JSONObject();
		try {
			JSONObject jsonParameter=JSONObject.parseObject(json);
			String userName=jsonParameter.getString("userName");
			String imageUrl=jsonParameter.getString("imageUrl");
			UserInfoPo userInfo=userInfoDao.isUserExistByUserName(userName);
			if(userInfo!=null){
				userInfo.setImageUrl(imageUrl);
				userInfo.setUpdatedBy(userName);
				userInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
				userInfoDao.save(userInfo);
				result.put("code", "SUC000");
				result.put("message", "用户头像修改成功");
				return result;
			}else{
				result.put("code", "F00002");
				result.put("message", "用户头像修改失败,用户不存在，请您联系客服！");
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "用户头像修改异常，请您联系客服！");
			return result;
		}
	} 
	
	
	/**
	 * 修改用户个人信息
	 * {userName/用户名,sex/性别,name/昵称,address/地址}
	 * @param json
	 * @return
	 */
	public JSONObject updateUserInfo(String json){
		JSONObject result=new JSONObject();
		try {
			JSONObject jsonParameter=JSONObject.parseObject(json);
			String userName=jsonParameter.getString("userName");
			String sex=jsonParameter.getString("sex");
			String name=jsonParameter.getString("name");
			String address=jsonParameter.getString("address");
			UserInfoPo userInfo=userInfoDao.isUserExistByUserName(userName);
			if(userInfo!=null){
				userInfo.setSex(sex);
				userInfo.setName(name);
				userInfo.setUpdatedBy(userName);
				userInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
				userInfoDao.save(userInfo);
				result.put("code", "SUC000");
				result.put("message", "用户个人信息修改成功");
				return result;
			}else{
				result.put("code", "F00002");
				result.put("message", "用户个人信息修改失败,用户不存在，请您联系客服！");
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "用户个人信息修改异常，请您联系客服！");
			return result;
		}
	} 
	
	/**
	 * 用户解锁,需要短信验证
	 * {userName/用户名}
	 * @param json
	 * @return
	 */
	public JSONObject userDeblocking(String json){
		JSONObject result=new JSONObject();
		try {
			JSONObject jsonParameter=JSONObject.parseObject(json);
			String userName=jsonParameter.getString("userName");
			//缺判断用户解锁次数方法
			UserInfoPo userInfo=userInfoDao.isUserExistByUserName(userName);
			if(userInfo!=null){
				userInfo.setUserStatus("1");
				userInfo.setUpdatedBy(userName);
				userInfo.setUpdatedDate(new Date(System.currentTimeMillis()));
				userInfoDao.save(userInfo);
				result.put("code", "SUC000");
				result.put("message", "用户解锁成功");
				return result;
			}else{
				result.put("code", "F00002");
				result.put("message", "用户解锁失败,用户不存在，请您联系客服！");
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "用户解锁异常，请您联系客服！");
			return result;
		}
	}
	
}
