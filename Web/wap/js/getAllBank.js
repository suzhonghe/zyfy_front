//获取所有可以快捷支付的银行
;define(function(require,exports,module){
	require("./common.min.js");
	module.exports = function (fn,num){
		var getAllBank = Request(); 
		getAllBank.ajax({	
			url:"/zycfMarket/queryBankList",  
			type:'post',  
			data:{"number":num}, 
			success:function(str){
				var obj = JSON.parse(str);
				fn && fn(obj);
			}
		});
	};
});
