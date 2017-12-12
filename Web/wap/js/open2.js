//开通Ⅱ类账户
;define(function(require,exports,module){
	require("./common.min.js");
	var projectUrl = '';
	var query_string = window.location.search.substring(1);
	var result_code = '0';
	
    var resultDiv = document.querySelector(".open2 h2 span");

    if (query_string) {
    	var arr = query_string.split("&");
	    for (var i = 0, len = arr.length; i < len; i++) {
	    	if( arr[i].indexOf('xib_signup', 0) >= 0 ) {
	    		result_code = arr[i].split("=")[1]
	    	}
	    };
    }

    if (result_code == '1' || result_code == 1) {
    	resultDiv.innerHTML = "成功";
    	var openAjax = Request(); 
		openAjax.ajax({	
			url: projectUrl + "/front/bmAccount/openIIAccountResult",  
			type: 'post',  
			data:{
				"xib_signup": result_code
			}, 
			success:function(str){
				setTimeout(function () {
					window.location.href = "../index.html"
				}, 1500);
			}
		});
    } else {
    	resultDiv.innerHTML = "失败";
    };
});
