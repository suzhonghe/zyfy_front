//体验标详情
;define(function(require,exports,module){
	require("./common.min.js");

	var oRate = document.querySelector(".rate strong");
	var oMonth = document.querySelector(".month strong");
	var oBottomBtn = document.querySelector(".bottomBtn span");

	var getTestBid = require("./enterTestBid.js");
	getTestBid(testBidData);
	function testBidData(obj){
		oRate.innerHTML = obj.data.rate;
		oMonth.innerHTML = obj.data.loanDay;
		if(obj.code == 0){
			oBottomBtn.style.backgroundColor = "#ccc";
			oBottomBtn.innerHTML = "已结束";
		}else{
			oBottomBtn.onclick = function(){
				window.location.href = "./../html/testBidPay.html";
			};
		};
	};
});
