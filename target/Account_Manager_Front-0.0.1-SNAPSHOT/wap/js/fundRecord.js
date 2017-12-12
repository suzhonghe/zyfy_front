//资金记录
;define(function(require,exports,module){
	require("./common.min.js");
	module.exports = function(opt,fn){
		var req = Request(); 
		req.ajax({	
			url: "/front/userAccount/userFundRecordList",   
			type: 'POST',  
			data:{
				"pageNo":opt.page,
				"pageSize":opt.num,
				"params[type]":opt.type
			}, 
			success:function(str){
				var obj = JSON.parse(str);
				fn && fn(obj);
			}
		});
	};
});
