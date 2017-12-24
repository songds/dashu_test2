package com.passkey;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;


public class TestRSAPasskey {

	private static final String ALGOGRITHM="RSA";
	private static final String PUBLIC_KEY_PATH="public.key";
	private static final String PRIVATE_KEY_PATH="private.key";
	
	private static final String RSA_TYPE="RSA2";
	
	 /** *//** 
     * RSA最大加密明文大小 
     */  
    private static final int MAX_ENCRYPT_BLOCK = 117;  
      
    /** *//** 
     * RSA最大解密密文大小 
     */  
    private static final int MAX_DECRYPT_BLOCK = 128; 
    
    private static final int BLOCK_SIZE = 245;    //一次RSA加密操作所允许的最大长度
    
    //这个值与 KEY_SIZE 已经padding方法有关。因为 1024的key的输出是128，2048key输出是256字节可能11个字节用于保存padding信息了，所以最多可用的就只有245字节了。  
    private static final int OUTPUT_BLOCK_SIZE = 256;
    
	public static void main(String[] args)  { 
		try {
			Map<String, Key> map=createKey(2048); 
			String public_key=Base64.encode(map.get(PUBLIC_KEY_PATH).getEncoded());
			String private_key=Base64.encode(map.get(PRIVATE_KEY_PATH).getEncoded());
			System.out.println("PUBLIC_KEY_PATH:"+public_key);
			System.out.println("PRIVATE_KEY_PATH:"+private_key);
			String privateStr=privateToKey("钟桂平", private_key);
			System.out.println("privateStr:"+privateStr);
			String decryptPublicStr=decryptPublicKey(privateStr, public_key);
			System.out.println("decryptPublicStr:"+decryptPublicStr);
			
			String publicStr=publicToKey("钟桂平!123", public_key);
			System.out.println("publicStr:"+publicStr);
			String decryptPrivateStr=decryptPrivateKey(publicStr, private_key);
			System.out.println("decryptPrivateStr:"+decryptPrivateStr);
			
			  String content="ihep_这是用于签名的原始数据";  
		        String signstr=TestRSASign.sign(content,private_key,"utf-8");  
		        System.out.println("签名原串："+content);  
		        System.out.println("签名串："+signstr);  
		        System.out.println();  
		          
		        System.out.println("---------------公钥校验签名------------------");  
		        System.out.println("签名原串："+content);  
		        System.out.println("签名串："+signstr);  
		          
		        System.out.println("验签结果："+TestRSASign.doCheck(content, signstr, public_key,"utf-8"));
		        System.out.println();  
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	/**
	 * 生成公钥私钥对
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static Map<String, Key> createKey(int max_keySize) throws NoSuchAlgorithmException{
		KeyPairGenerator generator=KeyPairGenerator.getInstance(ALGOGRITHM);
		generator.initialize(max_keySize);
		KeyPair keyPair=generator.generateKeyPair();
		PublicKey public_key=keyPair.getPublic();
		PrivateKey private_key=keyPair.getPrivate();
		Map<String, Key> map=new HashMap<>();
		map.put(PUBLIC_KEY_PATH, public_key);
		map.put(PRIVATE_KEY_PATH, private_key);
		//Cipher cipher = Cipher.getInstance(ALGOGRITHM); 
		return map;
	}
	/**
	 * 根据私钥加密数据
	 * @param data 内容
	 * @param private_key 私钥
	 * @return
	 */
	public static String privateToKey(String data,String private_key){
		try {
			byte[] buffer = Base64.decode(private_key);  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            PrivateKey privateKey= keyFactory.generatePrivate(keySpec); 
			Cipher cipher = Cipher.getInstance(ALGOGRITHM);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] enData=data.getBytes("utf-8");
			int inputLen = enData.length;  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();  
	        int offSet = 0;  
	        byte[] cache;  
	        int i = 0;  
	        if(RSA_TYPE.equals("RSA2")){
	        	 // 对数据分段加密  
		        while (inputLen - offSet > 0) {  
		            if (inputLen - offSet > BLOCK_SIZE) {  
		                cache = cipher.doFinal(enData, offSet, BLOCK_SIZE);  
		            } else {  
		                cache = cipher.doFinal(enData, offSet, inputLen - offSet);  
		            }  
		            out.write(cache, 0, cache.length);  
		            i++;  
		            offSet = i * BLOCK_SIZE;  
		        }  
	        }else{
	        	 // 对数据分段加密  
		        while (inputLen - offSet > 0) {  
		            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
		                cache = cipher.doFinal(enData, offSet, MAX_ENCRYPT_BLOCK);  
		            } else {  
		                cache = cipher.doFinal(enData, offSet, inputLen - offSet);  
		            }  
		            out.write(cache, 0, cache.length);  
		            i++;  
		            offSet = i * MAX_ENCRYPT_BLOCK;  
		        }  
	        }
	       
	        byte[] encryptedData = out.toByteArray();  
	        out.close();  
			return Base64.encode(encryptedData);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Base64DecodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * 根据公钥解密数据
	 * @param cipherData
	 * @param public_key
	 * @return
	 */
	public static String decryptPublicKey(String cipherData,String public_key){
		try {
			byte[] buffer= Base64.decode(public_key);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGOGRITHM);  
	        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
	        PublicKey publicKey=keyFactory.generatePublic(keySpec);  
			Cipher cipher = Cipher.getInstance(ALGOGRITHM);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);  
			byte[] encryptedData= Base64.decode(cipherData);
			int inputLen = encryptedData.length;  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();  
	        int offSet = 0;  
	        byte[] cache;  
	        int i = 0;  
	        if(RSA_TYPE.equals("RSA2")){
	        	// 对数据分段解密  
		        while (inputLen - offSet > 0) {  
		            if (inputLen - offSet >OUTPUT_BLOCK_SIZE) {  
		                cache = cipher.doFinal(encryptedData, offSet, OUTPUT_BLOCK_SIZE);  
		            } else {  
		                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
		            }  
		            out.write(cache, 0, cache.length);  
		            i++;  
		            offSet = i * OUTPUT_BLOCK_SIZE;  
		        }  
	        }else{
	        	// 对数据分段解密  
		        while (inputLen - offSet > 0) {  
		            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
		                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
		            } else {  
		                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
		            }  
		            out.write(cache, 0, cache.length);  
		            i++;  
		            offSet = i * MAX_DECRYPT_BLOCK;  
		        }  
	        }
	        
	        byte[] decryptedData = out.toByteArray();  
	        out.close();  
			return new String(decryptedData,"utf-8");
		} catch (Base64DecodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return null;
	}
	
	
	/**
	 * 根据公钥加密数据
	 * @param data 内容
	 * @param private_key 私钥
	 * @return
	 */
	public static String publicToKey(String data,String public_key){
		try {
			byte[] buffer = Base64.decode(public_key);  
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            PublicKey publicKey= keyFactory.generatePublic(keySpec); 
			Cipher cipher = Cipher.getInstance(ALGOGRITHM);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] enData=data.getBytes("utf-8");
			int inputLen = enData.length;  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();  
	        int offSet = 0;  
	        byte[] cache;  
	        int i = 0;  
	        if(RSA_TYPE.equals("RSA2")){
	        	 // 对数据分段加密  
		        while (inputLen - offSet > 0) {  
		            if (inputLen - offSet > BLOCK_SIZE) {  
		                cache = cipher.doFinal(enData, offSet, BLOCK_SIZE);  
		            } else {  
		                cache = cipher.doFinal(enData, offSet, inputLen - offSet);  
		            }  
		            out.write(cache, 0, cache.length);  
		            i++;  
		            offSet = i * BLOCK_SIZE;  
		        }  
	        }else{
	        	 // 对数据分段加密  
		        while (inputLen - offSet > 0) {  
		            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
		                cache = cipher.doFinal(enData, offSet, MAX_ENCRYPT_BLOCK);  
		            } else {  
		                cache = cipher.doFinal(enData, offSet, inputLen - offSet);  
		            }  
		            out.write(cache, 0, cache.length);  
		            i++;  
		            offSet = i * MAX_ENCRYPT_BLOCK;  
		        }  
	        }
	       
