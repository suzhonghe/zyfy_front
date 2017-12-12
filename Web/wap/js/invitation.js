//邀请列表
;define(function(require,exports,module){
	require("./common.min.js");
	var oInvitation = document.querySelector(".invitation");
	var oHasBeen = document.querySelector(".invitation .hasBeen");
	var oRecord = document.querySelector(".invitation .record span");
	var oWrap = document.querySelector(".wrap");
    
    var iPage = 1;
	var flag = true;
    
    invitationAjax({"page":1,"num":10},invitationData);    
    function invitationAjax(opt,fn){
    	var req = Request(); 
		req.ajax({	
			url:"/front/userAccount/queryInviterInvest",  
			type:'post',  
			data:{
				"pageNo":opt.page,
				"pageSize":opt.num
			}, 
			success:function(str){
				var obj = JSON.parse(str);
				if(obj.message.code == 2002){
		    		window.location.href = "../login.html";
		    	};
		    	fn && fn(obj.pageInvest);
			}
		});
    };
	
		
	function invitationData(obj){
		if(!obj.results.length){
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
	    	setData(obj);
	    	flag = true;
	    };
	    oRecord.innerHTML = obj.totalRecord;
	    function setData(obj){
			for(var i=0;i<obj.results.length;i++){
				var oARecord = document.createElement("div");
                oARecord.className = "aRecord";
				oARecord.innerHTML = '<div class="aRows">\
					<p class="alignLeft colorRed">投资人</p>\
					<div class="alignRight">投资项目</div>\
				</div>\
				<div class="aRows">\
					<p class="alignLeft">'+obj.results[i].userName+'</p>\
					<div class="alignRight">'+obj.results[i].loanTitle+'</div>\
				</div>\
				<div class="aRows">\
					<p class="alignLeft">投资金额￥'+parseFloat(obj.results[i].amount).toFixed(2)+'</p>\
					<div class="alignRight">投资时间'+obj.results[i].strSubmitTime+'</div>\
				</div>';
				oHasBeen.appendChild(oARecord);
			};
		};
	};
	oWrap.onscroll = function() {
		var oChildren = oWrap.querySelectorAll(".aRecord");
		var len = oChildren.length;
		var oEle = oChildren[len-2];
		if ( (getTop( oEle ) + oEle.offsetHeight) < (oWrap.clientHeight + oWrap.scrollTop) ) {
			if ( flag ) {
				flag = false;
				iPage++;
				invitationAjax({"page":iPage,"num":10},invitationData); 
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
