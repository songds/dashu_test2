package com.wechat.pp.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.wechat.pp.dao.AlibabaPayInfoDao;
import com.wechat.pp.dao.TradingRecordInfoDao;
import com.wechat.pp.po.AlibabaPayInfoPo;
import com.wechat.pp.po.TradingRecordInfoPo;
import com.wechat.pp.util.CreateOrderNoUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * 支付宝支付功能
 * @author songds
 */
@Service
@Slf4j
public class AliPayService {

	@Value("${alibaba.appid}")
	private String appId;
	
	@Value("${alibaba.public.key}")
	private String publicKey;
	
	@Value("${alibaba.private.key}")
	private String privateKey;
	
	@Value("${alibaba.notifyUrl}")
	private String notifyUrl;
	
	@Value("${alibaba.trade.app.pay.url}")
	private String payUrl;
	
	@Value("${alibaba.charset}")
	private String charset;
	
	@Value("${alibaba.timeoutExpress}")
	private String timeoutExpress;
	
	@Resource
	private AlibabaPayInfoDao alibabaPayInfoDao;
	
	@Resource
	private TradingRecordInfoDao tradingRecordInfoDao;
	
	@Resource
	private UserMemberRelationService userMemberRelationService;
	
	/**
	 * 支付宝支付接口
	 * attach:{"userName":"","memberId":"","memberType":"","cardType":""}
	 * @return
	 */
	public JSONObject alibabaPay(String json){
		JSONObject result=new JSONObject();
		try{
			if(!StringUtils.isEmpty(json)){
				JSONObject parameter=JSONObject.parseObject(json);
				if(StringUtils.isEmpty(parameter.getString("body"))){
					result.put("code", "F00003");
					result.put("message", "支付宝下单失败,参数商品描述不能为空值!");
					return result;
				}else if(StringUtils.isEmpty(parameter.getString("totalAmount"))&&parameter.getFloatValue("totalAmount")<0){
					result.put("code", "F00006");
					result.put("message", "支付宝下单失败,参数商户总金额不能小于零!");
					return result;
				}else if(StringUtils.isEmpty(parameter.getString("subject"))){
					result.put("code", "F00007");
					result.put("message", "支付宝下单失败,参数订单标题不能为空!");
					return result;
				}else if(StringUtils.isEmpty(parameter.getString("attach"))){
					result.put("code", "F00008");
					result.put("message", "支付宝下单失败,参数附加参数不能为空!");
					return result;
				}
				String outTradeNo=CreateOrderNoUtil.createOrderNo();
				String body=parameter.getString("body");
				String totalAmount=parameter.getString("totalAmount");
				String subject=parameter.getString("subject");
				String attach=parameter.getString("attach");
				//实例化客户端
				AlipayClient alipayClient = new DefaultAlipayClient(payUrl, appId, privateKey, "json", charset, publicKey, "RSA2");
				//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
				AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
				//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
				AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
				model.setBody(body);
				model.setSubject(subject);
				model.setOutTradeNo(outTradeNo);
				model.setTimeoutExpress(timeoutExpress);
				model.setTotalAmount(totalAmount);
				model.setPassbackParams(attach);
				request.setBizModel(model);
				request.setNotifyUrl(notifyUrl);
				log.info(" alibaba interface to input parameter message {} ",model);
		        //这里和普通的接口调用不同，使用的是sdkExecute
		        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
		        String alibabaBody=response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
		        log.info(" alibaba interface to result message {} ",alibabaBody);
		        AlibabaPayInfoPo alibabaPayInfo=new AlibabaPayInfoPo();
		        alibabaPayInfo.setBody(model.getBody());
		        alibabaPayInfo.setSubject(subject);
		        alibabaPayInfo.setOutTradeNo(outTradeNo);
		        alibabaPayInfo.setTimeoutExpress(timeoutExpress);
		        alibabaPayInfo.setTotalAmount(Float.valueOf(model.getTotalAmount()));
		        alibabaPayInfo.setAttach(model.getPassbackParams());
		        
		        alibabaPayInfoDao.save(alibabaPayInfo);
		        result.put("code", "SUC000");
				result.put("message", "支付宝下单成功!");
				result.put("data", alibabaBody);
				return result;
			}else{
				result.put("code", "F00002");
				result.put("message", "支付宝下单失败,参数不能为空值,请您重新下单!");
				return result;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e.getMessage());
			result.put("code", "F00001");
			result.put("message", "支付宝下单异常,请您联系系统管理员!");
			return result;
		}
	}
	
