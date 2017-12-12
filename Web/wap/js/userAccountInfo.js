//获取账户信息
;define(function(require,exports,module){
	require("./common.min.js");
	module.exports = function userAccountInfo(fn){
		var req1 = Request(); 
		req1.ajax({	
			url: "/front/userAccount/userAccountInfo",
			type:'post',  
			data:{}, 
			success:function(str){
				var obj = JSON.parse(str);
				fn && fn(obj);
			}
		});
	};
});
