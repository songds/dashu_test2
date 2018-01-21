package com.wechat.pp.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pp.dao.TradingRecordInfoDao;
import com.wechat.pp.dao.WeixinOrderRetDao;
import com.wechat.pp.dao.WeixinPayInfoDao;
import com.wechat.pp.dto.WeixinOrderReqDto;
import com.wechat.pp.po.TradingRecordInfoPo;
import com.wechat.pp.po.WeixinPayInfoPo;
import com.wechat.pp.util.CreateNonceStrUtil;
import com.wechat.pp.util.CreateOrderNoUtil;
import com.wechat.pp.util.CreateSignUtil;
import com.wechat.pp.util.HttpClientUtil;
import com.wechat.pp.util.WeiXinXmlUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * @author Administrator
 * 微信支付接口
 */
@Service
@Slf4j
public class WeixinPayService {
	
	/**
	 * 应用ID
	 */
	@Value("${app.appid}")
	private String appId;
	
	/**
	 * 应用秘钥
	 */
	@Value("${weixin.app.key}")
	private String key;
	
	/**
	 * 回调访问地址
	 */
	@Value("${weixin.notifyUrl}")
	private String notifyUrl;
	
	/**
	 * 微信统一下单访问地址
	 */
	@Value("${weinxin.unifiedOrderUrl}")
	private String unifiedOrderUrl;
	
	/**
	 * 微信订单查询访问地址
	 */
	@Value("${weinxin.orderQueryUrl}")
	private String orderQueryUrl;
	
	/**
	 * 微信关闭订单访问地址
	 */
	@Value("${weinxin.closeOrderUrl}")
	private String closeOrderUrl;
	
	@Resource
	private WeixinOrderRetDao weixinOrderRetDao;
	
	@Resource
	private WeixinPayInfoDao weixinPayInfoDao;
	
	@Resource
	private TradingRecordInfoDao tradingRecordInfoDao;
	
