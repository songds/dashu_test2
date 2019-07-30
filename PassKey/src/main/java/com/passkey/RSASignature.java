package com.passkey;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


   
/** 
 * RSA签名验签类 
 */ 
public class RSASignature{  
       
    /** 
     * 签名算法 
     */ 
    public static final String SIGN_ALGORITHMS = "SHA256withRSA";  
   
    /** 
    * RSA签名 
    * @param content 待签名数据 
    * @param privateKey 商户私钥 
    * @param encode 字符集编码 
    * @return 签名值 
    */ 
    public static String sign(String content, String privateKey, String encode)  
    {  
        try  
        {  
            PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64.decode(privateKey) );   
               
            KeyFactory keyf                 = KeyFactory.getInstance("RSA");  
            PrivateKey priKey               = keyf.generatePrivate(priPKCS8);  
   
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);  
   
            signature.initSign(priKey);  
            signature.update( content.getBytes(encode));  
   
            byte[] signed = signature.sign();  
               
            return Base64.encode(signed);  
        }  
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
           
        return null;  
    }  
       
    public static String sign(String content, String privateKey)  
    {  
        try  
        {  
           PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64.decode(privateKey) );   
            KeyFactory keyf = KeyFactory.getInstance("RSA");  
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);  
        	//RSAPrivateKey privateKey= RSAUtil.getRsaPriKeyByFile(privateKeyPath);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);  
            signature.initSign(priKey);  
            signature.update( content.getBytes());  
            byte[] signed = signature.sign();  
            return Base64.encode(signed);  
        }  
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
        return null;  
    }  
       
    /** 
    * RSA验签名检查 
    * @param content 待签名数据 
    * @param sign 签名值 
    * @param publicKey 分配给开发商公钥 
    * @param encode 字符集编码 
    * @return 布尔值 
    */ 
    public static boolean doCheck(String content, String sign, String publicKey,String encode)  
    {  
        try  
        {  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            byte[] encodedKey = Base64.decode(publicKey);  
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));  
   
           
            java.security.Signature signature = java.security.Signature  
            .getInstance(SIGN_ALGORITHMS);  
           
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
       
    public static boolean doCheck(String content, String sign, String publicKeyPath) throws IOException  
    {  
    	FileInputStream fins=null;
        try  
        {  CertificateFactory cf = CertificateFactory.getInstance("X.509");
        	fins=new FileInputStream(publicKeyPath);
    		Certificate oCert = cf.generateCertificate(fins);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);  
           
            signature.initVerify(oCert.getPublicKey());  
            signature.update( content.getBytes() );  
           
            boolean bverify = signature.verify( Base64.decode(sign) );  
            return bverify;  
               
        }   
        catch (Exception e)   
        {
            e.printStackTrace();  
        }finally {
			if(fins!=null){
				fins.close();
			}
		}
           
        return false;  
    }  
       
}