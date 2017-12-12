//提现
;define(function(require,exports,module){
	require("./common.min.js");
	var projectUrl = '';

	var oBankBg = document.querySelector(".withdrawalsPage .bankBg");
	var oBankName = document.querySelector(".withdrawalsPage .bankName");
	var oAccount = document.querySelector(".withdrawalsPage .account");
	var oAvailableAmount = document.querySelector(".withdrawalsPage .availableAmount strong");
	var oWithdrawalsAmount = document.querySelector(".withdrawalsPage .withdrawalsAmount input");
	var oBtn = document.querySelector(".withdrawalsPage .bottomBtn span");

	var sortMessage = document.querySelector("input[name='sortMessage']");
	var reAbtain = document.querySelector(".reAbtain");
	var reSend = document.querySelector(".reSend");

	var flag = false;
    
    oWithdrawalsAmount.onfocus = function(){
    	flag = false;
    };
    
    var withdrawalsInfo = Request(); 
	withdrawalsInfo.ajax({	
		url: projectUrl + "/front/userAccount/queryFundAccount",
		type:'post',  
		data:{}, 
		success:function(str){
			var data = JSON.parse(str);
			var obj = data.userAccountVo;
			if(data.message.code == 2002){
	    		window.location.href = "../login.html";
	    	};
			oBankBg.className = "alignLeft setWidth bankBg "+obj.cardName;
			oBankName.innerHTML = obj.cardName;
			oAccount.innerHTML = obj.cardNo;
			oAvailableAmount.innerHTML = parseFloat(obj.availableAmount).toFixed(2);
			oWithdrawalsAmount.onblur = function(){
				if( !isNaN(this.value) && this.value!="" && this.value!="0" && parseFloat(this.value)<=parseFloat(obj.availableAmount) ){
					flag = true;
				}else{
					var floatWindow = new PopUpBox();
					var content = "";
					if( this.value=="" || !!isNaN(this.value) || this.value=="0" ){
						content = "<p style='text-align:center'>请输入提现金额</p>";
					}else if( parseFloat(this.value)>parseFloat(obj.availableAmount) ){
						content = "<p style='text-align:center'>提现金额不能大于"+obj.availableAmount+"</p>";
					};
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
		if( flag && isSend){
			var withdrawalsAjax = Request(); 
			withdrawalsAjax.ajax({	
				url: projectUrl + "/front/fund/personWithdraw",
				type:'post',  
				data:{
					"amount": oWithdrawalsAmount.value,
                	"sms_code": sortMessage.value
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
					if (obj.message.code === 1000) {
                		setTimeout(function () {
                			window.location.href = "../../index.html";
                		}, 2500)
                	}else if (obj.message.code === 2002) {
                		setTimeout(function () {
                			window.location.href = "../login.html";
                		}, 2500)
                	}
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
				content = "<p style='text-align:center'>请输入正确提现金额</p>";
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
