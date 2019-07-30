package com.passkey;

import com.alibaba.fastjson.JSONObject;

public class AlibbPayTest {

	public static void main(String[] args) {
		
	}
	
	public String packPayContent(){
		String appId="2014072300007148";
		String method="alipay.trade.app.pay";
		String format="JSON";
		String charset="utf-8";
		String signType="RSA2";
		String sign="";
		String timestamp="2017-12-25 16:47:50";
		String version="1.0";
		String notifyUrl="https://api.xx.com/receive_notify.htm";
		String bizContent="1.0";
		JSONObject jsonParameter=new JSONObject();
		jsonParameter.put("app_id", appId);
		jsonParameter.put("method", method);
		jsonParameter.put("format", format);
		jsonParameter.put("charset", charset);
		jsonParameter.put("signType", signType);
		jsonParameter.put("sign", sign);
		jsonParameter.put("timestamp", timestamp);
		jsonParameter.put("version", version);
		jsonParameter.put("notify_url", notifyUrl);
		jsonParameter.put("biz_content", bizContent);
		JSONObject bizContentPara=new JSONObject();
		bizContentPara.put("body", "Iphone6 16G");
		bizContentPara.put("subject", "大乐透");
		bizContentPara.put("out_trade_no", "70501111111S001111119");
		bizContentPara.put("timeout_express", "90m");
		bizContentPara.put("total_amount", "10");
		bizContentPara.put("product_code", "QUICK_MSECURITY_PAY");
		bizContentPara.put("goods_type", "0");
		bizContentPara.put("passback_params", "userName#memberId#memberType#cardType");
		bizContentPara.put("promo_params", "");
		bizContentPara.put("extend_params", "credit_group,promotion,voucher,point,mdiscount");
		bizContentPara.put("enable_pay_channels", "");
		bizContentPara.put("disable_pay_channels", "");
		bizContentPara.put("store_id", "");
		bizContentPara.put("ext_user_info", "");
		return null;
	}
}
