package com.zhongyang.java.bankmanager.biz.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhongyang.java.bankmanager.biz.BmCallBackBiz;
import com.zhongyang.java.bankmanager.entity.ClearNotify;
import com.zhongyang.java.bankmanager.entity.NotifyRefund;
import com.zhongyang.java.bankmanager.service.ClearNotifyService;
import com.zhongyang.java.bankmanager.service.NotifyRefundService;
import com.zhongyang.java.system.util.GetUUID;

/**
 *@package com.zhongyang.java.bankmanager.biz.impl
 *@filename BmCallBackBizImpl.java
 *@date 2017年10月18日上午9:27:12
 *@author suzh
 */
@Service
public class BmCallBackBizImpl implements BmCallBackBiz {
	
	@Autowired
	private ClearNotifyService clearNotifyService;
	
	@Autowired
	private NotifyRefundService notifyRefundService;
	
	@Override
	public Object callBackRefund(NotifyRefund notifyRefund) {
		notifyRefund.setId(GetUUID.getUniqueKey());
		notifyRefund.setCreateTime(new Date());
		if(notifyRefund.getOrder_no()!=null&&notifyRefund.getPlatcust()!=null)
			notifyRefundService.addNotifyRefund(notifyRefund);
		
		Map<String,String>map=new HashMap<String,String>();
		map.put("recode", "success");
		return JSON.toJSON(map);
	}

	
	@Override
	public Object callBackClear(ClearNotify clearNotify) {
		clearNotify.setId(GetUUID.getUniqueKey());
		clearNotifyService.addClearNotify(clearNotify);
		Map<String,String>map=new HashMap<String,String>();
		map.put("recode", "success");
		return JSON.toJSON(map);
	}

}
