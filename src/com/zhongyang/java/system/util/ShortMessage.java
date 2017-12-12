package com.zhongyang.java.system.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.http.ZyHttpClient;

/**
 *
 * @author Matthew
 *
 */
public class ShortMessage {
	private static Logger logger=Logger.getLogger(ShortMessage.class);
	private static ShortMessage shortMessage;
    private static String verificationCode;
    private static String resetPasswd;
    private static String url;
    private static String sn;
    private static String pwd;
    private static String mobile;
    private static String content;
    private static String ext;
    private static String stime;
    private static String rrid;
    private static String msgfmt;
    private static String loanmsg;




    public  String getVerificationCode(List<String> mobiles) {
        String string =String.valueOf(Math.round(Math.random()*899999+100000));
        content=verificationCode.replaceAll("random",string);
        mobile=getMobile(mobiles);
        try {
            ZyHttpClient.requestByPostMethod(new String(getPath().getBytes(),"UTF-8"));
            return string;
        } catch (UnsupportedEncodingException e) {
            throw new UException(e);
        }


    }
    
    public  String getResetPasswdCode(String mobiles) {
        String string =String.valueOf(Math.round(Math.random()*899999+100000));
        content=resetPasswd.replaceAll("resetCode",string);
        mobile=mobiles;
        try {
            ZyHttpClient.requestByPostMethod(new String(getPath().getBytes(),"UTF-8"));
            return string;
        } catch (UnsupportedEncodingException e) {
            throw new UException(e);
        }


    }
    public  String getSendToUserMsg(Map<String, Object> map) {
    	String msg = (String) map.get("info");
    	String string = "";
    	String mobiles = (String) map.get("mobiles");
        content = loanmsg.replaceAll("msg",msg);
        mobile = mobiles;
        try {
            ZyHttpClient.requestByPostMethod(new String(getSendMSg().getBytes(),"UTF-8"));
            return string;
        } catch (UnsupportedEncodingException e) {
            throw new UException(e);
        }
     
    
  /*  public String getResetPasswdCode(String mobile){
    	String string =String.valueOf(Math.round(Math.random()*899999+100000));
    	content = resetPasswd.replaceAll("resetCode", string);
    	this.mobile=mobile;
    	   try {
               ZyHttpClient.requestByGetMethod(new String(getPath().getBytes(),"UTF-8"));
               return string;
           } catch (UnsupportedEncodingException e) {
               throw new UException(e);
           }

    }*/


    }
    public  String sendToUserMsg(Map<String, Object> map) {
    	String msg = (String) map.get("info");
    	String string = "";
    	String mobiles = (String) map.get("mobiles");
        content = "【左右逢园】"+msg;
        mobile = mobiles;
        try {
            ZyHttpClient.requestByPostMethod(new String(getPath().getBytes(),"UTF-8"));
            return string;
        } catch (UnsupportedEncodingException e) {
            throw new UException(e);
        }


    }
    public String getLoanmsg() {
		return loanmsg;
	}
	public  ShortMessage() {
        fill();
        getSendMSgTuUser();
    }

    public static ShortMessage getShortMessage() {
        if(shortMessage==null){
            shortMessage=new ShortMessage();
        }
        return shortMessage;
    }
   
    private static void fill(){
        String path=ShortMessage.class.getClassLoader().getResource("/").getPath();
        Map<String,Object> map=DomParseService.xmlTOmap(path+"shortMessage.xml");
        verificationCode=map.get("verificationCode").toString();
        verificationCode="欢迎注册左右逢园，手机验证码：random，验证码在10分钟内有效【左右逢园】";
        if(map.get("resetPasswd")!=null)
        { resetPasswd = map.get("resetPasswd").toString();
        resetPasswd ="验证码：resetCode（10分钟内输入有效），您正在进行找回密码操作，如非本人操作请联系400-861-5058。【左右逢园】";
        }
        url=map.get("url").toString();
        sn=map.get("sn").toString();
        String ss = map.get("pwd").toString();
        System.out.println();
        pwd=map.get("pwd").toString();
        Object ob;
        if((ob=map.get("ext"))!=null){
            ext=map.get("ext").toString();
        }else{
            ext="";
        }
        if((ob=map.get("stime"))!=null){
            stime=map.get("stime").toString();
        }else{
            stime="";
        }
        if((ob=map.get("rrid"))!=null){
            rrid=map.get("rrid").toString();
        }else{
            rrid="";
        }
        if((ob=map.get("msgfmt"))!=null){
            msgfmt=map.get("msgfmt").toString();
        }else{
            msgfmt="";
        }
    }
    
    private static void getSendMSgTuUser(){
        String path=ShortMessage.class.getClassLoader().getResource("/").getPath();
        Map<String,Object> map=DomParseService.xmlTOmap(path+"shortMessage.xml");
        loanmsg=map.get("loanmsg").toString();
        url=map.get("url").toString();
        sn=map.get("sn").toString();
        pwd=map.get("pwd").toString();
        Object ob;
        if((ob=map.get("ext"))!=null){
            ext=map.get("ext").toString();
        }else{
            ext="";
        }
        if((ob=map.get("stime"))!=null){
            stime=map.get("stime").toString();
        }else{
            stime="";
        }
        if((ob=map.get("rrid"))!=null){
            rrid=map.get("rrid").toString();
        }else{
            rrid="";
        }
        if((ob=map.get("msgfmt"))!=null){
            msgfmt=map.get("msgfmt").toString();
        }else{
            msgfmt="";
        }
    }
    private String getSendMSg(){
        StringBuffer path=new StringBuffer();
        path.append(url).append("?sn=").append(sn).append("&pwd=").append(CheckUtil.zycf32MD5(sn+pwd)).append("&mobile=").append(mobile);
        path.append("&content=").append(loanmsg).append("&ext=").append(ext).append("&stime=").append(stime).append("&rrid=").append(rrid)
                .append("&msgfmt=").append(msgfmt);
        return path.toString();
    }
    
    private String getPath(){
        StringBuffer path=new StringBuffer();
        path.append(url).append("?sn=").append(sn).append("&pwd=").append(CheckUtil.zycf32MD5(sn+pwd)).append("&mobile=").append(mobile);
        path.append("&content=").append(content).append("&ext=").append(ext).append("&stime=").append(stime).append("&rrid=").append(rrid)
                .append("&msgfmt=").append(msgfmt);
        return path.toString();
    }

    private String getMobile(List<String> list){
        StringBuffer sb=new StringBuffer();
        if(list!=null&&list.size()>0){
            sb.append(list.get(0));
            for(int i=1;i<list.size();i++){
                sb.append(",").append(list.get(i));
            }
            return sb.toString();
        }else{
            throw new UException(SystemEnum.UNKNOW_EXCEPTION,"没有手机号");
        }
    }
}
