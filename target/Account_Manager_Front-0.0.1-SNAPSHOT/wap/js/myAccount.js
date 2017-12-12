//我的账户
;define(function(require,exports,module){
	require("./common.min.js");
	var url = '';

	document.querySelector(".myAccount .setUpBtn").onclick = function(){
		window.location.href = "./../html/account/setUp.html";
	};


	var userAccountInfo = require("./userAccountInfo.js");
	userAccountInfo(getAccountInfo);
	function getAccountInfo(obj){
		if(obj.message.code == 2002){
			window.location.href = "./../html/login.html";
		};
		document.querySelector(".myAccount .amount").innerHTML = parseFloat(obj.userAccountVo.allCapital).toFixed(2)+"<span>元（总资产）</span>";
		document.querySelector(".myAccount .profitAmount span").innerHTML = parseFloat(obj.userAccountVo.allRevue).toFixed(2);
		document.querySelector(".myAccount .balance span").innerHTML = parseFloat(obj.userAccountVo.availableAmount).toFixed(2);
	};

    var oUserName = document.querySelector(".myAccount .userName");
    var oRecharge = document.querySelector(".myAccount .recharge");
    var oWithdrawals = document.querySelector(".myAccount .withdrawals");
	var userInfo = Request(); 
	userInfo.ajax({	
		url: url + "/front/userAccount/getUserSetting",
		type:'post',  
		data:{}, 
		success:function(str){
			var obj = JSON.parse(str);
			if(obj.message.code == 2002){
	    		window.location.href = "login.html";
	    	};
			if(obj.userInfo.userName){
				oUserName.innerHTML = obj.userInfo.userName+"的账户";
			}else{
				oUserName.innerHTML = obj.userInfo.mobile+"的账户";
			};
			if(!obj.userInfo.cardNo){
				oRecharge.onclick = function(){
					msgInfo();
				};
				oWithdrawals.onclick = function(){
					msgInfo();
				};
				function msgInfo(){
					var floatWindow = new PopUpBox();
			        var content = "<p style='text-align:center'>请先绑定银行卡</p>";
					floatWindow.init({
						iNow:0,          // 确保一个对象只创建一次
						tBar:false,  
						time:1500,  
						content:content,     // 内容
						workBar:false
					});
				};
			}else{
				oRecharge.style.backgroundColor = "#ff5d16";
				oRecharge.onclick = function(){
					window.location.href = "./../html/account/recharge.html";
				};
				oWithdrawals.style.backgroundColor = "#ff5d16";
				oWithdrawals.onclick = function(){
					window.location.href = "./../html/account/withdrawals.html";
				};
			};
		}
	});
});
