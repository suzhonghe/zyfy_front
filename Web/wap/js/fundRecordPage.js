//充值记录
;define(function(require,exports,module){
	require("./common.min.js");
	var oFundRecord = document.querySelector(".fundRecord");
	var oHasBeen = document.querySelector(".hasBeen");
	var oWrap = document.querySelector(".wrap");
    var iPage = 1;
	var flag = true;

	var getRechargeRecord = require("./fundRecord.js");
	getRechargeRecord({"page":iPage,"num":10,"type":""},ReRecordData); //获取充值记录type=DEPOSIT
	function ReRecordData(obj){
		if(obj.message.code == 2002){
			window.location.href = "../login.html";
		};
		if(!obj.pageRecords.results.length){
	    	var noMore = document.createElement("div");
	    	noMore.className = "noMore";
	    	if(iPage == 1){
	    		noMore.innerHTML = "暂无数据";
	    	}else{
	    		noMore.innerHTML = "数据已加载完毕";
	    	};
	    	oWrap.appendChild(noMore);
	    }else{
	    	setData(obj.pageRecords);
	    	flag = true;
	    };
	    function setData(obj){
			for(var i=0;i<obj.results.length;i++){
				var oARecord = document.createElement("div");
                oARecord.className = "aRecord";
				oARecord.innerHTML = '<div class="aRows backgroundColor">\
				    <p class="alignLeft">'+obj.results[i].type+'</p>\
					<div class="alignRight">'+obj.results[i].strTimeRecorded+'</div>\
				</div>\
				<div class="aRows">\
					<p class="alignLeft '+((obj.results[i].operation == "IN")?"redColor":"greenColor")+'" >'+((obj.results[i].operation == "IN")?parseFloat(obj.results[i].amount).toFixed(2):"-"+parseFloat(obj.results[i].amount).toFixed(2))+'</p>\
					<div class="alignRight">'+obj.results[i].status+'</div>\
				</div>\
				<div class="aRows">\
					<p class="alignLeft">订单号</p>\
					<div class="alignRight">'+obj.results[i].orderid+'</div>\
				</div>\
				<div class="aRows">\
					<p class="alignLeft">备注</p>\
					<div class="alignRight">'+obj.results[i].description+'</div>\
				</div>';
				oHasBeen.appendChild(oARecord);
			};
		};
	};
	oWrap.onscroll = function() {
		var oChildren = document.querySelectorAll(".aRecord");
		var len = oChildren.length;
		var oEle = oChildren[len-2];
		if ( (getTop( oEle ) + oEle.offsetHeight) < (oWrap.clientHeight + oWrap.scrollTop) ) {
			if ( flag ) {
				flag = false;
				iPage++;
				getRechargeRecord({"page":iPage,"num":10,"type":""},ReRecordData);
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