	@Resource
	private UserMemberRelationService userMemberRelationService;
	/**
	 * 微信统一下单接口
	 * @param json
	 * 附加数据/attach:userName/用户名#memberId/会员编号#memberType/会员类型#cardType/会员卡类型
	 * @return
	 */
	public JSONObject payUnifiedOrder(String json){
		JSONObject result=new JSONObject();
		try{
			if(!StringUtils.isEmpty(json)){
				WeixinOrderReqDto weixinOrderReqDto=JSONObject.parseObject(json, WeixinOrderReqDto.class);
				//mch_id,body,out_trade_no,total_fee,spbill_create_ip,trade_type
				//appid,notify_url,nonce_str,sign
				if(StringUtils.isEmpty(weixinOrderReqDto.getMchId())){
					result.put("code", "F00003");
					result.put("message", "微信下单失败,参数商户号不能为空值!");
					return result;
				}else if(StringUtils.isEmpty(weixinOrderReqDto.getBody())){
					result.put("code", "F00004");
					result.put("message", "微信下单失败,参数商品描述不能为空值!");
					return result;
				}else if(weixinOrderReqDto.getTotalFee()<0){
					result.put("code", "F00006");
					result.put("message", "微信下单失败,参数商户总金额不能小于零!");
					return result;
				}else if(StringUtils.isEmpty(weixinOrderReqDto.getSpbillCreateIp())){
					result.put("code", "F00007");
					result.put("message", "微信下单失败,参数终端IP不能为空!");
					return result;
				}else if(StringUtils.isEmpty(weixinOrderReqDto.getTradeType())){
					result.put("code", "F00008");
					result.put("message", "微信下单失败,参数支付类型不能为空!");
					return result;
				}else if(StringUtils.isEmpty(weixinOrderReqDto.getAttach())){
					result.put("code", "F00010");
					result.put("message", "微信下单失败,参数附加数据不能为空!");
					return result;
				}
				String orderNo=CreateOrderNoUtil.createOrderNo();
				weixinOrderReqDto.setOutTradeNo(orderNo);
				weixinOrderReqDto.setAppId(appId);
				weixinOrderReqDto.setNotifyUrl(notifyUrl);
				String nonceStr=CreateNonceStrUtil.createNonceStr(CreateNonceStrUtil.processNonceStrContent(weixinOrderReqDto), key);
				weixinOrderReqDto.setNonceStr(nonceStr);	
				String sign=CreateSignUtil.createSign(CreateSignUtil.processSignContent(weixinOrderReqDto), key);
				weixinOrderReqDto.setSign(sign);
				String xml=WeiXinXmlUtil.ObjectToXml(weixinOrderReqDto);
				JSONObject weixinResult=HttpClientUtil.httpsWeixinRequest(unifiedOrderUrl, "POST", xml);
				log.info(" weixin interface to result message {} ",weixinResult);
				if(weixinResult.getString("code").equals("SUC000")){
					JSONObject weixinData=weixinResult.getJSONObject("data");
					//WeixinOrderRespDto weixinOrderRespDto=(WeixinOrderRespDto) weiXinXmlUtil.XmlStrToObject(data);
					if(weixinData.getString("return_code").equals("SUCCESS")){
						if(weixinData.getString("result_code").equals("SUCCESS")){
							/*WeixinOrderRetPo weixinOrderRet=new WeixinOrderRetPo();
							weixinOrderRet.setReturnCode(weixinData.getString("return_code"));
							weixinOrderRet.setReturnMsg(weixinData.getString("return_msg"));
							weixinOrderRet.setAppid(weixinData.getString("appid"));
							weixinOrderRet.setMchId(weixinData.getString("mch_id"));
							weixinOrderRet.setDeviceInfo(weixinData.getString("device_info"));
							weixinOrderRet.setNonceStr(weixinData.getString("nonce_str"));
							weixinOrderRet.setSign(weixinData.getString("sign"));
							weixinOrderRet.setResultCode(weixinData.getString("result_code"));
							weixinOrderRet.setErrCode(weixinData.getString("err_code"));
							weixinOrderRet.setErrCodeDes(weixinData.getString("err_code_des"));
							weixinOrderRet.setTradeType(weixinData.getString("trade_type"));
							weixinOrderRet.setPrepayId(weixinData.getString("prepay_id"));*/
							WeixinPayInfoPo weixinPayInfo=new WeixinPayInfoPo();
							weixinPayInfo.setAppid(weixinData.getString("appid"));
							weixinPayInfo.setMchId(weixinData.getString("mch_id"));
							weixinPayInfo.setPrepayId(weixinData.getString("prepay_id"));
							weixinPayInfo.setPackages("Sign=WXPay");
							//String userName=weixinOrderReqDto.getUserName();
							Date currentDate=new Date(System.currentTimeMillis());
							String time=System.currentTimeMillis()/1000+"";
							weixinPayInfo.setTimestamps(time);
							Map<String,Object> map=new LinkedHashMap<String,Object>();
							map.put("appid", weixinData.getString("appid"));
							map.put("partnerid", weixinData.getString("mch_id"));
							map.put("prepayid", weixinData.getString("prepay_id"));
							map.put("package", "Sign=WXPay");
							String randRom=CreateNonceStrUtil.createNonceStr(map, key);
							weixinPayInfo.setNonceStr(randRom);
							map.put("noncestr", randRom);
							map.put("timestamp", time);
							String paySign=CreateSignUtil.createSign(map,key);
							map.put("sign", paySign);
							weixinPayInfo.setSign(paySign);
							weixinPayInfo.setOutTradeNo(orderNo);
							weixinPayInfo.setUserName(weixinOrderReqDto.getAttach().split("#")[0]);
							weixinPayInfo.setTradeType(weixinData.getString("trade_type"));
							weixinPayInfo.setPayType("1");
							Calendar calendar=Calendar.getInstance();
							calendar.setTime(currentDate);
							calendar.add(Calendar.HOUR, 1);
							calendar.add(Calendar.MINUTE, 40);
							Date expirationTime=calendar.getTime();
							weixinPayInfo.setExpirationTime(expirationTime);
							weixinPayInfo.setTotalFee(weixinOrderReqDto.getTotalFee());
							weixinPayInfo.setBody(weixinOrderReqDto.getBody());
							weixinPayInfo.setAttach(weixinOrderReqDto.getAttach());
							int weixinOrderId=weixinPayInfoDao.save(weixinPayInfo).getId();
							StringBuffer sb=new StringBuffer();
							sb.append("<xml>");
							sb.append("<appid>"+weixinData.getString("appid")+"</appid>");
							sb.append("<partnerid>"+weixinData.getString("mch_id")+"</partnerid>");
							sb.append("<prepayid>"+weixinData.getString("prepay_id")+"</prepayid>");
							sb.append("<package>Sign=WXPay</package>");
							sb.append("<noncestr>"+randRom+"</noncestr>");
							sb.append("<timestamp>"+time+"</timestamp>");
							sb.append("<sign>"+paySign+"</sign>");
							sb.append("</xml>");
							result.put("code", "SUC000");
							result.put("message", "处理成功");
							result.put("data", sb.toString());
							result.put("orderId", weixinOrderId);
							return result;
						}else{
							result.put("code", "F00011");
							result.put("message", "微信下单失败,请您联系客服!");
							result.put("err_code", StringUtils.isEmpty(weixinData.getString("err_code"))?"微信下单失败,请联系客服!":weixinData.getString("err_code"));
							result.put("err_code_des", StringUtils.isEmpty(weixinData.getString("err_code_des"))?"微信下单失败,请联系客服!":weixinData.getString("err_code_des"));
							return result;
						}
					}else{
						result.put("code", "F00010");
						result.put("message", StringUtils.isEmpty(weixinData.getString("return_msg"))?"微信下单失败,请联系客服!":weixinData.getString("return_msg"));
						return result;
					}
					
				}else{
					result.put("code", "F00009");
					result.put("message", weixinResult.getString("message"));
					return result;
				}
			}else{
				result.put("code", "F00002");
				result.put("message", "微信下单失败,参数不能为空值,请您重新下单!");
				return result;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "微信下单异常,请您联系系统管理员!");
			return result;
		}
		
	}
	
