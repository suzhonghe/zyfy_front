//实名认证
;define(function(require,exports,module){
	require("./common.min.js");
	require("../../js/bank.js");
	var projectUrl = '';

    var oRealName = document.querySelector(".identity .realName input");
    var oPeopleID = document.querySelector(".identity .peopleID input");
    var oBtn = document.querySelector(".identity .bottomBtn span");

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

    oBtn.onclick = function(){
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
			        	var content = "";
						content = "<p style='text-align:center'>"+loginObj.message.message+"</p>";
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
    function makesureFn () {
    	document.querySelector(".makesureInfo .username .msg").innerHTML = oRealName.value;
    	document.querySelector(".makesureInfo .idCode .msg").innerHTML = oPeopleID.value;
    	document.querySelector(".makesureInfo .bank .msg").innerHTML = selectedBank.innerHTML;
    	document.querySelector(".makesureInfo .bankType .msg").innerHTML = bankTypeChoose.innerHTML;
    	document.querySelector(".makesureInfo .bankCode .msg").innerHTML = cardNum.value;
    	document.querySelector(".makesureInfo .mobile .msg").innerHTML = reserveNum.value;
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
				var floatWindow = new PopUpBox();
		        var content = "<p style='text-align:center'>"+loginObj.message.message+"</p>";
				floatWindow.init({
					iNow:0,          // 确保一个对象只创建一次
					tBar:false,  
					time:1500,  
					content:content,     // 内容
					workBar:false
				});
				if(loginObj.message.code == 1000){   // 实名验证成功
	                setTimeout(function(){
	                	window.location.href = "../../html/account/setUp.html";
	                },2000);   
				};
			},
			error: function () {
				This.btn = false;
				This.style.backgroundColor = '#ff5d16';
			}
		});
    };
});
