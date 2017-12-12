package com.zhongyang.java.zyfyfront.returndata;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.vo.LoanDetail;

public class LoanReturn {
	
	private Message message;
	
	private Page<LoanDetail>page;
	
	private LoanDetail loanDetail;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Page<LoanDetail> getPage() {
		return page;
	}

	public void setPage(Page<LoanDetail> page) {
		this.page = page;
	}

	public LoanDetail getLoanDetail() {
		return loanDetail;
	}

	public void setLoanDetail(LoanDetail loanDetail) {
		this.loanDetail = loanDetail;
	}
	
}
