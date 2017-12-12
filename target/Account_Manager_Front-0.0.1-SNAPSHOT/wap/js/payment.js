//投资支付
;define(function(require,exports,module){
	require("./common.min.js");
	var projectUrl = '';

	var oHeadBtn = document.querySelector(".payment header a");
	var oBotBtn = document.querySelector(".bottomBtn span");
	var oInvestAccount = document.querySelector(".investAccount input");
	var oRedPackList = document.querySelector(".redPackList");
	var oRedPackets = document.querySelector(".redPackets");
	var oRedMoney = document.querySelector(".redPackets span");
	var oRedPackWrap = document.querySelector(".redPackWrap");
	var oSureBtn = document.querySelector(".sureBtn");
	var oRedPackNum = document.querySelector(".redPackNum");
	var isAdd = false;

	var id = window.location.search.substring(1);

	var minAmount = "";
	var suplusAmount = "";
	var stepAmount = 0;
	var availableAmount = "";
	var flag = false;

	oHeadBtn.onclick = function(){
		window.location.href = "./../html/invested.html?"+id;
	};
	var req = Request(); 
	req.ajax({	
		url: projectUrl + "/front/loan/loanDetail",  
		type:'post', 
		async:false, 
		data:{"loan.id":id}, 
		success:function(str){
			var obj = JSON.parse(str).loanDetail;
			var method;
			if(obj.method=='MonthlyInterest'){
				method = '按月付息到期还本';
			}else if(obj.method=='EqualInstallment'){
				method = '按月等额本息';
			}else if(obj.method=='EqualPrincipal'){
				method = '按月等额本金';
			}else if(obj.method=='BulletRepayment'){
				method = '一次性还本付息';
			}else if(obj.method=='EqualInterest'){
				method = '月平息';
			};
			document.querySelector(".product").innerHTML = "【 "+obj.productName+" 】";
			document.querySelector(".title").innerHTML = obj.loanName;
			document.querySelector(".rate").innerHTML = "年利率："+obj.rate+"%"+"<span>"+(obj.addRate?'+'+obj.addRate+'%':'')+"</span>";
			document.querySelector(".month span").innerHTML = obj.months;
            document.querySelector(".surplus span").innerHTML = parseFloat(obj.suplusAmount).toFixed(2);
            document.querySelector(".mothed").innerHTML = method;
            oInvestAccount.setAttribute("placeholder",parseFloat(obj.minAmount).toFixed(2)+"的整数倍");
            minAmount = obj.minAmount;
            suplusAmount = obj.suplusAmount;
            stepAmount = obj.stepAmount || 0;
            if(obj.addRate){
            	isAdd = false;
            }else{
            	isAdd = true;
            }
		}
	});
	
    var requ = Request(); 
	requ.ajax({	
		url: projectUrl + "/front/userFund/queryUserFund",  
		type:'post',  
		data:{}, 
		success:function(str){
			var obj = JSON.parse(str);
			if(obj.message.code == 2002){  //未登录
				window.location.href = "./../html/login.html";
         	}else if(obj.message.code == 2005){  //未认证
         		oBotBtn.style.backgroundColor = "#ccc";
         		var floatWindow = new PopUpBox();
		        var content = "<p style='text-align:center'>"+obj.message.message+"</p>";
				floatWindow.init({
					iNow:0,          // 确保一个对象只创建一次
					tBar:false,  
					time:1500,  
					content:content,     // 内容
					workBar:false
				});
         	}else if(obj.message.code == 1000){  //登录和认证完
         		flag=true;
         		if(obj.userFund.availableAmount){
					document.querySelector(".available span").innerHTML = parseFloat(obj.userFund.availableAmount).toFixed(2);
					availableAmount = obj.userFund.availableAmount;
				}else{
					document.querySelector(".available span").innerHTML = "0.00";
					availableAmount = "0";
				};
	    		if(obj.userFund.availableAmount<parseFloat(minAmount)){
	    			oBotBtn.style.backgroundColor = "#ccc";
	    		};
         	};
		}
	});
	
	oInvestAccount.onfocus = function(){
		oRedPackWrap.style.display = "none";
		redPackDis = false;
	};
	oInvestAccount.onchange = function(){
		oRedMoney.innerHTML = "￥0";
	};
	oSureBtn.onclick = function(){
		var oRedPackListLi = document.querySelectorAll(".redPackList li");
		for (var i=0,len=oRedPackListLi.length;i<len;i++) {
			if(hasClass(oRedPackListLi[i], "active")){
				oRedMoney.innerHTML = "￥"+oRedPackListLi[i].getAttribute("code");
				oRedMoney.setAttribute("code",oRedPackListLi[i].getAttribute("id"));
			}
		};
		oRedPackWrap.style.display = "none";
		redPackDis = false;
	};
	/*var getLoginStatus = require("./getUseRedPack.js");
	getLoginStatus(id,redPackData);
	function redPackData(obj){
		if(obj.length && obj.length>0){
    		for (var i = 0,len=obj.length; i < len; i++) {
    			var oLi = document.createElement("li");
    			oLi.id = obj[i].id;
    			oLi.setAttribute("code",obj[i].amount);
    			oLi.setAttribute("lit",obj[i].amountLimit);
    			oLi.innerHTML = '<div class="aRows">\
					<p class="alignLeft setColor">￥<span>'+obj[i].amount+'</span></p>\
					<div class="alignRight">\
						<p>起投金额￥'+obj[i].amountLimit+'</p>\
						<p>到期日'+new Date(parseInt(obj[i].endTime,10)).toLocaleDateString()+'</p>\
					</div>\
				</div>';
				oRedPackList.appendChild(oLi);
    		};
    	}
	};
	var redPackNum = 0;
	var redPackDis = false;
	oRedPackets.onclick = function(){
		if(!isAdd){
			var floatWindow = new PopUpBox();
	        var content = "<p style='text-align:center'>红包不能与超指标同时使用！</p>";
	        floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
        	return false;
		}
		var oRedPackListLi = document.querySelectorAll(".redPackList li");
		if(!redPackDis){
			redPackNum = 0;
			oRedPackWrap.style.display = "block";
			oRedPackList.style.height = (oRedPackListLi.length>=3)?(3*oRedPackListLi[0].offsetHeight+"px"):(oRedPackListLi.length*oRedPackListLi[0].offsetHeight+"px");
			for(var i=0,len=oRedPackListLi.length;i<len;i++){
				removeClass(oRedPackListLi[i], "active");
				var oP = oRedPackListLi[i].querySelector(".setColor");
				oP.style.color = "#ff5d16";
				removeEvents(oRedPackListLi[i], "click", selectRedpack);
				removeEvents(oRedPackListLi[i], "click", noSelect);
				if( parseFloat(oInvestAccount.value) >= parseFloat(oRedPackListLi[i].getAttribute("lit")) ){
					redPackNum++;
					oRedPackList.insertBefore(oRedPackListLi[i],oRedPackListLi[0]);
					bind(oRedPackListLi[i], "click", selectRedpack);
    			}else{
    				oP.style.color = "#808080";
    				bind(oRedPackListLi[i], "click", noSelect);
    			};
    			oRedPackNum.innerHTML = "共"+redPackNum+"张可用";
			}
		}else{
			oRedPackWrap.style.display = "none";
		};
		redPackDis = !redPackDis;
	};
	function selectRedpack(){
		var oRedPackListLi = document.querySelectorAll(".redPackList li");
		for(var i=0,len=oRedPackListLi.length;i<len;i++){
			removeClass(oRedPackListLi[i], "active");
		};
		addClass(this, "active")
	};
	function noSelect(){
		var floatWindow = new PopUpBox();
        var content = "<p style='text-align:center'>投资满"+this.getAttribute("lit")+"起使用</p>";
        floatWindow.init({
			iNow:0,          // 确保一个对象只创建一次
			tBar:false,  
			time:1500,  
			content:content,     // 内容
			workBar:false
		});
	}; */

	oBotBtn.onclick = function(){
		var This = this;
    	if (This.isClick) {
    		var floatWindow = new PopUpBox();
	        var content = "<p style='text-align:center'>投资中...</p>";
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
		if( oInvestAccount.value!="" && flag && oInvestAccount.value%stepAmount==0 && oInvestAccount.value>=minAmount && oInvestAccount.value<=suplusAmount && oInvestAccount.value<=availableAmount){
			var reque = Request(); 
			reque.ajax({	
				url: projectUrl + "/front/loan/userInvest", 
				type:'post',  
				data:{
					"amount":oInvestAccount.value,
					"loanId":id
				}, 
				success:function(str){
					This.isClick = false;
					var obj = JSON.parse(str);
					var floatWindow = new PopUpBox();
			        var content = "<p style='text-align:center'>"+obj.message.message+"</p>";
					floatWindow.init({
						iNow:0,          // 确保一个对象只创建一次
						tBar:false,  
						time:1500,  
						content:content,     // 内容
						workBar:false
					});
					if (obj.message.code === 1000) {
						setTimeout(() => {
							window.location.href = '../index.html'
						}, 1500)
	            	}
				},
				error: function (err) {
					This.isClick = false;
					console.log(err)
				}
			});
		}else{
			This.isClick = false;
			var floatWindow = new PopUpBox();
	        var content = "";
	        if(oInvestAccount.value==""){
                content = "<p style='text-align:center'>请输入投资金额</p>";
	        }else if( !flag ){
	        	content = "<p style='text-align:center'>请先到官网认证身份</p>";
	        }else if( oInvestAccount.value%stepAmount!=0 ){
	        	content = "<p style='text-align:center'>请输入"+stepAmount+"的整数倍</p>";
	        }else if( oInvestAccount.value<minAmount ){
	        	content = "<p style='text-align:center'>投资金额不能小于"+minAmount+"</p>";
	        }else if( oInvestAccount.value>suplusAmount ){
	        	content = "<p style='text-align:center'>投资金额不能大于剩余可投金额</p>";
	        }else if( oInvestAccount.value>availableAmount ){
	        	content = "<p style='text-align:center'>投资金额不能大于可用余额</p>";
	        };
			floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
		}
	};
});