	/**
	 * 支付宝回调接口
	 * @param request
	 * @return
	 */
	@Transactional
	public String aliPayCallBack(HttpServletRequest request){
		try {
			Map<String,String> params = new HashMap<String,String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			    String name = (String) iter.next();
			    String[] values = (String[]) requestParams.get(name);
			    String valueStr = "";
			    for (int i = 0; i < values.length; i++) {
			        valueStr = (i == values.length - 1) ? valueStr + values[i]
			                    : valueStr + values[i] + ",";
			  	}
			    //乱码解决，这段代码在出现乱码时使用。
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
			//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
			boolean flag = AlipaySignature.rsaCheckV1(params, publicKey, charset,"RSA2");
			if(flag){
				String code=params.get("code");
				String outTradeNo=params.get("out_trade_no");
				AlibabaPayInfoPo alibabaPayInfo=alibabaPayInfoDao.getByOutTradeNo(outTradeNo);
				float totalAmount=alibabaPayInfo.getTotalAmount();
				if(totalAmount==Float.valueOf(params.get("total_amount"))){
					if(StringUtils.isEmpty(alibabaPayInfo.getTradeStatus())){
						if(code.equals("10000")){
							String tradeStatus = params.get("trade_status");
							if(tradeStatus.equals("TRADE_FINISHED")||tradeStatus.equals("TRADE_SUCCESS")){
								alibabaPayInfo.setTradeStatus(tradeStatus);
								alibabaPayInfoDao.save(alibabaPayInfo);
								String attach=alibabaPayInfo.getAttach();
								log.info(" update alibabaPayInfo is table to message {} ", JSONObject.toJSONString(alibabaPayInfo));
								JSONObject attachJson=JSONObject.parseObject(attach);
								TradingRecordInfoPo tradingRecordInfo=new TradingRecordInfoPo();
								tradingRecordInfo.setUserName(attachJson.getString("userName"));
								tradingRecordInfo.setTradingAmt((int)(Float.valueOf(alibabaPayInfo.getTotalAmount())*100));
								tradingRecordInfo.setTradingType("2");
								tradingRecordInfo.setTradingDesc(alibabaPayInfo.getSubject());
								tradingRecordInfo.setTradingId(alibabaPayInfo.getOutTradeNo());
								tradingRecordInfo.setTradingTime(params.get("gmt_payment")!=null?params.get("time_end").toString():"");
								tradingRecordInfo.setTradingStatus("SUCCESS");
								tradingRecordInfoDao.save(tradingRecordInfo);
								log.info(" insert into tradingRecordInfo is table to message {} ", JSONObject.toJSONString(tradingRecordInfo));
								//String attach=weixinResult.get("attach")!=null?weixinResult.get("attach").toString():"";
								if(!StringUtils.isEmpty(attach)){
									//String[] attachs=attach.split("#");
									//JSONObject memberJson=new JSONObject();
									//memberJson.put("userName", attachs[0]);
									//memberJson.put("memberId", attachs[1]);
									//memberJson.put("memberType", attachs[2]);
									//memberJson.put("cardType", attachs[3]);
									log.info(" load   userMemberRelationService--->buyMember is parameter to message {} ",attach);
									JSONObject memberResult=userMemberRelationService.buyMember(JSONObject.toJSONString(attach));
									log.info(" load   userMemberRelationService--->buyMember is result to message {}",JSONObject.toJSONString(memberResult));
								}
								return "success";
							}else{
								alibabaPayInfo.setTradeStatus(tradeStatus);
								alibabaPayInfoDao.save(alibabaPayInfo);
								log.info(" interface aliPayCallBack is WeChat payment failed. ");
								return "success";
							}
						}else{
							alibabaPayInfo.setTradeStatus("fail");
							alibabaPayInfoDao.save(alibabaPayInfo);
							log.info(" interface aliPayCallBack is WeChat payment failed. ");
							return "success";
						}
					}else{
						log.info(" interface aliPayCallBack is The order has been paid successfully and is not allowed to be repeated. ");
						return "success";
					}
					
				}else{
					log.info(" interface aliPayCallBack is payment verification is FAIL. ! ");
					return "fail";
				}
			}else{
				log.info(" interface aliPayCallBack Signature verification FAIL ! ");
				return "fail";
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			return "fail";
		}
	}
	
}
