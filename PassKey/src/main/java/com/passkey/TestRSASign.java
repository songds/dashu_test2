package com.passkey;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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
}
