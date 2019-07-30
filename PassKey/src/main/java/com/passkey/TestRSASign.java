package com.passkey;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class TestRSASign {
	/** 
     * 签名算法 
     */  
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    /**
     * RSA2
     */
    private static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";
    
    public static String sign(String content,String private_key,String encode){
    	try {
    		byte[] buff=Base64.decode(private_key);
    		PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec(buff);
    		KeyFactory keyf                 = KeyFactory.getInstance("RSA");  
            PrivateKey priKey               = keyf.generatePrivate(priPKCS8);  
            Signature signature = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);  
            signature.initSign(priKey);  
            signature.update( content.getBytes(encode));  
            byte[] signed = signature.sign();  
            return Base64.encode(signed);  
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return null;
    }
    
    public static boolean doCheck(String content, String sign, String publicKey,String encode)  
    {  
        try   
        {  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            byte[] encodedKey = Base64.decode(publicKey);  
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));  
  
          
            Signature signature = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);  
            signature.initVerify(pubKey);  
            signature.update( content.getBytes(encode) );  
            boolean bverify = signature.verify( Base64.decode(sign) );  
            return bverify;  
              
        }   
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
          
        return false;  
    }
    
    
    public static void main(String[] args) throws ParseException {
    	/*Map<String, Object> parameter=JSONObject.parseObject("{\"a\":\"1\",\"b\":\"2\"}", Map.class);
    	Collection<String> keySet=parameter.keySet();
		List<String> parameterKeys=new ArrayList<String>(keySet);
		Collections.sort(parameterKeys);
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < parameterKeys.size(); i++) {
			String k=parameterKeys.get(i);
			String v =  parameter.get(k)!=null? parameter.get(k).toString():null;
	        //为空不参与签名、参数名区分大小写  
	        if (null != v && !"".equals(v) && !"sign".equals(k)  
	                && !"key".equals(k)) {  
	        	if(i>=parameterKeys.size()-1){
	        		sb.append(k + "=" + v );
	        	}else{
	        		sb.append(k + "=" + v + "&");
	            }  
	        }  
		}
		String str=sb.toString();
		//System.out.println(str);
		JSONObject json=JSONObject.parseObject("{\"biz_content\":\"{\\\"ch_given_name\\\":\\\"1234\\\",\\\"crt_date_end\\\":\\\"1234\\\",\\\"crt_date_start\\\":\\\"1234\\\",\\\"current_num\\\":\\\"1\\\",\\\"cus_type\\\":\\\"100\\\",\\\"ent_no\\\":\\\"1234\\\",\\\"option\\\":\\\"2\\\",\\\"page_size\\\":\\\"5\\\"}\",\"charset\":\"utf-8\",\"content_type\":\"json\",\"partner_id\":\"UBFS\",\"sign_type\":\"RSA2\",\"third_trade_no\":\"hcn201802046101006OPNE026113\",\"timestamp\":\"2017-07-2 13:07:50\",\"trade_code\":\"queryOpenCifAccInfoList\",\"version\":\"1.0\"}");
		String content="{\"biz_content\":\"{\\\"ch_given_name\\\":\\\"1234\\\",\\\"crt_date_end\\\":\\\"1234\\\",\\\"crt_date_start\\\":\\\"1234\\\",\\\"current_num\\\":\\\"1\\\",\\\"cus_type\\\":\\\"100\\\",\\\"ent_no\\\":\\\"1234\\\",\\\"option\\\":\\\"2\\\",\\\"page_size\\\":\\\"5\\\"}\",\"charset\":\"utf-8\",\"content_type\":\"json\",\"partner_id\":\"UBFS\",\"sign_type\":\"RSA2\",\"third_trade_no\":\"hcn201802046101006OPNE026113\",\"timestamp\":\"2017-07-2 13:07:50\",\"trade_code\":\"queryOpenCifAccInfoList\",\"version\":\"1.0\"}";
		String url="D:/deshun_song/git_hub/dsfgWeb/ndsfgWeb/docs";
		String privateUrl=url+"/pkcs8_private_key.pem";
		String publicUrl=url+"/rsa_public_key.pem";
		String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMtem2G+OU0k13Bl4Lwv7wTO1W7JSr+4kvRUKej/VnDAycRDdqJPQeb1yKMtJx2KwI6nXjXwBc5AR7S6SYddnP85k3bnrb2au9qj9OrvobqNIF7POSMQnYA5hW0dQp6YoFz6gBuxvDq1SSC3iGWf15HxA8cY5LNBBslQAMtHXXpXAgMBAAECgYAbiYHsqD9GDNkHJSRbFvp8Yi1QRP1UHH/5iXPrUre3qMMpSiFENQstZNZ4EeMcAu+z4UHGd+uxU87hz9+o7qoVPzEWjsJLNiwvsGGAq7DkYf9MuKp4tIciQNaEBg4WQ+M1znQylSizx8zd6njC1F1mvA7/AcC6WpAD2U8R7ITYEQJBAO7op8NZoMUtr7S/ZpOz05TLdWU4Ysrqx1eRjHCgzCXmmSpGP70JYyR1BpE5j7N2J3RrT5JkUyClkUUDtI//cGUCQQDZ6xcm17gyoyxcR24rTbv13CHI4SbFNl0Hx4qwnCiXCC73OpRQsAumvA0DjhdgZt2MpJwy6BxpPW9kvPPDk64LAkAoqD1MmGcsxy6csunw/uxl6Q4McMEXSUctVt6wmBzF4m10bUoRkJRnsuawNjyHTPc/77c18Y7YAnUraosC6nSlAkBX7DxZFaxh8egSC1ft7N0rY3fWHtGPvFFotB/HBhpyZYDdiysQKxdoXHM7vfa8lIRLHJdRCsl7xKfPE1We0VTlAkEAsicDriBXz6IyGbCURXGLDzA5WWZU95CEedOIW91rn7HmwhMbFH3X4prsxtf+5smT0BmsyEnGHJuBech9kt1NTA==";
		System.out.println(sign(content, privateKey, "utf-8"));
		String sign="YwXyuyek2dzpkSGy/0/s8uFGyHvGnfAcdMxmLb/3OAhn387MBnHDzPcLzRLaXtsYEzPJrA36wZuhE6yGq7A54vj72cc+eQpsnT4xdjs6x1DuT1/XO10RrZyt2dL5+EndQYmAZt/xCAnIAQWN5VGusp8MysiVHNxgcb/yCxGXjb4=";
		String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDLXpthvjlNJNdwZeC8L+8EztVuyUq/uJL0VCno/1ZwwMnEQ3aiT0Hm9cijLScdisCOp1418AXOQEe0ukmHXZz/OZN25629mrvao/Tq76G6jSBezzkjEJ2AOYVtHUKemKBc+oAbsbw6tUkgt4hln9eR8QPHGOSzQQbJUADLR116VwIDAQAB";
		System.out.println(doCheck(content, sign, publicKey, "utf-8"));*/
    	String f="2018/05/31";
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
    	String assureBeginDate=f.replaceAll("/", "").replaceAll("-", "");
		Date dt=sdf.parse(assureBeginDate);
		Calendar c=Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.MONTH, Integer.valueOf(12));
		String assureFinishDate=sdf.format(c.getTime());
		System.out.println(assureFinishDate);
    	
		
	}
}
