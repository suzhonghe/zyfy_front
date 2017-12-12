//首页banner
;define(function(require,exports,module){ 
	require("./swiper.js");
	require("./common.min.js");
	var req = Request(); 
	req.ajax({	
		url: '/front/banner/queryBannerPhotos',
		type: 'post',  
		data: {
			"type": "app_banner"
		}, 
		success:function(str){
			var obj = JSON.parse(str).bannerPhotos;
		    if(!obj.length || obj.length<1){
		    	return;
		    }else{
		    	setData(obj);
		    	var swiper = new Swiper(".swiper-container", {
			        pagination: '.swiper-pagination',
			        autoplay: 5000,
			        loop:true,
			        autoplayDisableOnInteraction : false,
			    });
		    };
			function setData(obj){
		    	var oBanner = document.querySelector(".banner");
				for(var i=0;i<obj.length;i++){
					var oA = document.createElement("a");
					oA.className = "swiper-slide";
					oA.setAttribute("href",(obj[i].jumpAddress?obj[i].jumpAddress:"javascript:"));
					//oA.setAttribute("href","javascript:");
					var oImg = document.createElement("img");
					oImg.setAttribute("src",obj[i].pathAddress+"?"+Math.random());
					oA.appendChild(oImg);
					oBanner.appendChild(oA);
				}
			};
		},
		error:function(str){
		    alert(str.message)
		}
	});
});
