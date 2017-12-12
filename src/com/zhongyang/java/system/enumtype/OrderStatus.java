package com.zhongyang.java.system.enumtype;
/**
 * 
 *@package com.zhongyang.java.system.enumtype
 *@filename OrderStatus.java
 *@date 20172017年6月26日下午4:44:51
 *@author suzh
 */
public enum OrderStatus implements StatusEnum {
	    INITIALIZED("初始"),
	    PROCESSING("处理中"),
	    AUDITING("审核中"),//目前主要用于取现申请复核
	    PAY_PENDING("支付结果待查"),// 目前用于银联单笔代付没有实时返回最终成功或者失败结果的情况
	    CUT_PENDING("代扣结果待查"),// 目前用于银联单笔代扣没有实时返回最终成功或者失败结果的情况
	    SUCCESSFUL("成功"),
	    FAILED("失败"),
	    REJECTED("拒绝"),
	    CANCELED("取消"),
	    SETTLED("回款中"),
		CLEARED("已还清"),
	    CLOSED("已关闭"),
	    RESULT_WATT_QUERY("结果待查");

	    private final String key;

	    private OrderStatus(String key) {
	        this.key = key;
	    }

	    @Override
	    public String getKey() {
	        return key;
	    }

}
