package com.zhongyang.java.system.enumtype;
/**
 * 
 *@package com.zhongyang.java.system.enumtype
 *@filename OrderType.java
 *@date 20172017年6月26日下午4:45:08
 *@author suzh
 */
public enum OrderType implements StatusEnum {
	
	REGISTE_APPLY("开户申请"),
	REGISTE_AFFIRM("开户确认"),
	LOAN_PUBLISH("标的发布"),
	LOAN_FAILED("流标"),
	LOAN_REPAY("标的还款"),
	BORROWER_REPAY("借款人还款"),
	PLAT_TRANS("平台子账户之间转账"),
	RELIEVE_CARD("解绑"),
	ACCOUNTINFO_UPDATE("客户信息变更"),
	PLAT_TO_PERSON("平台转账给个人"),
	ORDER_SEARCH("订单状态查询"),
	COMPANY_OPEN_ACCOUNT("企业客户开户"),
	COMANY_CARD("企业客户绑卡"),
	COMANY_RELIEVE_CARD("企业客户解绑"),
	COMPENSATE_REPAY("代偿还款"),
	
    INVEST("投标"),
    WITHDRAW("取现"),
    DEPOSIT("充值"),
    LOAN("结算放款");
    
    private final String key;

    private OrderType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

