//已投项目
;define(function(require,exports,module){
	require("./common.min.js");
	var oVotedProject = document.querySelector(".votedProject");
	var oNav = document.querySelectorAll(".votedProject .litNav li");
	var oHasBeen = document.querySelector(".votedProject .hasBeen");
	var oRecord = document.querySelector(".votedProject .record span");
	var oWrap = document.querySelector(".wrap");
    
    var iPage = 1;
	var flag = true;

	for(var i=0,len=oNav.length;i<len;i++){
		oNav[i].onclick = function(){
			for(var i=0,len=oNav.length;i<len;i++){
				oNav[i].className = "";
			};
			this.className = "active";
			oHasBeen.innerHTML = '';
			oRecord.innerHTML = "0";
			votedProjectAjax({"page":1,"num":10,"loanStatus":this.getAttribute("code")},votedProjectData);
		};
	};
    
    var userAccountInfo = require("./userAccountInfo.js");
	userAccountInfo(getAccountInfo);
	function getAccountInfo(obj){
		if(obj.message.code == 2002){
			window.location.href = "../login.html";
		};
		document.querySelector(".votedProject .profit").innerHTML = "￥"+parseFloat(obj.userAccountVo.allRevue).toFixed(2);
		document.querySelector(".votedProject .principal").innerHTML = "待收本金：￥"+parseFloat(obj.userAccountVo.undueAmountCapital).toFixed(2);
		document.querySelector(".votedProject .interest").innerHTML = "待收利息：￥"+parseFloat(obj.userAccountVo.undueAmountInterest).toFixed(2);
	};
	
    votedProjectAjax({"page":1,"num":10,"loanStatus":""},votedProjectData);

    function votedProjectAjax(opt,fn){
    	var req = Request(); 
		req.ajax({	
			url: "/front/userAccount/userInvestLoanList",  
			type:'POST',  
			data:{
				"pageNo":opt.page,
				"pageSize":opt.num,
				"params[loanStatus]":opt.loanStatus
			}, 
			success:function(str){
				var obj = JSON.parse(str);
				if(obj.message.code == 2002){
					window.location.href = "../login.html";
				};
				fn && fn(obj.userInvestLoanVo);
			}
		});
    };
	
	function votedProjectData(obj){
		if(!obj.results.length){
	    	var noMore = document.createElement("div");
	    	noMore.className = "noMore";
	    	if(iPage == 1){
	    		noMore.innerHTML = "暂无数据";
	    	}else{
	    		noMore.innerHTML = "数据已加载完毕";
	    	};
	    	oHasBeen.appendChild(noMore);
	    }else{
	    	setData(obj);
	    	flag = true;
	    };
	    oRecord.innerHTML = obj.totalRecord;
	    function setData(obj){
			for(var i=0;i<obj.results.length;i++){
				var oARecord = document.createElement("div");
				oARecord.setAttribute("code",obj.results[i].loanId);
                oARecord.className = "aRecord";
				oARecord.innerHTML = '<div class="title">\
	    			    <span class="PaddingRight">'+obj.results[i].productName+'</span>\
	    			    <span>'+obj.results[i].title+'</span>\
	    			</div>\
	    			<div class="litBar">\
	    				<div>'+obj.results[i].rate+'%'+'<span class="addRate">'+(obj.results[i].addRate?'+'+obj.results[i].addRate+'%':'')+'</span>'+'</div>\
	    				<div>'+obj.results[i].months+'个月</div>\
	    				<div>'+(obj.results[i].strTimeSettled?obj.results[i].strTimeSettled:"未起息")+'</div>\
	    			</div>\
	    			<div class="litBar fontColor">\
	    				<div>预计年化收益</div>\
	    				<div>期限</div>\
	    				<div>起息日</div>\
	    			</div>\
	    			<div class="aLine"></div>\
	    			<div class="aRows">\
	    				<p class="alignLeft">投资金额<span>￥'+parseFloat(obj.results[i].amount).toFixed(2)+'</span></p>\
	    				<div class="alignLeft">预期收益<span>￥'+parseFloat(obj.results[i].dueInterest).toFixed(2)+'</span></div>\
	    			</div>';
	    		oARecord.onclick = function(){
	    			window.location.href = "../invested.html?"+this.getAttribute("code");
	    		};
				oHasBeen.appendChild(oARecord);
			};
		};
	};
	oWrap.onscroll = function() {
		var oChildren = document.querySelectorAll(".aRecord");
		var len = oChildren.length;
		var oEle = oChildren[len-2];
		var loanStatus = "";
		for(var i=0,len=oNav.length;i<len;i++){
			if(oNav[i].className == "active"){
				loanStatus = oNav[i].getAttribute("code");
			}
		};
		if ( (getTop( oEle ) + oEle.offsetHeight) < (oWrap.clientHeight + oWrap.scrollTop) ) {
			if ( flag ) {
				flag = false;
				iPage++;
				votedProjectAjax({"page":iPage,"num":10,"loanStatus":loanStatus},votedProjectData);
			}
		}
	}
	function getTop(obj) {
		var iTop = 0;
		while(obj) {
			iTop += obj.offsetTop;
			obj = obj.offsetParent;
		}
		return iTop;
	};
});
