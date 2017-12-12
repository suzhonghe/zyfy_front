//登录
;define(function(require,exports,module){
	require("./common.min.js");
	var loginBtn = document.querySelector(".login .bottomBtn");
	var flag = true;
    if(flag){
    	loginBtn.onclick = function(){
    		flag = false;
    		var name = document.querySelector(".login .user input").value;
            var pass = document.querySelector(".login .password input").value;

            if(name == "" || pass == ""){
                var floatWindow = new PopUpBox();
			    var content = "<p style='text-align:center'>用户名或密码不能为空</p>";
				floatWindow.init({
					iNow:0,          // 确保一个对象只创建一次
					tBar:false,  
					time:1500,  
					content:content,     // 内容
					workBar:false
				});
				flag = true;
                return;
            };
            var req = Request(); 
			req.ajax({	
				url: "/front/user/login",
				type: 'post',  
				data:{
					"userVo.loginName": name,
					"userVo.passphrase": pass
				}, 
				success:function(str){
					flag = true;
					var loginObj = JSON.parse(str).message;
					if(loginObj.code == 1000){
	                    window.history.go( -1 );
	                }else{
	                	var floatWindow = new PopUpBox();
					    var content = "<p style='text-align:center'>"+loginObj.message+"</p>";
						floatWindow.init({
							iNow:0,          // 确保一个对象只创建一次
							tBar:false,  
							time:1500,  
							content:content,     // 内容
							workBar:false
						});
	                };
				}
			});
    	};
    };

    var getLoginStatus = require("./getLoginStatus.js");
	getLoginStatus(fnLink);
	function fnLink(obj){
		if( obj.userName ){  //用户为登录状态
			window.location.href = "./myAccount.html";
		}
	};
});
