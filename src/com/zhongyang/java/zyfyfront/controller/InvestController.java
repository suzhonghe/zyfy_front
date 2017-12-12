package com.zhongyang.java.zyfyfront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.zyfyfront.biz.InvestBiz;
import com.zhongyang.java.zyfyfront.params.InvestParams;
import com.zhongyang.java.zyfyfront.returndata.InvestReturn;

/**
 *@package com.zhongyang.java.zyfyfront.controller
 *@filename InvestController.java
 *@date 2017年8月3日上午10:28:29
 *@author suzh
 */
@Controller
public class InvestController extends BaseController{

	@Autowired
	private InvestBiz investBiz;
	
	@RequestMapping(value="/front/invest/searchInvestRecord", method = RequestMethod.POST)
	public @ResponseBody InvestReturn searchInvestRecord(InvestParams params){
		return investBiz.queryInvestRecords(params);
	}
}
