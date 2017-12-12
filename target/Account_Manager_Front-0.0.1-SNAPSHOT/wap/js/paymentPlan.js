//充值记录
;define(function(require,exports,module){
	require("./common.min.js");
	var oPaymentPlan = document.querySelector(".paymentPlan");
	var oNav = document.querySelectorAll(".paymentPlan .litNav span");
	var oHasBeen = document.querySelector(".paymentPlan .hasBeen");
	var oHasNo = document.querySelector(".paymentPlan .hasNo");
	var oRecord = document.querySelector(".paymentPlan .record span");
	var oWrap = document.querySelector(".wrap");
    
    var iPage = 1;
	var flag = true;
    
    paymentPlanAjax({"page":iPage,"num":10,"repayStatus":"UNDUE','OVERDUE","status":"UN"},paymentPlanData,oHasBeen);    //已回"PREPAY','REPAYED"
    function paymentPlanAjax(opt,fn,id){
    	var req = Request(); 
		req.ajax({	
			url: "/front/userAccount/userInvestRepaymentList",  
			type:'post',  
			data:{
				"pageNo":opt.page,
				"pageSize":opt.num,
				"params[repayStatus]":opt.repayStatus,
				"params[status]":opt.status
			}, 
			success:function(str){
				var obj = JSON.parse(str);
				if(obj.message.code == 2002){
		    		window.location.href = "../login.html";
		    	};
		    	fn && fn(obj.userInvestRecordVo,id);
			}
		});
    };
	
	for(var i=0,len=oNav.length;i<len;i++){
		oNav[i].index = i;
		oNav[i].onclick = function(){
			for(var i=0,len=oNav.length;i<len;i++){
				oNav[i].className = "";
			};
			if(this.index == 0){
				oHasBeen.style.display = "block";
				oHasNo.style.display = "none";
			}else{
				oHasNo.style.display = "block";
				oHasBeen.style.display = "none";
			};
			oNav[this.index].className = "active";
			var paymentStatus = (this.index==1)?"PREPAY','REPAYED":"UNDUE','OVERDUE";
			var showDiv = (this.index==1)?oHasNo:oHasBeen;
			var sta = (this.index==1)?"PR":"UN";
			showDiv.innerHTML = "";
			if(document.querySelector(".noMore")){
				document.querySelector(".noMore").innerHTML = "";
			}
			paymentPlanAjax({"page":1,"num":10,"repayStatus":paymentStatus,"status":sta},paymentPlanData,showDiv); 
		};
	};
		
	function paymentPlanData(obj,id){
		if(!obj.results.length || obj.results.length<1){
			if(document.querySelector(".noMore")){
				var noMore = document.querySelector(".noMore")
			}else{
				var noMore = document.createElement("div");
			};
	    	noMore.className = "noMore";
	    	if(iPage == 1){
	    		noMore.innerHTML = "暂无数据";
	    	}else{
	    		noMore.innerHTML = "数据已加载完毕";
	    	};
	    	oWrap.appendChild(noMore);
	    }else{
	    	setData(obj,id);
	    	flag = true;
	    };
	    oRecord.innerHTML = obj.totalRecord;
	    function setData(obj,id){
			for(var i=0;i<obj.results.length;i++){
				var oARecord = document.createElement("div");
                oARecord.className = "aRecord";
				oARecord.innerHTML = '<div class="title">\
			    <span>'+obj.results[i].productName+'</span>\
			    <span>'+obj.results[i].title+'-'+obj.results[i].currentPeriod+'期'+'</span>\
			</div>\
			<div class="aRows">\
				<p class="alignLeft">投标日期</p>\
				<div class="alignRight">'+obj.results[i].investTime+'</div>\
			</div>\
			<div class="aLine"></div>\
			<div class="aRows">\
				<p class="alignLeft">回款日期</p>\
				<div class="alignRight">'+obj.results[i].dueDate+'</div>\
			</div>\
			<div class="aLine"></div>\
			<div class="aRows">\
				<p class="alignLeft">本金</p>\
				<div class="alignRight">¥'+parseFloat(obj.results[i].amountPrincipal).toFixed(2)+'</div>\
			</div>\
			<div class="aLine"></div>\
			<div class="aRows">\
				<p class="alignLeft">利息</p>\
				<div class="alignRight">¥'+parseFloat(obj.results[i].amountInterest).toFixed(2)+'</div>\
			</div>\
			<div class="aLine"></div>\
			<div class="aRows total">\
				<p class="alignLeft">总计金额</p>\
				<div class="alignRight">¥'+(parseFloat(obj.results[i].amountPrincipal)+parseFloat(obj.results[i].amountInterest)).toFixed(2)+'</div>\
			</div>';
				id.appendChild(oARecord);
			};
		};
	};
	oWrap.onscroll = function() {
		var num = 0;
		var paymentStatus,showDiv,sta;
		for(var i=0,len=oNav.length;i<len;i++){
		    if(oNav[i].className == "active"){
		    	num = i;
		    }
		};
		if(num == 1){
			paymentStatus = "PREPAY','REPAYED";
			sta = "PR";
			showDiv = oHasNo;
		}else{
			paymentStatus = "UNDUE','OVERDUE";
			sta = "UN";
			showDiv = oHasBeen;
		};
		var oChildren = showDiv.querySelectorAll(".aRecord");
		var len = oChildren.length;
		var oEle = oChildren[len-2];
		if ( (getTop( oEle ) + oEle.offsetHeight) < (oWrap.clientHeight + oWrap.scrollTop) ) {
			if ( flag ) {
				flag = false;
				iPage++;
				paymentPlanAjax({"page":iPage,"num":10,"repayStatus":paymentStatus,"status":sta},paymentPlanData,showDiv); 
			}
		}
	};
	
	function getTop(obj) {
		var iTop = 0;
		while(obj) {
			iTop += obj.offsetTop;
			obj = obj.offsetParent;
		}
		return iTop;
	};
});
