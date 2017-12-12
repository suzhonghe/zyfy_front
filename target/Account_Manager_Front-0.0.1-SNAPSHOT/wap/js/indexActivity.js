//首页标的列表
;define(function(require,exports,module){
	require("./common.min.js");

	var oClose = document.querySelector(".EAActivity .close");

	if(getCookie('at')){
		oClose.parentNode.style.display = "none";
	}else{
		oClose.parentNode.style.display = "block";
	};

	setCookie('at', "1",{"path":"/"});
	
	oClose.onclick = function(){
		this.parentNode.style.display = "none";
	};
});
