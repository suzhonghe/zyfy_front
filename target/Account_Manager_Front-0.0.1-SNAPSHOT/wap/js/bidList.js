//首页标的列表
;define(function(require,exports,module){
	require("./common.min.js");

	var url = '';

	var oContent = document.querySelector(".content");
	var oWrap = document.querySelector(".wrap");
	var req = Request(); 
	var iPage = 1;
	var flag = true;

	/*var getTestBid = require("./enterTestBid.js");
	getTestBid(indexTestBid);
	function indexTestBid(obj){
		if(obj.code){
			var oBid = document.createElement("div");
			oBid.className = "bid";
			oBid.innerHTML = '<div class="aRow testBid">\
					<span class="product">新手体验标</span>\
				</div>\
				<div class="aRow">\
					<span class="rate">'+obj.data.rate+'%</span>\
					<span class="month">'+obj.data.loanDay+'天</span>\
					<a class="investmentBtn" href="html/testBidMobile.html">投资</a>\
				</div>\
				<div class="center">\
					<span class="surplus">到期付息</span>\
				</div>';
			oBid.onclick = function(){
				window.location.href = "html/testBidMobile.html";
			};
			if(oContent.children.length && oContent.children.length>0){
				oContent.insertBefore(oContent.children[0],oBid);
			}else{
				oContent.appendChild(oBid);
			};
		}
	};*/

    getBidData()
    function getBidData(){
    	req.ajax({	
			url: url + "/front/loan/searchLoansByParams",
			type: 'post',  
			data: {
				"page.pageNo": iPage,
				"page.pageSize": 10,
				"page.params.productId": ''
	        },
			success:function(str){
				var obj = JSON.parse(str).page;
			    if(!obj.results.length || obj.results.length<1){
			    	var noMore = document.createElement("div");
			    	noMore.className = "noMore";
			    	if(iPage == 1){
			    		noMore.innerHTML = "暂无数据";
			    	}else{
			    		noMore.innerHTML = "数据已加载完毕";
			    	};
			    	oWrap.appendChild(noMore);
			    }else{
			    	for(var i=0;i<obj.results.length;i++){
			    		setData(obj.results[i]);
			    	};
			    	flag = true;
			    };
			    function setData(obj){
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

					var oBid = document.createElement("div");
					if(obj.status == "OPENED"){
						oBid.className = "bid";
					}else{
						oBid.className = "bid "+obj.status;
					};
					if(obj.addRate){
						addClass(oBid, "addRate");
					};
					oBid.setAttribute("code",obj.loanId);
					oBid.innerHTML = '<div class="aRow">\
						<span class="product">'+obj.productName+'</span>\
						<span class="title">'+obj.loanName+'</span>\
						<span></span>\
					</div>\
					<div class="aRow">\
						<span class="rate">'+obj.rate+'%'+'<em>'+(obj.addRate?'+'+obj.addRate+'%':'')+'</em>'+'</span>\
						<span class="month">'+obj.months+'个月</span>\
						<span class="investmentBtn">投资</span>\
					</div>\
					<div class="center">\
						<span class="surplus">剩 '+parseFloat(obj.suplusAmount).toFixed(2)+' 元/'+parseFloat(obj.amount).toFixed(2)+' 元</span>\
						<span class="method">'+method+'</span>\
					</div>\
					<div class="ProgressBar">\
						<div class="Progress" style="width:'+(1-parseFloat(obj.suplusAmount)/parseFloat(obj.amount).toFixed(2))*100+'%"></div>\
					</div>';
					if(obj.status == "SCHEDULED"){
						var oTimeout = document.createElement("div");
						oTimeout.className = "timing";
						var oTimInfo = document.createElement("div");
						oTimInfo.className = "timingInfo";
						oTimInfo.innerHTML = "开标倒计时";
						obj.t = parseInt(parseInt(obj.divTime)/1000);
			            if(obj.t<=0){
			            	obj.t = 0;
			            };
			            oTimeout.innerHTML = '<strong>'+Math.floor(obj.t/3600)+'</strong>'+'时'+'<strong>'+Math.floor(obj.t%86400%3600/60)+'</strong>'+'分'+'<strong>'+obj.t%60+'</strong>'+'秒';
			            obj.time = setInterval(function(){
					        obj.t-=1;
					        if(obj.t<=0){
					            obj.t=0;
					            clearInterval(obj.time);
					            setTimeout(function(){
					            	window.location.reload();
					            },1000);
					        };
					        oTimeout.innerHTML = '<strong>'+Math.floor(obj.t/3600)+'</strong>'+'时'+'<strong>'+Math.floor(obj.t%86400%3600/60)+'</strong>'+'分'+'<strong>'+obj.t%60+'</strong>'+'秒';
					    },1000);
						oBid.appendChild(oTimeout);
						oBid.appendChild(oTimInfo);
					}else{
						bind.call(oBid,oBid, 'click', fnToBidDetailsPage);
					};
					oContent.appendChild(oBid);
					function fnToBidDetailsPage(){
						window.location.href = "html/invested.html?"+this.getAttribute("code");
					};
				};
			},
			error:function(str){
			    alert(str.message)
			}
		});
    };

	oWrap.onscroll = function() {
		var oChildren = oContent.children;
		var len = oChildren.length;
		var oEle = oChildren[len-2];
		if ( (getTop( oEle ) + oEle.offsetHeight) < (oWrap.clientHeight + oWrap.scrollTop) ) {
			if ( flag ) {
				flag = false;
				iPage++;
				getBidData();
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

	var oAccountBtn = document.querySelector(".accountBtn");
	
	bind(oAccountBtn, 'click', accountBtn);
	function accountBtn(){             // 首页点击我的账户按钮
		var getLoginStatus = require("./getLoginStatus.js");
		getLoginStatus(fnLink);
		function fnLink(obj){
			if( obj.userName ){  //用户为登录状态
				window.location.href = "./html/myAccount.html";
			}else{                                   //用户为未登录状态
				window.location.href = "./html/login.html";
			}
		};
	};
});
