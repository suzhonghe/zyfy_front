package com.zhongyang.java.zyfyfront.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.zhongyang.java.system.enumtype.LoanStatus;
import com.zhongyang.java.system.enumtype.Method;

public class Loan {
	private String id;//ID
	
	private Date timeFailed;//流标日期
	
	private String serial;//唯一编号
	
	private BigDecimal amount;//贷款金额
	
	private BigDecimal bidAmount;//实际投标金额
	
	private Integer bidNumber;//实际投标数目
	
	private Method method;//还款方式
	
	private String strMethod;
	
	private Double rate;//借款费率
	
	private Double addRate;
	
	private LoanStatus status;//借款状态
	
	private String strStatus;
	
	private Date timeSubmit;//申请时间
	
	private Date timeCancle;//取消时间
	
	private Date timeCleared;//还清时间
	
	private Date timeFinished;//募集成功结束时间
	
	private Date timeOpen;//开始募集时间
	
	private Date timeSettled;//结标时间
	
	private String title;//借款标标题
	
	private Integer days;//借款期限(日)
	
	private Integer months;//借款期限(月)

	private Integer years;//借款期限(年)
	
	private String loanUserId;//借款人用户ID
	
	private String guaranteeId;//关联担保企业ID
	
	private String guaranteeRealm;//担保实体
	
	private String productId;//关联产品ID
	
	private String productName;//产品名称
	
	private Integer groupId;//投资人群 1：新用户 0：全部用户
	
	private BigDecimal minAmount;//起投金额
	
	private BigDecimal stepAmount;//投资增量
	
	private BigDecimal maxAmount;//最大投资金额
	
	private BigDecimal loanServiceFee;//服务费率
	
	private BigDecimal loanGuaranteeFee;//担保费率
	
	private BigDecimal loanRiskFee;//风险管理费率
	
	private BigDecimal loanManageFee;//借款管理费率
	
	private BigDecimal loanInterestFee;//还款（借款）利息管理费率
	
	private BigDecimal investInterestFee;//回款（投资）利息管理费率
	
	private String projectId;//关联项目ID
	
	private String other;//其他说明
	
	private Date timeScheduled;//调度时间
	
	private String loanUserName;//借款人
	
	private String reason;
	
	private String comentSateUserId;//COMENTSATEUSERID
    
	public String getComentSateUserId() {
		return comentSateUserId;
	}

	public void setComentSateUserId(String comentSateUserId) {
		this.comentSateUserId = comentSateUserId;
	}
		
	public String getStrMethod() {
		return strMethod;
	}

	public void setStrMethod(String strMethod) {
		this.strMethod = strMethod;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public Double getAddRate() {
		return addRate;
	}

	public void setAddRate(Double addRate) {
		this.addRate = addRate;
	}

	public Date getTimeScheduled() {
		return timeScheduled;
	}

	public void setTimeScheduled(Date timeScheduled) {
		this.timeScheduled = timeScheduled;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Date getTimeCancle() {
		return timeCancle;
	}

	public void setTimeCancle(Date timeCancle) {
		this.timeCancle = timeCancle;
	}

	public Date getTimeSubmit() {
		return timeSubmit;
	}

	public void setTimeSubmit(Date timeSubmit) {
		this.timeSubmit = timeSubmit;
	}

	public Date getTimeFailed() {
		return timeFailed;
	}

	public void setTimeFailed(Date timeFailed) {
		this.timeFailed = timeFailed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}

	public Integer getBidNumber() {
		return bidNumber;
	}

	public void setBidNumber(Integer bidNumber) {
		this.bidNumber = bidNumber;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}

	public Date getTimeCleared() {
		return timeCleared;
	}

	public void setTimeCleared(Date timeCleared) {
		this.timeCleared = timeCleared;
	}

	public Date getTimeFinished() {
		return timeFinished;
	}

	public void setTimeFinished(Date timeFinished) {
		this.timeFinished = timeFinished;
	}

	public Date getTimeOpen() {
		return timeOpen;
	}

	public void setTimeOpen(Date timeOpen) {
		this.timeOpen = timeOpen;
	}

	public Date getTimeSettled() {
		return timeSettled;
	}

	public void setTimeSettled(Date timeSettled) {
		this.timeSettled = timeSettled;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public String getLoanUserId() {
		return loanUserId;
	}

	public void setLoanUserId(String loanUserId) {
		this.loanUserId = loanUserId;
	}

	public String getGuaranteeId() {
		return guaranteeId;
	}

	public void setGuaranteeId(String guaranteeId) {
		this.guaranteeId = guaranteeId;
	}

	public String getGuaranteeRealm() {
		return guaranteeRealm;
	}

	public void setGuaranteeRealm(String guaranteeRealm) {
		this.guaranteeRealm = guaranteeRealm;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getStepAmount() {
		return stepAmount;
	}

	public void setStepAmount(BigDecimal stepAmount) {
		this.stepAmount = stepAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getLoanServiceFee() {
		return loanServiceFee;
	}

	public void setLoanServiceFee(BigDecimal loanServiceFee) {
		this.loanServiceFee = loanServiceFee;
	}

	public BigDecimal getLoanGuaranteeFee() {
		return loanGuaranteeFee;
	}

	public void setLoanGuaranteeFee(BigDecimal loanGuaranteeFee) {
		this.loanGuaranteeFee = loanGuaranteeFee;
	}

	public BigDecimal getLoanRiskFee() {
		return loanRiskFee;
	}

	public void setLoanRiskFee(BigDecimal loanRiskFee) {
		this.loanRiskFee = loanRiskFee;
	}

	public BigDecimal getLoanManageFee() {
		return loanManageFee;
	}

	public void setLoanManageFee(BigDecimal loanManageFee) {
		this.loanManageFee = loanManageFee;
	}

	public BigDecimal getLoanInterestFee() {
		return loanInterestFee;
	}

	public void setLoanInterestFee(BigDecimal loanInterestFee) {
		this.loanInterestFee = loanInterestFee;
	}

	public BigDecimal getInvestInterestFee() {
		return investInterestFee;
	}

	public void setInvestInterestFee(BigDecimal investInterestFee) {
		this.investInterestFee = investInterestFee;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getLoanUserName() {
		return loanUserName;
	}

	public void setLoanUserName(String loanUserName) {
		this.loanUserName = loanUserName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}