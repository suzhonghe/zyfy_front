//我的银行卡
;define(function(require,exports,module){
	require("./common.min.js");
	
    var oBankBar = document.querySelector(".myBankCard .bankBar");
    var oNoCard = document.querySelector(".myBankCard .noCard");
    var oBankName = document.querySelector(".myBankCard .bankName");
    var oAccount = document.querySelector(".myBankCard .account");

    var oSetWidth = document.querySelector(".myBankCard .setWidth");


	var getUserInfo = Request(); 
	getUserInfo.ajax({	
		url:"/zycfMarket/user/userSettings",  
		type:'post',  
		data:{}, 
		success:function(str){
			var obj = JSON.parse(str);
			if(obj.code == "USER_NOLOGIN"){
	    		window.location.href = "./../login.html";
	    	};
			if(obj.cardauthenticated){
				oBankBar.style.display = "block";
				oNoCard.style.display = "none";
				oSetWidth.className = "alignLeft setWidth bankBg "+obj.bank;
				oBankName.innerHTML = obj.bank;
				oAccount.innerHTML = obj.account;
			}else{
				oBankBar.style.display = "none";
				oNoCard.style.display = "block";
			}
		}
	});
	
});