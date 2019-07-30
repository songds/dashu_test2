package com.passkey;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class RSAUtil {
	//Begin[版本号：20181214，需求编号：1300 ，开发：宋德顺，修改日期：20181207]
	//private static RSAUtil instance;
	private RSAUtil(){}
	private static BouncyCastleProvider bouncyCastleProvider = null;
	private static class RSAUtilHolder{
        private final static RSAUtil instance=new RSAUtil();
    }
    public static RSAUtil getInstance(){
        return RSAUtilHolder.instance;
    }
	/*public static RSAUtil instance() {
		try {
			if (instance == null) {
				instance = new RSAUtil();
			}
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
  //End[版本号：20181214，需求编号：1300 ，开发：宋德顺，修改日期：20181207]
	public static RSAPrivateKey getRsaPriKeyByFile(String strFile)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		InputStream in_private = new FileInputStream(new File(strFile));
		BufferedReader br = new BufferedReader(new InputStreamReader(in_private));
		String readLine = null;
		StringBuffer sb = new StringBuffer();
		while ((readLine = br.readLine()) != null) {
			if (readLine.charAt(0) != '-') {
				sb.append(readLine);
				sb.append('\r');
			}
		}
//		BASE64Decoder base64Decoder = new BASE64Decoder();
//		byte[] buffer = base64Decoder.decodeBuffer(sb.toString());
//		Decoder decoder = Base64.getDecoder();
//		byte[] buffer = decoder.decode(sb.toString());
		byte[] buffer = Base64.decodeBase64(sb.toString());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
	}

	public static RSAPublicKey getRsaPubKeyByFile(String strFile)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, CertificateException {
		InputStream fin = new FileInputStream(new File(strFile));
		CertificateFactory f = CertificateFactory.getInstance("X.509");
		X509Certificate certificate = (X509Certificate) f.generateCertificate(fin);

		PublicKey pk = certificate.getPublicKey();
		return (RSAPublicKey) pk;
	}

	public RSAPublicKey getRsaPubKey(String strFile)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, CertificateException {
		InputStream in = new FileInputStream(new File(strFile));
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String readLine = null;
		StringBuffer sb = new StringBuffer();
		while ((readLine = br.readLine()) != null) {
			if (readLine.charAt(0) != '-') {
				sb.append(readLine);
				sb.append('\r');
			}
		}
//		BASE64Decoder base64Decoder = new BASE64Decoder();
//		byte[] buffer = base64Decoder.decodeBuffer(sb.toString());
//		Decoder decoder = Base64.getDecoder();
//		byte[] buffer = decoder.decode(sb.toString());
		byte[] buffer = Base64.decodeBase64(sb.toString());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
		return (RSAPublicKey) keyFactory.generatePublic(keySpec);
	}

	private static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception {
		if (privateKey == null) {
			throw new Exception("加密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA", getBouncyCastleProvider());
			cipher.init(1, privateKey);
			return cipher.doFinal(plainTextData);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData) throws Exception {
		if (publicKey == null) {
			throw new Exception("解密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA", getBouncyCastleProvider());
			cipher.init(2, publicKey);
			return cipher.doFinal(cipherData);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String bytesToHexString(byte[] src) {
		StringBuffer stringBuilder = new StringBuffer("");
		if ((src == null) || (src.length <= 0)) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	private static byte[] hexStringToBytes(String hexString) {
		if ((hexString == null) || (hexString.equals(""))) {
			return null;
		}
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = ((byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[(pos + 1)])));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789abcdef".indexOf(c);
	}

	public static String encString(RSAPrivateKey prikey, String src) {
		String encStr = "";
		try {
			byte[] encryptByte = encrypt(prikey, src.getBytes("UTF8"));
			encStr = bytesToHexString(encryptByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encStr;
	}

	public static String encString(RSAPublicKey pubKey, String src) {
		String encStr = "";
		try {
			byte[] encryptByte = encrypt(pubKey, src.getBytes("UTF8"));
			encStr = bytesToHexString(encryptByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encStr;
	}

	private static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA", getBouncyCastleProvider());
			cipher.init(1, publicKey);
			return cipher.doFinal(plainTextData);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA", getBouncyCastleProvider());
			cipher.init(2, privateKey);
			return cipher.doFinal(cipherData);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decString(RSAPublicKey publicKey, String enc) {
		String dec = "";
		try {
			RSAUtil rsaEncryptUtils = new RSAUtil();

			byte[] unDecryptByte = hexStringToBytes(enc);
			byte[] decryptByte = decrypt(publicKey, unDecryptByte);
			dec = new String(decryptByte, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dec;
	}

	public static String decString(RSAPrivateKey priKey, String enc) {
		String dec = "";
		try {
			byte[] unDecryptByte = hexStringToBytes(enc);
			byte[] decryptByte = decrypt(priKey, unDecryptByte);
			dec = new String(decryptByte, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dec;
	}

	public static String signString(String plainText, RSAPrivateKey prikey)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		String signStr = "";

		Signature signet = Signature.getInstance("MD5withRSA");
		signet.initSign(prikey);
		signet.update(plainText.getBytes());
		signStr = bytesToHexString(signet.sign());
		return signStr;
	}

	public static boolean vsign(String info, String signed, RSAPublicKey pubKey)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature signetcheck = Signature.getInstance("MD5withRSA");
		signetcheck.initVerify(pubKey);
		signetcheck.update(info.getBytes());
		if (signetcheck.verify(hexStringToBytes(signed))) {
			return true;
		}
		System.out.println("非签名正常");
		return false;
	}

	public static RSAPublicKey getRsaPubKeyByString(String str)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, CertificateException {
		InputStream fin = new ByteArrayInputStream(str.toString().getBytes());
		CertificateFactory f = CertificateFactory.getInstance("X.509");
		X509Certificate certificate = (X509Certificate) f.generateCertificate(fin);

		PublicKey pk = certificate.getPublicKey();
		return (RSAPublicKey) pk;
	}

	public static RSAPrivateKey getRsaPriKeyByString(String str)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		InputStream in_private = new ByteArrayInputStream(str.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(in_private));
		String readLine = null;
		StringBuffer sb = new StringBuffer();
		while ((readLine = br.readLine()) != null) {
			if (readLine.charAt(0) != '-') {
				sb.append(readLine);
				sb.append('\r');
			}
		}
//		BASE64Decoder base64Decoder = new BASE64Decoder();
//		byte[] buffer = base64Decoder.decodeBuffer(sb.toString());
//		Decoder decoder = Base64.getDecoder();
//		byte[] buffer = decoder.decode(sb.toString());
		byte[] buffer = Base64.decodeBase64(sb.toString());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
	}
	
	public static synchronized BouncyCastleProvider getBouncyCastleProvider() {
        if (bouncyCastleProvider == null) {
            bouncyCastleProvider = new BouncyCastleProvider();
        }
        return bouncyCastleProvider;
    }

	// public static void main(String[] args)
	// throws IOException, InvalidKeyException, NoSuchAlgorithmException,
	// SignatureException, InvalidKeySpecException, CertificateException
	// {
	// RSAUtil rsa = instance();
	//
	// RSAPublicKey unionpayPubkey =
	// getRsaPubKeyByFile("d:\\chenz\\myCerts\\pbcrcpubkey.cer");
	// RSAPrivateKey unionpayPrikey =
	// getRsaPriKeyByFile("d:\\chenz\\myCerts\\pbcrcprivkey.pem");
	//
	// String data = "王五610303199901010101xxxxxxxxxxxxx09090020150330";
	// System.out.println("原始数据：" + data);
	// String encstr = encString(unionpayPubkey, data);
	// System.out.println("银联公钥加密数据：" + encstr);
	//
	// String des = decString(unionpayPrikey, encstr);
	// System.out.println("银联解密数据：" + des);
	//
	// RSAPrivateKey companyPrikey =
	// getRsaPriKeyByFile("d:\\chenz\\myCerts\\pbcid_private.pem");
	// String signStr = signString(encstr, companyPrikey);
	// System.out.println("小贷私钥签名数据：" + signStr);
	//
	// RSAPublicKey companyPublickey =
	// getRsaPubKeyByFile("d:\\chenz\\myCerts\\pbcid_public.cer");
	// boolean result = vsign(encstr, signStr, companyPublickey);
	// System.out.println("小贷公钥验签结果：" + result);
	// }
	
	
	public static String decString(RSAPrivateKey privateKey, String enc, String programType){
	    String dec = "";
	    try
	    {
	      byte[] unDecryptByte = null;
	      if (StringUtils.isBlank(programType)) {
	        unDecryptByte = hexStringToBytes(enc);
	      } else {
	        unDecryptByte = Hex2Bytes(enc);
	      }
	      byte[] decryptByte = decrypt(privateKey, unDecryptByte, programType);
	      dec = new String(decryptByte, "UTF8");
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return dec;
	}
	
	 public static byte[] Hex2Bytes(String hexStr){
	    if (hexStr.length() % 2 != 0) {
	      hexStr = "0" + hexStr;
	    }
	    byte[] bytes = new byte[hexStr.length() / 2];
	    for (int i = 0; i < bytes.length; i++) {
	      bytes[i] = ((byte)Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16));
	    }
	    return bytes;
	  }
	 
	 public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData, String programType)
			    throws Exception
	  {
	    if (privateKey == null) {
	      throw new Exception("解密私钥为空, 请设置");
	    }
	    Cipher cipher = null;
	    try
	    {
	      if (StringUtils.isBlank(programType)) {
	        cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
	      } else {
	        cipher = Cipher.getInstance("RSA");
	      }
	      cipher.init(2, privateKey);
	      return cipher.doFinal(cipherData);
	    }
	    catch (NoSuchAlgorithmException e)
	    {
	      throw new Exception("无此解密算法");
	    }
	    catch (NoSuchPaddingException e)
	    {
	      return null;
	    }
	    catch (InvalidKeyException e)
	    {
	      throw new Exception("解密私钥非法,请检查");
	    }
	    catch (IllegalBlockSizeException e)
	    {
	      throw new Exception("密文长度非法");
	    }
	    catch (BadPaddingException e)
	    {
	      throw new Exception("密文数据已损坏");
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return null;
	  }
}
