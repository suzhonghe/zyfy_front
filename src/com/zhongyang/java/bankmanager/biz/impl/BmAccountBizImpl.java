package com.zhongyang.java.bankmanager.biz.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.zhongyang.java.bankmanager.biz.BmAccountBiz;
import com.zhongyang.java.bankmanager.entity.BindCardRecord;
import com.zhongyang.java.bankmanager.entity.BmAccount;
import com.zhongyang.java.bankmanager.entity.BmOrder;
import com.zhongyang.java.bankmanager.entity.Company;
import com.zhongyang.java.bankmanager.params.BmAccountParams;
import com.zhongyang.java.bankmanager.params.BmCompany;
import com.zhongyang.java.bankmanager.params.RelieveCardParams;
import com.zhongyang.java.bankmanager.returndata.BmReturnData;
import com.zhongyang.java.bankmanager.service.BindCardRecordService;
import com.zhongyang.java.bankmanager.service.BmAccountService;
import com.zhongyang.java.bankmanager.service.BmOrderService;
import com.zhongyang.java.bankmanager.service.CompanyService;
import com.zhongyang.java.bankmanager.util.Des3;
import com.zhongyang.java.bankmanager.util.DesUtil;
import com.zhongyang.java.bankmanager.util.RequestUtil;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.enumtype.OrderStatus;
import com.zhongyang.java.system.enumtype.OrderType;
import com.zhongyang.java.system.enumtype.SystemEnum;
import com.zhongyang.java.system.util.DESTextCipher;
import com.zhongyang.java.system.util.FormatUtils;
import com.zhongyang.java.system.util.GetUUID;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.system.util.SystemPro;
import com.zhongyang.java.system.util.UtilBiz;
import com.zhongyang.java.system.util.Validator;
import com.zhongyang.java.zyfyfront.biz.impl.BannerBizImpl;
import com.zhongyang.java.zyfyfront.pojo.User;
import com.zhongyang.java.zyfyfront.service.UserService;

/**
 * @package com.zhongyang.java.bankmanager.biz.impl
 * @filename BankAccountBizImpl.java
 * @date 20172017年6月26日下午5:17:05
 * @author suzh
 */
