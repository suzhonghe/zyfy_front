//设置
;define(function(require,exports,module){
	require("./common.min.js");
	require("./loginOut.js");
	var projectUrl = '';
	
    var oIdentInfo = document.querySelector(".setUp .identInfo");
    var oBankInfo = document.querySelector(".setUp .bankInfo");
    var unbank = document.querySelector(".setUp .removeCode .unbank");
    var open2 = document.querySelector(".setUp .removeCode .open2");
    var formWrap = document.querySelector(".setUp .formWrap");
    var form_action = null;

	var setUpAjax = Request(); 
	setUpAjax.ajax({	
		url: projectUrl + "/front/userAccount/getUserSetting",  
		type: 'post',  
		data:{}, 
		success:function(str){
			var data = JSON.parse(str);
			var obj = data.userInfo;
			if(data.message.code == 2002){
	    		window.location.href = "./../login.html";
	    	};
	    	if (!obj.idCode || obj.open_ii_account) {
	    		open2.style.display = 'none';
	    	};
	    	if (obj.idCode && !obj.open_ii_account) {
	    		open2.onclick = function () {
					var This = this;
			    	if (This.isClick) {
			    		var floatWindow = new PopUpBox();
				        var content = "<p style='text-align:center'>正在进行中</p>";
						floatWindow.init({
							iNow:0,          // 确保一个对象只创建一次
							tBar:false,  
							time:1500,  
							content:content,     // 内容
							workBar:false
						});
			    		return;
			    	};
			    	This.isClick = true;
			    	var openAjax = Request(); 
			    	openAjax.ajax({	
						url: projectUrl + "/front/bmAccount/openIIAccountApp",  
						type: 'post',  
						data:{}, 
						success:function(str){
							This.isClick = false;
							var data = JSON.parse(str);
							if(data.message.code == 1000){
								formWrap.innerHTML = data.rep;
								form_action = document.querySelector("form");
								var floatWindow = new PopUpBox();
						        var content = "<p style='text-align:center'>开通Ⅱ类账户</p>";
								floatWindow.init({
									iNow:0,          // 确保一个对象只创建一次
									tBar:false,  
									time:null,  
									content:content,     // 内容
									workBar:true,
									callback: function () {
										form_action.submit();
									}
								});
			            	} else {
			            		var floatWindow = new PopUpBox();
						        var content = "<p style='text-align:center'>"+data.message.message+"</p>";
								floatWindow.init({
									iNow:0,          // 确保一个对象只创建一次
									tBar:false,  
									time:1500,  
									content:content,     // 内容
									workBar:false
								});
			            	};
						},
						error: function (err) {
							This.isClick = false;
							console.log(err)
						}
					});
				};
	    	};
			if(!obj.idCode){
				oIdentInfo.innerHTML = "";
				oBankInfo.parentNode.setAttribute("href","javascript:");
				oBankInfo.parentNode.onclick = function(){
					var floatWindow = new PopUpBox();
			        var content = "<p style='text-align:center'>请先实名认证</p>";
					floatWindow.init({
						iNow:0,          // 确保一个对象只创建一次
						tBar:false,  
						time:1500,  
						content:content,     // 内容
						workBar:false
					});
				};
			}else{
				oIdentInfo.innerHTML = "<span class='marginRight'>"+obj.userName+"</span><span>"+obj.idCode+"</span>";
				oIdentInfo.parentNode.setAttribute("href","javascript:");
			};
			if(!obj.cardNo){
				oBankInfo.innerHTML = "";
				unbank.style.display = 'none';
			}else{
				oBankInfo.innerHTML = obj.bankEnName + " " + obj.cardNo;
				oBankInfo.parentNode.setAttribute("href","javascript:");
				unbank.onclick = function () {
					var This = this;
		        	if (This.isClick) {
		        		var floatWindow = new PopUpBox();
				        var content = "<p style='text-align:center'>正在进行中</p>";
						floatWindow.init({
							iNow:0,          // 确保一个对象只创建一次
							tBar:false,  
							time:1500,  
							content:content,     // 内容
							workBar:false
						});
		        		return;
		        	};
		        	This.isClick = true;
		        	var setUpAjax = Request(); 
		        	setUpAjax.ajax({	
						url: projectUrl + "/front/bmAccount/relieveCard",  
						type: 'post',  
						data:{}, 
						success:function(str){
							This.isClick = false;
							var data = JSON.parse(str);
							var floatWindow = new PopUpBox();
					        var content = "<p style='text-align:center'>"+data.message.message+"</p>";
							floatWindow.init({
								iNow:0,          // 确保一个对象只创建一次
								tBar:false,  
								time:1500,  
								content:content,     // 内容
								workBar:false
							});
							if(data.message.code == 1000){
								setTimeout(function(){
									window.location.reload();
								}, 1500)
			            	};
						},
						error: function (err) {
							This.isClick = false;
							console.log(err)
						}
					});
				};
			};
		}
	});
});
