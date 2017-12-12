package com.zhongyang.java.bankmanager.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.zhongyang.java.system.util.SystemPro;

/**
 * 主要包含签名、验签功能。
 * <p>
 * Created by wuxinw on 2017/5/3.
 */
public class SignUtil {
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		PRIVATE_KEY_PATH=(String) sysMap.get("PRIVATE_KEY_PATH");
		PUBLIC_KEY_PATH=(String) sysMap.get("PUBLIC_KEY_PATH");
		PRIVATE_KEY_PASSWD=(String) sysMap.get("PRIVATE_KEY_PASSWD");
	}
	
	public static String PRIVATE_KEY_PATH;
	public static String PUBLIC_KEY_PATH;
	public static String PRIVATE_KEY_PASSWD; 
	
	private static Logger logger=Logger.getLogger(SignUtil.class);
	
	/**
	 * 签名
	 *
	 * @return
	 */
	public static String sign(Map<String, Object> params) {
		String sign = sign(params2PlainText(params));
		logger.info("签名：" + sign);
		return sign;
	}

	/**
	 * 签名
	 *
	 * @param plainText
	 * @return
	 */
	public static String sign(String plainText) {
		try {
			Signature sig = Signature.getInstance("SHA1WithRSA");
			sig.initSign(getPrivateKey());
			sig.update(plainText.getBytes());
			byte[] b = sig.sign();
			return new String(Base64.encodeBase64(b));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static boolean verifySign(Map<String, Object> params, String signedText) {
		return verifySign(params2PlainText(params), signedText);
	}

	/**
	 * 使用公钥验签
	 *
	 * @param plainText
	 * @param signedText
	 * @return
	 */
	public static boolean verifySign(String plainText, String signedText) {
		try {
			signedText = signedText.replaceAll(" ", "+");
			Signature sig = Signature.getInstance("SHA1WithRSA");
			X509Certificate certificate = loadCertificate();
			PublicKey publicKey=certificate.getPublicKey();
			sig.initVerify(publicKey);
			sig.update(plainText.getBytes());
			byte[] b = Base64.decodeBase64(signedText.getBytes());
			return sig.verify(b);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取私钥
	 *
	 * @return
	 */
	private static PrivateKey getPrivateKey() {
		String path = SignUtil.class.getClassLoader().getResource(PRIVATE_KEY_PATH).getPath();
		KeyStore ks = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			ks = KeyStore.getInstance("PKCS12");
			ks.load(fis, PRIVATE_KEY_PASSWD.toCharArray());
			fis.close();
			String keyAlias = null;
			if (ks.aliases().hasMoreElements()) {
				keyAlias = ks.aliases().nextElement();
			}
			return (PrivateKey) ks.getKey(keyAlias, PRIVATE_KEY_PASSWD.toCharArray());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(fis);
		}
		return null;
	}

	/**
	 * 参数转为签名原文
	 *
	 * @param params
	 * @return
	 */
	private static String params2PlainText(Map<String, Object> params) {
		TreeMap<String, Object> sortedParams = new TreeMap<>();
        sortedParams.putAll(params);
        StringBuilder plainText = new StringBuilder();
        for (String key : sortedParams.keySet()) {
            if (sortedParams.get(key) instanceof String || sortedParams.get(key) instanceof Number) {
                plainText.append("|").append(sortedParams.get(key));
            } else {
                plainText.append("|").append(JSONObject.toJSONString(sortedParams.get(key)));
            }
        }
        plainText.deleteCharAt(0);
        String str = plainText.toString();
        logger.info("签名原文："+str);
		return str;
	}
	

	/**
	 * 获取公钥
	 *
	 * @return
	 * @throws Exception
	 */
	private static X509Certificate loadCertificate() throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		URL path = SignUtil.class.getClassLoader().getResource(PUBLIC_KEY_PATH);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream is = null;
		if (path != null)
			try {
				is = new FileInputStream(path.getFile());
			} catch (FileNotFoundException e) {
				is = classLoader.getResourceAsStream(PUBLIC_KEY_PATH);
			}
		else {
			is = new FileInputStream(PUBLIC_KEY_PATH);
		}
		X509Certificate certificate = (X509Certificate) factory.generateCertificate(is);
		is.close();
		return certificate;
	}
}