	/**
	 * 加工支付调用接口xml
	 * @return
	public String processPayContent(WeixinPayInfoPo weixinPayInfo){
		
	}*/
	
	/**
	 * 微信回调函数接口
	 * @param request
	 * @return
	 */
	@Transactional
	public String payCallBackNotifyUrl(HttpServletRequest request){
		StringBuffer sb=new StringBuffer();
		try {
			InputStream inputStream=request.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
			Map<String, Object> weixinResult=WeiXinXmlUtil.parseXML(inputStreamReader);
			log.info(" weixin interface to result message {} ",JSONObject.toJSONString(weixinResult));
			if(weixinResult.get("return_code")!=null&&weixinResult.get("return_code").toString().equals("SUCCESS")){
				String outTradeNo=weixinResult.get("out_trade_no")!=null?weixinResult.get("out_trade_no").toString():"";
				String sign=weixinResult.get("sign")!=null?weixinResult.get("sign").toString():"";
				int totalFee=weixinResult.get("total_fee")!=null?Integer.parseInt(weixinResult.get("total_fee").toString()):0;
				String createSign=CreateSignUtil.createSign(weixinResult, key);
				log.info(" method payCallBackNotifyUrl create sign is result message : {} ",createSign);
				WeixinPayInfoPo weixinPayInfo=weixinPayInfoDao.getByOutTradeNo(outTradeNo);
				if(createSign.equals(sign)){
					log.info(" Signature verification successful ! ");
					if(totalFee==weixinPayInfo.getTotalFee()){
						log.info(" WeChat payment verification is successful. ! ");
						if(StringUtils.isEmpty(weixinPayInfo.getTradeStatus())){
							log.info(" WeChat payment status verifies success. ! ");
							if(weixinResult.get("result_code")!=null&&weixinResult.get("result_code").toString().equals("SUCCESS")){
								log.info(" WeChat pays off.! ");
								weixinPayInfo.setTradeStatus("SUCCESS");
								weixinPayInfoDao.save(weixinPayInfo);
								log.info(" update weixinPayInfo is table to message {} ", JSONObject.toJSONString(weixinPayInfo));
								TradingRecordInfoPo tradingRecordInfo=new TradingRecordInfoPo();
								tradingRecordInfo.setUserName(weixinPayInfo.getUserName());
								tradingRecordInfo.setTradingAmt(weixinPayInfo.getTotalFee());
								tradingRecordInfo.setTradingType("1");
								tradingRecordInfo.setTradingDesc(weixinPayInfo.getBody());
								tradingRecordInfo.setTradingId(weixinPayInfo.getOutTradeNo());
								tradingRecordInfo.setTradingTime(weixinResult.get("time_end")!=null?weixinResult.get("time_end").toString():"");
								tradingRecordInfo.setTradingStatus("SUCCESS");
								tradingRecordInfoDao.save(tradingRecordInfo);
								log.info(" insert into tradingRecordInfo is table to message {} ", JSONObject.toJSONString(tradingRecordInfo));
								String attach=weixinResult.get("attach")!=null?weixinResult.get("attach").toString():"";
								if(!StringUtils.isEmpty(attach)){
									String[] attachs=attach.split("#");
									JSONObject memberJson=new JSONObject();
									memberJson.put("userName", attachs[0]);
									memberJson.put("memberId", attachs[1]);
									memberJson.put("memberType", attachs[2]);
									memberJson.put("cardType", attachs[3]);
									log.info(" load   userMemberRelationService--->buyMember is parameter to message {} ",JSONObject.toJSONString(memberJson));
									JSONObject memberResult=userMemberRelationService.buyMember(JSONObject.toJSONString(memberJson));
									log.info(" load   userMemberRelationService--->buyMember is result to message {}",JSONObject.toJSONString(memberResult));
								}
								sb.append("<xml>");
								sb.append("<return_code><![CDATA[SUCCESS]]></return_code>");
								sb.append("<return_msg><![CDATA[OK]]></return_msg>");
								sb.append("</xml>");
							}else{
								log.info(" WeChat payment failed. ");
								weixinPayInfo.setTradeStatus("FAIL");
								weixinPayInfoDao.save(weixinPayInfo);
								sb.append("<xml>");
								sb.append("<return_code><![CDATA["+weixinResult.get("result_code")+"]]></return_code>");
								sb.append("<return_msg><![CDATA["+weixinResult.get("err_code")+"]]></return_msg>");
								sb.append("</xml>");
							}
						}else{
							log.info(" The order has been paid successfully and is not allowed to be repeated. ");
							sb.append("<xml>");
							sb.append("<return_code><![CDATA[SUCCESS]]></return_code>");
							sb.append("<return_msg><![CDATA[OK]]></return_msg>");
							sb.append("</xml>");
						}
					}else{
						log.info(" WeChat payment verification is FAIL. ! ");
						sb.append("<xml>");
						sb.append("<return_code><![CDATA[FAIL]]></return_code>");
						sb.append("<return_msg><![CDATA[微信付款失败,请联系客服!]]></return_msg>");
						sb.append("</xml>");
					}
				}else{
					log.info(" Signature verification successful ! ");
					sb.append("<xml>");
					sb.append("<return_code><![CDATA[FAIL]]></return_code>");
					sb.append("<return_msg><![CDATA[微信付款失败,请联系客服!]]></return_msg>");
					sb.append("</xml>");
				}
			}else{
				sb.append("<xml>");
				sb.append("<return_code><![CDATA["+weixinResult.get("return_code")+"]]></return_code>");
				sb.append("<return_msg><![CDATA["+weixinResult.get("return_msg")+"]]></return_msg>");
				sb.append("</xml>");
			}
			inputStreamReader.close();  
            inputStream.close();  
            inputStream = null; 
            log.info(" return weixin interface to result message {} " ,sb.toString());
            return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			sb.append("<xml>");
			sb.append("<return_code><![CDATA[FAIL]]></return_code>");
			sb.append("<return_msg><![CDATA[系统支付回调异常,请您联系客服!]]></return_msg>");
			sb.append("</xml>");
			return sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			sb.append("<xml>");
			sb.append("<return_code><![CDATA[FAIL]]></return_code>");
			sb.append("<return_msg><![CDATA[系统支付回调异常,请您联系客服!]]></return_msg>");
			sb.append("</xml>");
			return sb.toString();
		}
	}
	
	
	
