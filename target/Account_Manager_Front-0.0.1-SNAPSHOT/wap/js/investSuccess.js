//投资成功
;define(function(require,exports,module){
	require("./common.min.js");

    var oHeadSpan = document.querySelector(".InvestSuccess header span");
    var oSuccessBg = document.querySelector(".InvestSuccess .successBg");
    var oMsg = document.querySelector(".InvestSuccess .msg");
    var oProduct = document.querySelector(".InvestSuccess .product");
    var oAmount = document.querySelector(".InvestSuccess .amount");
    var oBidDetails = document.querySelector(".InvestSuccess .bidDetails");
    var oSee = document.querySelector(".InvestSuccess .see");

	var str = window.location.toString();
	var arr = str.split(/&|=|,/);
	var flag = false;

	for(var i=0;i<arr.length;i++){
	    if(arr[i]=="ret_code" && arr[i+1] == "0000"){
	        flag = true;
	    }else if(arr[i]=="ret_code" && arr[i+1] != "0000"){
	    	oHeadSpan.innerHTML = "投资失败";
	    	oSuccessBg.className = "failBg";
	    	oMsg.innerHTML = "投资失败！";
	    	oBidDetails.style.display = "none";
	    }
	};
	for(var i=0;i<arr.length;i++){
	    if(arr[i]=="order_id" && flag){
	    	var req = Request(); 
			req.ajax({	
				url:"/zycfMarket/queryByOrderId",  
				type:'post',  
				data:{"orderId":arr[i+1]}, 
				success:function(str){
					var obj = JSON.parse(str);
					oProduct.innerHTML = obj.productName+"，"+obj.title;
					oAmount.innerHTML = parseFloat(obj.investAmount).toFixed(2)+"元";
				}
			});
	    }
	};

	setTimeout(function(){
	    window.location.href = "https://www.zuoyoufy.com/wap/index.html";
	},3000);

    /*oSee.onclick = function(){
    	window.location.href = "/zycfMarket/wap/html/invested.html?"+loanID;
    };*/
});
