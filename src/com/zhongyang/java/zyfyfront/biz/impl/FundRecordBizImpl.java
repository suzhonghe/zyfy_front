package com.zhongyang.java.zyfyfront.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.zyfyfront.biz.FundRecordBiz;
import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.service.FundRecordService;
import com.zhongyang.java.zyfyfront.vo.FundRecordCalenderVo;

/**
 * @author 作者:zhaofq
 * @version 创建时间：2015年12月4日 下午1:00:52 类说明
 */
@Service
public class FundRecordBizImpl implements FundRecordBiz {

	private static Logger logger=Logger.getLogger(InvestBizImpl.class);
	
	@Autowired
	private FundRecordService fundRecordService;

	
	//用户资金日历
	public List<FundRecordCalenderVo> userFundRecordCalendar(String newData, User user) {
		try {
			List<FundRecordCalenderVo> transList = new ArrayList<FundRecordCalenderVo>();
			//充值
			List<FundRecordCalenderVo> rechargeList = new ArrayList<FundRecordCalenderVo>();
			//回款
			List<FundRecordCalenderVo> repList = new  ArrayList<FundRecordCalenderVo>();
			String []  lastday = getMonthDays(newData);
			String []  lastday1 = getMonthDays1(newData);
			FundRecordCalenderVo fundRecordCalenderVo = new FundRecordCalenderVo();
			fundRecordCalenderVo.setStartTime(lastday[0]);
			fundRecordCalenderVo.setEndTime(lastday[1]);
			fundRecordCalenderVo.setUserId(user.getId());

			List<FundRecordCalenderVo> fundRecordCalenderVos = fundRecordService.userFundRecordCalendar(fundRecordCalenderVo);
			fundRecordCalenderVo.setStartTime(lastday1[0]);
			fundRecordCalenderVo.setEndTime(lastday1[1]);
			List<FundRecordCalenderVo> repmentfundRecordCalenderVos = fundRecordService.repmentfundRecordCalenderVos(fundRecordCalenderVo);//回款和还款
			SimpleDateFormat df= new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat dfs= new SimpleDateFormat("dd");
			if(fundRecordCalenderVos.size()>0){
				
				for(int i = 0;i<fundRecordCalenderVos.size();i++){
					FundRecordCalenderVo fdc = new FundRecordCalenderVo();
					if("DEPOSIT".endsWith(fundRecordCalenderVos.get(i).getType().toString())){
						fdc.setTimeRecordeds(df.format(fundRecordCalenderVos.get(i).getTimeRecorded()));//时间
						fdc.setAmount(fundRecordCalenderVos.get(i).getAmount());
						fdc.setDates(dfs.format(fundRecordCalenderVos.get(i).getTimeRecorded()));
						fdc.setType("充值");
						fdc.setStatuss(fundRecordCalenderVos.get(i).getStatus().getKey());
					}else if("INVEST".endsWith(fundRecordCalenderVos.get(i).getType().toString())){
						fdc.setTimeRecordeds(df.format(fundRecordCalenderVos.get(i).getTimeRecorded()));//时间
						fdc.setAmount(fundRecordCalenderVos.get(i).getAmount());
						fdc.setDates(dfs.format(fundRecordCalenderVos.get(i).getTimeRecorded()));
						fdc.setType("投资");
						fdc.setStatuss(fundRecordCalenderVos.get(i).getStatus().getKey());
					}else if("WITHDRAW".endsWith(fundRecordCalenderVos.get(i).getType().toString())){
						fdc.setTimeRecordeds(df.format(fundRecordCalenderVos.get(i).getTimeRecorded()));//时间
						fdc.setDates(dfs.format(fundRecordCalenderVos.get(i).getTimeRecorded()));
						fdc.setAmount(fundRecordCalenderVos.get(i).getAmount());
						fdc.setType("提现");
						fdc.setStatuss(fundRecordCalenderVos.get(i).getStatus().getKey());
					}else{
						fdc.setTimeRecordeds(df.format(fundRecordCalenderVos.get(i).getTimeRecorded()));//时间
						fdc.setDates(dfs.format(fundRecordCalenderVos.get(i).getTimeRecorded()));
						fdc.setAmount(fundRecordCalenderVos.get(i).getAmount());
						fdc.setType("放款");
						fdc.setStatuss(fundRecordCalenderVos.get(i).getStatus().getKey());
					}
					rechargeList.add(fdc);
				}
				transList.addAll(rechargeList);
			}
			if(repmentfundRecordCalenderVos.size()>0){
				for(int i = 0;i<repmentfundRecordCalenderVos.size();i++){
					FundRecordCalenderVo fdc = new FundRecordCalenderVo();
					fdc.setDudate(repmentfundRecordCalenderVos.get(i).getDudate());//时间
					fdc.setAmountInterest(repmentfundRecordCalenderVos.get(i).getAmountInterest());
					fdc.setAmountOutStanding(repmentfundRecordCalenderVos.get(i).getAmountOutStanding());
					String strdate = repmentfundRecordCalenderVos.get(i).getDudate();
					if(null !=strdate){
						strdate = strdate.substring(8,10);
						fdc.setDates(strdate);
					}
					fdc.setAmountPrincipal(repmentfundRecordCalenderVos.get(i).getAmountPrincipal());
					fdc.setType("回款");
					repList.add(fdc);
				}
				transList.addAll(repList);
			}
			return transList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
		//回款,借款，
	
	}


	private static String[]  getMonthDays(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));//年
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7))-1);//月
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		String yaer = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = String.valueOf(maxDay);
		String [] days = new String[2];
		days[0] = yaer+"-"+month+"-01 00:00:00";//开始时间
		days[1] = yaer+"-"+month+"-"+day+" 23:59:59";//开始时间
		return (days);
		}
	private static String[]  getMonthDays1(String date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));//年
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7))-1);//月
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		String yaer = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = String.valueOf(maxDay);
		String [] days = new String[2];
		days[0] = yaer+"-"+month+"-01";//开始时间
		days[1] = yaer+"-"+month+"-"+day;//开始时间
		return (days);
		}
	
	
	public static void main(String[] args) {
		
		String date = "2016-08";
		String []  lastday = getMonthDays(date);
		}
}
