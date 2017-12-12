package com.zhongyang.java.zyfyfront.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.util.FormatUtils;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.biz.InvestBiz;
import com.zhongyang.java.zyfyfront.params.InvestParams;
import com.zhongyang.java.zyfyfront.pojo.Invest;
import com.zhongyang.java.zyfyfront.returndata.InvestReturn;
import com.zhongyang.java.zyfyfront.service.InvestService;

/**
 *@package com.zhongyang.java.zyfyfront.biz.impl
 *@filename InvestBizImpl.java
 *@date 2017年8月3日上午10:33:48
 *@author suzh
 */
@Service
public class InvestBizImpl implements InvestBiz{

	@Autowired
	private InvestService investService;
	
	@Override
	public InvestReturn queryInvestRecords(InvestParams params) {
		InvestReturn ir=new InvestReturn();
		try {
			if(params==null||params.getPage()==null)
				throw new UException(SystemEnum.PARAMS_ERROR,"参数接收错误");

			if(params.getPage().getStrStart()!=null&&!"".equals(params.getPage().getStrStart()))
				params.getPage().setStartTime(FormatUtils.timeFormat(params.getPage().getStrStart()));
			
			if(params.getPage().getStrEnd()!=null&&!"".equals(params.getPage().getStrEnd()))
				params.getPage().setEndTime(FormatUtils.timeFormat(params.getPage().getStrEnd()));
				
			List<Invest> invests = investService.queryInvestByPage(params.getPage());
			for (Invest invest : invests) {
				invest.setUserName(invest.getUserName().substring(0, 1)+"**");
			}
			params.getPage().setResults(invests);
			ir.setPage(params.getPage());
			ir.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"查询成功"));
		} catch (UException e) {
			e.printStackTrace();
			ir.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return ir;
	}
}
