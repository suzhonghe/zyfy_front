//红包和体验标
;define(function(require,exports,module){
	require("./common.min.js");

	var testBidNo = document.querySelector(".testBidWrap .noUsed");
	var testBidHas = document.querySelector(".testBidWrap .wasUsed");
	var redPackNo = document.querySelector(".redPacketsWrap .noUsed");
	var redPackHas = document.querySelector(".redPacketsWrap .wasUsed");
	var servicesTab = document.querySelectorAll("header li");
	var beUsed = document.querySelectorAll(".beUsed li");
	var oBottomBtn = document.querySelector(".bottomBtn span");

	oBottomBtn.onclick = function(){
		window.location.href = "./../../index.html";
	};
	
	tab(servicesTab);
	tab(beUsed);
	function tab(obj){
		for(var i=0,len=obj.length;i<len;i++){
			obj[i].onclick = function(){
				for(var i=0,len=obj.length;i<len;i++){
					removeClass(obj[i], "active");
				};
				addClass(this, "active");
				myAward();
			};
		}
	};
	function myAward(){
		if(hasClass(servicesTab[0], "active") && hasClass(beUsed[0], "active")){
			testBidNo.style.display = "block";
			testBidHas.style.display = "none";
			redPackNo.style.display = "none";
			redPackHas.style.display = "none";
		}else if(hasClass(servicesTab[0], "active") && hasClass(beUsed[1], "active")){
			testBidNo.style.display = "none";
			testBidHas.style.display = "block";
			redPackNo.style.display = "none";
			redPackHas.style.display = "none";
		}else if(hasClass(servicesTab[1], "active") && hasClass(beUsed[0], "active")){
			testBidNo.style.display = "none";
			testBidHas.style.display = "none";
			redPackNo.style.display = "block";
			redPackHas.style.display = "none";
		}else if(hasClass(servicesTab[1], "active") && hasClass(beUsed[1], "active")){
			testBidNo.style.display = "none";
			testBidHas.style.display = "none";
			redPackNo.style.display = "none";
			redPackHas.style.display = "block";
		}
	};

	var req = Request(); 
	req.ajax({	
		url:"/zycfMarket/queryMyAward",  
		type:'post',  
		data:{}, 
		success:function(str){
			var obj = JSON.parse(str);
			if(obj.data && obj.data.length && obj.data.length>0){
            	for(var i=0,len=obj.data.length;i<len;i++){
            		var oLi = document.createElement("li");
            		if(obj.data[i].status == 1){
            			oLi.className = "overDue";
            		};
            		var months = "";
            		if(obj.data[i].months == 1){
            			months = "月月发";
            		}else if(obj.data[i].months == 3){
            			months = "季度红";
            		}else if(obj.data[i].months == 6){
            			months = "半年财";
            		}else if(obj.data[i].months == 12){
            			months = "全年盈";
            		}else{
            			months = "适用所有";
            		};
                	if(obj.data[i].type == 1){
                		oLi.innerHTML = '<div class="aRows content">\
                            <h3 class="alignLeft">红包（'+months+'）</h3>\
                            <div class="alignRight">\
                                <p>金额 <strong>¥'+obj.data[i].amount+'</strong></p>\
                                <p>到期日 <span>'+obj.data[i].endTime+'</span></p>\
                            </div>\
                        </div>\
                        <div class="aRows other">\
                            <p class="alignLeft">投资≥￥'+obj.data[i].limitAmount+'可用</p>\
                        </div>';
                        if(obj.data[i].status == 1){
                        	redPackHas.appendChild(oLi);
                        }else if(obj.data[i].status == 2){
                        	var oDiv = document.createElement("div");
                        	oDiv.className = "used";
                        	oLi.appendChild(oDiv);
                        	redPackNo.appendChild(oLi);
                        }else{
                        	redPackNo.appendChild(oLi);
                        };
                	}else if(obj.data[i].type == 2){
                		oLi.innerHTML = '<div class="aRows content">\
                            <h3 class="alignLeft">体验金</h3>\
                            <div class="alignRight">\
                                <p>体验金 <strong>¥'+obj.data[i].amount+'</strong></p>\
                                <p>到期日 <span>'+obj.data[i].endTime+'</span></p>\
                            </div>\
                        </div>\
                        <div class="aRows other">\
                            <p class="alignLeft">仅限体验标使用</p>\
                            <p class="alignRight">有效期'+obj.data[i].limitDays+'天</p>\
                        </div>';
                        if(obj.data[i].status == 1){
                        	testBidHas.appendChild(oLi);
                        }else if(obj.data[i].status == 2){
                        	var oDiv = document.createElement("div");
                        	oDiv.className = "used";
                        	oLi.appendChild(oDiv);
                        	testBidNo.appendChild(oLi);
                        }else{
                        	testBidNo.appendChild(oLi);
                        };
                	};
                }
            }
		}
	});
	
});
