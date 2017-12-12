//获取可用红包
;define(function(require,exports,module){
	require("./common.min.js");
	module.exports = function getLoginStatus(id,fn){
		var req = Request(); 
		req.ajax({	
			url:"/zycfMarket/queryFreshAmounts",  
			type:'post',  
			data:{"loanId":id}, 
			success:function(str){
				var obj = JSON.parse(str);
				fn && fn(obj);
			}
		});
	};
});
