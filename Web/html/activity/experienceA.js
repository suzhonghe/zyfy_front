;$(".registerBtn a").click(function(){
	getData(registerBtnCall);
});

$(".rechargeBtn a").click(function(){
	getData(rechargeBtnCall);
});


function registerBtnCall(obj){
	if(obj.userName!=null){
		$(".registerBtn a").attr("href","javascript:");
	}else{
		window.location.href = "./../regsiter.html"
	}
};

function rechargeBtnCall(obj){
	if(obj.userName!=null){
		window.location.href = "./../account/recharge.html"
	}else{
		window.location.href = "./../zyfyLogin.html"
	}
};

;function getData(fn){
	$.ajax({
		url: '/zycfMarket/loginStatus',
		type: 'post',
		data: {},
		success: function(str){
			fn && fn(str);
			/**/
		}
	});
};

