package com.zhongyang.java.bankmanager.util;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.zhongyang.java.system.util.SystemPro;

public class RequestUtil {
	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_PLAT_NO = (String) sysMap.get("ZYCF_PLAT_NO");
		REQ_URL = (String) sysMap.get("ZYCF_REQ_URL");
	}
	
	private static Logger logger=Logger.getLogger(RequestUtil.class);
	
	private static final String REQ_URL;

	public static final String SIGNDATA_TAG = "signdata";

	public static final String ZYCF_PLAT_NO;
	
	public static final String DES_KEY="mpciLDQIyc6B8p90IZMPm2YMOzmeZ41C"; //des密钥

	public static Map<String, Object> sendRequest(Map<String, Object> params, String action) throws JSONException,IOException {

		HttpClient client = new HttpClient();

		PostMethod method = new PostMethod(REQ_URL + action);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		method.getParams().setParameter(HTTP.CONTENT_TYPE, "UTF-8");

		params.put("plat_no", ZYCF_PLAT_NO);

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			method.setParameter(entry.getKey(), String.valueOf(entry.getValue()));
		}

		// 签名
		String signdata = SignUtil.sign(params);
		// 公钥验签
		boolean verifySign = SignUtil.verifySign(params, signdata);
		
		logger.info("验签是否通过:" + (verifySign ? "通过" : "未通过"));
		method.setParameter(SIGNDATA_TAG, signdata);

		logger.info("编码方式:" + method.getRequestCharSet());
		RequestEntity requestEntity = method.getRequestEntity();

		logger.info("Content-Type:" + requestEntity.getContentType());

		client.executeMethod(method);
		byte[] responseByte = method.getResponseBody();
		String responseBody = new String(responseByte, "UTF-8");
		logger.info("响应："+responseBody);
		return JSON.parseObject(responseBody);

	}

	public static String sendReqResponse(Map<String, Object> params, String action) throws IOException {

		HttpClient client = new HttpClient();

		PostMethod method = new PostMethod(REQ_URL + action);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		method.getParams().setParameter(HTTP.CONTENT_TYPE, "UTF-8");

		params.put("plat_no", ZYCF_PLAT_NO);

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			method.setParameter(entry.getKey(), String.valueOf(entry.getValue()));
		}

		// 签名
		String signdata = SignUtil.sign(params);
		// 公钥验签
		boolean verifySign = SignUtil.verifySign(params, signdata);

		logger.info("验签是否通过:" + (verifySign ? "通过" : "未通过"));
		method.setParameter(SIGNDATA_TAG, signdata);

		logger.info("编码方式:" + method.getRequestCharSet());
		RequestEntity requestEntity = method.getRequestEntity();

		logger.info("Content-Type:" + requestEntity.getContentType());

		client.executeMethod(method);
		byte[] responseByte = method.getResponseBody();
		String responseBody = new String(responseByte, "UTF-8");
		return responseBody;

	}
	
	public static String openIIAcountRequest(Map<String, Object> params, String action) throws Exception {

		HttpClient client = new HttpClient();

		PostMethod method = new PostMethod(action);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		method.getParams().setParameter(HTTP.CONTENT_TYPE, "UTF-8");
		
		String encodeChannel = Des3.getEncData(params.get("channel").toString());
		logger.info("des3 channel密文："+encodeChannel);
		
		String encodeUserid = Des3.getEncData(params.get("userid").toString());
		logger.info("des3 userid密文："+encodeUserid);
		
		
		// 签名
		String signdata = DesUtil.sign(params);
		// 公钥验签
		boolean verifySign = DesUtil.verifySign(params, signdata);
		
		logger.info("验签是否通过:" + (verifySign ? "通过" : "未通过"));
		params.put("userid", encodeUserid);
		params.put("channel", encodeChannel);
		
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			method.setParameter(entry.getKey(), String.valueOf(entry.getValue()));
		}
		method.setParameter("sign", signdata);

		logger.info("编码方式:" + method.getRequestCharSet());
		RequestEntity requestEntity = method.getRequestEntity();

		logger.info("Content-Type:" + requestEntity.getContentType());

		client.executeMethod(method);
		byte[] responseByte = method.getResponseBody();
		String responseBody = new String(responseByte, "UTF-8");
		logger.info("响应："+responseBody);
		return responseBody;

	}
}
