package com.zhongyang.java.zyfyfront.biz.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.util.BigDecimalAlgorithm;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.biz.LoanBiz;
import com.zhongyang.java.zyfyfront.params.LoanParams;
import com.zhongyang.java.zyfyfront.pojo.Loan;
import com.zhongyang.java.zyfyfront.returndata.LoanReturn;
import com.zhongyang.java.zyfyfront.service.LoanService;
import com.zhongyang.java.zyfyfront.vo.LoanDetail;

/**
 * 
 * @Title: LoanBizImpl.java
 * @Package com.zhongyang.java.zyfyfront.biz.impl
 * @Description:标的业务处理实现类
 * @author 苏忠贺
 * @date 2015年12月4日 上午9:09:55
 * @version V1.0
 */
@Service
public class LoanBizImpl implements LoanBiz {

	private static Logger logger = Logger.getLogger(LoanBizImpl.class);

	@Autowired
	private LoanService loanService;

	@Override
	public LoanReturn queryLoansByPage(LoanParams params) {
		LoanReturn lr=new LoanReturn();
		try {
			List<LoanDetail> loanDetails = new ArrayList<>();
			if (params == null) {
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数接收出错");
			}
			List<Loan> loans = loanService.queryLoansByPage(params.getPage());
			for (Loan loan : loans) {
				LoanDetail lv = new LoanDetail();
				lv.setLoanId(loan.getId());
				lv.setAmount(loan.getAmount());
				if (loan.getTitle().contains("】")) {
					lv.setLoanName(loan.getTitle().substring(loan.getTitle().lastIndexOf("】")+1));
				}
				else{
					lv.setLoanName(loan.getTitle());
				}
				lv.setMethod(loan.getMethod());
				lv.setMonths(loan.getMonths());
				if (loan.getBidAmount() != null && loan.getAmount() != null) {
					BigDecimal planing = BigDecimalAlgorithm.divScale(loan.getBidAmount(), loan.getAmount(), 2,
							RoundingMode.DOWN);
					lv.setPlaning(BigDecimalAlgorithm.mul(planing, new BigDecimal(100)));
					lv.setSuplusAmount(BigDecimalAlgorithm.sub(loan.getAmount(), loan.getBidAmount()).setScale(2,
							BigDecimal.ROUND_DOWN));
				}
				Date date = new Date();
				if (loan.getTimeOpen() != null) {
					Long res = loan.getTimeOpen().getTime() - date.getTime();
					if (res < 0) {
						lv.setDivTime(0L);
					} else {
						lv.setDivTime(res);
					}
					
				}
				lv.setProductName(loan.getProductName());
				lv.setRate(loan.getRate());
				lv.setBidAmount(loan.getBidAmount());
				lv.setStatus(loan.getStatus());
				lv.setTimeOpen(loan.getTimeOpen());
				lv.setAddRate(loan.getAddRate());
				loanDetails.add(lv);
			}
			Page<LoanDetail>rePage=new Page<LoanDetail>();
			rePage.setResults(loanDetails);
			rePage.setPageNo(params.getPage().getPageNo());
			rePage.setPageSize(params.getPage().getPageSize());
			rePage.setParams(params.getPage().getParams());
			rePage.setTotalRecord(params.getPage().getTotalRecord());
			rePage.setTotalPage(params.getPage().getTotalPage());
			lr.setPage(rePage);
			lr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"操作成功"));
			return lr;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e, e.fillInStackTrace());
			lr.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),"操作失败"));
			return lr;
		}
	}
	@Override
	public LoanReturn queryLoanByParams(LoanParams params) {
		LoanReturn lr=new LoanReturn();
		LoanDetail loanVo = new LoanDetail();
		try {
			if (params == null||params.getLoan()==null) {
				throw new UException(SystemEnum.PARAMS_ERROR, "参数接收异常");
			}
			Loan loan = loanService.queryLoanByParams(params.getLoan());
			loanVo.setLoanId(loan.getId());
			loanVo.setAmount(loan.getAmount());
			loanVo.setMethod(loan.getMethod());
			loanVo.setMonths(loan.getMonths());
			if (loan.getBidAmount() != null && loan.getAmount() != null) {
				BigDecimal planing = BigDecimalAlgorithm.divScale(loan.getBidAmount(), loan.getAmount(), 2,
						RoundingMode.DOWN);
				loanVo.setPlaning(BigDecimalAlgorithm.mul(planing, new BigDecimal(100)));
				loanVo.setSuplusAmount(BigDecimalAlgorithm.sub(loan.getAmount(), loan.getBidAmount()));
			}
			loanVo.setLoanName(loan.getTitle());
			loanVo.setMinAmount(loan.getMinAmount());
			loanVo.setMaxAmount(loan.getMaxAmount());
			loanVo.setStepAmount(loan.getStepAmount());
			loanVo.setProductName(loan.getProductName());
			loanVo.setRate(loan.getRate());
			loanVo.setAddRate(loan.getAddRate());
			loanVo.setBidAmount(loan.getBidAmount());
			loanVo.setStatus(loan.getStatus());
			lr.setLoanDetail(loanVo);
			lr.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"操作成功"));
			return lr;
		} catch (UException e) {
			e.printStackTrace();
			logger.info(e, e.fillInStackTrace());
			lr.setMessage(new Message(e.getCode().value(),e.getMessage()));
			return lr;
		}
	}


}
