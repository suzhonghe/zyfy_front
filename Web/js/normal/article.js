var projectUrl = "";
var article = {
	exception:function(obj){
		var obj = obj.message;
        if(obj.code){
            if(obj.code == "UNKNOW_EXCEPTION" || obj.code == "UNKNOW_NAME" || obj.code == "USETTLE_FAIL" || obj.code == "NO_UUSERINVEST" || obj.code == "NO_INFORMATIONLOAN" || obj.code == "PREPARE_DEPOSIT_FAILED" || obj.code == "FILE_READ_WRITE_EXCEPTION" || obj.code == "EN_CODE_MD5_EXCEPTION" || obj.code == "SERVER_REFUSED" || obj.code == "LOGIN_ERROR" || obj.code == "USER_NOT_EXISTS" || obj.code == "USER_PASSWORD_VAILD_FAILURE" || obj.code == "NOREGISTERED_UMPACCOUNT" || obj.code == "USER_NOLOGIN" || obj.code == "LOGIN_SUCCESS" ){
                return;
            }
        }
    },
	articleListAjax:function(opt){
    	var settings = {
			page:1,
			num:10,
			params:{},
			url:"",
			id:"",
			callBack:function(){}
		};
		$.extend(settings,opt);
    	$.ajax({
            type:"POST",
            url:settings.url,
            data:{"pageNo":settings.page,
			    "pageSize":settings.num,
			    "params":settings.params
			    },
            dataType:"json",
            success:function(str){
            	article.exception(str);
            	settings.callBack(str);
            	if(str.totalPage>1){
			    	page({
						id:settings.id,
						nowNum:settings.page,
						allNum:str.totalPage,
						callBack:function(now,all){
							article.articleListAjax({
								page:now,
							    num:settings.num,
							    params:settings.params,
							    id:settings.id,
							    url:settings.url,
			                    callBack:settings.callBack
							});
						}
					});
			    };
			}
        });
    },
	indexNotice:function(){
		article.articleListAjax({
			page:1,
		    num:10,
		    params:{"colKey":"PTGG"},
		    url: projectUrl + "/front/cms/queryArticleByParams",
			callBack:function(obj){
				gonggao(obj.page);
			}
		});
		function gonggao(obj){
			$("#noticeScroll").html("");
			if(!obj.results || !obj.results.length || obj.results.length<1){
				$("#noticeScroll").html("<li>暂时没有公告</li>");
			}else{
				var len = obj.results.length;
				for(var i=0;i<len;i++){
					var number = "";
					if(obj.results[i].columnName == "平台公告"){
						number = "1";
					}else if(obj.results[i].columnName == "左右动态"){
						number = "2";
					}else if(obj.results[i].columnName == "行业动态"){
						number = "3";
					}else if(obj.results[i].columnName == "媒体报道"){
						number = "4";
					};
					var oLi = $("<li><a href='html/activityZonePage/article.html?"+obj.results[i].id+"#"+number+"' >"+obj.results[i].title+"</a><span>"+obj.results[i].submitTime+"</span></li>");
					oLi.appendTo($("#noticeScroll"));
				};
				if(len>1){
					var iTime = null;
					var num = 0;
					function auto(){
						clearInterval(iTime);
						iTime = setInterval(function(){
							num++;
							if(num>=len){
								num=0;
								$("#noticeScroll").css("top","-30px")
							};
							$("#noticeScroll").stop().animate({"top":-30*num});
						},4000);
				    };
				    auto();
				    $("#noticeScroll").hover(function(){
				    	clearInterval(iTime);
				    },function(){
				    	auto();
				    });
				}
			}
		};
		article.articleListAjax({
			page:1,
		    num:5,
		    params:{"colKey":""},
		    url: projectUrl + "/front/cms/queryArticleByParams",
			callBack:function(obj){
				xinwen(obj.page);
			}
		});
		function xinwen(obj){
			$(".newsTextList").html("");
			if(!obj.results || !obj.results.length || obj.results.length<1){
				$(".newsTextList").html("<li>暂时没有文章</li>");
			}else{
				var len = obj.results.length;
				for(var i=0;i<len;i++){
					var number = "";
					if(obj.results[i].columnName == "平台公告"){
						number = "1";
					}else if(obj.results[i].columnName == "左右动态"){
						number = "2";
					}else if(obj.results[i].columnName == "行业动态"){
						number = "3";
					}else if(obj.results[i].columnName == "媒体报道"){
						number = "4";
					};
					var oLi = $("<li><a class='clear' href='html/activityZonePage/article.html?"+obj.results[i].id+"#"+number+"' ><span class='left textOverflow'>"+obj.results[i].title+"</span><span class='right'>"+obj.results[i].submitTime+"</span></a></li>");
					oLi.appendTo($(".newsTextList"));
				};
			}
		};
	},
	article:function(){
		if(window.location.search){
			var id=window.location.search.substring(1),number=window.location.hash.substring(1);
			$(".navList a").each(function(){
				if($(this).html() == "平台公告" && parseFloat(number) == 1){
					$(this).parent().addClass("on");
					return false;
				};
				if($(this).html() == "左右动态" && parseFloat(number) == 2){
					$(this).parent().addClass("on");
					return false;
				};
				if($(this).html() == "行业动态" && parseFloat(number) == 3){
					$(this).parent().addClass("on");
					return false;
				};
				if($(this).html() == "媒体报道" && parseFloat(number) == 4){
					$(this).parent().addClass("on");
					return false;
				};
			});
			$.ajax({
				type:"POST",
				url: projectUrl + "/front/cms/getArticleContent",
				data:{"id":id},
				success:function(str){
					var obj = str;
					article.exception(obj);
					var message = str.message;
					if (message.code !== 1000) {
						return;
					}
					var str = str.article;
					var oTitle = $("<p class='title' >"+str.title+"</p>");
					var oSource = $("<p class='source' ><span>来源：<a href="+str.sourceLink+" >"+str.source+"</a></span>&nbsp;&nbsp;<span>作者："+str.author+"</span>&nbsp;&nbsp;<span>时间："+new Date(parseInt(str.submitTime,10)).toLocaleDateString()+"</span></p>");
					var ocontent = $("<div>"+str.content+"</div>");
					oTitle.appendTo( $(".content") );
					oSource.appendTo( $(".content") );
					ocontent.appendTo( $(".content") );
					if(str.preid || str.surid){
					    var oBottom = $("<p class='bottomLink'></p>");
					    if(str.preid){
					    	var oPrev = $("<a href='" + projectUrl + "article.html?"+str.preid+"#"+number+"' >上一篇："+str.preTitle+" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>");
					    	oPrev.appendTo(oBottom);
					    };
					    if(str.surid){
					    	var oNext = $("<a href='" + projectUrl + "article.html?"+str.surid+"#"+number+"' >下一篇："+str.surTitle+"</a>");
					    	oNext.appendTo(oBottom);
					    }
					    oBottom.appendTo( $(".content") );
					};
				}
			});
		}else{
			$(".content").html("<p>没有查到文章，请刷新页面</p>");
		}
	},
	column:function(colKey,number){
		article.articleListAjax({
			page:1,
		    num:20,
		    id:"paging",
		    params:{"colKey":colKey},
		    url: projectUrl + "/front/cms/queryArticleByParams",
			callBack:function(obj){
				columnData(obj.page,number);
			}
		});
		function columnData(obj,number){
			$(".mediaList ul").html("");
			$("#paging").html("");
			if(!obj.results || !obj.results.length || obj.results.length<1){
				$(".mediaList ul").html("<li>暂时没有文章</li>");
			}else{
				var len = obj.results.length;
				for(var i=0;i<len;i++){
					var oLi = $("<li class='clear'><a class='left' href='article.html?"+obj.results[i].id+"#"+number+"' >"+obj.results[i].title+"</a><span class='right'>"+obj.results[i].submitTime+"</span></li>");
					oLi.appendTo($(".mediaList ul"));
				};
			}
		};
	}
}