	/**
	 * 微信订单查询接口
	 * @param json
	 * @return
	 */
	public JSONObject payOrderQuery(String json){
		JSONObject result=new JSONObject();
		try {
			JSONObject jsonParameter=JSONObject.parseObject(json);
			if(StringUtils.isEmpty(jsonParameter.getString("mchId"))){
				result.put("code", "F00002");
				result.put("message", "微信订单查询失败,参数商品号不能为空!");
				return result;
			}else if(StringUtils.isEmpty(jsonParameter.getString("transactionId"))
					&&StringUtils.isEmpty(jsonParameter.getString("outTradeNo"))){
				result.put("code", "F00003");
				result.put("message", "微信订单查询失败,参数微信订单号、商户订单号（至少一个参数）不能为空!");
				return result;
			}
			String mchId=jsonParameter.getString("mchId");
			String transactionId=jsonParameter.getString("transactionId");
			String outTradeNo=jsonParameter.getString("outTradeNo");
			StringBuffer reqXml=new StringBuffer();
			reqXml.append("<xml>");
			reqXml.append("<appid>"+appId+"</appid>");
			reqXml.append("<mch_id>"+mchId+"</mch_id>");
			Map<String, Object> map=new LinkedHashMap<String, Object>();
			map.put("appid", appId);
			map.put("mch_id", mchId);
			if(!StringUtils.isEmpty(jsonParameter.getString("transactionId"))){
				map.put("transaction_id", transactionId);
				reqXml.append("<transaction_id>"+transactionId+"</transaction_id>");
			}else{
				map.put("out_trade_no", outTradeNo);
				reqXml.append("<out_trade_no>"+outTradeNo+"</out_trade_no>");
			}
			String nonceStr=CreateNonceStrUtil.createNonceStr(map, key);
			map.put("nonce_str", nonceStr);
			reqXml.append("<nonce_str>"+nonceStr+"</nonce_str>");
			String sign=CreateSignUtil.createSign(map, key);
			reqXml.append("<sign>"+sign+"</sign>");
			reqXml.append("</xml>");
			log.info(" send  weixin interface to paramater message {} ",reqXml.toString());
			JSONObject weixinResult=HttpClientUtil.httpsWeixinRequest(orderQueryUrl, "POST", reqXml.toString());
			log.info(" weixin orderquery interface to result message {} ",weixinResult);
			if(weixinResult.getString("code").equals("SUC000")){
				JSONObject weixinData=weixinResult.getJSONObject("data");
				//WeixinOrderRespDto weixinOrderRespDto=(WeixinOrderRespDto) weiXinXmlUtil.XmlStrToObject(data);
				if(weixinData.getString("return_code").equals("SUCCESS")){
					if(weixinData.getString("result_code").equals("SUCCESS")){
						result.put("code", "SUC000");
						result.put("message", "处理成功");
						result.put("data", weixinData);
						return result;
					}else{
						result.put("code", "F00011");
						result.put("message", "微信订单查询失败,请您联系客服!");
						result.put("err_code", StringUtils.isEmpty(weixinData.getString("err_code"))?"微信订单查询失败,请联系客服!":weixinData.getString("err_code"));
						result.put("err_code_des", StringUtils.isEmpty(weixinData.getString("err_code_des"))?"微信订单查询失败,请联系客服!":weixinData.getString("err_code_des"));
						return result;
					}
				}else{
					result.put("code", "F00010");
					result.put("message", StringUtils.isEmpty(weixinData.getString("return_msg"))?"微信订单查询失败,请联系客服!":weixinData.getString("return_msg"));
					return result;
				}
				
			}else{
				result.put("code", "F00009");
				result.put("message", weixinResult.getString("message"));
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "微信订单查询异常,请您联系客服!");
			return result;
		}
	}
	
