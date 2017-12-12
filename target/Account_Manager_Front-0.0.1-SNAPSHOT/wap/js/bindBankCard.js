//绑定银行卡
;define(function(require,exports,module){
	require("./common.min.js");
	require("./../../js/bank.js");
	
    var oRealName = document.querySelector(".bindBankCard .realName");
    var oCardNumber = document.querySelector(".bindBankCard .cardNumber input");
    var oBank = document.querySelector(".bindBankCard .bank span");
    var oChooseBtn = document.querySelector(".bindBankCard .chooseBtn");
    var oBtn = document.querySelector(".bindBankCard .bottomBtn span");
    var oChooseBar = document.querySelector(".bindBankCard .chooseBank");
    var oUl = document.querySelector(".chooseBank ul");
    var oABack = document.querySelector(".bindBankCard .chooseBank header a");

    var bindCard = false;
    var onOff = false;
	var getRealName = Request(); 
	getRealName.ajax({	
		url:"/zycfMarket/user/userSettings",  
		type:'post',  
		data:{}, 
		success:function(str){
			var obj = JSON.parse(str);
			if(obj.code == "USER_NOLOGIN"){
	    		window.location.href = "./../login.html";
	    	};
	    	onOff = obj.idauthenticated;
			oRealName.innerHTML = obj.name;
			bindCard = obj.cardauthenticated;
		}
	});
	/*选择银行页面*/
    oChooseBtn.onclick = function(){
    	oChooseBar.style.display = "block";
    };
    oABack.onclick = function(){
    	oChooseBar.style.display = "none";
    };
    var getAllBank = require("./getAllBank.js");
    getAllBank(showAllBank,"00");
    function showAllBank(obj){
    	oUl.innerHTML = "";
    	if( obj instanceof Array ){
    		for(var i=0,len=obj.length;i<len;i++){
    			var oLi =document.createElement("li");
    			oLi.setAttribute("abbr",obj[i].bankCode);
    			oLi.className = "bankBg "+obj[i].bankCode;
    			oLi.innerHTML = obj[i].bankName;
    			oUl.appendChild(oLi);
    			oLi.onclick = function(){
                    oBank.setAttribute("abbr",this.getAttribute("abbr"));
                    oBank.className = this.className;
                    oBank.innerHTML = this.innerHTML;
                    oChooseBar.style.display = "none";
    			};
    		};
    	};
    };
    /*选择银行页面 end*/

    
    oBtn.onclick = function(){
    	var cardURL = "/zycfMarket/app/bankCardBinding";  //修改银行卡、绑定银行卡URL
		var bankObj = distinctionBankCard( oCardNumber.value );
    	var reg = /^\d{16}|\d{19}$/;
    	if(onOff && reg.test(oCardNumber.value) && oBank.innerHTML == bankObj.bank){
    		var binkAjax = Request(); 
			binkAjax.ajax({	
				url:cardURL,  
				type:'post',  
				data:{"bank":oBank.getAttribute("abbr"),"account":oCardNumber.value,"fastPayment":"0","source":"wap"},
				success:function(str){
					var obj = JSON.parse(str);
					if(obj.code == 1){
	            		window.location.href = obj.message;
	            	}else{
	            		var floatWindow = new PopUpBox();
				        var content = "<p style='text-align:center'>"+obj.message+"</p>";
						floatWindow.init({
							iNow:0,          // 确保一个对象只创建一次
							tBar:false,  
							time:1500,  
							content:content,     // 内容
							workBar:false
						});
	            	};
				}
			});
    	}else{
    		var floatWindow = new PopUpBox();
	        var content = "";
			if(!onOff){   // 未实名验证
				content = "<p style='text-align:center'>请先实名认证，再绑定银行卡</p>";
			}else if(!reg.test(oCardNumber.value)){
				content = "<p style='text-align:center'>银行卡位数不正确</p>";
			}else if(oBank.innerHTML != bankObj.bank){
				content = "<p style='text-align:center'>您填写的卡号与发卡银行不一致，请重新选择发卡银行</p>";
			};
			floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
    	};
    };
});