	        byte[] encryptedData = out.toByteArray();  
	        out.close();  
			return Base64.encode(encryptedData);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Base64DecodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 根据私钥解密数据
	 * @param cipherData
	 * @param private_key
	 * @return
	 */
	public static String decryptPrivateKey(String cipherData,String private_key){
		try {
			byte[] buffer= Base64.decode(private_key);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGOGRITHM);  
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
	        PrivateKey privateKey=keyFactory.generatePrivate(keySpec);  
			Cipher cipher = Cipher.getInstance(ALGOGRITHM);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);  
			byte[] encryptedData= Base64.decode(cipherData);
			int inputLen = encryptedData.length;  
	        ByteArrayOutputStream out = new ByteArrayOutputStream();  
	        int offSet = 0;  
	        byte[] cache;  
	        int i = 0;  
	        if(RSA_TYPE.equals("RSA2")){
	        	 // 对数据分段解密  
		        while (inputLen - offSet > 0) {  
		            if (inputLen - offSet > OUTPUT_BLOCK_SIZE) {  
		                cache = cipher.doFinal(encryptedData, offSet, OUTPUT_BLOCK_SIZE);  
		            } else {  
		                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
		            }  
		            out.write(cache, 0, cache.length);  
		            i++;  
		            offSet = i * OUTPUT_BLOCK_SIZE;  
		        }  
	        }else{
		        // 对数据分段解密  
		        while (inputLen - offSet > 0) {  
		            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
		                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
		            } else {  
		                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
		            }  
		            out.write(cache, 0, cache.length);  
		            i++;  
		            offSet = i * MAX_DECRYPT_BLOCK;  
		        }  
	        }
	        byte[] decryptedData = out.toByteArray();  
	        out.close();  
			return new String(decryptedData,"utf-8");
		} catch (Base64DecodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return null;
	}
}
