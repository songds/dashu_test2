package com.wechat.pp.dto;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

import lombok.Data;
/**
 * 微信统一下单数据表
 * @author ex-songdeshun
 *
 */
@Data
@XStreamAlias("xml")
public class WeixinOrderReqDto {
	
	/**
	 * 用户名
	 */
	//private String userName;

	/**
	 * 应用ID
	 */
	@XStreamAlias(value="appid")
	private String appId;
	/**
	 * 商户号
	 */
	@XStreamAlias(value="mch_id")
	private String mchId;
	/**
	 * 设备号
	 */
	@XStreamAlias(value="device_info")
	private String deviceInfo;
	/**
	 * 随机字符串
	 */
	@XStreamAlias(value="nonce_str")
	private String nonceStr;
	/**
	 * 签名
	 */
	@XStreamAlias(value="sign")
	private String sign;
	/**
	 * 签名类型,默认MD5
	 */
	@XStreamAlias(value="sign_type")
	private String signType="MD5";
	/**
	 * 商品描述
	 */
	@XStreamAlias(value="body")
	private String body;
	/**
	 * 商品详情
	 */
	@XStreamAlias(value="detail")
	private String detail;
	/**
	 * 附加数据
	 */
	@XStreamAlias(value="attach")
	private String attach;
	/**
	 * 商户订单号
	 */
	@XStreamAlias(value="out_trade_no")
	private String outTradeNo;
	/**
	 * 货币类型,默认CNY
	 */
	@XStreamAlias(value="fee_type")
	private String feeType="CNY";
	/**
	 * 总金额
	 */
	@XStreamAlias(value="total_fee")
	private int totalFee;
	/**
	 * 终端IP
	 */
	@XStreamAlias(value="spbill_create_ip")
	private String spbillCreateIp;
	/**
	 * 交易起始时间
	 */
	@XStreamAlias(value="time_start")
	private String timeStart;
	/**
	 * 交易结束时间
	 */
	@XStreamAlias(value="time_expire")
	private String timeExpire;
	/**
	 * 订单优惠标记
	 */
	@XStreamAlias(value="goods_tag")
	private String goodsTag;
	/**
	 * 通知地址
	 */
	@XStreamAlias(value="notify_url")
	private String notifyUrl;
	/**
	 * 交易类型
	 */
	@XStreamAlias(value="trade_type")
	private String tradeType;
	/**
	 * 指定支付方式,默认no_credit--不允许信用卡支付
	 */
	@XStreamAlias(value="limit_pay")
	private String limitPay="no_credit";
	/**
	 * 场景信息
	 */
	@XStreamAlias("scene_info")
	private String sceneInfo;
	
	/*public static void main(String[] args) {
		WeixinOrderReqDto weixinOrderDto=new WeixinOrderReqDto();
		XmlFriendlyReplacer xmlFriendlyReplacer=new XmlFriendlyReplacer("-_", "_");
		StaxDriver staxDriver=new StaxDriver(xmlFriendlyReplacer);
		XStream xStream=new XStream(staxDriver);
		xStream.autodetectAnnotations(true);
		String xml=xStream.toXML(weixinOrderDto);
		System.out.println(xml);
	}*/
}
