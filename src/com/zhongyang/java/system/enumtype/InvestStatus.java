package com.zhongyang.java.system.enumtype;

public enum InvestStatus implements StatusEnum {
		INITIALIZED("初始"),
		PROCESSING("处理中"),
	    AUDITING("待结算"),
	    FAILED("失败"),
	    SETTLED("回款中"),
	    CLEARED("已还清"),
	    CLOSED("已关闭"),
	    RESULT_WATT_QUERY("结果待查");

	    private final String key;

	    private InvestStatus(String key) {
	        this.key = key;
	    }

	    @Override
	    public String getKey() {
	        return key;
	    }
	
}
