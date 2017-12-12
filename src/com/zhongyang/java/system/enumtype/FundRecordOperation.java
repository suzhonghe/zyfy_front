package com.zhongyang.java.system.enumtype;
/**
 * 
 *@package com.zhongyang.java.system.enumtype
 *@filename FundRecordOperation.java
 *@date 2017年7月5日上午8:59:02
 *@author suzh
 */
public enum FundRecordOperation implements StatusEnum{
	    FREEZE("冻结"),
	    RELEASE("解冻"),
	    IN("资金转入"),
	    OUT("资金转出");
           
	    private final String key;

	    private FundRecordOperation(String key) {
	        this.key = key;
	    }

	    @Override
	    public String getKey() {
	        return key;
	    }
         
}
