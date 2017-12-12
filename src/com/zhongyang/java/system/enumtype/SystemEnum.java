package com.zhongyang.java.system.enumtype;

/**
 * 
 *@package com.zhongyang.java.system.enumtype
 *@filename SystemEnum.java
 *@date 20172017年6月28日上午9:33:19
 *@author suzh
 */
public enum SystemEnum {
	/**
	 * 操作成功
	 */
	OPRARION_SUCCESS(1000),
	/**
	 * 操作成功
	 */
	OPRARION_FAILED(1001),
	
	
	/**
	 * 用户名或密码错误
	 */
	USER_PASSWORD_VAILD_FAILURE(2000),
	/**
	 * 用户不存在  
	 */
	USER_NOT_EXISTS(2001),
	
	/**
	 * 没有登录
	 */
	USER_NOLOGIN(2002),

	/**
	 *用户名不能更改
	 */
	USERNAME_NO_CHANGE(2003),	
    /**
	 * 登录失败
	 */
	LOGIN_ERROR(2004),
    /**
     * 没有绑卡实名认证
     */
    UN_AUTHENTICATION(2005),
    /**
     * 实名认证失败
     */
    AUTHENTICATION_FAIL(2006),
    /**
     * 账户被锁定
     */
    USER_LOCK(2007),
    
    /**
	 * 短验开户申请失败
	 */
	USER_BINKCARDCODE_FAILED(2008),
    
    
    /**
     * 短信次数查过规定次数
     */
    MESSAGE_NUM_OUT(3001),
    /**
     * 短信获取超时
     */
    MESSAGE_TIME_OUT(3002),
    /**
     * 短信验证码失效
     */
    MESSAGE_LOSE_EFFICACY(3003),
    /**
     * 短信验证码错误
     */
    MESSAGE_ERROR(3004),
    /**
     * 参数错误
     */
    PARAMS_ERROR(4001),
    /**
     * 网络异常
     */
    NET_CONNET_EXCEPTION(5000),
	/**
	 * 数据库连接失败
	 */
	SERVER_REFUSED(9001),
	/**
	 * 数据加密异常
	 */
	DATA_SECURITY_EXCEPTION(9002),
	/**
	 * 文件读写错误
	 */
	FILE_READ_WRITE_EXCEPTION(9003),
	/**
	 * 数据入库异常
	 */
	DATA_REFUSED(9004),
	/**
	 * 存款准备失败
	 */
	PREPARE_DEPOSIT_FAILED(8001),
	/**
	 * 调用第三方失败
	 */
	UMPAT_SIG__FAILED(8002),
	/**
	 * 申请金额大约可用金额
	 */
	AMOUNT_TOO_BIG(8009),
	/**
	 * 未知异常,请与管理员联系
	 */
	UNKNOW_EXCEPTION(9999),
    /**
     * 充值遇到问题，请联系客服
     */
	RECHARGE_EXCEPTION(9901),
	
	UPDATE_MOBLE_FAILED(9902);
	
	private final Integer value;  
	    
	  /**
	 * @param value
	 */
	private SystemEnum(Integer value){  
	      this.value=value;  
	  }  
	    
	  /**
	 * @return
	 */
	public Integer value(){  
	      return value;  
	  }  
}
