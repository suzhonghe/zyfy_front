//体验标支付
;define(function(require,exports,module){
	require("./common.min.js");

	var oRate = document.querySelector(".rate");
	var oMonth = document.querySelector(".month");
	var oMount = document.querySelector(".mount");
	var oInvestAccount = document.querySelector(".investAccount span");
	var oBottomBtn = document.querySelector(".bottomBtn span");

	var getTestBid = require("./enterTestBid.js");
	getTestBid(testBidData);
	function testBidData(obj){
		oRate.innerHTML = "预计年化收益："+obj.data.rate+"%";
		oMonth.innerHTML = "期限："+obj.data.loanDay+"天";
		oMount.innerHTML = "用户体验金余额："+obj.data.experienceAmount+"元";
		oInvestAccount.innerHTML = obj.data.experienceAmount;
		oBottomBtn.onclick = function(){
			if(!obj.data.login){
				var floatWindow = new PopUpBox();
		        var content = "<p style='text-align:center'>请先登录</p>";
		        floatWindow.init({
					iNow:0,          // 确保一个对象只创建一次
					tBar:false, 
					content:content,    // 内容
					callback:function(){
						window.location.href = "./../html/login.html";
					}
				});
			}else if(obj.data.status == 1 && obj.data.experienceAmount > 0){
				var req = Request(); 
				req.ajax({	
					url:"/zycfMarket/investVirtualLoan",  
					type:'post',  
					data:{}, 
					success:function(str){
						var obj = JSON.parse(str);
						var floatWindow = new PopUpBox();
				        var content = "<p style='text-align:center'>"+obj.message+"</p>";
				        floatWindow.init({
							iNow:0,          // 确保一个对象只创建一次
							tBar:false,  
							time:1500,  
							content:content,     // 内容
							workBar:false
						});
						if(obj.code == 1){
							setTimeout(function(){
								window.location.href = "https://www.zuoyoufy.com/wap/index.html";
							},500);
						};
					}
				});
			}else if(obj.data.status != 1 || obj.data.experienceAmount <= 0){
				var floatWindow = new PopUpBox();
		        var content = "";
		        if(obj.data.status == 0 ){
		        	content = "<p style='text-align:center'>体验标未开始</p>";
		        }else if(obj.data.status == 2){
		        	content = "<p style='text-align:center'>体验标已结束</p>";
		        }else if(obj.data.status == 3){
		        	content = "<p style='text-align:center'>体验标已取消</p>";
		        }
		        if(obj.data.experienceAmount <= 0){
		        	content = "<p style='text-align:center'>没有可用的体验金</p>";
		        }
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
});
