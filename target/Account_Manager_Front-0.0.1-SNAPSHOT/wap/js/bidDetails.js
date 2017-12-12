//标的详情
;define(function(require,exports,module){
	var oBottomBtn = document.querySelector(".bottomBtn span");
	require("./common.min.js");
	var id=window.location.search.substring(1);
	var req = Request(); 
	req.ajax({	
		url: "/front/loan/loanDetail",  
		type:'post',  
		data:{
			"loan.id": id
		}, 
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
			document.querySelector(".amountInfo").innerHTML = (parseFloat(obj.amount)/10000).toFixed(2)+"<span> 万</span>";
			document.querySelector(".rateInfo").innerHTML = obj.rate+"%"+"<span>"+(obj.addRate?'+'+obj.addRate+'%':'')+"</span>";
			document.querySelector(".monthInfo").innerHTML = obj.months+"<span> 个月</span>";
			document.querySelector(".ProgressTop").style.width = (1-parseFloat(obj.suplusAmount)/parseFloat(obj.amount).toFixed(2))*100+"%";
			document.querySelector(".scale").innerHTML = (1-parseFloat(obj.suplusAmount)/parseFloat(obj.amount).toFixed(2))*100+"%";
			document.querySelector(".surplus").innerHTML = "剩余可投：<span>"+parseFloat(obj.suplusAmount).toFixed(2)+"</span> 元";
			document.querySelector(".product").innerHTML = obj.productName;
			document.querySelector(".title").innerHTML = obj.loanName;
			document.querySelector(".mothed").innerHTML = "还款方式：<span>"+method+"</span>";
			document.querySelector(".weaterPositin").className = "weaterPositin "+obj.status;
        	if(obj.status == "OPENED"){
		    	oBottomBtn.style.backgroundColor = "#ff5d16";
		    	oBottomBtn.innerHTML = "投资";
		    	oBottomBtn.onclick = function(){
		    		var getLoginStatus = require("./getLoginStatus.js");
					getLoginStatus(fnLink);
					function fnLink(obj){
						if( obj.userName && obj.userName!="" ){  //用户为登录状态
							window.location.href = "./../html/payment.html?"+id;
						}else{                                   //用户为未登录状态
							window.location.href = "./../html/login.html";
						}
					};
		    	};
		    }else{
		    	oBottomBtn.style.backgroundColor = "#cccccc";
		    	oBottomBtn.innerHTML = "已结算";
		    }
		},
		error:function(str){
		    alert(str.message)
		}
	});
    
    require("./queryProject.js");
    require("./investRecord.js");
    
    var oLi = document.querySelectorAll(".subNav li");
    var aWrapChild = document.querySelector(".wrapBox").children;

    oLi[0].className = "active";
    for(var i=0,oLen=oLi.length;i<oLen;i++){
    	aWrapChild[i].style.display = "none";
    }
    aWrapChild[0].style.display = "block";

    for(var i=0,oLen=oLi.length;i<oLen;i++){
    	oLi[i].index = i;
    	bind.call(oLi[i],oLi[i], 'click', fnTap);
    };
    function fnTap(){
    	for(var i=0,oLen=oLi.length;i<oLen;i++){
    		oLi[i].className = "";
    		aWrapChild[i].style.display = "none";
    	};
    	this.className = "active";
    	aWrapChild[this.index].style.display = "block";
    };
});
