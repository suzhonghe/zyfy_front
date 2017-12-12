//首页标的列表
;define(function(require,exports,module){
	require("./common.min.js");

	var oRegister = document.querySelector(".register a");
	var oRecharge = document.querySelector(".recharge a");

	bind(oRegister, 'click', function(){
		accountBtn(noLink);
	});
	bind(oRecharge, 'click', function(){
		accountBtn(fnLink);
	});

	function accountBtn(fn){             // 首页点击我的账户按钮
		var getLoginStatus = require("./getLoginStatus.js");
		getLoginStatus(fn);
	};

	function fnLink(obj){
		if( obj.userName && obj.userName!="" ){  //用户为登录状态
			window.location.href = "./../html/account/recharge.html";
		}else{                                   //用户为未登录状态
			window.location.href = "./../html/login.html";
		}
	};

	function noLink(obj){
		if( obj.userName && obj.userName!="" ){  //用户为登录状态
			oRegister.setAttribute("href","javascript:");
		}else{                                   //用户为未登录状态
			window.location.href = "./../html/register.html";
		}
	};
});
