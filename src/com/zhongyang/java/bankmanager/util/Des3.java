package com.zhongyang.java.bankmanager.util;

import java.security.Key;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.log4j.Logger;

public class Des3 {  
	
	private static Logger logger =Logger.getLogger(Des3.class);

	private static final String DES_KEY="mpciLDQIyc6B8p90IZMPm2YMOzmeZ4lC";//生产
	//private static final String DES_KEY="ntakICrLxb5C7t89HYIOs1XLNyndX3kB";//测试
	private static final byte[] KEY_IV = { 1, 2, 3, 4, 5, 6, 7, 8 };
	
    /** 
     * CBC加密 
     * @param key 密钥 
     * @param keyiv IV 
     * @param data 明文 
     * @return Base64编码的密文 
     * @throws Exception 
     */  
    private static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)  
            throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(keyiv);  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }  
    
    /** 
     * CBC解密 
     * @param key 密钥 
     * @param keyiv IV 
     * @param data Base64编码的密文 
     * @return 明文 
     * @throws Exception 
     */  
    private static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)  
            throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(key);  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(keyiv);  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
        byte[] bOut = cipher.doFinal(data);  
        return bOut;  
    }
    
    public static byte[] des3Decode(String deskey, byte[] keyiv, String value) throws Exception{
   	 	byte[] key=Base64Util.decode(deskey);    
        byte[] data=Base64Util.decode(value);
        byte[] str5 = des3DecodeCBC(key, keyiv, data);
        return str5;
   }
    
    public static String des3Encode(String deskey, byte[] keyiv, String value) throws Exception{
    	 byte[] key=Base64Util.decode(deskey);    
         byte[] data=value.getBytes("UTF-8");
         byte[] str5 = des3EncodeCBC(key, keyiv, data);
         return Base64Util.encode(str5);
    }
    
    public static long getTimeStamp(String t) {
    	SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDhhmmss");
    	String d = sdf.format(new Date());
    	try {
    		Date time = DateFormat.getInstance().parse(d);
    		return time.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	return 0;
    }
    
    public static String getEncData(String data) throws Exception {
    	String encData = des3Encode(DES_KEY, KEY_IV, data);
    	logger.info("=========test======key=[" + DES_KEY + "]==origndata=[" + data + "]");
    	logger.info("=======des3Encode==encData=[" + encData + "]");
    	String decData = new String(des3Decode(DES_KEY, KEY_IV, encData));
    	logger.info("=======des3Decode==decData=[" + decData + "]\n");
    	return encData;
    }
    
    public static void main(String[] args) throws Exception {  
        String orginData = "XIB-ZYFYL-B-41551130";

        String test = getEncData(orginData);
        System.out.println(test);
    }
    
}  