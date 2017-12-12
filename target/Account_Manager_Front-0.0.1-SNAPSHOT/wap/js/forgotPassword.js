//找回密码
;define(function(require,exports,module){
	require("./common.min.js");

	var projectUrl = '';

	var oSecond = document.querySelector(".forgotPassword .second");
	var oThree = document.querySelector(".forgotPassword .three");
	var firstBtn = document.querySelector(".forgotPassword .first .bottomBtn span");
	var oReSend = document.querySelector(".forgotPassword .second .reSend");
	var getPho = document.querySelector(".forgotPassword .first .user input");
	var oCaptcha = document.querySelector(".forgotPassword .second .captcha input");
	var secondBtn = document.querySelector(".forgotPassword .second .bottomBtn span");
	var threeBtn = document.querySelector(".forgotPassword .three .bottomBtn span");
	var oPassword = document.querySelector(".forgotPassword .three .user input");
	var oMakesurePassword = document.querySelector(".forgotPassword .three .password input");
	var flag = true;
    if(flag){
    	firstBtn.onclick = function(){
    		flag = false;
            if(getPho.value == "" || !checkMobile(getPho.value) ){
                var floatWindow = new PopUpBox();
			    var content = "";
			    if(getPho.value == ""){
			    	content = "<p style='text-align:center'>不能为空</p>";
			    }else if( !checkMobile(getPho.value) ){
			    	content = "<p style='text-align:center'>请输入正确手机号</p>";
			    };
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
            sendMessage();
    	};
    };
    oReSend.onclick = function(){
    	sendMessage();
    };
    function sendMessage(){
    	var req = Request(); 
		req.ajax({	
			url: projectUrl + '/front/util/resetPasswdMsg', 
			type: 'post',  
			data:{"mobile": getPho.value}, 
			success:function(str){
				var loginObj = JSON.parse(str);
				if(loginObj.code == 1000){  // 成功，已发短信
                    document.querySelector(".forgotPassword .first").style.display = "none";
                    oSecond.style.display = "block";
                    document.querySelector(".forgotPassword .second .pho").innerHTML = getPho.value;
                    var oCountdown = document.querySelector(".forgotPassword .second .number");
                    var num = 60;
                    var myTime = null;
                    oCountdown.style.display = "block";
                    oCountdown.innerHTML = num+"s";
                    myTime = setInterval(function(){
                        num--;
                        if(num<=0){
                            clearInterval(myTime);
                            oCountdown.style.display = "none";
                            oReSend.style.display = "block";
                        };
                        oCountdown.innerHTML = num+"s";
                    },1000)
			    }else{  
			    	var floatWindow = new PopUpBox();
		            var content = "";
			    	if(loginObj.code == 0){   // 手机号不存在
						content = "<p style='text-align:center'>手机号还未在平台注册</p>";
				    }else if(loginObj.code == 5){
				    	content = "<p style='text-align:center'>请求次数过多，每天不能多于2次</p>";
				    };
				    floatWindow.init({
						iNow:0,          // 确保一个对象只创建一次
						tBar:false,  
						time:1500,  
						content:content,     // 内容
						workBar:false
					});
					flag = true;
	                return;
                    oReSend.style.display = "none";
			    };
			}
		});
    };
    secondBtn.onclick = function(){
    	var reg = /^[a-zA-Z0-9]+$/;
    	if(oCaptcha.value == "" || !reg.test(oCaptcha.value) ){
	        var floatWindow = new PopUpBox();
	        var content = "";
	        if(oCaptcha.value == ""){
                content = "<p style='text-align:center'>不能为空</p>";
	        }else if( !reg.test(oCaptcha.value) ){
	        	content = "<p style='text-align:center'>请输入正确短信验证码</p>";
	        };
			floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
	        return;
	    }else{
    		var requ = Request(); 
			requ.ajax({	
				url: projectUrl + "/front/util/resetPasswdValidate",
				type:'post',  
				data:{"resetCode":oCaptcha.value}, 
				success:function(str){
					var loginObj = JSON.parse(str);
					if(loginObj.code == 1000){
    					oSecond.style.display = "none";
    					oThree.style.display = "block";
    				}else{
    					var floatWindow = new PopUpBox();
				        var content = loginObj.message;
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
    threeBtn.onclick = function(){
    	if( oPassword.value != "" && oMakesurePassword.value != "" && testNum(oPassword.value) && oPassword.value==oMakesurePassword.value ){
    		var reque = Request(); 
			reque.ajax({	
				url: projectUrl + "/front/user/resetPassWord", 
				type:'post',  
				data:{
					"user.passphrase":oPassword.value
				}, 
				success:function(str){
					var loginObj = JSON.parse(str);
					var floatWindow = new PopUpBox();
			        var content = loginObj.message.message;
					floatWindow.init({
						iNow:0,          // 确保一个对象只创建一次
						tBar:false,  
						time:2000,  
						content:content,     // 内容
						workBar:false
					});
					if(loginObj.message.code == 1000){
						setTimeout(function(){
							window.location.href = "./../html/login.html";
						},2100);
					}
				}
			});
    	}else{
    		var floatWindow = new PopUpBox();
	        var content = "";
	        if(oPassword.value == ""){
                content = "<p style='text-align:center'>请输入新密码</p>";
	        }else if( oMakesurePassword.value == "" ){
	        	content = "<p style='text-align:center'>请确认新密码</p>";
	        }else if( !testNum(oPassword.value) ){
	        	content = "<p style='text-align:center'>请输入6-16位数字与字母组合密码</p>";
	        }else if( oPassword.value!=oMakesurePassword.value ){
	        	content = "<p style='text-align:center'>请确认2次密码输入一致</p>";
	        };
			floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
    	};
    };
});