	/**
	 * 关闭微信订单接口
	 * @param json
	 * @return
	 */
	public JSONObject payCloseOrder(String json){
		JSONObject result=new JSONObject();
		try{
			JSONObject jsonParameter=JSONObject.parseObject(json);
			if(StringUtils.isEmpty(jsonParameter.getString("mchId"))){
				result.put("code", "F00002");
				result.put("message", "微信订单查询失败,参数商品号不能为空!");
				return result;
			}else if(StringUtils.isEmpty(jsonParameter.getString("outTradeNo"))){
				result.put("code", "F00003");
				result.put("message", "微信订单查询失败,参数商户订单号不能为空!");
				return result;
			}
			String mchId=jsonParameter.getString("mchId");
			String outTradeNo=jsonParameter.getString("outTradeNo");
			StringBuffer reqXml=new StringBuffer();
			reqXml.append("<xml>");
			reqXml.append("<appid>"+appId+"</appid>");
			reqXml.append("<mch_id>"+mchId+"</mch_id>");
			Map<String, Object> map=new LinkedHashMap<String, Object>();
			map.put("appid", appId);
			map.put("mch_id", mchId);
			map.put("out_trade_no", outTradeNo);
			reqXml.append("<out_trade_no>"+outTradeNo+"</out_trade_no>");
			String nonceStr=CreateNonceStrUtil.createNonceStr(map, key);
			map.put("nonce_str", nonceStr);
			reqXml.append("<nonce_str>"+nonceStr+"</nonce_str>");
			String sign=CreateSignUtil.createSign(map, key);
			reqXml.append("<sign>"+sign+"</sign>");
			reqXml.append("</xml>");
			log.info(" send  weixin interface to paramater message {} ",reqXml.toString());
			JSONObject orderQueryResult=payOrderQuery(json);
			log.info(" send  weixin payCloseOrder-->orderQueryResult interface to result message {} ",orderQueryResult);
			if(orderQueryResult.getString("code").equals("SUC000")){
				JSONObject weixinResult=HttpClientUtil.httpsWeixinRequest(closeOrderUrl, "POST", reqXml.toString());
				log.info(" weixin closeorder interface to result message {} ",weixinResult);
				if(weixinResult.getString("code").equals("SUC000")){
					JSONObject weixinData=weixinResult.getJSONObject("data");
					//WeixinOrderRespDto weixinOrderRespDto=(WeixinOrderRespDto) weiXinXmlUtil.XmlStrToObject(data);
					if(weixinData.getString("return_code").equals("SUCCESS")){
						if(weixinData.getString("result_code").equals("SUCCESS")){
							result.put("code", "SUC000");
							result.put("message", "处理成功");
							result.put("data", weixinData);
							return result;
						}else{
							result.put("code", "F00011");
							result.put("message", "关闭微信失败,请您联系客服!");
							result.put("err_code", StringUtils.isEmpty(weixinData.getString("err_code"))?"关闭微信订单失败,请联系客服!":weixinData.getString("err_code"));
							result.put("err_code_des", StringUtils.isEmpty(weixinData.getString("err_code_des"))?"关闭微信订单失败,请联系客服!":weixinData.getString("err_code_des"));
							return result;
						}
					}else{
						result.put("code", "F00010");
						result.put("message", StringUtils.isEmpty(weixinData.getString("return_msg"))?"关闭微信订单失败,请联系客服!":weixinData.getString("return_msg"));
						return result;
					}
					
				}else{
					result.put("code", "F00009");
					result.put("message", weixinResult.getString("message"));
					return result;
				}
			}else{
				return orderQueryResult;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "微信关闭订单异常,请您联系客服!");
			return result;
		}
	}
}
