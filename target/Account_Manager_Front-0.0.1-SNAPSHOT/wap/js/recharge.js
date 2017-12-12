//充值
;define(function(require,exports,module){
	require("./common.min.js");
	var projectUrl = '';

	var oBankBg = document.querySelector(".recharge .bankBg");
	var oBankName = document.querySelector(".recharge .bankName");
	var oAccount = document.querySelector(".recharge .account");
	var oRechargeMoney = document.querySelector(".recharge .rechargeMoney input");
	var oReallyMoney = document.querySelector(".recharge .reallyMoney");
	var oBtn = document.querySelector(".recharge .bottomBtn span");

	var sortMessage = document.querySelector("input[name='sortMessage']");
	var reAbtain = document.querySelector(".reAbtain");
	var reSend = document.querySelector(".reSend");

	var flag = false;

	var isSend = false;
	var timeOut = null;
	reAbtain.onclick = function() {
		var sendMessage = Request(); 
		sendMessage.ajax({	
			url: projectUrl + "/front/util/sendMSgCodeApply",
			type:'post',  
			data:{}, 
			success:function(str){
				var data = JSON.parse(str);
				if (data.message.code === 1000) {
	        		isSend = true;
	        		var num = 60;
	        		reSend.style.display = 'inline-block';
	        		reSend.innerHTML = num + '秒后重新发送';
					reAbtain.style.display = 'none';
					timeOut = setInterval(function(){
						num--;
						if(num == 0){
							clearInterval(timeOut);
							reAbtain.style.display = 'inline-block';
							reSend.style.display = 'none';
						}
						reSend.innerHTML = num + '秒后重新发送';
					}, 1000)
	        	}
			}
		});
	};

	var getLoginStatus = require("./getLoginStatus.js");
	getLoginStatus(fnLink);
	function fnLink(obj){
		if( obj.userName ){  //用户为登录状态
			getBankInfo();
		}else{                                   //用户为未登录状态
			window.location.href = "../login.html";
		};
	};
    
    oRechargeMoney.onfocus = function(){
    	flag = false;
    };
    function getBankInfo(){
    	var userInfo = Request(); 
		userInfo.ajax({	
			url: projectUrl + "/front/userAccount/queryFundAccount",
			type:'post',  
			data:{}, 
			success:function(str){
				var obj = JSON.parse(str).userAccountVo;
				oBankBg.className = "alignLeft setWidth bankBg "+obj.cardName;
				oBankName.innerHTML = obj.cardName;
				oAccount.innerHTML = obj.cardNo;
				oRechargeMoney.onblur = function(){
					if( !isNaN(this.value) && this.value!="" && this.value!="0" ){
						oReallyMoney.innerHTML = parseFloat(this.value).toFixed(2)+"元";
						flag = true;
					}else{
						var floatWindow = new PopUpBox();
						var content = "<p style='text-align:center'>请输入充值金额</p>";
						floatWindow.init({
							iNow:0,          // 确保一个对象只创建一次
							tBar:false,  
							time:1500,  
							content:content,     // 内容
							workBar:false
						});
					}
				};
			}
		});
    };
	oBtn.onclick = function(){
		var This = this;
		if (This.btn) {
			var floatWindow = new PopUpBox();
			var content = "<p style='text-align:center'>处理中...</p>";
			floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
			return;
		}
		This.btn = true;
		This.style.backgroundColor = '#737373';
		if( flag && isSend ){
			var rechargeAjax = Request(); 
			rechargeAjax.ajax({	
				url: projectUrl + "/front/fund/quickAccountRecharge",  
				type:'post',  
				data:{
					"amt": oRechargeMoney.value,
                	"smsCode": sortMessage.value
				}, 
				success:function(str){
					This.btn = false;
					This.style.backgroundColor = '#ff5d16';
					var obj = JSON.parse(str);
					var floatWindow = new PopUpBox();
					var content = "<p style='text-align:center'>"+obj.message.message+"</p>";
					floatWindow.init({
						iNow:0,          // 确保一个对象只创建一次
						tBar:false,  
						time:1500,  
						content:content,     // 内容
						workBar:false
					});
					if(obj.message.code == 2002){
						setTimeout(function () {
							window.location.href = "../login.html";
						}, 2000)
					} else if (obj.message.code == 1000) {
						setTimeout(function () {
							window.location.href = "../../index.html";
						}, 2000)
					};
				},
				error: function () {
					This.btn = false;
					This.style.backgroundColor = '#ff5d16';
				}
			});
		}else{
			This.btn = false;
			This.style.backgroundColor = '#ff5d16';
			var floatWindow = new PopUpBox();
			var content = "";
			if (!flag) {
				content = "<p style='text-align:center'>请输入正确充值金额</p>";
			} else if (!isSend) {
				content = "<p style='text-align:center'>请先获取短信验证码</p>";
			}
			floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
		}
	};
});
