package com.zhongyang.java.system.enumtype;
/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月4日 上午9:56:46
* 类说明：资金状态
*/
public enum FundRecordType implements StatusEnum {

	 INVEST("投标"),
	 WITHDRAW("取现"),
	 DEPOSIT("充值"),
	 LOAN("放款"),
	 LOAN_REPAY("贷款还款"),
	 DISBURSE("垫付还款"),
	 FAILED_LOAN_REPAY("流标返款"),
	 INVEST_REPAY("投资还款"),
	 TRANSFERIN("平台转入"),//用户资金类型
	 TRANSFEROUT("平台转出"),//用户资金类型
	 PLAT_TRANS("平台不同子账户之间转账"),
	 BORROWER_REPAY("借款人还款"),
	 COMPENSATE_REPAY("代偿还款"),
	 

	    /**
	     * 服务管理手续费
	     */
	 FEE_WITHDRAW("提现手续费"),
	 FEE_AUTHENTICATE("身份验证手续费"),
	 FEE_INVEST_INTEREST("投资管理费"),
	 FEE_LOAN_SERVICE("借款服务费"),
	 FEE_LOAN_MANAGE("借款管理费"),
	 FEE_LOAN_INTEREST("还款管理费"),
	 FEE_LOAN_GUARANTEE("担保费"),//一般对应担保类贷款
	 FEE_LOAN_RISK("风险管理费"),//一般对应信用类贷款
	 FEE_DEPOSIT("充值手续费");
    
    private final String key;

    private FundRecordType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}

