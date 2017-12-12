//首页体验标获取
;define(function(require,exports,module){
	require("./common.min.js");
	module.exports = function getTestBid(fn){
		var req = Request(); 
		req.ajax({	
			url:"/zycfMarket/queryVirtualLoanOpened",  
			type:'post',  
			data:{}, 
			success:function(str){
				var obj = JSON.parse(str);
				fn && fn(obj);
			}
		});
	};
});
