package com.zhongyang.java.zyfyfront.returndata;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.pojo.Invest;

/**
 *@package com.zhongyang.java.zyfyfront.returndata
 *@filename InvestReturn.java
 *@date 2017年8月3日上午10:32:39
 *@author suzh
 */
public class InvestReturn {
	
	private Message message;
	
	private Page<Invest> page;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Page<Invest> getPage() {
		return page;
	}

	public void setPage(Page<Invest> page) {
		this.page = page;
	}
}
