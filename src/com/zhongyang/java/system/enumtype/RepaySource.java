package com.zhongyang.java.system.enumtype;

public enum RepaySource implements StatusEnum {
	ENTRUSTED_PAYMENT("委托还款"),
	COMPENSATORY_PAYMENT("代偿还款"),
	NORMAL_PAYMENT("正常还款");

	    private final String key;

	    private RepaySource(String key) {
	        this.key = key;
	    }

	    @Override
	    public String getKey() {
	        return key;
	    }
	
}
