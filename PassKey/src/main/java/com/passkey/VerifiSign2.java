package com.passkey;
import java.io.ByteArrayOutputStream;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.security.KeyStore;  
import java.security.PrivateKey;  
import java.security.PublicKey;  
import java.security.cert.Certificate;  
import java.security.cert.CertificateException;  
import java.util.Enumeration;  
  
import com.sun.org.apache.xml.internal.security.utils.Base64;  
  
public class VerifiSign2  
{  
    public static void main(String[] args)  
    {  
    	VerifiSign2 v=new VerifiSign2();
    	 v.getPrivateKeyInfo();  
    	 v.getPublicKeyInfo();  
    }  
  
    /** 
     * 获取私钥别名等信息 
     */  
    public String getPrivateKeyInfo()  
    {  
    String privKeyFileString = "d:/test.pfx";  
    String privKeyPswdString = "cfca1234" ;  
    String keyAlias = null;  
    try  
    {  
        KeyStore keyStore = KeyStore.getInstance("PKCS12");  
        FileInputStream fileInputStream = new FileInputStream(privKeyFileString);  
        char[] nPassword = null;  
        if ((privKeyPswdString == null) || privKeyPswdString.trim().equals(""))  
        {  
        nPassword = null;  
        } else  
        {  
        nPassword = privKeyPswdString.toCharArray();  
        }  
        keyStore.load(fileInputStream, nPassword);  
        fileInputStream.close();  
        System.out.println("keystore type=" + keyStore.getType());  
  
        Enumeration<String> enumeration = keyStore.aliases();  
  
        if (enumeration.hasMoreElements())  
        {  
        keyAlias = (String) enumeration.nextElement();  
        System.out.println("alias=[" + keyAlias + "]");  
        }  
        System.out.println("is key entry=" + keyStore.isKeyEntry(keyAlias));  
        PrivateKey prikey = (PrivateKey) keyStore.getKey(keyAlias, nPassword);  
        Certificate cert = keyStore.getCertificate(keyAlias);  
        PublicKey pubkey = cert.getPublicKey();  
        System.out.println("cert class = " + cert.getClass().getName());  
        System.out.println("cert = " + cert);  
        System.out.println("public key = " + Base64.encode(pubkey.getEncoded()));  
        System.out.println("private key = " + Base64.encode(prikey.getEncoded()));  
  
    } catch (Exception e)  
    {  
        System.out.println(e);  
    }  
    return keyAlias;  
    }  
  
    /** 
     * 获取公钥信息 
     */  
    public void getPublicKeyInfo()  
    {  
    String tmp0 = "";  
    String tmp1 = "";  
    try  
    {  
        tmp1 = getPublicKey("").replaceAll("\n", "");  
        System.out.println("商户公钥字符串：\n" + tmp1);  
        System.out.println("\n商户公钥字符给定串：\n" + tmp0);  
        if (tmp0.equals(tmp1))  
        System.out.println("=========");  
        else  
        {  
        System.out.println("**************");  
        }  
  
    } catch (CertificateException e)  
    {  
        e.printStackTrace();  
        System.out.println(e);  
    } catch (IOException e)  
    {  
        e.printStackTrace();  
        System.out.println(e);  
    }  
    }  
  
    /** 
     * 读取公钥cer 
     *  
     * @param path 
     *            .cer文件的路径 如：c:/abc.cer 
     * @return base64后的公钥串 
     * @throws IOException 
     * @throws CertificateException 
     */  
    public static String getPublicKey(String path) throws IOException, CertificateException  
    {  
    InputStream inStream = new FileInputStream(path);  
    ByteArrayOutputStream out = new ByteArrayOutputStream();  
    int ch;  
    String res = "";  
    while ((ch = inStream.read()) != -1)  
    {  
        out.write(ch);  
    }  
    byte[] result = out.toByteArray();  
    // res = Base64.byteArrayToBase64(result);  
    res = Base64.encode(result);  
    return res;  
    }  
  
}  