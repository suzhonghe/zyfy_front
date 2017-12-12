//登录接口，传入一个执行函数
;define(function(require,exports,module){
	require("./common.min.js");
	module.exports = function getLoginStatus(fn){
		var req = Request(); 
		req.ajax({	
			url: '/front/user/loginStatus', 
			type: 'post',  
			data: {}, 
			success:function(str){
				var obj = JSON.parse(str);
				fn && fn(obj);

			}
		});
	};
});
