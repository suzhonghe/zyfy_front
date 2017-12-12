//注册
;define(function(require,exports,module){
	require("./common.min.js");
	require("../../js/bank.js");

	var projectUrl = '';

	var oFirst = document.querySelector(".register .first");
	var oSecond = document.querySelector(".register .second");
	var oThree = document.querySelector(".register .three");
	var oSuccess = document.querySelector(".register .success");

	var firstBtn = document.querySelector(".register .first .bottomBtn span");
	var getPho = document.querySelector(".register .first .user input");
	var oPassword = document.querySelector(".register .first .password input");
	var oReferee = document.querySelector(".register .first .referee input");

	var userTpye = document.querySelector(".userTpye");
	var userTpyeSpan = document.querySelector(".userTpye span");
	var userTpyeUl = document.querySelector(".userTpye ul");
	var imgCodeInput = document.querySelector(".imgCode input");
	var imgCodeImg = document.querySelector(".imgCode img");

	var oReSend = document.querySelector(".register .second .reSend");
	var oNumber = document.querySelector(".register .second .number");
	var secondBtn = document.querySelector(".register .second .bottomBtn span");
	var oSmsCocd = document.querySelector(".register .second .smsCocd input");
	var oPho = document.querySelector(".register .second .pho");

	var oJunp = document.querySelector(".register .three header em");
	var oRealName = document.querySelector(".register .three .realName input");
	var oPeopleID = document.querySelector(".register .three .peopleID input");
	var threeBtn = document.querySelector(".register .three .bottomBtn span");

	var bank = document.querySelector(".bank");
	var bankUl = document.querySelector(".bank ul");
	var selectedBank = document.querySelector("#currentBank");

	var bankType = document.querySelector(".bankType");
	var bankTypeUl = document.querySelector(".bankType ul");
	var bankTypeChoose = document.querySelector("#currentBankType");

	var cardNum = document.querySelector("input[name='cardNum']");
	var	reserveNum = document.querySelector("input[name='reserveNum']");

	var	auth = document.querySelector(".three .auth");
	var	makesureInfo = document.querySelector(".three .makesureInfo");

	var makesureBtn = document.querySelector(".makesureInfo .bottomBtn span");
	var shortMessageCode = document.querySelector("input[name='shortMessageCode']");

	var successBtn = document.querySelector(".register .success .bottomBtn span");

	userTpye.onclick = function (e) {
		if (e.target.nodeName === 'SPAN' || e.target.nodeName === 'I') {
			if (userTpyeUl.style.display == 'block') {
				userTpyeUl.style.display = 'none';
			} else {
				userTpyeUl.style.display = 'block';
			}
		} else if (e.target.nodeName === 'LI') {
			userTpyeSpan.setAttribute('forSort', e.target.getAttribute('code'));
			userTpyeSpan.innerHTML = e.target.innerHTML;
			userTpyeUl.style.display = 'none';
		}
		return false;
	};
	bank.onclick = function (e) {
		if (e.target.id === 'currentBank' || e.target.nodeName === 'I') {
			if (bankUl.style.display == 'block') {
				bankUl.style.display = 'none';
			} else {
				bankUl.style.display = 'block';
			}
		} else if (e.target.nodeName === 'LI') {
			selectedBank.setAttribute('forSort', e.target.getAttribute('code'));
			selectedBank.innerHTML = e.target.innerHTML;
			bankUl.style.display = 'none';
		}
		return false;
	};
	bankType.onclick = function (e) {
		if (e.target.id === 'currentBankType' || e.target.nodeName === 'I') {
			if (bankTypeUl.style.display == 'block') {
				bankTypeUl.style.display = 'none';
			} else {
				bankTypeUl.style.display = 'block';
			}
		} else if (e.target.nodeName === 'LI') {
			bankTypeChoose.setAttribute('forSort', e.target.getAttribute('code'));
			bankTypeChoose.innerHTML = e.target.innerHTML;
			bankTypeUl.style.display = 'none';
		}
		return false;
	};

	getImg();
	function getImg () {
		imgCodeImg.setAttribute('src',projectUrl+'/front/util/imgValalidate'+'?'+ Math.random());
	};
	imgCodeImg.onclick = getImg;

	getAllBank(function(obj){
		var html = '';
		for (var i = 0, len = obj.banks.length; i < len; i++) {
			html += '<li code="'+obj.banks[i].bankCode+'">'+obj.banks[i].bankShortName+'</li>'
		};
		bankUl.innerHTML = html;
		selectedBank.innerHTML = obj.banks[0].bankShortName;
		selectedBank.setAttribute("forSort", obj.banks[0].bankCode);
	});
	function getAllBank (fn) {
		var reques = Request(); 
		reques.ajax({	
			url: projectUrl + '/front/util/searchAllBanks',  //获取银行列表
			type: 'post',  
			data: {}, 
			success: function(str){
				var loginObj = JSON.parse(str);
				if (loginObj.message.code === 1000) {
					fn && fn(loginObj);
				} else {
					alert(loginObj.message.message)
					var floatWindow = new PopUpBox();
			        var content = "<p style='text-align:center'>"+loginObj.message.message+"</p>";
					floatWindow.init({
						iNow:0,          // 确保一个对象只创建一次
						tBar:false,  
						time:1500,  
						content:content,     // 内容
						workBar:false
					});
				}
			}
		});
	};

    var oRefereeVal = "";
	var flag = true;
	var referFlag = false;    //判断验证码通过与否
	var onlyPho = false;      //手机号唯一
	var timeOut = null;       //定时器
	var vf = false;          //图片验证码

    getPho.onfocus = function(){
    	onlyPho = false;
    };
	getPho.onblur = function(){
		if(this.value == ""){
			return;
		};
		if (checkMobile(this.value)) {
			var reqOnlyPho = Request(); 
			reqOnlyPho.ajax({	
				url: projectUrl + "/front/user/mobileUnique",  
				type:'post',  
				data:{"user.mobile": this.value}, 
				success:function(str){
					var onlyPhoObj = JSON.parse(str);
			        if(onlyPhoObj.message.code == 1000){
			        	var floatWindow = new PopUpBox();
		                var content = "<p style='text-align:center'>手机号码已存在</p>";
		                floatWindow.init({
							iNow:0,          // 确保一个对象只创建一次
							tBar:false,  
							time:1500,  
							content:content,     // 内容
							workBar:false
						});
			        }else{
			        	onlyPho = true;
			        };
				}
			});
		} else {
			var floatWindow = new PopUpBox();
            var content = "<p style='text-align:center'>手机号格式错误</p>";
            floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
		}
	};
	imgCodeInput.onchange = function(){
		vf = false;
		if (this.value.length == 4) {
			var re = /^\w{4}$/;
			if(this.value == ''){
				vf = false;
			}else if( re.test(this.value) ){
				var reqOnlyPho = Request(); 
				reqOnlyPho.ajax({	
					url: projectUrl+'/front/util/codeValalidate',  //图片验证码
					type:'post',  //请求方式  get||post
					data:{"imgCode": this.value},  //请求数据
					success:function(str){
						var obj = JSON.parse(str);
						if(obj.code == 1000){
				    	    vf = true;   // 成功
						}else{
				            vf = false;
						};
					}
				});
			}
		}
	};

    if(flag){
    	firstBtn.onclick = function(){
    		flag = false;
            if(getPho.value == "" || oPassword.value == "" || !checkMobile(getPho.value) || !testNum(oPassword.value) || imgCodeInput.value == '' || !vf){
                var floatWindow = new PopUpBox();
			    var content = "";
			    if(getPho.value == ""){
			    	content = "<p style='text-align:center'>手机号或用户名不能为空</p>";
			    }else if( oPassword.value == "" ){
			    	content = "<p style='text-align:center'>密码不能为空</p>";
			    }else if( !checkMobile(getPho.value) ){
			    	content = "<p style='text-align:center'>请输入正确手机号</p>";
			    }else if( !testNum(oPassword.value) ){
			    	content = "<p style='text-align:center'>请输入6-16位数字与字母组合密码</p>";
			    } else if (imgCodeInput.value == '') {
			    	content = "<p style='text-align:center'>请输入图片验证码</p>";
			    } else if (!vf) {
			    	content = "<p style='text-align:center'>图片验证码为空或错误</p>";
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
            if( oReferee.value!="" && checkMobile(oReferee.value) ){
            	var req = Request(); 
				req.ajax({	
					url: projectUrl + "/front/user/searchUser",
					type:'post',  
					async:false,
					data:{"user.mobile":oReferee.value}, 
					success:function(str){
						var loginObj = JSON.parse(str);
				        if(loginObj.message.code != 1000){
				        	var floatWindow = new PopUpBox();
				            var content = "<p style='text-align:center'>推荐人手机号未在平台注册</p>";
			                floatWindow.init({
								iNow:0,          // 确保一个对象只创建一次
								tBar:false,  
								time:1500,  
								content:content,     // 内容
								workBar:false
							});
				        }else if( loginObj.message.code == 1000 ){
				        	referFlag = true;
				        };
					}
				});
            };
            if(referFlag){
            	oRefereeVal = oReferee.value;
            }else if(oReferee.value == ""){
            	oRefereeVal = "15311340737";
            }else{
            	flag = true;
            	return;
            };
            if(onlyPho){
            	oFirst.style.display = "none";
            	oSecond.style.display = "block";
            	oPho.innerHTML = getPho.value;
            	sendMessage();
            }else{
				var floatWindow = new PopUpBox();
		        var content = "<p style='text-align:center'>手机号还未在平台注册</p>";
				floatWindow.init({
					iNow:0,          // 确保一个对象只创建一次
					tBar:false,  
					time:1500,  
					content:content,     // 内容
					workBar:false
				});
            };
    	};
    };
    oReSend.onclick = function(){
    	sendMessage();
    };
    function sendMessage(){
    	var requ = Request(); 
		requ.ajax({	
			url: projectUrl+'/front/util/mobolileCertWeb',  //手机短信发送 
			type:'post',  
			data:{}, 
			success:function(str){
				var loginObj = JSON.parse(str);
				clearInterval(timeOut);
				if(loginObj.code == 1000){
					var num = 60;
					oNumber.style.display = "block";
					oNumber.innerHTML = num+"s";
					timeOut = setInterval(function(){
						num--;
						if(num <= 0){
							clearInterval(timeOut);
							oNumber.style.display = "none";
	                        oReSend.style.display = "block";
						}
						oNumber.innerHTML = num+"s";
					},1000);
				}else{
					var floatWindow = new PopUpBox();
			        var content = "";
			        if(loginObj.code == 3002){
		                content = "<p style='text-align:center'>时间间隔不足60秒</p>";
			        }else if( loginObj.code == 3001 ){
			        	content = "<p style='text-align:center'>提交次数大于4次</p>";
			        }else if( loginObj.code == 3003 ){
			        	content = "<p style='text-align:center'>验证码失效</p>";
			        }else if( loginObj.code == 3004 ){
			        	content = "<p style='text-align:center'>验证码错误</p>";
			        };
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
    
    secondBtn.onclick = function(){
    	var reg = /^[a-zA-Z0-9]+$/;
    	if(oSmsCocd.value == "" || !reg.test(oSmsCocd.value) ){
	        var floatWindow = new PopUpBox();
	        var content = "";
	        if(oSmsCocd.value == ""){
                content = "<p style='text-align:center'>短信验证码不能为空</p>";
	        }else if( !reg.test(oSmsCocd.value) ){
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
    		var reque = Request(); 
			reque.ajax({	
				url: projectUrl+'/front/user/registeUser',  //注册提交
				type:'post',  
				data:{
					"mobile":getPho.value,
					"passphrase":oPassword.value,
					"referralMobile":oRefereeVal,
					"regcode":oSmsCocd.value,
					"userType": userTpyeSpan.getAttribute('forSort')
				}, 
				success:function(str){
					var loginObj = JSON.parse(str);
					if(loginObj.message.code == 1000){   // 注册成功
						oSecond.style.display = "none";
						oThree.style.display = "block";
					}else{
						var floatWindow = new PopUpBox();
				        var content = "<p style='text-align:center'>"+loginObj.message.message+"</p>";
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

    oJunp.onclick = function(){
    	oThree.style.display = "none";
    	oSuccess.style.display = "block";
    };
    threeBtn.onclick = function(){
    	var bankObj = distinctionBankCard( cardNum.value );
    	if( !bankObj || !bankObj.bank || selectedBank.innerHTML != bankObj.bank ){
    		var floatWindow = new PopUpBox();
	        var content = "您填写的卡号与发卡银行不一致";
			floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
    		return;
    	};
    	var This = this;
		if (This.btn) {
			var floatWindow = new PopUpBox();
			var content = "<p style='text-align:center'>处理中...</p>";
			floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
			return;
		}
		This.btn = true;
		This.style.backgroundColor = '#737373';
    	if( oRealName.value != "" && oPeopleID.value != "" && checkChinese(oRealName.value) && checkCard(oPeopleID.value) && checkMobile(reserveNum.value) ){
    		var reques = Request(); 
			reques.ajax({	
				url: projectUrl + '/front/bmAccount/registeApply',  //实名验证
				type:'post',  
				data:{
					"bmaccount.userName": oRealName.value,
				    "bmaccount.idCode": oPeopleID.value,
				    "bmaccount.openBranch": selectedBank.getAttribute("forSort"),
				    "bmaccount.cardNo": cardNum.value,
				    "bmaccount.preMobile": reserveNum.value,
				    "bmaccount.cardType": bankTypeChoose.getAttribute("forSort")
				}, 
				success:function(str){
					This.btn = false;
					This.style.backgroundColor = '#ff5d16';
					var loginObj = JSON.parse(str);
					if(loginObj.message.code == 1000){   // 实名验证成功
						auth.style.display = "none";
						makesureInfo.style.display = "block"; 
						makesureFn();
					}else{
						var floatWindow = new PopUpBox();
				        var content = "<p style='text-align:center'>"+loginObj.message.message+"</p>";
						floatWindow.init({
							iNow:0,          // 确保一个对象只创建一次
							tBar:false,  
							time:1500,  
							content:content,     // 内容
							workBar:false
						});
					};
				},
				error: function () {
					This.btn = false;
					This.style.backgroundColor = '#ff5d16';
				}
			});
    	}else{
    		This.btn = false;
			This.style.backgroundColor = '#ff5d16';
    		var floatWindow = new PopUpBox();
	        var content = "";
	        if(oRealName.value == ""){
                content = "<p style='text-align:center'>请输入姓名</p>";
	        }else if( oPeopleID.value == "" ){
	        	content = "<p style='text-align:center'>请输入身份证号</p>";
	        }else if( !checkChinese(oRealName.value) ){
	        	content = "<p style='text-align:center'>请输入中文姓名</p>";
	        }else if( !checkCard(oPeopleID.value) ){
	        	content = "<p style='text-align:center'>请输入正确身份证号</p>";
	        } else if( !checkMobile(reserveNum.value) ){
	        	content = "<p style='text-align:center'>预留手机号格式不正确</p>";
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

    makesureBtn.onclick = function () {
    	if (shortMessageCode.value == '') {
    		var floatWindow = new PopUpBox();
	        var content = "<p style='text-align:center'>短信验证码不能为空</p>";
			floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
    		return;
    	}
    	var This = this;
		if (This.btn) {
			var floatWindow = new PopUpBox();
			var content = "<p style='text-align:center'>处理中...</p>";
			floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
			return;
		}
		This.btn = true;
		This.style.backgroundColor = '#737373';
    	var reques = Request(); 
		reques.ajax({	
			url: projectUrl + '/front/bmAccount/registeAffirm',  //信息确认
			type:'post',  
			data:{
				"bmaccount.userName": oRealName.value,
			    "bmaccount.idCode": oPeopleID.value,
			    "bmaccount.openBranch": selectedBank.getAttribute("forSort"),
			    "bmaccount.cardNo": cardNum.value,
			    "bmaccount.preMobile": reserveNum.value,
			    "bmaccount.cardType": bankTypeChoose.getAttribute("forSort"),
			    "bmaccount.identifyingCode": shortMessageCode.value
			}, 
			success:function(str){
				This.btn = false;
				This.style.backgroundColor = '#ff5d16';
				var loginObj = JSON.parse(str);
				if(loginObj.message.code == 1000){   // 实名验证成功
					oThree.style.display = "none";
	                oSuccess.style.display = "block"; 
	                setTimeout(function(){
	                	window.location.href = "./../index.html";
	                },3000);   
				}else{
					var floatWindow = new PopUpBox();
			        var content = "<p style='text-align:center'>"+loginObj.message.message+"</p>";
					floatWindow.init({
						iNow:0,          // 确保一个对象只创建一次
						tBar:false,  
						time:1500,  
						content:content,     // 内容
						workBar:false
					});
				};
			},
			error: function () {
				This.btn = false;
				This.style.backgroundColor = '#ff5d16';
			}
		});
    };

    function makesureFn () {
    	document.querySelector(".makesureInfo .username .msg").innerHTML = oRealName.value;
    	document.querySelector(".makesureInfo .idCode .msg").innerHTML = oPeopleID.value;
    	document.querySelector(".makesureInfo .bank .msg").innerHTML = selectedBank.innerHTML;
    	document.querySelector(".makesureInfo .bankType .msg").innerHTML = bankTypeChoose.innerHTML;
    	document.querySelector(".makesureInfo .bankCode .msg").innerHTML = cardNum.value;
    	document.querySelector(".makesureInfo .mobile .msg").innerHTML = reserveNum.value;
    };

    successBtn.onclick = function(){
    	window.location.href = "./../index.html";
    };
});
