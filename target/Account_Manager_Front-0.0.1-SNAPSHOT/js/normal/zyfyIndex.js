$(function(){
    //banner轮播图淡入淡出
    var photos = [];
    $.ajax({
        url: '/front/banner/queryBannerPhotos',
        type: "post",
        data:{"type":"pc_banner"},
        async: false,
        success: function(str){
            photos = str.bannerPhotos;
        }
    });
    if(photos.length && photos.length>1){
        for(var i=0,len=photos.length;i<len;i++){
            var oA = $("<a class='item'></a>");
            var oLi = $('<li data-target="#myCarousel" data-slide-to="'+i+'"></li>');
            if(i==0){
                oA.addClass("active");
                oLi.addClass("active");
            };
            oA.attr("href",(photos[i].jumpAddress)?photos[i].jumpAddress:"javascript:");
            var oImg = $("<img />");
            oImg.attr("src",(photos[i].pathAddress)?photos[i].pathAddress:"#");
            oImg.appendTo(oA);
            oA.appendTo($(".carousel-inner"));
            oLi.appendTo($(".carousel-indicators"));
        };
        $('.tabBanner').carousel({
            interval: 5000
        });
    };
});