//标的详情
;define(function(require,exports,module){
	require("./common.min.js");
	var id=window.location.search.substring(1);
	var req = Request(); 
	req.ajax({	
		url: "/front/invest/searchInvestRecord",  
		type:'post',  
		data:{
			"page.params[loanId]": id,
			"page.pageNo": 1,
			"page.pageSize": 999
	      }, 
		success:function(str){
			var obj = JSON.parse(str).page;
			var oRecord = document.querySelector(".record");
			if(!obj || !obj.results.length ){
				var oDiv = document.createElement("div");
				oDiv.className = "alignCenter";
				oDiv.innerHTML = "暂无数据";
				oRecord.appendChild(oDiv);
			}else{
				for(var i=0,len=obj.results.length;i<len;i++){
					var d = new Date(parseInt(obj.results[i].submitTime,10));
        			var t = d.getFullYear() + '-' + (d.getMonth()+1) + '-' + d.getDate() + ' ' + (d.getHours()<10?"0"+d.getHours():d.getHours()) + ':' + (d.getMinutes()<10?"0"+d.getMinutes():d.getMinutes()) + ':' + (d.getSeconds()<10?"0"+d.getSeconds():d.getSeconds());
					var oDiv = document.createElement("div");
					oDiv.className = "one";
				    oDiv.innerHTML = '<div class="seq">'+(i+1)+'</div>\
			    					<div class="alignCenter">\
			    						<p class="user">'+obj.results[i].userName+'</p>\
			    						<p class="time">'+ t +'</p>\
			    					</div>\
			    					<div class="alignRight">'+parseFloat(obj.results[i].amount).toFixed(2)+'元</div>';
			    	oRecord.appendChild(oDiv);
				}
			}
		},
		error:function(str){
		    alert(str.message)
		}
	});

});
