package com.wechat.pp.service;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.wechat.pp.dao.SmsInfoDao;
import com.wechat.pp.po.SmsInfoPo;
import com.wechat.pp.util.AliyunSmsUtil;
import com.wechat.pp.util.RandomVerfCode;

import lombok.extern.slf4j.Slf4j;
/**
 * 短信服务接口
 * @author ex-songdeshun
 *
 */
@Service
@Slf4j
public class SMSService {
	
	
	@Resource
	private SmsInfoDao smsInfoDao;
	
	@Value("${sms.verf.dataRange}")
	private String dataRange;
	
	@Value("${sms.verf.length}")
	private int verfLength;
	
	@Value("${sms.verf.validMinute}")
	private int validMinute;
	
	@Value("${spring.application.name}")
	private String systemName;
	/**
	 * 发送短信验证码
	 * @param json 
	 * case:{"mobileNo":"18373515600"}
	 * @return
	 * 
	 */
	public JSONObject sendSMS(String json) {
		JSONObject result = new JSONObject();
		try {
			if (!StringUtils.isEmpty(json)) {
				JSONObject jsonParameter=JSONObject.parseObject(json);
				if(StringUtils.isEmpty(jsonParameter.getString("mobileNo"))){
					result.put("code", "F00003");
					result.put("message","发送验证码失败,参数手机号码不能为空值!");
					return result;
				}
				String mobileNo=jsonParameter.getString("mobileNo");
				//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date currentDate=new Date(System.currentTimeMillis());
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(currentDate);
				calendar.add(Calendar.MINUTE, validMinute);
				Date expirationTime=calendar.getTime();
				SmsInfoPo smsInfoPo=smsInfoDao.getSmsInfo( mobileNo, currentDate);
				if(smsInfoPo!=null){
					SendSmsResponse sendSmsResponse = AliyunSmsUtil.sendSms(mobileNo,  "{\"code\":\""+smsInfoPo.getVerificationCode()+"\"}");
					if(sendSmsResponse.getCode()!=null&&sendSmsResponse.getCode().equals("OK")){
						result.put("code","SUC000");
						result.put("message","发送验证码成功");
						return result;
					}else{
						result.put("code","F00005");
						result.put("message",StringUtils.isEmpty(sendSmsResponse.getMessage())?"":sendSmsResponse.getMessage());
						return result;
					}
				}else{
					log.info("  interface dataRange:{} verfLength:{} is  sendSMS-->create sned parameter message", dataRange,verfLength);
					String verfCode=RandomVerfCode.createVerfCode(dataRange, verfLength);
					log.info(" interface {} is  sendSMS->create verfCode method result message", verfCode);
					JSONObject verfCodeResult=JSONObject.parseObject(verfCode);
					if(verfCodeResult.getString("code").equals("SUC000")){
						SendSmsResponse sendSmsResponse = AliyunSmsUtil.sendSms(mobileNo,  "{\"code\":\""+verfCodeResult.getString("verfCode")+"\"}");
						log.info(" interface {} is  sendSMS->sendSms send SMS method result message", JSONObject.toJSONString(sendSmsResponse));
						if(sendSmsResponse.getCode()!=null&&sendSmsResponse.getCode().equals("OK")){
							smsInfoPo=new SmsInfoPo();
							smsInfoPo.setCreatedBy(systemName);
							smsInfoPo.setUpdatedBy(systemName);
							smsInfoPo.setCreatedDate(currentDate);
							smsInfoPo.setUpdatedDate(currentDate);
							smsInfoPo.setExpirationTime(expirationTime);
							smsInfoPo.setStatus("0");
							smsInfoPo.setMobileNo(mobileNo);
							smsInfoPo.setVerificationCode(verfCodeResult.getString("verfCode"));
							log.info(" method sendSMS insert into  database Entity class message {} ",JSONObject.toJSONString(smsInfoPo));
							smsInfoDao.save(smsInfoPo);
							result.put("code","SUC000");
							result.put("message","发送验证码成功");
							return result;
						}else{
							result.put("code","F00004");
							result.put("message",StringUtils.isEmpty(sendSmsResponse.getMessage())?"":sendSmsResponse.getMessage());
							return result;
						}
					}else{
						return verfCodeResult;
					}
				}
			} else {
				result.put("code","F00002");
				result.put("message","发送验证码失败,参数不能为空值!");
				log.info(" error {} is  sendSMS method result message", result);
				return result;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("code","F00001");
			result.put("message","发送验证码异常,请联系系统管理员！");
			log.info(" error {} is  sendSMS method result message", result);
			return result;
		}
	}
	/**
	 * 短信验证码验证
	 * @param json
	 * @return
	 */
	public JSONObject SMSVerificationCode(String json) {
		JSONObject result = new JSONObject();
		try {
			if (!StringUtils.isEmpty(json)) {
				JSONObject jsonParameter=JSONObject.parseObject(json);
				if(StringUtils.isEmpty(jsonParameter.getString("mobileNo"))){
					result.put("code","F00004");
					result.put("message","短信验证失败,参数手机号码不能为空值!");
					return result;
				}else if(StringUtils.isEmpty(jsonParameter.getString("verificationCode"))){
					result.put("code","F00006");
					result.put("message","短信验证失败,参数验证码不能为空值!");
					return result;
				}
				String mobileNo=jsonParameter.getString("mobileNo");
				String verificationCode=jsonParameter.getString("verificationCode");
				Date currentDate=new Date(System.currentTimeMillis());
				SmsInfoPo smsInfoPo=smsInfoDao.getSmsInfo(mobileNo, currentDate, verificationCode);
				if(smsInfoPo!=null){
					smsInfoPo.setStatus("1");
					smsInfoPo.setUpdatedBy(systemName);
					smsInfoPo.setUpdatedDate(currentDate);
					smsInfoDao.save(smsInfoPo);
					result.put("code","SUC000");
					result.put("message","验证通过!");
				}else{
					result.put("code","F00001");
					result.put("message","验证码错误!");
				}
				//String result = zcCommonSao.SMSVerificationCode(json);
				log.info(" interface {} is  SMSVerificationCode method result message",result);
				return result;
			} else {
				result.put("code","F00002");
				result.put("message","短信验证失败,参数不能为空值!");
				log.info(" error {} is  SMSVerificationCode method result message", result);
				return result;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("code","F00001");
			result.put("message","短信验证异常,请联系系统管理员！");
			log.info(" error {} is  SMSVerificationCode method result message", result);
			return result;
		}
	}
}
