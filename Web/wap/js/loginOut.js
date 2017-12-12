//退出登录
;define(function(require,exports,module){
	require("./common.min.js");
	var oLoginOut = document.querySelector(".outLogin");
	oLoginOut.onclick = function(){
		var floatWindow = new PopUpBox();
        var content = "<p style='text-align:center'>你确定要退出登录吗</p>";
		floatWindow.init({
			iNow:0,          // 确保一个对象只创建一次
			tBar:false, 
			content:content,     // 内容
			callback:fnLoginOut
		});
		function fnLoginOut(){
			var req = Request(); 
			req.ajax({	
				url: '/front/user/logOut',  
				type:'post',  
				data:{}, 
				success:function(str){
					var obj = JSON.parse(str);
					if(obj.message.code == 1000){
						window.location.href="./../../index.html";
					}else{
						var floatWindow1 = new PopUpBox();
				        var content1 = "<p style='text-align:center'>退出失败，请刷新再试</p>";
						floatWindow1.init({
							iNow:0,          // 确保一个对象只创建一次
							tBar:false,  
							time:1500,  
							content:content1,     // 内容
							workBar:false
						});
					}
				}
			});
		};
	};
});
