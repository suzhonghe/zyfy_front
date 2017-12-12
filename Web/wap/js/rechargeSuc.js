//提现、充值成功
;define(function(require,exports,module){
	require("./common.min.js");
    
    var oHead = document.querySelector(".withdrawalsSuc header span");
	var oSuccessBg = document.querySelector(".withdrawalsSuc .successBg");
	var oMsg = document.querySelector(".withdrawalsSuc .success .msg");
	var oAmount = document.querySelector(".withdrawalsSuc .amount");
	var oCounterFee = document.querySelector(".withdrawalsSuc .counterFee");
	var oAccount = document.querySelector(".withdrawalsSuc .account");

	var str = window.location.toString();
	var arr = str.split(/&|=|,/);
	var flag = false;
	for(var i=0;i<arr.length;i++){
	    if(arr[i]=="ret_code" && arr[i+1] == "0000"){
	        flag = true;
	    }else if(arr[i]=="ret_code" && arr[i+1] != "0000"){
	    	oSuccessBg.style.backgroundImage = "/zycfMarket/wap/img/failedBig.png";
	    }
	};
	for(var i=0;i<arr.length;i++){
	    if(arr[i]=="ret_msg"){
	    	oHead.innerHTML = decodeURI(arr[i+1]);
			oMsg.innerHTML = decodeURI(arr[i+1]);
			document.title = decodeURI(arr[i+1]);
			break;
		}
	};
	if( getCookie('money') && getCookie('rates') ){
		oAmount.innerHTML = parseFloat(getCookie('money')).toFixed(2);
		oCounterFee.innerHTML = parseFloat(getCookie('rates')).toFixed(2);
		oAccount.innerHTML = (parseFloat( getCookie('money') )-parseFloat( getCookie('rates') )).toFixed(2);
	};

	setTimeout(function(){
	    window.location.href = "https://www.zuoyoufy.com/wap/index.html";
	},3000);
});
