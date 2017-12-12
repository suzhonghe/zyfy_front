//绑卡、换卡成功
;define(function(require,exports,module){
	require("./common.min.js");
    
    var oHead = document.querySelector(".withdrawalsSuc header span");
	var oSuccessBg = document.querySelector(".withdrawalsSuc .successBg");
	var oMsg = document.querySelector(".withdrawalsSuc .success .msg");

	var str = window.location.toString();
	var arr = str.split(/&|=|,/);
	for(var i=0;i<arr.length;i++){
	    if(arr[i]=="ret_code" && arr[i+1] == "0000"){
	    }else if(arr[i]=="ret_code" && arr[i+1] != "0000"){
	    	oSuccessBg.style.backgroundImage = "/zycfMarket/wap/img/failedBig.png";
	    }
	};
	for(var i=0;i<arr.length;i++){
	    if(arr[i]=="ret_msg"){
	    	oHead.innerHTML = decodeURI(arr[i+1]);
			oMsg.innerHTML = decodeURI(arr[i+1]);
			break;
		}
	};

	setTimeout(function(){
	    window.location.href = "https://www.zuoyoufy.com/wap/index.html";
	},3000);
});
