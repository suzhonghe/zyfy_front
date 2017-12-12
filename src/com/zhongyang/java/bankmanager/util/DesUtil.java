package com.zhongyang.java.bankmanager.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * 主要包含签名、验签功能。
 * <p>
 * Created by wuxinw on 2017/5/3.
 */
public class DesUtil {
	private static Logger logger=Logger.getLogger(DesUtil.class);
	
	private static String plainText(Map<String, Object> params) {
		
		TreeMap<String, Object> sortedParams = new TreeMap<>();
		sortedParams.putAll(params);
		StringBuilder plainText = new StringBuilder();
		for (String key : sortedParams.keySet()) {
			if (sortedParams.get(key) instanceof String || sortedParams.get(key) instanceof Number) {
				plainText.append(sortedParams.get(key));
			} else {
				plainText.append(JSONObject.toJSONString(sortedParams.get(key)));
			}
		}
		String str = plainText.toString();
		logger.info("签名原文：" + str);
		return str;
	}
    		
    /**
     * 签名
     *
     * @return
     */
    public static String sign(Map<String, Object> params) {
        return sign(plainText(params));
    }

    /**
     * 签名
     *
     * @param plainText
     * @return
     */
    public static String sign(String plainText) {
        try {
            Signature sig = Signature.getInstance("SHA256WithRSA");
            sig.initSign(getPrivateKey());
            sig.update(plainText.getBytes("UTF-8"));
            byte[] b = sig.sign();
            String str = new String(Base64Util.encode(b));
            logger.info("签名:"+str);
			return str;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifySign(Map<String, Object> params, String signedText) {
        return verifySign(plainText(params), signedText);
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
            Signature sig = Signature.getInstance("SHA256WithRSA");
            X509Certificate certificate = loadCertificate();
            sig.initVerify(certificate);
            sig.update(plainText.getBytes("UTF-8"));
            byte[] b = Base64Util.decode(signedText);
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
        String path = SignUtil.class.getClassLoader().getResource(SignUtil.PRIVATE_KEY_PATH).getPath();
        KeyStore ks = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            ks = KeyStore.getInstance("PKCS12");
            ks.load(fis,SignUtil.PRIVATE_KEY_PASSWD.toCharArray());
            fis.close();
            String keyAlias = null;
            if (ks.aliases().hasMoreElements()) {
                keyAlias = ks.aliases().nextElement();
            }
            return (PrivateKey) ks.getKey(keyAlias, SignUtil.PRIVATE_KEY_PASSWD.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }


    /**
     * 获取公钥
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("resource")
	private static X509Certificate loadCertificate() throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        URL path = SignUtil.class.getClassLoader().getResource(SignUtil.PUBLIC_KEY_PATH);
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        InputStream is = null;
        if (path != null) {
            try {
                is = new FileInputStream(path.getFile());
            } catch (FileNotFoundException e) {
                is = classLoader.getResourceAsStream(SignUtil.PUBLIC_KEY_PATH);
            }
        } else {
            is = new FileInputStream(SignUtil.PUBLIC_KEY_PATH);
        }
        X509Certificate certificate = (X509Certificate) factory.generateCertificate(is);
        is.close();
        return certificate;
    }
}