@Service
public class BmAccountBizImpl extends UtilBiz implements BmAccountBiz {

	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		ZYCF_REGISTER_PAY_CODE = (String) sysMap.get("ZYCF_REGISTER_PAY_CODE");
		ZYCF_BINK_CARD_APPLY = (String) sysMap.get("ZYCF_BINK_CARD_APPLY");
		ZYCF_BINK_CARD_AFFIRM = (String) sysMap.get("ZYCF_BINK_CARD_AFFIRM");
		ZYCF_RELIEVE_CARD = (String) sysMap.get("ZYCF_RELIEVE_CARD");
		ZYCF_COMPANY_OPEN_ACCOUNT = (String) sysMap.get("ZYCF_COMPANY_OPEN_ACCOUNT");
		ZYCF_COMPANY_BINDCARD_CALL = (String) sysMap.get("ZYCF_COMPANY_BINDCARD_CALL");
		ZYCF_COMPAY_BINDCARD = (String) sysMap.get("ZYCF_COMPAY_BINDCARD");
		ZYCF_PC_OPEN_II_ACCOUNT = (String) sysMap.get("ZYCF_PC_OPEN_II_ACCOUNT");
		ZYCF_APP_OPEN_II_ACCOUNT = (String) sysMap.get("ZYCF_APP_OPEN_II_ACCOUNT");
		ZYCF_PLAT_NO = (String) sysMap.get("ZYCF_PLAT_NO");
		ZYCF_PC_OPEN_II_ACCOUNT_SUCCESSFUL = (String) sysMap.get("ZYCF_PC_OPEN_II_ACCOUNT_SUCCESSFUL");
		ZYCF_APP_OPEN_II_ACCOUNT_SUCCESSFUL = (String) sysMap.get("ZYCF_APP_OPEN_II_ACCOUNT_SUCCESSFUL");
	}

	private static Logger logger = Logger.getLogger(BannerBizImpl.class);

	private static String ZYCF_REGISTER_PAY_CODE;

	private static String ZYCF_BINK_CARD_APPLY;// 4.1.4. 短验绑卡（个人开户）申请

	private static String ZYCF_BINK_CARD_AFFIRM;// 4.1.5短验绑卡（个人开户）确认

	private static String ZYCF_RELIEVE_CARD;// 4.1.3解绑

	private static String ZYCF_COMPANY_OPEN_ACCOUNT;// 4.1.1企业客户开户

	private static String ZYCF_COMPANY_BINDCARD_CALL;// 企业绑卡异步通知回调路径

	private static String ZYCF_COMPAY_BINDCARD;// 4.1.2企业客户绑卡

	private static String ZYCF_PC_OPEN_II_ACCOUNT;

	private static String ZYCF_APP_OPEN_II_ACCOUNT;
	
	private static String ZYCF_APP_OPEN_II_ACCOUNT_SUCCESSFUL;
	
	private static String ZYCF_PC_OPEN_II_ACCOUNT_SUCCESSFUL;

	public static String ZYCF_PLAT_NO;

	@Autowired
	private BmAccountService bmAccountService;

	@Autowired
	private BmOrderService bmOrderService;

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private BindCardRecordService bindCardRecordService;

	@Override
	public BmReturnData bankManagerRegisteApply(HttpServletRequest request, BmAccountParams params) {
		BmReturnData brd = new BmReturnData();
		BmAccount bmAccount = null;
		User user = (User) WebUtils.getSessionAttribute(request, "zycfLoginUser");
		try {
			logger.info("====================短验开户申请参数校验====================");
			if (user == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");

			bmAccount = this.checkBmAccountParams(params);

			DESTextCipher cipher = DESTextCipher.getDesTextCipher();

			User selUser = new User();
			try {
				selUser.setIdCode(cipher.encrypt(bmAccount.getIdCode()));
			} catch (GeneralSecurityException e) {
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "数据加密异常");
			}
			User res = userService.queryUserByParams(selUser);
			if (res != null && !user.getMobile().equals(res.getMobile()))
				throw new UException(SystemEnum.PARAMS_ERROR, "此身份证号已验证过其他注册用户，不允许再次验证该账户");

			logger.info("====================短验开户申请参数校验通过====================");

			logger.info("====================生成短验开户申请订单====================");

			BmOrder order = new BmOrder();
			order.setOrderType(OrderType.REGISTE_APPLY);
			order.setUserId(user.getId());
			order.setRemark("短验开户申请");
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e) {
				logger.info("订单入库异常" + e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据入库异常");
			}
			logger.info("====================短验开户申请订单入库正常,订单号：" + order.getOrderId() + "====================");

			BmAccount account = new BmAccount();
			account.setUserId(user.getId());
			account = bmAccountService.queryBmAccountByParams(account);

			logger.info("====================生成短验开户申请,构造存管系统参数,订单号：" + order.getOrderId() + "====================");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("card_no", bmAccount.getCardNo());

			if (bmAccount.getCardType() != null && !"".equals(bmAccount.getCardType())) {
				if ("1".equals(bmAccount.getCardType()))
					map.put("card_type", bmAccount.getCardType());
				else
					throw new UException(SystemEnum.PARAMS_ERROR, "暂仅支持借记卡");
			}
			map.put("id_code", bmAccount.getIdCode());
			map.put("id_type", 1);// 证件类型只选择身份证
			map.put("name", bmAccount.getUserName());
			map.put("order_no", order.getOrderId());
			map.put("open_branch", bmAccount.getOpenBranch());
			map.put("pay_code", ZYCF_REGISTER_PAY_CODE);

			map.put("partner_trans_date", order.getOrderDate().replace("-", ""));
			map.put("partner_trans_time", order.getOrderTime().replace(":", ""));

			if (account.getPlatcust() != null)
				map.put("platcust", account.getPlatcust());

			map.put("pre_mobile", bmAccount.getPreMobile());

			logger.info("====================生成短验开户申请,订单发送存管系统,订单号：" + order.getOrderId() + "====================");
			Map<String, Object> sendRequest = null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_BINK_CARD_APPLY);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}

			logger.info(sendRequest);

			String recode = (String) sendRequest.get("recode");
			String remsg = (String) sendRequest.get("remsg");

			logger.info("====================短验开户申请,修改本地订单状态,订单号：" + order.getOrderId() + "====================");

			order.setRecord(recode);
			order.setRemsg(remsg);
			if ("10000".equals(recode)) {

				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				// 订单放入缓存
				super.setZsession(user.getId(), order);

				brd.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "短验开户申请成功"));
			} else {
				order.setOrderStatus(OrderStatus.FAILED);
				brd.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),
						"短验开户申请失败: recode[" + recode + "]remsg" + remsg));
			}
			logger.info("====================短验开户申请,修改本地订单状态成功,订单号：" + order.getOrderId() + "====================");
			try {
				bmOrderService.bmOrderModify(order);
			} catch (Exception e) {
				logger.info("订单修改异常" + e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据修改异常");
			}

		} catch (UException e) {
			logger.info(e.fillInStackTrace());
			brd.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return brd;
	}

	@Override
	public BmReturnData bankManagerRegisteAffirm(HttpServletRequest request, BmAccountParams params) {
		BmReturnData brd = new BmReturnData();
		BmAccount bmAccount = null;
		User user = (User) WebUtils.getSessionAttribute(request, "zycfLoginUser");
		try {
			logger.info("====================短验开户确认，参数校验====================");
			if (user == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");

			bmAccount = this.checkBmAccountParams(params);

			if (bmAccount.getIdentifyingCode() == null || "".equals(bmAccount.getIdentifyingCode()))
				throw new UException(SystemEnum.PARAMS_ERROR, "短信验证码不能为空");

			logger.info("====================短验开户确认，参数校验通过====================");

			// 获得缓存中的订单信息

			BmOrder bao = (BmOrder) super.getZsessionObject(user.getId());
			if (bao == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "申请订单已失效");

			super.removeZsessionObject(user.getId());

			logger.info("====================生成短验开户确认订单====================");
			BmOrder order = new BmOrder();
			order.setOrderType(OrderType.REGISTE_AFFIRM);
			order.setUserId(user.getId());
			order.setRemark("短验开户确认");
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e) {
				logger.info("订单入库异常" + e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据入库异常");
			}

			logger.info("====================短验开户确认订单入库正常,订单号：" + order.getOrderId() + "====================");
			BmAccount account = new BmAccount();
			account.setUserId(user.getId());
			account = bmAccountService.queryBmAccountByParams(account);

			logger.info("====================短验开户确认,构造存管系统参数,订单号：" + order.getOrderId() + "====================");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate().replace("-", ""));
			map.put("partner_trans_time", order.getOrderTime().replace(":", ""));
			if (account.getPlatcust() != null)
				map.put("platcust", account.getPlatcust());
			map.put("id_type", 1);
			map.put("id_code", bmAccount.getIdCode());
			map.put("name", bmAccount.getUserName());
			map.put("card_no", bmAccount.getCardNo());
			if (bmAccount.getCardType() != null && !"".equals(bmAccount.getCardType()))
				map.put("card_type", bmAccount.getCardType());

			map.put("open_branch", bmAccount.getOpenBranch());
			map.put("pre_mobile", bmAccount.getPreMobile());
			map.put("identifying_code", bmAccount.getIdentifyingCode());
			map.put("origin_order_no", bao.getOrderId());
			map.put("pay_code", ZYCF_REGISTER_PAY_CODE);

			logger.info("====================短验开户确认,订单发送存管系统,订单号：" + order.getOrderId() + "====================");
			Map<String, Object> sendRequest = null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_BINK_CARD_AFFIRM);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}
			logger.info(sendRequest);
			String recode = (String) sendRequest.get("recode");
			String remsg = (String) sendRequest.get("remsg");

			String platcust = (String) (String) sendRequest.get("platcust");
			order.setRecord(recode);
			order.setRemsg(remsg);
			logger.info("====================生成短验开户确认,存管系统返回状态,订单号：" + order.getOrderId() + ",状态码：" + recode + ",返回信息："
					+ order.getRemsg() + "====================");

			if ("10000".equals(recode)) {
				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				logger.info("====================" + bmAccount.getUserId() + bmAccount.getUserName()
						+ "短验开户确认成功，修改本地用户数据====================");

				DESTextCipher cipher = DESTextCipher.getDesTextCipher();
				try {
					user.setIdCode(cipher.encrypt(bmAccount.getIdCode()));
					bmAccount.setIdType("1");
					bmAccount.setIdCode(cipher.encrypt(bmAccount.getIdCode()));
					bmAccount.setPreMobile(cipher.encrypt(bmAccount.getPreMobile()));
					bmAccount.setCardNo(cipher.encrypt(bmAccount.getCardNo()));
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "身份证号加密异常");
				}

				user.setBirthDate(FormatUtils.birthDateFormat(user.getIdCode().substring(6, 14)));
				user.setUserName(bmAccount.getUserName());
				user.setLastModifyDate(new Date());
				logger.info(user.getId() + "修改用户信息:" + bmAccount.getUserName() + bmAccount.getIdCode());
				userService.modifyUserByParams(user);

				bmAccount.setUserId(user.getId());
				bmAccount.setPlatcust(platcust);
				try {
					bmAccountService.bmAccountModify(bmAccount);
				} catch (Exception e) {
					logger.info("订单修改异常" + e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "数据修改异常");
				}

				logger.info("===================短验开户确认成功====================");
				brd.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "短验开户确认成功"));
			} else {
				order.setOrderStatus(OrderStatus.FAILED);
				brd.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(),
						"短验开户确认失败:recode[" + recode + "]remsg" + remsg));
			}

			logger.info("====================短验开户确认,修改本地订单状态,订单号：" + order.getOrderId() + "====================");
			try {
				bmOrderService.bmOrderModify(order);
			} catch (Exception e) {
				logger.info("订单修改异常" + e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据修改异常");
			}

		} catch (UException e) {
			logger.info(e.fillInStackTrace());
			brd.setMessage(new Message(e.getCode().value(),
					e.getMessage() + brd.getMessage() == null ? "" : brd.getMessage().getMessage()));
		}

		return brd;
	}

	/**
	 * 短验卡户参数校验
	 * 
	 * @date 2017年6月29日下午3:51:55
	 * @param params
	 * @return
	 * @throws UException
	 * @author suzh
	 */
	BmAccount checkBmAccountParams(BmAccountParams params) throws UException {

		if (params == null || params.getBmaccount() == null)
			throw new UException(SystemEnum.PARAMS_ERROR, "参数未接收到");

		BmAccount bmAccount = params.getBmaccount();

		if (bmAccount.getIdCode() == null || !Validator.isIDCard(bmAccount.getIdCode()))
			throw new UException(SystemEnum.PARAMS_ERROR, "证件号码格式输入错误");

		if (bmAccount.getIdCode() == null || bmAccount.getIdCode().length() != 18)
			throw new UException(SystemEnum.PARAMS_ERROR, "必须使用18位的身份证号");

		bmAccount.setIdCode(bmAccount.getIdCode().toUpperCase());

		if (bmAccount.getUserName() == null || "".equals(bmAccount.getUserName()))
			throw new UException(SystemEnum.PARAMS_ERROR, "姓名不能为空");

		if (bmAccount.getCardType() != null
				&& !("1".equals(bmAccount.getCardType()) || "2".equals(bmAccount.getCardType())))
			throw new UException(SystemEnum.PARAMS_ERROR, "卡的类型输入错误");

		if (bmAccount.getOpenBranch() == null || "".equals(bmAccount.getOpenBranch()))
			throw new UException(SystemEnum.PARAMS_ERROR, "开户银行不能为空");

		if (!Validator.isDigit(bmAccount.getCardNo()))
			throw new UException(SystemEnum.PARAMS_ERROR, "银行卡号格式输入错误");

		if (!Validator.isMobile(bmAccount.getPreMobile()))
			throw new UException(SystemEnum.PARAMS_ERROR, "预留手机号格式输入错误");

		return bmAccount;
	}

	@Override
	public BmReturnData relieveCard(HttpServletRequest request) {

		BmReturnData brd = new BmReturnData();
		try {
			User user = (User) WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if (user == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");

			BmAccount account = new BmAccount();
			account.setUserId(user.getId());
			account = bmAccountService.queryBmAccountByParams(account);

			if (account.getPlatcust() == null)
				throw new UException(SystemEnum.UN_AUTHENTICATION, "没有绑卡实名认证,不可以解绑银行卡");

			if (account.getCardNo() == null)
				throw new UException(SystemEnum.UN_AUTHENTICATION, "没有绑定银行卡，无法解绑银行卡");

			BmOrder order = new BmOrder();
			order.setOrderType(OrderType.RELIEVE_CARD);
			order.setUserId(user.getId());
			order.setRemark("明细编号：" + order.getOrderId() + "-001");
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e) {
				logger.info("订单入库异常" + e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据入库异常");
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate().replace("-", ""));
			map.put("partner_trans_time", order.getOrderTime().replace(":", ""));
			map.put("total_num", 1);
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();

			RelieveCardParams params = new RelieveCardParams();
			params.setDetail_no(order.getOrderId() + "-001");
			try {
				params.setCard_no_old(cipher.decrypt(account.getCardNo()));
				if (user.getCustType() == 1)
					params.setMobile(cipher.decrypt(account.getPreMobile()));
				// else
				// params.setMobile(cipher.decrypt(user.getMobile()));
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "数据解密异常");
			}
			params.setName(account.getUserName());
			params.setPlatcust(account.getPlatcust());
			params.setPay_code(ZYCF_REGISTER_PAY_CODE);

			List<RelieveCardParams> list = new ArrayList<RelieveCardParams>();
			list.add(params);
			map.put("data", JSON.toJSON(list));

			Map<String, Object> sendRequest = null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_RELIEVE_CARD);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}
			logger.info(sendRequest);
			System.out.println(sendRequest);
			String recode = (String) sendRequest.get("recode");
			String remsg = (String) sendRequest.get("remsg");
			int success_num = (int) sendRequest.get("success_num");

			order.setRecord(recode);
			order.setRemsg(remsg);

			if ("10000".equals(recode) && success_num == 1) {
				BmAccount updateAccount = new BmAccount();
				updateAccount.setUserId(user.getId());
				bmAccountService.relieveCard(updateAccount);

				order.setRemark("用户解绑银行卡，原卡号：" + params.getCard_no_old() + "解绑成功");
				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				brd.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "解绑成功"));
			} else if ("10000".equals(recode) && success_num != 1) {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> res = (List<Map<String, Object>>) sendRequest.get("error_data");
				String error_no = res.get(0).get("error_no").toString();
				String error_info = res.get(0).get("error_info").toString();
				String detail_no = res.get(0).get("detail_no").toString();

				order.setRemark("解绑银行卡失败【" + error_no + "】" + error_info + " 明细编号：" + detail_no);
				order.setOrderStatus(OrderStatus.SUCCESSFUL);
				brd.setMessage(
						new Message(SystemEnum.OPRARION_FAILED.value(), "解绑银行卡失败【" + error_no + "】" + error_info));
			} else if (!"10000".equals(recode)) {
				order.setOrderStatus(OrderStatus.FAILED);
			}
			try {
				bmOrderService.bmOrderModify(order);
			} catch (Exception e) {
				logger.info("订单修改异常" + e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据修改异常");
			}
		} catch (UException e) {
			logger.info("解绑银行卡失败");
			logger.info(e.fillInStackTrace());
			brd.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return brd;
	}

	@Override
	public BmReturnData companyOpenAccont(HttpServletRequest request, BmAccountParams params) {
		BmReturnData brd = new BmReturnData();
		try {
			User user = (User) WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if (user == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");

			if (params == null || params.getBmCompany() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数未接收到");

			if (params.getBmCompany().getOrg_name() == null || "".equals(params.getBmCompany().getOrg_name()))
				throw new UException(SystemEnum.PARAMS_ERROR, "企业名称不能为空");

			if ((params.getBmCompany().getBank_license() == null || "".equals(params.getBmCompany().getBank_license()))
					&& (params.getBmCompany().getBusiness_license() == null
							|| "".equals(params.getBmCompany().getBusiness_license())))
				throw new UException(SystemEnum.PARAMS_ERROR, "营业执照编号、社会信用代码证不能都为空");

			BmOrder order = new BmOrder();
			order.setOrderType(OrderType.COMPANY_OPEN_ACCOUNT);
			order.setUserId(user.getId());
			order.setRemark("明细编号：" + order.getOrderId() + "-001");
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e) {
				logger.info("订单入库异常" + e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据入库异常");
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate());
			map.put("partner_trans_time", order.getOrderTime());
			map.put("total_num", 1);
			BmCompany bmCompany = new BmCompany();
			bmCompany.setDetail_no(order.getOrderId() + "-001");
			bmCompany.setCus_type("2");
			bmCompany.setOrg_name(params.getBmCompany().getOrg_name());
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			try {
				bmCompany.setMobile(cipher.decrypt(user.getMobile()));
			} catch (GeneralSecurityException e1) {
				e1.printStackTrace();
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "手机号解密异常");
			}

			if (params.getBmCompany().getBusiness_license() != null
					&& !"".equals(params.getBmCompany().getBusiness_license()))
				bmCompany.setBusiness_license(params.getBmCompany().getBusiness_license());

			if (params.getBmCompany().getBank_license() != null && !"".equals(params.getBmCompany().getBank_license()))
				bmCompany.setBank_license(params.getBmCompany().getBank_license());
			List<BmCompany> bmc = new ArrayList<BmCompany>();
			bmc.add(bmCompany);
			map.put("data", JSON.toJSONString(bmc));
			Map<String, Object> sendRequest = null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_COMPANY_OPEN_ACCOUNT);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}
			logger.info(sendRequest);
			String recode = sendRequest.get("recode").toString();
			String remsg = sendRequest.get("remsg").toString();
			String success_num = sendRequest.get("success_num").toString();
			String order_status = sendRequest.get("order_status").toString();

			order.setRecord(recode);
			order.setRemsg(remsg);

			if ("10000".equals(recode) && "1".equals(success_num) && "1".equals(order_status)) {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> res = (List<Map<String, Object>>) sendRequest.get("success_data");
				String platcust = res.get(0).get("platcust").toString();

				Company company = new Company();
				company.setUserId(user.getId());
				company.setOrg_name(params.getBmCompany().getOrg_name());
				company.setPlatcust(platcust);

				BmAccount account = new BmAccount();
				account.setUserId(user.getId());
				account.setPlatcust(platcust);
				account.setIdType("2");
				try {
					account.setIdCode(cipher.encrypt(params.getBmCompany().getBusiness_license() == null
							? params.getBmCompany().getBank_license() : params.getBmCompany().getBusiness_license()));

					company.setBusiness_license(cipher.encrypt(params.getBmCompany().getBusiness_license()));
					company.setBank_license(cipher.encrypt(params.getBmCompany().getBank_license()));
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "数据加密异常");
				}
				try {
					companyService.modifyCompanyByParams(company);
				} catch (Exception e) {
					logger.info("数据修改异常" + e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "数据修改异常");
				}

				account.setUserName(params.getBmCompany().getOrg_name());
				try {
					bmAccountService.modifyBmAccountByParams(account);
				} catch (Exception e) {
					logger.info("数据修改异常" + e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "数据修改异常");
				}

				User updateUser = new User();
				updateUser.setId(user.getId());
				updateUser.setUserName(params.getBmCompany().getOrg_name());
				updateUser.setIdCode(account.getIdCode());
				userService.modifyUserByParams(updateUser);

				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				brd.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "存管系统注册成功"));
			} else if ("10000".equals(recode) && !"1".equals(success_num)) {
				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				@SuppressWarnings("unchecked")
				List<Map<String, Object>> res = (List<Map<String, Object>>) sendRequest.get("error_data");
				String error_no = res.get(0).get("error_no").toString();
				String error_info = res.get(0).get("error_info").toString();

				order.setRemark("订单处理成功，开户失败" + error_no + error_info);

				brd.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), error_no + error_info));

			} else if (!"10000".equals(recode)) {
				order.setOrderStatus(OrderStatus.FAILED);

				brd.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), recode + remsg));
			}

			try {
				bmOrderService.bmOrderModify(order);
			} catch (Exception e) {
				logger.info("订单修改异常" + e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据修改异常");
			}

		} catch (UException e) {
			e.printStackTrace();
			brd.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return brd;
	}

	@Override
	public BmReturnData companybindCard(HttpServletRequest request, BmAccountParams params) {
		BmReturnData brd = new BmReturnData();
		try {
			User user = (User) WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if (user == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");

			if (params == null || params.getBmCompany() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "参数未接收到");

			if (params.getBmCompany().getOrg_no() == null || "".equals(params.getBmCompany().getOrg_no()))
				throw new UException(SystemEnum.PARAMS_ERROR, "组织机构代码不能为空");

			if (params.getBmCompany().getAcct_name() == null || "".equals(params.getBmCompany().getAcct_name()))
				throw new UException(SystemEnum.PARAMS_ERROR, "账户名称不能为空");

			if (params.getBmCompany().getAcct_no() == null || "".equals(params.getBmCompany().getAcct_no()))
				throw new UException(SystemEnum.PARAMS_ERROR, "账号不能为空");

			if (params.getBmCompany().getOpen_branch() == null || "".equals(params.getBmCompany().getOpen_branch()))
				throw new UException(SystemEnum.PARAMS_ERROR, "开户银行不能为空");

			BmAccount account = new BmAccount();
			account.setUserId(user.getId());
			account = bmAccountService.queryBmAccountByParams(account);

			BmOrder order = new BmOrder();
			order.setOrderType(OrderType.COMANY_CARD);
			order.setUserId(user.getId());
			try {
				order = bmOrderService.bmOrderPersistence(order);
			} catch (Exception e) {
				logger.info("订单入库异常" + e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据入库异常");
			}

			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			BindCardRecord record = new BindCardRecord();
			record.setId(GetUUID.getUniqueKey());
			record.setOrderId(order.getOrderId());
			record.setAcct_name(params.getBmCompany().getAcct_name());
			try {
				record.setAcct_no(cipher.encrypt(params.getBmCompany().getAcct_no()));
				record.setOrg_no(cipher.encrypt(params.getBmCompany().getOrg_no()));
				record.setPreMobile(params.getBmCompany().getMobile() == null ? user.getMobile()
						: cipher.encrypt(params.getBmCompany().getMobile()));
			} catch (GeneralSecurityException e1) {
				e1.printStackTrace();
				throw new UException(SystemEnum.DATA_SECURITY_EXCEPTION, "数据加密异常");
			}
			record.setCreateTime(new Date());
			record.setOpen_branch(params.getBmCompany().getOpen_branch());
			record.setUserId(user.getId());
			record.setStatus(OrderStatus.PROCESSING);
			try {
				bindCardRecordService.addBindCardRecord(record);
			} catch (Exception e) {
				logger.info("订单入库异常" + e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据入库异常");
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("order_no", order.getOrderId());
			map.put("partner_trans_date", order.getOrderDate());
			map.put("partner_trans_time", order.getOrderTime());
			map.put("platcust", account.getPlatcust());
			map.put("type", 2);
			map.put("org_no", params.getBmCompany().getOrg_no());
			map.put("acct_name", params.getBmCompany().getAcct_name());
			map.put("acct_no", params.getBmCompany().getAcct_no());
			map.put("open_branch", params.getBmCompany().getOpen_branch());
			map.put("pay_code", ZYCF_REGISTER_PAY_CODE);
			map.put("notify_url", ZYCF_COMPANY_BINDCARD_CALL);

			Map<String, Object> sendRequest = null;
			try {
				sendRequest = RequestUtil.sendRequest(map, ZYCF_COMPAY_BINDCARD);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}
			logger.info(sendRequest);
			String recode = sendRequest.get("recode").toString();
			String remsg = sendRequest.get("remsg").toString();

			order.setRecord(recode);
			order.setRemsg(remsg);

			if ("10000".equals(recode)) {

				order.setOrderStatus(OrderStatus.AUDITING);

				record.setStatus(OrderStatus.AUDITING);

				brd.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "绑卡申请成功"));
			} else {
				order.setOrderStatus(OrderStatus.FAILED);
				record.setStatus(OrderStatus.FAILED);

				brd.setMessage(new Message(SystemEnum.OPRARION_FAILED.value(), recode + remsg));
			}

			try {
				bmOrderService.bmOrderModify(order);
				bindCardRecordService.modifyBindCardRecord(record);
			} catch (Exception e) {
				logger.info("订单修改异常" + e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据修改异常");
			}

		} catch (UException e) {
			e.printStackTrace();
			brd.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}
		return brd;
	}

	@Override
	public Object companyBindCardCall(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			String order_no = (String) request.getParameter("order_no");
			String order_status = (String) request.getParameter("order_status");
			String error_info = (String) request.getParameter("error_info");
			String error_no = (String) request.getParameter("error_no");

			BindCardRecord record = new BindCardRecord();
			record.setOrderId(order_no);
			record = bindCardRecordService.queryRecordByParams(record);
			if (record == null)
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "没有此订单号");

			BmOrder order = new BmOrder();
			order.setOrderId(order_no);

			if ("1".equals(order_status)) {

				order.setOrderStatus(OrderStatus.SUCCESSFUL);

				Company company = new Company();
				company.setUserId(record.getUserId());
				company.setOrg_no(record.getOrg_no());
				company.setAcct_name(record.getAcct_name());
				company.setAcct_no(record.getAcct_no());
				company.setOpen_branch(record.getOpen_branch());
				try {
					companyService.modifyCompanyByParams(company);
				} catch (Exception e) {
					logger.info("数据修改异常" + e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "数据修改异常");
				}

				BmAccount account = new BmAccount();
				account.setUserId(record.getUserId());
				account.setCardNo(record.getAcct_no());
				account.setCardType("3");
				account.setOpenBranch(record.getOpen_branch());
				try {
					bmAccountService.modifyBmAccountByParams(account);
				} catch (Exception e) {
					logger.info("数据修改异常" + e.fillInStackTrace());
					throw new UException(SystemEnum.DATA_REFUSED, "数据修改异常");
				}

				record.setStatus(OrderStatus.SUCCESSFUL);
			} else {
				record.setStatus(OrderStatus.FAILED);
				record.setRemark(error_no + error_info);
				order.setOrderStatus(OrderStatus.FAILED);
			}

			try {
				bmOrderService.bmOrderModify(order);
				bindCardRecordService.modifyBindCardRecord(record);
			} catch (Exception e) {
				logger.info("订单修改异常" + e.fillInStackTrace());
				throw new UException(SystemEnum.DATA_REFUSED, "数据修改异常");
			}

			map.put("recode", "success");

		} catch (UException e) {
			e.printStackTrace();
		}
		return JSON.toJSON(map);
	}

	@Override
	public BmReturnData openIIAccountApp(HttpServletRequest request) {
		BmReturnData brd = new BmReturnData();
		try {
			User user = (User) WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if (user == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");

			BmAccount account = new BmAccount();
			account.setUserId(user.getId());
			BmAccount res = bmAccountService.queryBmAccountByParams(account);
			if (res == null || res.getOpen_ii_account() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "您的账户异常，请联系技术人员");
			if (res.getPlatcust() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "您没有实名认证，不能开通银行II类账户");
			if (res.getOpen_ii_account())
				throw new UException(SystemEnum.PARAMS_ERROR, "您的账户已开通银行II类账户，不能重复开通");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channel", ZYCF_PLAT_NO);
			map.put("skipurl", ZYCF_APP_OPEN_II_ACCOUNT_SUCCESSFUL);
			map.put("time", String.valueOf(System.currentTimeMillis()));
			map.put("userid", res.getPlatcust());
			StringBuffer createUrl = new StringBuffer();
			try {
				String encodeChannel = Des3.getEncData(ZYCF_PLAT_NO);
				logger.info("des3 channel密文：" + encodeChannel);

				String encodeUserid = Des3.getEncData(res.getPlatcust());
				logger.info("des3 userid密文：" + encodeUserid);

				// 签名
				String signdata = DesUtil.sign(map);
				// 公钥验签
				boolean verifySign = DesUtil.verifySign(map, signdata);

				logger.info("验签是否通过:" + (verifySign ? "通过" : "未通过"));
				createUrl.append("<form action='").append(ZYCF_APP_OPEN_II_ACCOUNT+"' ").append("method='post'>");
				createUrl.append("<input type='text' name='channel' value='").append(encodeChannel).append("'>");
				createUrl.append("<input type='text' name='userid' value='").append(encodeUserid).append("'>");
				createUrl.append("<input type='text' name='skipurl' value='").append(ZYCF_APP_OPEN_II_ACCOUNT_SUCCESSFUL).append("'>");
				createUrl.append("<input type='text' name='time' value='").append(map.get("time").toString()).append("'>");
				createUrl.append("<input type='text' name='sign' value='").append(signdata).append("'>");
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}
			brd.setRep(createUrl.toString());
			brd.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "成功"));
		} catch (UException e) {
			logger.info("开通银行II类账户异常");
			logger.info(e.fillInStackTrace());
			brd.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}

		return brd;
	}

	@Override
	public BmReturnData openIIAccountPc(HttpServletRequest request) {
		BmReturnData brd = new BmReturnData();
		try {
			User user = (User) WebUtils.getSessionAttribute(request, "zycfLoginUser");
			if (user == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");

			BmAccount account = new BmAccount();
			account.setUserId(user.getId());
			BmAccount res = bmAccountService.queryBmAccountByParams(account);
			if (res == null || res.getOpen_ii_account() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "您的账户异常，请联系技术人员");
			if (res.getPlatcust() == null)
				throw new UException(SystemEnum.PARAMS_ERROR, "您没有实名认证，不能开通银行II类账户");
			if (res.getOpen_ii_account())
				throw new UException(SystemEnum.PARAMS_ERROR, "您的账户已开通银行II类账户，不能重复开通");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channel", ZYCF_PLAT_NO);
			map.put("skipurl", ZYCF_PC_OPEN_II_ACCOUNT_SUCCESSFUL);
			map.put("time",  String.valueOf(System.currentTimeMillis()));
			map.put("userid", res.getPlatcust());
			StringBuffer createUrl = new StringBuffer();
			try {
				String encodeChannel = Des3.getEncData(ZYCF_PLAT_NO);
				logger.info("des3 channel密文：" + encodeChannel);

				String encodeUserid = Des3.getEncData(res.getPlatcust());
				logger.info("des3 userid密文：" + encodeUserid);

				// 签名
				String signdata = DesUtil.sign(map);
				// 公钥验签
				boolean verifySign = DesUtil.verifySign(map, signdata);
				logger.info("验签是否通过:" + (verifySign ? "通过" : "未通过"));
				
//				String openIIAcountRequest = RequestUtil.openIIAcountRequest(map, ZYCF_PC_OPEN_II_ACCOUNT);
// 				System.out.println(openIIAcountRequest);
				
				createUrl.append("<form action='").append(ZYCF_PC_OPEN_II_ACCOUNT+"' ").append("method='post'>");
				createUrl.append("<input type='text' name='channel' value='").append(encodeChannel).append("'>");
				createUrl.append("<input type='text' name='userid' value='").append(encodeUserid).append("'>");
				createUrl.append("<input type='text' name='skipurl' value='").append(ZYCF_PC_OPEN_II_ACCOUNT_SUCCESSFUL).append("'>");
				createUrl.append("<input type='text' name='time' value='").append(map.get("time").toString()).append("'>");
				createUrl.append("<input type='text' name='sign' value='").append(signdata).append("'>");
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(e.fillInStackTrace());
				throw new UException(SystemEnum.NET_CONNET_EXCEPTION, "网络连接异常");
			}
			brd.setRep(createUrl.toString());
			brd.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(), "成功"));
		} catch (UException e) {
			logger.info("开通银行II类账户异常");
			logger.info(e.fillInStackTrace());
			brd.setMessage(new Message(e.getCode().value(), e.getMessage()));
		}

		return brd;
	}

	@Override
	public BmReturnData openIIAccountResult(HttpServletRequest request,BmAccountParams params) {
		
		BmReturnData brd=new BmReturnData();
		User user = (User) WebUtils.getSessionAttribute(request, "zycfLoginUser");
		try{
			if (user == null)
				throw new UException(SystemEnum.USER_NOLOGIN, "您没有登录");
			BmAccount account = new BmAccount();
			account.setUserId(user.getId());
			if(params!=null&&"1".equals(params.getXib_signup())){
				account.setOpen_ii_account(true);
				try {
					bmAccountService.modifyBmAccountByParams(account);
				} catch (Exception e) {
					e.printStackTrace();
					throw new UException(SystemEnum.DATA_REFUSED, "数据修改异常");
				}
				brd.setMessage(new Message(SystemEnum.OPRARION_SUCCESS.value(),"成功"));
			}else
				brd.setMessage(new Message(SystemEnum.PARAMS_ERROR.value(),"参数错误"));
			
		}catch(UException e){
			logger.info("开通II类账户结果处理异常"+e.fillInStackTrace());
			brd.setMessage(new Message(e.getCode().value(),e.getMessage()));
		}
		return brd;
	}
}
