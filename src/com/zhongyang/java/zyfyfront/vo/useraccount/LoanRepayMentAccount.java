package com.zhongyang.java.zyfyfront.vo.useraccount;

import com.zhongyang.java.system.enumtype.StatusEnum;

/**
 * 
 * @Title: FundRecordStatus.java
 * @Package com.zhongyang.java.vo
 * @Description: TODO(用一句话描述该文件做什么)
 * @author 苏忠贺
 * @date 2015年12月17日 下午2:45:47
 * @version V1.0
 */


public enum LoanRepayMentAccount implements StatusEnum {
	   //每期还款状态
		UNDUE("待还款"),
		OVERDUE("逾期"),
		BREACH("违约"),
		REPAYED("已还请"),
		PREPAY("提前还款");
		
		private final String key;
        
		private LoanRepayMentAccount(String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return key;
		}

}
