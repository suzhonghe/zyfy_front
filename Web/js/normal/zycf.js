var projectUrl = "";
var zycfZ = {
	name:"",
    /*indexAcrivity:function(){
    	if(getCookie('at')){
			$(".indexActivity").hide();
		}else{
			$(".indexActivity").show();
		};
		$(".indexActivity .close").click(function(){
			setCookie('at', "1",{"path":"/"});
			$(this).parent().hide();
			return false;
		});
    },*/
	userName:function(fn,accountLink,loginLink){
		$.ajax({
			url: projectUrl + '/front/user/loginStatus',
			type: 'post',
			data: {},
			success: function(str){
				fn&&fn(str,accountLink,loginLink);
				if(str.userName!=null){
					name = str.userName;
					$("#loginStatus").html("你好,");
					$("#companyLink").hide();
					$("#userName").attr("href", projectUrl + "/html/myAccount.html").removeClass("login").addClass("loginName").html(str.userName);
					$('#userOut').attr("href","javascript:void(0);").addClass("userOut").html("退出");
					$("#userOut").click(function(){
						$.ajax({
							url: projectUrl + '/front/user/logOut',
							type: 'post',
							data: {},
							success: function(str){
								if(str.message.code == 1000){
									window.location.href = projectUrl + "/index.html";
								}
							}
						});
					});
					$('#userOut').removeClass("register");
				}
			}
		});
	},
	linkMyAccount:function(obj,accountLink,loginLink){
		$(".headNav .myAccount").click(function(){
			zycfZ.alertLogin(obj,accountLink,loginLink);
		});
	},
	myAccountLoginStatus:function(obj,accountLink,loginLink){
		//zycfZ.alertLogin(obj,accountLink,loginLink);
		if(obj.userName!=null){
			$(".headNav .myAccount").attr("href",accountLink);
		}else{
			$(".headNav .myAccount").attr("href","javascript:void(0);");
			window.location.href = projectUrl + "/html/" + loginLink;
		}
	},
	alertLogin:function(obj,accountLink,loginLink){
		if(obj.userName!=null){
			$(".headNav .myAccount").attr("href",accountLink);
		}else{
			$(".headNav .myAccount").attr("href","javascript:void(0);");
			var content = "<p style='text-align:center;line-height:50px;font-size:14px;margin:20px 0;'>你还未登录，请先<a href="+loginLink+" style='color:blue;'>登录</a></p>";
			var addStaff = new PopUpBox();
			addStaff.init({
				w:300,
				h:200,
				iNow:0,          // 确保一个对象只创建一次
				opacity:0.7,
				content:content,
				makesure:false, 
	            cancel:false
			});
		}
	},
	// 首页新闻Tab
	newsImg:function(){
		function getImg(strImg,fn){
			$.ajax({
				url: projectUrl + '/front/banner/queryBannerPhotos',
				type: 'post',
				data: {"type":strImg},
				success: function(str){
					fn && fn(str.bannerPhotos);
				}
			});
		};
		getImg("news",creatNewImg);
		getImg("advertisement",creatAD);
		function creatAD(obj){
			$(".banner .banner1").attr("src",obj[0].pathAddress);
			$(".banner .banner2").attr("src",obj[1].pathAddress);
		};
		function creatNewImg(obj){
			var len = (obj.length<=3)?obj.length:3;
			$("#newsDotList").html("");
			$("#newsImg").html("");
			for(var i=0;i<len;i++){
				var oA = $("<a href="+obj[i].jumpAddress+"><img src="+obj[i].pathAddress+" alt='新闻图片' /></a>");
				oA.appendTo("#newsImg");
				var litDot = $("<li></li>");
				litDot.appendTo("#newsDotList");
			};
			var iTime = null;
			var num = 0;
			$("#newsImg a").css({"opacity":0,"z-index":0}).eq(0).css({"opacity":1,"z-index":1});
			$("#newsDotList li").removeClass("active").eq(0).addClass("active");
			$("#newsDotList li").click(function(){
				$("#newsDotList li").removeClass("active").eq($(this).addClass("active"));
				$("#newsImg a").stop().eq($(this).index()).animate({"opacity":1,"z-index":1},600).siblings().animate({"opacity":0,"z-index":0},600);
			});
			function myTime(){
				iTime = setInterval(function(){
					num++;
					if(num>=len){
						num = 0;
					};
					$("#newsDotList li").removeClass("active").eq(num).addClass("active");
				    $("#newsImg a").stop().eq(num).animate({"opacity":1,"z-index":1},600).siblings().animate({"opacity":0,"z-index":0},600);
				},4000);
			};
			if(len>1){
				myTime();
				$("#newsImg").hover(function(){clearInterval(iTime)},function(){myTime()});    // 鼠标放上去停止动画
				$("#newsDotList").hover(function(){clearInterval(iTime)},function(){myTime()});  // 鼠标放上去停止动画
			};
		};
	},
	// 首页投标小喇叭
	bidList:function(){
		var d = new Date();
		var t = new Date(d.getTime() - 24*60*60*1000*2);
		var start = t.getFullYear() + '-' + (t.getMonth()+1) + '-' + t.getDate() + ' ' + (t.getHours()<10?"0"+t.getHours():t.getHours()) + ':' + (t.getMinutes()<10?"0"+t.getMinutes():t.getMinutes()) + ':' + (t.getSeconds()<10?"0"+t.getSeconds():t.getSeconds());
		var end = d.getFullYear() + '-' + (d.getMonth()+1) + '-' + d.getDate() + ' ' + (d.getHours()<10?"0"+d.getHours():d.getHours()) + ':' + (d.getMinutes()<10?"0"+d.getMinutes():d.getMinutes()) + ':' + (d.getSeconds()<10?"0"+d.getSeconds():d.getSeconds());
		$.ajax({
			type:"POST",
			url: projectUrl + "/front/invest/searchInvestRecord",
			data:{
            	"page.pageNo": 1,
            	"page.pageSize": 999,
            	"page.strStart": start,
            	"page.strEnd": end
			},
			success:function(str){
				var str = str.page.results;
				if(!str.length || str.length<1){
					$("#bidList").html("<li>暂无数据</li>");
				}else{
					var len = str.length;
					$("#bidList").height(len*40);
					for(var i=0;i<len;i++){
						var time = d.getTime() - str[i].submitTime;
						var myHour = (Math.abs(time)<24*60*60*1000)?Math.floor(time/3600000)+"小时前":"1天前";
						if(Math.abs(time) < 60*60*1000){
							myHour = "1小时内"
						};
						var oLi = $("<li><span>"+str[i].userName+"</span><span class='money'>"+str[i].amount+"</span><span>"+myHour+"</span></li>")
						oLi.appendTo($("#bidList"));
					};
					function autoPlay(){
						$("#bidList").stop().animate({top:H-80+"px"},(-H)*40,"linear",function(){
							$("#bidList").css({top:"0px"});
							setTimeout(function(){
							    autoPlay();
							},2000);
						});
					};
					var H = $("#bidList").parent().height()-$("#bidList").height();
					if(H<0){
						autoPlay();
						$("#bidList").hover(function(){
						    $("#bidList").stop()
						},function(){
							H = $("#bidList").parent().height()-$("#bidList").height();
							autoPlay();
						});
					}
				}
			}
		});
	},
	// 返回顶部
	backTop:function(){
		var oBackTop = document.getElementById('backTop');
		oBackTop.onclick = function(){
			var t = document.documentElement.scrollTop || document.body.scrollTop;
			var iTime = null;
			clearInterval(iTime);
			iTime = setInterval(function(){
				t-=20;
				if(t<=0){t=0};
			    document.documentElement.scrollTop = document.body.scrollTop =t;
			    if(t==0) clearInterval(iTime);
			},10);
		};
		window.onscroll = function(){
			var t = document.documentElement.scrollTop || document.body.scrollTop;
			if(t>100){
				oBackTop.style.display = 'block';
			}else{
				oBackTop.style.display = 'none';
			};
		};
	},
	// 计算器
	jishuqi:function(){
		var oCalculator = document.getElementById('Calculator');
		var oH3 = oCalculator.getElementsByTagName('h3')[0];
		drag(oH3,oCalculator);
		var isMoney = false,isMonth = false,isRate = false;
		$("#jishuqi").click(function(){
			$("#Calculator").show();
		});
		$("#calClose").click(function(){
			$("#Calculator").hide();
		});
		$("#Calculator .yuan input").focus(function(){
			$('#Calculator .yuanMsg').css('display','none');
			if($(this).val() == '000.00'){
				$(this).val('');
			};
		});
		$("#Calculator .yuan input").blur(function(){
			if($(this).val() == ''){
				$(this).val('000.00');
				$('#Calculator .yuanMsg').css('display','inline-block').html('不能为空！');
				isMoney = false;
			}else if( !isNaN( $(this).val() ) ){
				isMoney = true;
			}else if( isNaN( $(this).val() )){
				isMoney = false;
				$('#Calculator .yuanMsg').css('display','inline-block').html('请输入数字！');
			};
		});
		$("#Calculator .time input").eq(0).focus(function(){
			$('#Calculator .monMsg').css('display','none');
		});
		$("#Calculator .time input").eq(0).blur(function(){
			if($(this).val() == ''){
				$('#Calculator .monMsg').css('display','inline-block').html('不能为空！');
				isMonth = false;
			}else if( !isNaN( $(this).val() ) && parseInt( $(this).val() ) ==  parseFloat( $(this).val() ) ){
				isMonth = true;
			}else if( isNaN( $(this).val() ) ){
				isMonth = false;
				$('#Calculator .monMsg').css('display','inline-block').html('请输入数字！');
			}else if(parseInt( $(this).val() ) !=  parseFloat( $(this).val() )){
				isMonth = false;
				$('#Calculator .monMsg').css('display','inline-block').html('请输入整数！');
			};
		});
		$("#Calculator .time input").eq(1).focus(function(){
			$('#Calculator .rateMsg').css('display','none');
		});
		$("#Calculator .time input").eq(1).blur(function(){
			if($(this).val() == ''){
				$('#Calculator .rateMsg').css('display','inline-block').html('不能为空！');
				isRate = false;
			}else if( !isNaN( $(this).val() ) ){
				isRate = true;
			}else if( isNaN( $(this).val() )){
				isRate = false;
				$('#Calculator .rateMsg').css('display','inline-block').html('请输入数字！');
			};
		});
		$(".start").click(function(){
			if(isMoney && isMonth && isRate){
				var interest = ((parseFloat($("#Calculator .yuan input").val())*parseFloat($("#Calculator .time input").eq(1).val())/100/12).toFixed(2))*parseFloat($("#Calculator .time input").eq(0).val());
				var principal = interest+parseFloat($("#Calculator .yuan input").val());
				$(".interest em").html(interest.toFixed(2));
				$(".principal em").html(principal.toFixed(2));
			}else if(!isMoney){
				$('#Calculator .yuanMsg').css('display','inline-block').html('请输入正确数字！');
			}else if(!isMonth){
				$('#Calculator .monMsg').css('display','inline-block').html('请输入正确数字！');
			}else if(!isRate){
				$('#Calculator .rateMsg').css('display','inline-block').html('请输入正确数字！');
			}
		});
		$(".reset").on("click",function(){
			isMoney=false;isMonth=false;isRate=false;
			$(".interest em").html("0.00元");
			$(".principal em").html("0.00元");
		});
		$('#Calculator .option p').click(function(){
			$('#Calculator .option ul').toggle();
			$(document).click(function(){
				$('#Calculator .option ul').hide();
			});
			return false;
		});
		$('#Calculator .option li').click(function(){
			$('#Calculator .option p').html($(this).html());
			$('#Calculator .option ul').hide();

		});
	},
	// 首页投资列表
	indexProjectList:function(){  // 首页产品和标的加载
		zycfZ.getProduct();
        zycfZ.getDataInvestment({
        	page:1,
        	num:5
        });
	},
	getProduct:function(){   // 动态获取产品项
		$.ajax({
             type:"POST",
             url: projectUrl + "/front/product/queryAllProducts",
             data:{},
             dataType:"json",
             success:function(str){
             	$(".recommend ul").html("");
             	if (str.message.code == 1000) {
             		for(var i=0;i<str.products.length;i++){
	             		productList(str.products[i]);
	             	};
	             }
             }
        });
        function productList(obj){   // 动态加载产品项
        	if(obj.id == ''){
        		var oLi = $("<li class='on'><a code='' onclick='zycfZ.indexProjectSearch(this)' href='javascript:void(0);'>全部</a></li>");
        	}else{
        		var oLi = $("<li><a code="+obj.id+" onclick='zycfZ.indexProjectSearch(this)' href='javascript:void(0);'>"+obj.name+"</a></li>");
        	};
        	oLi.appendTo($(".recommend ul"));
        };
	},
	indexProjectSearch:function(This){   // 点击产品添加标的（分首页和列表页）
		$(".projectList").html("");
		$(".paging").hide();
		$(".recommend ul li").removeClass("on");
		$(This).parent().addClass("on")
		var reg = new RegExp("investment.html");
		if(reg.test(window.location.href)){
			zycfZ.getDataInvestment({page:1,num:10,productId:$(This).attr('code')});
		}else{
			zycfZ.getDataInvestment({page:1,num:5,productId:$(This).attr('code')});
		};
		
	},
	getDataInvestment:function(opt){   // 标的数据获取
		var settings = {
			page:1,
			num:5,
			productId:""
		};
		for (var i in opt) {
			settings[i] = opt[i];
		};
		$(".projectList").html("");
		$.ajax({
             type: "POST",
             url: projectUrl + "/front/loan/searchLoansByParams",
             data:{"page.pageNo": settings.page,
                  "page.pageSize": settings.num,
                  "page.params": {"productId": settings.productId}},
             dataType:"json",
             success:function(str){
             	$(".projectList").html("");
             	$('.paging').html("").hide();
             	if(!str.page.results || !str.page.results.length || str.page.results.length <= 0){
					var reg = new RegExp("investment.html");
					if(reg.test(window.location.href)){
						$('.projectListWrap .noData').show().html("暂无数据");
					}else{
						$('.projectListWrap .noData').show().html("暂无数据");
					};
             	}else{
					var reg = new RegExp("investment.html");
					if(reg.test(window.location.href)){
						zycfZ.getData(str);
	             		if(str.page.totalPage > 1){
             				$('.paging').html("").show();
             				page({
								id:'paging',
								nowNum:settings.page,
								allNum:str.page.totalPage,
								callBack:function(now,all){
									zycfZ.getDataInvestment({
							            page:now,
							            num: 10,
							            productId:settings.productId
									});
								}
							});
             			};
					}else{
						zycfZ.getData(str);
					};
             	};
             }
        });
	},
	getData:function(obj){   // 标的加载
		$('.projectListWrap .noData').hide();
 		for(var i=0;i<obj.page.results.length;i++){
 			getDataReader(obj.page.results[i]);
 		};
 		function getDataReader(arg){
 			var method,status,fnClick="javascript:void(0);",bgColor="bgColor";
 			var oDiv = $('<div class="timimg"></div>');
 			arg.oLi = $("<li class='one'></li>");
			if(arg.method=='MonthlyInterest'){
				method = '按月付息到期还本';
			}else if(arg.method=='EqualInstallment'){
				method = '按月等额本息';
			}else if(arg.method=='EqualPrincipal'){
				method = '按月等额本金';
			}else if(arg.method=='BulletRepayment'){
				method = '一次性还本付息';
			}else if(arg.method=='EqualInterest'){
				method = '月平息';
			};
			if(arg.addRate){
				arg.oLi.addClass("addRate");
			};
			if(arg.status=='SCHEDULED'){
				status = '开标倒计时';
				arg.t = parseInt(parseInt(arg.divTime)/1000);
	            if(arg.t<=0){
	            	arg.t = 0;
	            };
	            arg.countDown = Math.floor(arg.t/86400)+'天'+Math.floor(arg.t%86400/3600)+'时'+Math.floor(arg.t%86400%3600/60)+'分'+arg.t%60+'秒';
	            oDiv.html(arg.countDown);
	            arg.time = setInterval(function(){
			        arg.t-=1;
			        if(arg.t<=0){
			            arg.t=0;
			            clearInterval(arg.time);
			            setTimeout(function(){
			            	window.location.reload();
			            },1000);
			        };
			        arg.countDown = Math.floor(arg.t/86400)+'天'+Math.floor(arg.t%86400/3600)+'时'+Math.floor(arg.t%86400%3600/60)+'分'+arg.t%60+'秒';
			        arg.oLi.find(".timimg").html(arg.countDown);
			    },1000);
			}else if(arg.status=='OPENED'){
				status = '投资';
				fnClick = "zycfZ.toProjectDetails(this)";
				bgColor = "";
			}else if(arg.status=='FINISHED'){
				status = '已满标';
				fnClick = "zycfZ.toProjectDetails(this)";
			}else if(arg.status=='SETTLED'){
				status = '还款中';
				fnClick = "zycfZ.toProjectDetails(this)";
			}else if(arg.status=='CLEARED'){
				status = '已还清';
				fnClick = "zycfZ.toProjectDetails(this)";
			};
			if(!arg.planing){
				arg.planing=0;
			};
			arg.oLi.html("<div class='title'><span class='titleYin'>"+arg.productName+"</span><a class='titleContent' onclick='"+fnClick+"' code="+arg.loanId+" href='javascript:void(0);'>"+arg.loanName+"</a></div><div class='quanNianBottom clear'><div class='quanNianLeft'><div class='yearRates'><span>预计年化收益：<em>"+arg.rate+"%</em><em class='minSize'>"+(arg.addRate?'+'+arg.addRate+'%':'')+"</em></span><span>期限：<em>"+arg.months+"</em>个月</span><span class='schedule'>进度：&nbsp;<span class='scheduleBar'><span class='scheduleBarTop' style='width:"+Math.round(94*arg.planing/100)+"px"+"'></span></span>"+arg.planing+"%"+"</span></div><div class='total'><span>总额：<em>"+arg.amount/10000+"万"+"</em></span><span>还款方式：<em>"+method+"</em></span><span>剩余可投：<em>"+arg.suplusAmount+"元"+"</em></span></div></div><div class='quanNianRight "+bgColor+"' onclick="+fnClick+" code="+arg.loanId+">"+status+"</div></div>");
			oDiv.appendTo(arg.oLi);
			arg.oLi.appendTo($(".projectList"));
 		};
	},
	toProjectDetails:function(This){  // 首页和列表页跳转到标的详情页
		var reg = new RegExp("investment.html");
		if(reg.test(window.location.href)){
			window.location.href = "../html/account/investmentDetails.html#"+$(This).attr('code');
		}else{
			window.location.href = "html/account/investmentDetails.html#"+$(This).attr('code');
		};
	},
	// 我要投资页 查看更多
	inverMore:function(){
		zycfZ.getProduct();
		zycfZ.getDataInvestment({
            page:1,
            num:10
		});
	},
	// 投资详情页
	investmentDetails:function(){
		var id = window.location.hash.substring(1);
		var money=0,flag=false,suplusAmount,rate,months,status,minAmount,stepAmount,availableAmount,isAdd = false;
		$("#RechargeResults .title i").click(function(){
			$("#RechargeResults .rechargeAgain").click();
		});
		$("#RechargeResults .success a").click(function(){
			$("#RechargeResults .rechargeAgain").click();
		});
		$("#RechargeResults .rechargeAgain").click(function(){
			$("#RechargeResults").hide();
			window.location.reload();
		});
		$.ajax({
            type:"POST",
            url: projectUrl + "/front/loan/loanDetail",
            data:{"loan.id":id},
            dataType:"json",
            success:function(str){
            	var message = str.message;
            	if (message.code === 1000) {
            		var str = str.loanDetail
            		var method;
	             	suplusAmount = str.suplusAmount;
	             	status = str.status
	             	rate = str.rate;
	             	months = str.months;
	             	if(str.method=='MonthlyInterest'){
						method = '按月付息到期还本';
					}else if(str.method=='EqualInstallment'){
						method = '按月等额本息';
					}else if(str.method=='EqualPrincipal'){
						method = '按月等额本金';
					}else if(str.method=='BulletRepayment'){
						method = '一次性还本付息';
					}else if(str.method=='EqualInterest'){
						method = '月平息';
					};
	             	$(".projectDetails .title").html("【"+str.productName+"】"+str.loanName);
	             	$(".projectDetails .rate").html("<strong>"+str.rate+"%</strong>"+(str.addRate?'+'+str.addRate+'%':''));
	             	$(".projectDetails .total strong").html(str.amount/10000);
	             	$(".projectDetails .deadline strong").html(str.months);
	             	$(".projectDetails .method .left em").html(method);
	             	$(".projectDetails .method .right strong").html(str.suplusAmount);
	             	$(".projectDetails .schedule .top").css("width",Math.round(98*str.planing/100)+"px");
	             	$(".projectDetails .schedule .planing").html(str.planing+"%");
	             	minAmount = str.minAmount || 100;
	             	stepAmount = str.stepAmount || 100;
	             	$("input[name='invesmentMoney']").val(minAmount+"元起投，递增金额"+stepAmount+"元");
	             	if(str.addRate){
	             		isAdd = false;
	             	}else{
	             		isAdd = true;
	             	}
	             }
            }
        });
		$("input[name='invesmentMoney']").focus(function(){
 			if( $(this).val() == (minAmount+"元起投，递增金额"+stepAmount+"元") ){
 				$(this).val("");
 			};
 		});
 		$("input[name='invesmentMoney']").blur(function(){
 			if($(this).val() == ""){
 				$(this).val(minAmount+"元起投，递增金额"+stepAmount+"元");
 				$(".signInState .profits strong").html(0);
 			}else{
 				profit();
 			};
 			if(parseFloat($(this).val()) == $(this).val() && $(this).val()>=(parseFloat(minAmount)+parseFloat(stepAmount))){
 				$(".signInState .inputMoney .minus").css("background-position","0 -36px");
 			}else{
 				$(".signInState .inputMoney .minus").css("background-position","0 0px");
 			};
 		});
 		$("input[name='invesmentMoney']").on("change",function(){
 			$(".invesmentMoney .redPacketsBtn span").html("使用红包").attr("code","");
 		});
 		$(".signInState .inputMoney .minus").click(function(){
 			var n = 0;
 			n--;
 			if($("input[name='invesmentMoney']").val()<=minAmount){
 				return false;
 			};
 			changeMoney(n);
 		});
 		$(".signInState .inputMoney .plus").click(function(){
 			var n = 0;
 			n++;
 			changeMoney(n);
 		});
 		function changeMoney(n){
 			if($("input[name='invesmentMoney']").val() == "" || $("input[name='invesmentMoney']").val() == (minAmount+"元起投，递增金额"+stepAmount+"元") ){
 				$("input[name='invesmentMoney']").val("0");
 			};
 			money = parseFloat($("input[name='invesmentMoney']").val()) + n*parseFloat(minAmount);
 			if(money<0){
 				money=0;
 			};
 			$("input[name='invesmentMoney']").val(money);
 			if(parseFloat($("input[name='invesmentMoney']").val()) == $("input[name='invesmentMoney']").val() && $("input[name='invesmentMoney']").val()>=(parseFloat(minAmount)+parseFloat(stepAmount))){
 				$(".signInState .inputMoney .minus").css("background-position","0 -36px");
 			}else{
 				$(".signInState .inputMoney .minus").css("background-position","0 0px");
 			};
 			profit();
 			return false;
 		};
		$.ajax({
            type:"POST",
            url: projectUrl + "/front/userFund/queryUserFund",
            data:{},
            dataType:"json",
            success:function(str){
             	if(str.message.code == 2002){  //未登录
             	}else if(str.message.code == 2005){  //未认证
             		$(".signInState").hide();
             		$(".noSignIn").show();
             	}else if(str.message.code == 1000){  //登录和认证完
             		flag=true;
             		availableAmount = str.userFund.availableAmount;
             		invesment(str.userFund);
             		//getRedPackets(str.userFund);
             	};
            }
        });
        $(".invesmentMoney .redPacketsBtn").click(function(event){
        	event.stopPropagation();
        	if(!isAdd){
        		alert("红包不能与超指标同时使用！");
        		return false;
        	}
        	$(".invesmentMoney .redPacketsWrap").toggle();
        	$(document).click(function(){
        		$(".invesmentMoney .redPacketsWrap").hide();
        	});
        	if($(".invesmentMoney .redPacketsWrap").css("display") == "block"){
        		$(".invesmentMoney .redPacketsList li").each(function(){
        			$(this).unbind("click");
        			if( parseFloat($("input[name='invesmentMoney']").val()) >= parseFloat($(this).attr("lit")) && $("input[name='invesmentMoney']").val() != (minAmount+"元起投，递增金额"+stepAmount+"元") ){
        				$(this).css("cursor","pointer");
        				$(this).find(".setColor strong").css("color","#ff6f0a");
        				$(this).insertBefore($(".invesmentMoney .redPacketsList li").eq(0));
        				$(this).click(function(event){
        					$(".invesmentMoney .redPacketsBtn span").html($(this).attr("code")+"元").attr("code",$(this).attr("id"));
        					$(".invesmentMoney .redPacketsWrap").hide();
        					event.stopPropagation();
        					return false;
        				});
        			}else{
        				$(this).css("cursor","not-allowed");
        				$(this).find(".setColor strong").css("color","#999");
        				$(this).click(function(event){
        					event.stopPropagation();
        					return false;
        				});
        			};
        		});
        	};
        	return false;
        });
        function getRedPackets(){
        	$.ajax({
        		type:"POST",
	            url:"/queryFreshAmounts",
	            data:{"loanId":id},
	            success:function(str){
	            	if(str.length && str.length>0){
	            		$(".invesmentMoney .profits").addClass("left");
	            		$(".invesmentMoney .redPacketsBtn").show();
	            		for (var i = 0,len=str.length; i < len; i++) {
	            			var oLi = $('<li class="clear" code="'+str[i].amount+'" id="'+str[i].id+'" lit="'+str[i].amountLimit+'"></li>');
	            			oLi.html('<div class="left">\
				                        <p class="setColor"><strong>￥'+str[i].amount+'</strong></p>\
				                        <p class="pdLeft">金额</p>\
				                    </div>\
				                    <div class="right">\
				                        <p>起投金额￥'+str[i].amountLimit+'</p>\
				                        <p>到期日'+new Date(parseInt(str[i].endTime,10)).toLocaleDateString()+'</p>\
				                    </div>');
	            			oLi.appendTo($(".redPacketsList"));
	            		};
	            	}
	            }
        	});
        };
        function invesment(obj){
        	$(".signInState .balance span").html(obj.availableAmount+"元"+"<a href='../account/recharge.html'>充值</a>");
    		if(obj.availableAmount<parseFloat(minAmount)){
    			$(".signInState .invesmentBtn input").css("background-color","#d9d9d9");
    		};
        };
        function profit(){
        	var profit = parseFloat($("input[name='invesmentMoney']").val())*rate*months/100/12;
        	$(".signInState .profits strong").html(profit.toFixed(2));
        };
        $(".signInState .invesmentBtn input").click(function(){   // 点击投资
        	var This = this;
        	if (This.isClick) {
        		alert("正在投资中...");
        		return;
        	};
        	This.isClick = true;
        	var investAmount = ($("input[name='invesmentMoney']").val() != (minAmount+"元起投，递增金额"+stepAmount+"元"))?parseFloat($("input[name='invesmentMoney']").val()):0;
        	if(status == "SCHEDULED"){
        		This.isClick = false;
        		alert("未到开标时间，请持续关注！");
        	}else{
		    	if(flag==true &&investAmount%minAmount==0 && investAmount>=minAmount && investAmount<=suplusAmount && investAmount<=availableAmount){
		    		$.ajax({
			            type:"POST",
			            url: projectUrl + "/front/loan/userInvest",
			            data:{
			            	"amount":investAmount,
			            	"loanId":id,
			            },
			            dataType:"json",
			            success:function(str){
			            	alert(str.message.message);
			            	if (str.message.code === 1000) {
			            		$("#RechargeResults").show();
			            	}
			            	This.isClick = false;
			            },
			            error: function (err) {
			            	This.isClick = false;
			            	alert(err.message)
			            }
			        });
		    	} else {
		    		This.isClick = false;
		    	};
		    	if(flag==false){
	        		alert("用户未登录或未认证");
	        	}else if(investAmount<minAmount){
	        		alert("请输入大于"+minAmount+"的整数");
	        	}else if(investAmount%minAmount!=0){
	        		alert("请输入"+minAmount+"的倍数");
	        	}else if(investAmount>suplusAmount){
	        		alert("请输入小于剩余可投金额");
	        	}else if(investAmount>availableAmount){
	        		alert("你输入的金额已超出可用余额");
	        	};
        	};
        });
        $.ajax({
            type:"POST",
            url: projectUrl + "/front/project/searchProjectByParams",
            data:{"loan.id":id},
            dataType:"json",
            success:function(str){
            	var message = str.message;
            	if (message.code !== 1000) {
            		return;
            	};
            	var str = str.projectDetail;
             	$(".infoImg .infoImgList").html("");
             	$(".infoContent .resetMargin").html(str.firmInfo);
             	$(".infoContent .operationRange span").html(str.operationRange);
             	$(".infoContent .repaySource span").html(str.repaySource);
             	$(".infoContent .riskInfo span").html(str.riskInfo);
             	$(".infoContent .description span").html(str.description);
             	if(str.legalPersonPhotoUrl){
	            	var LPho = str.legalPersonPhotoUrl.split(",");
	            	for(var i=0;i<LPho.length;i++){
	            		var oLi = $("<li><a href='javascript:void(0);'><img src="+LPho[i]+" alt='认证书' /></a></li>");
	            		oLi.appendTo($(".infoImg .infoImgList"));
	            	};
	            };
	            if(str.enterpriseInfoPhotoUrl){
	            	var EPho = str.enterpriseInfoPhotoUrl.split(",");
	            	for(var i=0;i<EPho.length;i++){
	            		var oLi = $("<li><a href='javascript:void(0);'><img src="+EPho[i]+" alt='认证书' /></a></li>");
	            		oLi.appendTo($(".infoImg .infoImgList"));
	            	};
	            };
	            if(str.assetsPhotoUrl){
	            	var APho = str.assetsPhotoUrl.split(",");
	            	for(var i=0;i<APho.length;i++){
	            		var oLi = $("<li><a href='javascript:void(0);'><img src="+APho[i]+" alt='认证书' /></a></li>");
	            		oLi.appendTo($(".infoImg .infoImgList"));
	            	};
	            };
	            if(str.contractPhotoUrl){
	            	var CPho = str.contractPhotoUrl.split(",");
	            	for(var i=0;i<CPho.length;i++){
	            		var oLi = $("<li><a href='javascript:void(0);'><img src="+CPho[i]+" alt='认证书' /></a></li>");
	            		oLi.appendTo($(".infoImg .infoImgList"));
	            	};
	            };
	            if(str.othersPhotoUrl){
	            	var OPho = str.othersPhotoUrl.split(",");
	            	for(var i=0;i<OPho.length;i++){
	            		var oLi = $("<li><a href='javascript:void(0);'><img src="+OPho[i]+" alt='认证书' /></a></li>");
	            		oLi.appendTo($(".infoImg .infoImgList"));
	            	};
	            };
	            table();
            }
        });
        $.ajax({
            type:"POST",
            url: projectUrl + "/front/invest/searchInvestRecord",
            data:{
            	"page.params": {
            		"loanId": id
            	},
            	"page.pageNo": 1,
            	"page.pageSize": 999,
            	"page.strStart": '',
            	"page.strEnd": ''
            },
            dataType:"json",
            success:function(str){
             	$(".record .recordInfo").html("");
             	$(".record .num strong").html(str.page.totalRecord);
             	for(var i=0;i<str.page.results.length;i++){
             		creatTab(str.page.results[i]);
             	};
            }
        });
        function creatTab(obj){
        	var d = new Date(parseInt(obj.submitTime,10));
        	var t = d.getFullYear() + '-' + (d.getMonth()+1) + '-' + d.getDate() + ' ' + (d.getHours()<10?"0"+d.getHours():d.getHours()) + ':' + (d.getMinutes()<10?"0"+d.getMinutes():d.getMinutes()) + ':' + (d.getSeconds()<10?"0"+d.getSeconds():d.getSeconds());
        	var oLi = $("<li><span>"+obj.userName+"</span><span class='red'>"+"￥"+obj.amount+"</span><span>"+t+"</span></li>");
        	oLi.appendTo($(".record .recordInfo"));
        };
		$('.invesmentInfo .infoTab li').click(function(){
			$('.invesmentInfo .infoTab li').removeClass('active');
			$(this).addClass('active');
			switch ($(this).index()){
				case 0:
					$('.invesmentInfo .infoContent').show();
					$('.invesmentInfo .infoImg').hide();
					$(this).css('border-right','none');
				break;
				case 1:
				    $(this).css('border-left','1px solid #e4e4e4');
					$('.invesmentInfo .infoContent').hide();
					$('.invesmentInfo .infoImg').show();
				break;
			};
		});
		$('.safe_Problem .infoTab li').click(function(){
			$('.safe_Problem .infoTab li').removeClass('active');
			$(this).addClass('active');
			switch ($(this).index()){
				case 0:
					$('.safe_Problem .safe').show();
					$('.safe_Problem .problem').hide();
					$(this).css('border-right','none');
				break;
				case 1:
				    $(this).css('border-left','1px solid #e4e4e4');
					$('.safe_Problem .safe').hide();
					$('.safe_Problem .problem').show();
				break;
			};
		});
		function table(){
			var num = 0;
			var w = $('.invesmentInfo .infoImg .infoImgList').find('li').outerWidth();
			var len = $('.invesmentInfo .infoImg .infoImgList').find('li').size();
			$(".infoImgList").css("width",len*w);
			$('.invesmentInfo .infoImg .prev').click(function(){
				num--;
				if(num<0){
					num = len-4;
				};
				$('.invesmentInfo .infoImg .infoImgList').stop().animate({
					left:-w*num
				},500);
			});
			$('.invesmentInfo .infoImg .next').click(function(){
				num++;
				num%=len-3;
				$('.invesmentInfo .infoImg .infoImgList').stop().animate({
					left:-w*num
				},500);
			});
			if(len){
				$('.infoImgList li img').each(function(){
					enlarge( $(this) );
				});
			};
			function enlarge(obj){
				obj.click(function(){
					var oDiv = $("<div class='bigImg'></div>");
					oDiv.html("<img src="+$(this).attr("src")+" alt='资质文件图片' /><i class='bigImgClose'>x</i>");
					oDiv.appendTo( $("body") );
					$(".bigImgClose").click(function(){
						$(this).parent().remove();
					});
				});
			};
		};
	},
	teste:function(){
        zycfZ.getTestBid(testBidData);
		function testBidData(obj){
			$(".testeDetail .rate strong").html(obj.data.rate);
			$(".testeDetail .total strong").html(obj.data.loanDay);
			if(obj.data.login){
				$(".loginStatus").hide();
				$(".testMoneyNum").show().html(obj.data.experienceAmount+"元");
				$(".profit strong").html( parseFloat(parseFloat(obj.data.experienceAmount)*parseFloat(obj.data.rate)*parseFloat(obj.data.loanDay)/36500).toFixed(2) );
				if(obj.data.experienceAmount > 0){
					$(".invesmentBtn input").click(function(){
						$.ajax({
							type:"POST",
							url:"/investVirtualLoan",
							success:function(str){
								alert(str.message);
								if(str.code == 1){
									window.location.href = "https://www.zuoyoufy.com/";
								};
							}
						})
					});
				}else{
					$(".invesmentBtn input").css("background-color","#999");
				};
			}else{
				$(".invesmentBtn input").css("background-color","#999");
			}
		};
	},
	/* 从充值和提现页面到我的账户 */
	myAccountUrl:function(){
		var hash = window.location.hash.substring(1);
		if(hash){
			$('.rechargeMain .sidebarNav li').removeClass('active').eq(hash).addClass('active');
			$('.accountContent .Tabl').hide().eq(hash).show()
		};
	},
	// 注册页弹出框居中
	regsiterHeight:function(){
		var oPhoTestCon = $('#phoTest .phoTestCon');
		var oOpenSuccess = $('.openSuccess');
		var t = (document.documentElement.clientHeight-oPhoTestCon[0].offsetHeight)/2;
		var w = (document.documentElement.clientWidth-oPhoTestCon[0].offsetWidth)/2;
		var t1 = (document.documentElement.clientHeight-oOpenSuccess[0].offsetHeight)/2;
		var w1 = (document.documentElement.clientWidth-oOpenSuccess[0].offsetWidth)/2;
		oPhoTestCon[0].style.left = w+'px';
		oPhoTestCon[0].style.top = t+'px';
		oOpenSuccess[0].style.left = w1+'px';
		oOpenSuccess[0].style.top = t1+'px';
	},
	// 注册流程
	regsiterWorkFlow:function(){
		if(window.location.search){   //注册时判断地址栏是否有推荐人
			var refereeMobile = window.location.search.substring(1);
			var mobileArr = refereeMobile.split("=");
			if( checkMobile(mobileArr[1]) ){
				$('.referee input').val(mobileArr[1]);
				$('.referee input').focus();
			}
		};
		$("input[type!='button']").focus(function(){   //光标进入时，输入框边框颜色统一变红
			$(this).css('border-color','#ff6f0a');
		});
		$("input[type!='button']").blur(function(){    //光标离开时，输入框边框颜色统一变灰
			$(this).css('border-color','#ccc');
		});
        var btn = false;   //密码的眼睛是否开启
        var off = false;   //密码
        var phoValue = false;  //手机号码
        var ref = true;   //推荐人
        var timeOut = null;   //定时器
		$("input[name='pho']").focus(function(){
			$('.pho .msg').css('display','none');
			$('.pho .msgInfo').css('display','none');
			if($(this).val() == '手机号'){
				$(this).val('');
			};
		});
		$("input[name='pho']").blur(function(){
			if($(this).val() == ''){
				$(this).val('手机号');
				$('.pho .msgInfo').css('display','inline-block').html('手机号不能为空');
				phoValue = false;
			}else if( checkMobile($(this).val()) ){
				$('.pho .msg').css('display','inline-block');
				phoValue = true;
			}else if( !checkMobile($(this).val()) ){
				$('.pho .msgInfo').css('display','inline-block').html('请输入正确手机号码');
				phoValue = false;
			};
		});
		$(".password input[name='password']").focus(function(){
			$('.password .msg').css('display','none');
			$('.password .msgInfo').css('display','none');
			if($(this).val() == "6~16位数字与字母组合"){
				$(".password input[name='password1']").css("display","inline-block").focus();
			    $('.regsiterList .eyes').css('background-position','0 0');
			    btn = true;
			};
		});
		$(".password input[name='password']").blur(function(){
			if($(this).val() == ''){
				$('.password .msgInfo').css('display','inline-block').html('密码不能为空');
				off = false;
			}else if( testNum($(this).val()) ){
				$('.password .msg').css('display','inline-block');
				off = true;
			}else if(!testNum($(this).val())){
				$('.password .msgInfo').css('display','inline-block').html('请输入6~16位数字与字母组合');
				off = false;
			};
		});
		$(".password input[name='password']").change(function(){
			$("input[name='password1']").val($(this).val());
		});
		$(".password input[name='password1']").change(function(){
			$("input[name='password']").val($(this).val());
		});
		$("input[name='password1']").focus(function(){
			$('.password .msg').css('display','none');
			$('.password .msgInfo').css('display','none');
		});
		$("input[name='password1']").blur(function(){
			if($(this).val() == ''){
				$('.password .msgInfo').css('display','inline-block').html('密码不能为空');
				off = false;
			}else if( testNum($(this).val()) ){
				$('.password .msg').css('display','inline-block');
				off = true;
			}else if( !testNum($(this).val()) ){
				$('.password .msgInfo').css('display','inline-block').html('请输入6~16位数字与字母组合');
				off = false;
			};
		});
		$('.regsiterList .eyes').click(function(){
			if(!btn){
				$(this).css('background-position','0 0');
				if($("input[name='password']").val() == "6~16位数字与字母组合"){
					$("input[name='password1']").val("");
				}else{
					$("input[name='password1']").val($("input[name='password']").val());
				};
				$("input[name='password1']").css("display","inline-block").focus();
				btn = !btn;
			}else{
				$(this).css('background-position','0 -30px');
				if($("input[name='password1']").val() == ""){
					$("input[name='password']").val("6~16位数字与字母组合");
				}else{
					$(".password input[name='password']").val($("input[name='password1']").val());
				};
				$("input[name='password1']").css("display","none");
				$(".password input[name='password']").focus();
				btn = !btn;
			};
		});
		$('.referee input').focus(function(){
			$('.referee .msg').css("display","none");
			$('.referee .msgInfo').css("display","none");
			if($(this).val() == '选填'){
				$(this).val('');
			};
		});
		$('.referee input').blur(function(){
			if($(this).val() == ''){
				$(this).val('选填');
				ref = true;
			}else if( checkMobile($(this).val()) ){    // 验证推荐人手机号存在
				$.ajax({	
					url: projectUrl+'/front/user/searchUser',  //请求路径
					type: 'post',  //请求方式  get||post
					data: {'user.mobile': $('.referee input').val()},  //请求数据
					success: function(str){
						if(str.message.code == 1000){
							$('.referee .msg').css("display","inline-block");
							ref = true;
						} else {  // 用户不存在
							$('.referee .msgInfo').css("display","inline-block").html(str.message.message);
							ref = false;
						};
					}
				});
			}else{
				$('.referee .msgInfo').css("display","inline-block").html("请输入正确手机号码");
				ref = false;
			};
		});
		var vf = false;  //验证码
		$('.code input').focus(function(){
			$('.regsiterList .code .msg').css('display','none');
			$('.regsiterList .code .msgInfo').css('display','none');
			if($(this).val() == '右侧验证码'){
				$(this).val('');
			};
		});
		$('.code input').blur(function(){
			var re = /^\w{4}$/;
			if($(this).val() == ''){
				$(this).val('右侧验证码');
				$('.regsiterList .code .msgInfo').css('display','inline-block').html('验证码不能为空');
				vf = false;
			}else if( re.test($(this).val()) ){
				$.ajax({	
					url: projectUrl+'/front/util/codeValalidate',  //图片验证码
					type:'post',  //请求方式  get||post
					data:{"imgCode":$('.code input').val()},  //请求数据
					success:function(str){
						if(str.code == 1000){
							$('.regsiterList .code .msg').css('display','inline-block');
				    	    vf = true;   // 成功
						}else{
					    	$('.regsiterList .code .msgInfo').css('display','inline-block').html('验证码错误');
				            vf = false;
						};
					}
				});
			}else if( !re.test($(this).val()) ){
				vf = false;
				$('.regsiterList .code .msgInfo').css('display','inline-block').html('验证码格式错误');
			};
		});
        $('.regsiterList .code .Refresh').click(function(){
        	$('.regsiterList .code').find('img').attr('src',projectUrl+'/front/util/imgValalidate'+'?'+ Math.random());
        	$('.code input').val("");
        });
		$('.nextPage input').click(function(){
			if(off && phoValue && vf && ref && $("input[type='checkbox']").is(':checked')){
			    $.ajax({	
					url: projectUrl+'/front/user/mobileUnique',  //验证手机号唯一
					type: 'post',  //请求方式  get||post
					data:{'user.mobile': $("input[name='pho']").val()},  //请求数据
					success:function(str){
						if(str.message.code == 1000){
							$('.pho .msg').css('display','none');
							$('.pho .msgInfo').css('display','inline-block');
							$('.pho .msgInfo').html('手机号码已存在');
					    }else if (str.message.code == 2001){
					    	$('.pho .msg').css('display','inline-block');
					    	$('.pho .msgInfo').css('display','none');
					    	$('#phoTest').show();
			                zycfZ.regsiterHeight();   // 居中
			                var changeStar = $("input[name='pho']").val().replace(/(\d{3})\d{4}(\d{4})/,"$1****$2");
			                $('.phoForm .changStar').html(changeStar);
			                getMessage();
					    } else {
					    	$('.pho .msg').css('display','none');
							$('.pho .msgInfo').css('display','inline-block');
							$('.pho .msgInfo').html('出错了');
					    }
					}
				});
			}else if(!off){
				$('.password .msg').css('display','none');
				$('.password .msgInfo').css('display','inline-block');
				$('.password .msgInfo').html('密码格式错误或为空');
			}else if(!phoValue){
				$('.pho .msg').css('display','none');
				$('.pho .msgInfo').css('display','inline-block');
				$('.pho .msgInfo').html('手机号码错误或已注册');
			}else if(!vf){
				$('.regsiterList .code .msg').css('display','none');
		    	$('.regsiterList .code .msgInfo').css('display','inline-block');
	            $('.regsiterList .code .msgInfo').html('验证码错误或为空');
			}else if(!ref){
				$('.referee .msg').css("display","none");
				$('.referee .msgInfo').css("display","inline-block").html("手机号码错误或未注册");
			}else if($("input[type='checkbox']").is(':checked') == false){
				alert("请勾选协议，以便更好的为您服务")
			};
		});
		$('.TelInfo input').focus(function(){
			$('.phoForm .msg').css("visibility","hidden");
			if($(this).val() == '短信验证码'){
				$(this).val('');
			};
		});
		$('.TelInfo input').blur(function(){
			if($(this).val() == ''){
				$(this).val('短信验证码');
			};
		});
        $('.finishRegsiter input').click(function(){
    		if($("input[name='idnumber']").val() == '' || $("input[name='idnumber']").val() == '短信验证码'){
    			$('.phoForm .msg').html("请输入正确短信验证码").css("visibility","visible");
		    }else{
		    	sendInfo();
		    };
		    function sendInfo(){
		    	if($('.referee input').val() == '选填' || $('.referee input').val() == ''){
			    	$('.referee input').val('15311340737');
			    };
			    $.ajax({	
					url: projectUrl+'/front/user/registeUser',  //注册提交
					type:'post', 
					data:{
						"mobile":$("input[name='pho']").val(),
						"passphrase":$("input[name='password1']").val(),
						"referralMobile":$('.referee input').val(),
						"regcode":$("input[name='idnumber']").val(),
						"userType": $(".userTpyeChoose").attr("forSort")
					},  //请求数据
					success:function(str){
						if(str.message.code == 1000){   // 注册成功
							$('#phoTest').hide();
							$('.regsiterList').hide();
							$('.open').show();
							$('.regsiterStep li').addClass('bg');
							$('.apply').html('');
						}else{
							$('.phoForm .msg').html(str.message.message).css("visibility","visible");
						};
					}
				});
		    };
		});
		function getMessage(){
			$('.phoForm .reAbtain').css('display','none');
			$('.phoForm .reSend').css('display','inline-block');
			$.ajax({	
				url: projectUrl+'/front/util/mobolileCertWeb',  //手机短信发送
				type:'post',
				data:{},
				success:function(str){
					clearInterval(timeOut);
					if(str.code == 1000){
						var num = 60;
						$('.phoForm .reSend').html(num+'秒后重新发送');
						timeOut = setInterval(function(){
							num--;
							if(num == 0){
								clearInterval(timeOut);
								$('.phoForm .reAbtain').css('display','inline-block');
								$('.phoForm .reSend').css('display','none');
							}
							$('.phoForm .reSend').html(num+'秒后重新发送');
						},1000);
					}else if(str.code == 3002){
						$('.phoForm .msg').css("visibility","visible").html("时间间隔不足60秒");  
					}else if(str.code == 3001){
						$('.phoForm .msg').css("visibility","visible").html("提交次数大于4次");  
					}else if(str.code == 3003){
						$('.phoForm .msg').css("visibility","visible").html("验证码失效");  
					}else if(str.code == 3004){
						$('.phoForm .msg').css("visibility","visible").html("验证码错误");  
					}
				}
			});
		};
		$('.phoForm .reAbtain').click(function(){   // 重新获取验证码
			getMessage();
		});
		selectionFn('userTpye');
		selectionFn('bank');
		selectionFn('bankType');
		function selectionFn (str) {
			$("."+str+" i").click(function(){
				$(this).next().toggle();
				return false
			});
			$('.'+str+' ul').click(function(ev){
				if(ev.target.nodeName === 'LI'){
					$(this).siblings('.choosed').html($(ev.target).html());
					$(this).siblings('.choosed').attr('forSort', $(ev.target).attr('code'));
					$(this).hide();
				}
				return false;
			});
			$(document).click(function(){
				$('.'+str+' ul').hide();
				$('.'+str+' ul').hide();
			});
		};
		var idCode = false;   //身份证号码验证
		var bankCode = false;  //银行卡
		$(".name input").focus(function(){
			$('.name .msg').css('display','none');
			$('.name .msgInfo').css('display','none');
		});
		$(".name input").blur(function(){
			if( $(this).val() == "" ){
				$('.name .msgInfo').css('display','inline-block').html("请输入中文姓名");
			}else if( checkChinese($(this).val()) ){
				$('.name .msg').css('display','inline-block');
			}else{
				$('.name .msgInfo').css('display','inline-block').html("请输入中文姓名");
			}
		});
		$("input[name='nameId']").focus(function(){
			$('.nameId .msg').css('display','none');
			$('.nameId .msgInfo').css('display','none');
			if($(this).val() == '请输入正确身份证号'){
				$(this).val('');
			};
		});
		$("input[name='nameId']").blur(function(){
			if($(this).val() == ''){
				$(this).val('请输入正确身份证号');
				$('.nameId .msgInfo').css('display','inline-block').html('请输入正确身份证号');
				idCode = false;
			}else if( checkCard($(this).val()) && zycfZ.eighteen($(this).val()) ){
				$('.nameId .msg').css('display','inline-block');
				idCode = true;
			}else if( !checkCard($(this).val()) ){
				$('.nameId .msgInfo').css('display','inline-block').html('您输入的证件号码或用户姓名有误，请重新输入');
				idCode = false;
			}else if( !zycfZ.eighteen($(this).val()) ){
				$('.nameId .msgInfo').css('display','inline-block').html('用户未满18岁，不能认证');
				idCode = false;
			};
		});
		var cardBtn = true;
		if(cardBtn){
			$('.openNow input').click(function(){
				if( idCode && checkChinese($(".name input").val()) && bankCode && $("input[name='cardNum']").val() != '' && $("input[name='reserveNum']").val() != ''){
					cardBtn = false;
					$.ajax({	
						url: projectUrl + '/front/bmAccount/registeApply',  //实名验证
						type:'post',  //请求方式  get||post
						data:{
						    "bmaccount.userName": $(".name input").val(),
						    "bmaccount.idCode": $("input[name='nameId']").val(),
						    "bmaccount.openBranch": $(".selectedBank").attr("forSort"),
						    "bmaccount.cardNo": $("input[name='cardNum']").val(),
						    "bmaccount.preMobile": $("input[name='reserveNum']").val(),
						    "bmaccount.cardType": $(".bankTypeChoose").attr("forSort")
						},
						success:function(str){
							cardBtn = true;
							if(str.message.code == 1000){   // 
								makesureFn()
								$('.open').hide();
								$('.regsiterStep li').removeClass('bg').addClass('bg1');
							}else{
								$('.apply').html(str.message.message);
							};
						},
						error: function () {
							cardBtn = true;
						}
					});
				}else if(!idCode){
					$('.apply').html('您输入的证件号码或用户姓名有误，请重新输入');
				}else if( !checkChinese($(".name input").val()) ){
					$('.apply').html('您输入的证件号码或用户姓名有误，请重新输入');
				}else if ( !bankCode ) {
					$('.apply').html('您输入银行卡号有误或与开卡银行不一致');
				};
			});
        }else{
        	alert("正在连接服务器，请稍后")
        };
        function makesureFn () {
        	$('.makesureInfo').show();
        	$('.callbackMessage').html('');
        	$(".makesureInfo .username .msg").html( $(".name input").val() );
        	$(".makesureInfo .idCode .msg").html( $("input[name='nameId']").val() );
        	$(".makesureInfo .bank .msg").html( $(".selectedBank").html() );
        	$(".makesureInfo .bankType .msg").html( $(".bankTypeChoose").html() );
        	$(".makesureInfo .bankCode .msg").html( $("input[name='cardNum']").val() );
        	$(".makesureInfo .mobile .msg").html( $("input[name='reserveNum']").val() );
        };
        $(".makesureInfo .btn input").click(function () {
        	var This = this;
			if (This.btn) {
				return;
			}
			This.btn = true;
        	if($("input[name='shortMessageCode']").val() != ''){
					$.ajax({	
						url: projectUrl + '/front/bmAccount/registeAffirm',  //信息确认
						type:'post',  //请求方式  get||post
						data:{
						    "bmaccount.userName": $(".name input").val(),
						    "bmaccount.idCode": $("input[name='nameId']").val(),
						    "bmaccount.openBranch": $(".selectedBank").attr("forSort"),
						    "bmaccount.cardNo": $("input[name='cardNum']").val(),
						    "bmaccount.preMobile": $("input[name='reserveNum']").val(),
						    "bmaccount.cardType": $(".bankTypeChoose").attr("forSort"),
						    "bmaccount.identifyingCode": $("input[name='shortMessageCode']").val()
						},
						success:function(str){
							This.btn = false;
							if(str.message.code == 1000){
								$('.makesureInfo').hide();
								$('.regsiterSuccess').show();
								$('.regsiterStep li').removeClass('bg').addClass('bg1');
								var num = 3;
								setInterval(function(){
									num--;
									$('.regsiterSuccess .threeMin strong').html(num);
									if(num<=0){
										window.location.href = '../index.html';

									};
								},1000);          
							}else{
								$('.callbackMessage').html(str.message.message);
							};
						},
						error: function () {
							This.btn = false;
						}
					});
				}else if($("input[name='shortMessageCode']").val() == ''){
					This.btn = false;
					$('.callbackMessage').html('您输入短信验证码');
				}
        });
		$('.openNow .jumped').click(function(){
			$('.open').hide();
			$('.regsiterSuccess').show();
			$('.regsiterStep li').removeClass('bg').addClass('bg1');
			var num = 3;
			setInterval(function(){
				num--;
				$('.regsiterSuccess .threeMin strong').html(num);
				if(num<=0){
					window.location.href = '../index.html';
				};
			},1000);
		});
		$("input[name='cardNum']").focus(function(){
			bankCode = false;
			$(this).siblings('.msg').css('display','none');
			$(this).siblings('.msgInfo').css('display','none');
		});
		$("input[name='cardNum']").blur(function(){
			if($(this).val()){
				var bankObj = distinctionBankCard( $(this).val() );
		    	if( !bankObj || !bankObj.bank || $(".bank .selectedBank").html() != bankObj.bank ){
		    		$(this).siblings('.msgInfo').html('您填写的卡号与发卡银行不一致，请重新选择发卡银行').css('display','inline-block');
		    		return;
		    	} else {
		    		bankCode = true
		    	};
			}
		});
		getAllBank(function(obj){
			var html = '';
			for (var i = 0, len = obj.banks.length; i < len; i++) {
				html += '<li code="'+obj.banks[i].bankCode+'">'+obj.banks[i].bankShortName+'</li>'
			};
			$(".bank .bankList").html(html);
			$(".bank .selectedBank").attr("forSort", obj.banks[0].bankCode).html(obj.banks[0].bankShortName);
		});
		function getAllBank (fn) {
			$.ajax({
				url: projectUrl + '/front/util/searchAllBanks',  //获取银行列表
				type: 'post',  //请求方式  get||post
				data: {},
				success:function(str){
					if (str.message.code === 1000) {
						fn && fn(str);
					} else {
						alert(str.message.message)
					}
				}
			});
		};
	},
	goBack:function(){  
	    if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){ // IE  
	        if(history.length > 0){  
	            window.history.go( -1 );  
	        }else{  
	            window.location.href = '../index.html'  
	        }  
	    }else{ //非IE浏览器  
	        if (navigator.userAgent.indexOf('Firefox') >= 0 ||  
	            navigator.userAgent.indexOf('Opera') >= 0 ||  
	            navigator.userAgent.indexOf('Safari') >= 0 ||  
	            navigator.userAgent.indexOf('Chrome') >= 0 ||  
	            navigator.userAgent.indexOf('WebKit') >= 0){  
	  
	            if(window.history.length > 1){  
	                window.history.go( -1 );  
	            }else{  
	                window.location.href = '../index.html' 
	            }  
	        }else{ //未知的浏览器  
	            window.history.go( -1 );  
	        }  
	    }  
	},
	/* 我的账户 */
	myAccount:function(){
		zycfZ.userName(zycfZ.myAccountLoginStatus,"myAccount.html","zyfyLogin.html");
		$('.userFrame .user span').html("");
		$('.myAccountInfo .prevLogin').html("");
		$.ajax({
            type:"POST",
            url: projectUrl + "/front/userAccount/userAccountInfo",
            dataType:"json",
            success:function(str){
            	var str = str.userAccountVo
				$('.myAccountInfo .money strong').html(str.availableAmount?str.availableAmount:0);   //现金余额
				$('.myAccountInfo .allAssets strong').html(str.allCapital?str.allCapital:0);   //总资产
				$('.myAccountInfo .allProfit strong').html(str.allRevue?str.allRevue:0);   //总收益
				$('.myAccountInfo .toProfit span').html(str.undueAmountInterest?str.undueAmountInterest:0);   //待收收益
				$('.myAccountInfo .toPrincipal span').html(str.undueAmountCapital?str.undueAmountCapital:0);   //待收本金
				$('.myAccountInfo .frozen span').html(str.frozenAmount?str.frozenAmount:0);   //冻结金额
				//$('.myAccountInfo .testMoney span').html(str.freshAmount?str.freshAmount:0);   //冻结金额
			}
        });
        $.ajax({
            type:"POST",
            url: projectUrl + "/front/userAccount/getUserSetting",
            dataType:"json",
            success:function(str){
            	var message = str.message;
            	if(message.code == 2002){
            		window.location.href = "zyfyLogin.html";
            	};
            	if (message.code !== 1000) {
            		return;
            	};
            	var str = str.userInfo;
            	$('.userFrame .user span').html("您好，" + (str.loginName?str.loginName:str.mobile) + "！");
				if(!str.platcust){
					$(".userFrame .one").removeClass("active").find("a").html("未认证");
					$(".personalSetting .idauthenticated .state").removeClass("yes").html("未认证");
					$(".personalSetting .idauthenticated .text").html("");
					$(".personalSetting .idauthenticated .identityBtn").show();
					$(".personalSetting .bankBtn").css("background-color","#a5a5a5").attr("title","请先实名认证");
				}else{
					$(".userFrame .one").addClass("active").find("a").html("已认证");
					$(".personalSetting .idauthenticated .state").addClass("yes").html("已认证");
					$(".personalSetting .idauthenticated .text").html(str.userName+"("+str.idCode+")");
					$(".personalSetting .idauthenticated .identityBtn").hide();
				};
				/*开通2类账户*/
				/*if(!str.platcust || str.open_ii_account) {
					$(".open2 .openBtn").hide();
					if (!str.platcust) {
						$(".open2 .text").html("请先实名认证");
					} else {
						$(".open2 .text").html("已开通");
					}
				};*/
				$(".open2 .openBtn").click(function () {
					var This = this;
					if (This.btn) {
						return;
					}
					This.btn = true;
					$.ajax({
			            type:"POST",
			            url: projectUrl + "/front/bmAccount/openIIAccountPc",
			            dataType:"json",
			            success:function(str){
			            	This.btn = false;
			            	if(str.message.code == 1000){
								$(".open2_form").html(str.rep);
								var floatWindow = new PopUpBox();
						        var content = "<p style='text-align:center;font-size: 14px;'>开通Ⅱ类账户</p>";
								floatWindow.init({
									w: 220,
									h: 100,
									iNow:0,
									tBar:false,  
									time:null,  
									content:content,
									btnAlign: "center",
									callback: function () {
										$(".open2_form form").submit();
									}
								});
			            	} else {
			            		var floatWindow = new PopUpBox();
						        var content = "<p style='text-align:center'>"+str.message.message+"</p>";
								floatWindow.init({
									w: 220,
									h: 60,
									iNow:0,          // 确保一个对象只创建一次
									tBar:false,  
									time:1500,  
									content:content,     // 内容
									makesure:false,
									cancel:false
								});
			            	};
						},
						error: function () {
							This.btn = false;
						}
			        });
				});
				$(".personalSetting .identityBtn").click(function(){
					if (str.custType == '1') {
						window.location.href = '../html/account/identity.html'
					} else if (str.custType == '2') {
						window.location.href = '../html/account/companyIdentity.html'
					}
				});
				if (str.idCode && !str.cardNo) {
					$(".personalSetting .bankBtn").css("background-color","#ff6f0a").attr("title","").click(function(){
						if (str.custType == '1') {
							window.location.href = '../html/account/identity.html'
						} else if (str.custType == '2') {
							window.location.href = '../html/account/companyCord.html'
						}
					});
				} else {
					//$(".personalSetting .bankBtn").css("background-color","#a5a5a5")
				};
				if(!str.mobile){
					$(".userFrame .tow").removeClass("active").find("a").html("未绑定");
					$(".personalSetting .mobileBar .state").removeClass("yes").html("未绑定");
					$(".personalSetting .mobileBar .text").html("");
				}else{
					$(".userFrame .tow").addClass("active").find("a").html("已绑定");
					$(".personalSetting .mobileBar .state").addClass("yes").html("已绑定");
					$(".personalSetting .mobileBar .text").html(str.mobile);
					$(".personalSetting .mobileBar .mobileBtn").val("修改");
				};
				if(!str.cardNo){
					$(".userFrame .three").removeClass("active").find("a").html("未绑定");
					//$(".personalSetting .bankCard .state").removeClass("yes").html("未绑定");
					//$(".personalSetting .bankCard .text").html("");
					$(".personalSetting .bankCard .bankNo").html("请绑定银行卡");
					$('.withdrawalsBtn').css("background-color","#a5a5a5").attr("title","请先绑定银行账户");
					$(".agreementBtn").css("background-color","#a5a5a5").attr("title","请先绑定银行账户");
					//$('.rechargeBtn').css("background-color","#a5a5a5").attr("title","请先绑定银行账户");
				}else{
					$(".userFrame .three").addClass("active").find("a").html("已绑定");
					//$(".personalSetting .bankCard .state").addClass("yes").html("已绑定");
					//$(".personalSetting .bankCard .text").html(str.bankEnName+"("+str.cardNo+")"); 
					$(".personalSetting .bankCard .bankName").html(str.bankEnName);
					$(".personalSetting .bankCard .bankNo").html(str.cardNo);
					$('.withdrawalsBtn').css("background-color","#289dff").attr("title","");
					$('.withdrawalsBtn').click(function(){
						window.open('../html/account/withdrawals.html')
					}); 
					$(".agreementBtn").click(function(){
				    	window.location.href = "account/agreement.html";
				    });
				    if (str.custType == '1') {
				    	$(".personalSetting .bankBtn").css("background-color","#ff6f0a").val("解绑").click(function () {
					    	$.ajax({
					            type:"POST",
					            url: projectUrl + "/front/bmAccount/relieveCard",
					            data:{},
					            dataType:"json",
					            success:function(str){
					            	alert(str.message.message);
					            	if(str.message.code == 1000){
					            		window.location.reload();
					            	};
								}
					        });
					    });  //解绑银行卡
				    } else if (str.custType == '2') {
				    	$(".personalSetting .bankBtn").hide();
				    }
				};
				/* 充值 */
				if (!str.cardNo && str.userType == 1) {
					$('.rechargeBtn').css("background-color","#a5a5a5").attr("title","请先绑定银行账户");
				} else {
					$('.rechargeBtn').css("background-color","#ff6f0a").attr("title","").click(function(){
						window.open('../html/account/recharge.html?'+encodeURI(str.userName))
					});
				};
				if(!str.loginName || str.loginName == ""){
					$(".personalSetting .userBar .state").removeClass("yes").html("未设置");
					$(".personalSetting .userBar .text").val("");
					$(".personalSetting .userBar .userBtn").show();
					$(".personalSetting .userBar .text").attr("disabled",false).css({"border":"1px solid #dcdcdc"});
					$(".personalSetting .userBar .userBtn").click(function(){
						if($(".personalSetting .userBar .text").val() != "" && checkUserName($(".personalSetting .userBar .text").val())){
							$.ajax({
					            type:"POST",
					            url: projectUrl + "/front/user/modifyLoginName",
					            data:{"loginName":$(".personalSetting .userBar .text").val()},
					            dataType:"json",
					            success:function(str){
					            	alert(str.message.message);
					            	if(str.message.code == 1000){
					            		window.location.reload();
					            	};
								}
					        });
						}else if($(".personalSetting .userBar .text").val() == ""){
							alert("请输入用户名");
						}else if($(".personalSetting .userBar .text").val().length<2 || $(".personalSetting .userBar .text").val().length>30){
							alert("用户名长度不在2-30字符之间");
						}else if(!illegalCharactors($(".personalSetting .userBar .text").val())){
							alert("用户名不正确，只能包含字母、数字或下划线,以字母或下划线开头");
						}else if(!isNaN($(".personalSetting .userBar .text").val().charAt(0))){
							alert("用户名不能以数字开头,只能包含字母或下划线开头");
						};
					});
				}else{
					$(".personalSetting .userBar .state").addClass("yes").html("已设置");
					$(".personalSetting .userBar .text").val(str.loginname);
					$(".personalSetting .userBar .text").attr("disabled","disabled").css({"background-color":"#fff","border":"none"});
					$(".personalSetting .userBar .userBtn").hide();
				};
				$(".InviteRebateBar #copyInfo").val("https://www.zuoyoufy.com/html/regsiter.html?ref="+str.mobile);    //推广地址
				$(".personalSetting .referralId .text").html(str.refMobile?str.refMobile:"");   // 推荐人
				window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdDesc":"","bdUrl":"https://www.zuoyoufy.com/html/regsiter.html?ref="+str.mobile,"bdSign":"off","bdMini":"2","bdMiniList":false,"bdSize":"16"},"share":{},"bdStyle":"0"};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='https://www.zuoyoufy.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
			}
        });
		$(".servicesTab li").click(function(){
			$(this).addClass("active").siblings().removeClass("active");
			myAward();
		});
		$(".beUsed li").click(function(){
			$(this).addClass("active").siblings().removeClass("active");
			myAward();
		});
		function myAward(){
			if($(".servicesTab li").eq(0).hasClass("active") && $(".beUsed li").eq(0).hasClass("active")){
				$(".testBidWrap ul").hide().eq(0).show();
				$(".redPacketsWrap ul").hide();
			}else if($(".servicesTab li").eq(0).hasClass("active") && $(".beUsed li").eq(1).hasClass("active")){
				$(".testBidWrap ul").hide().eq(1).show();
				$(".redPacketsWrap ul").hide();
			}else if($(".servicesTab li").eq(1).hasClass("active") && $(".beUsed li").eq(0).hasClass("active")){
				$(".testBidWrap ul").hide();
				$(".redPacketsWrap ul").hide().eq(0).show();
			}else if($(".servicesTab li").eq(1).hasClass("active") && $(".beUsed li").eq(1).hasClass("active")){
				$(".testBidWrap ul").hide();
				$(".redPacketsWrap ul").hide().eq(1).show();
			}
		};
		$(".servicesBar .ruleDescription").click(function(){  //红包和体验标使用规则显示隐藏
			$(".servicesBar .ruleDetails").show();
		});
		$(".ruleDetails .close").click(function(){
			$(".servicesBar .ruleDetails").hide();
		});
		accountListAjax({     // 账户总览里的最近投资项目
			page:1,
		    num:5,
		    params:{
				loanStatus: ''
			},
		    url: projectUrl + "/front/userAccount/userInvestLoanList",
		    callBack:function(obj){investLoanZ(obj.userInvestLoanVo)}
		});
		function investLoanZ(obj){    // 账户总览里的最近投资项目
        	$(".lately tbody").html("");
			if(!obj.results.length || obj.results.length<1){
	        	$(".lately tbody").html("<td colspan='10'>暂无数据</td>");
	        }else{
	        	for(var i=0;i<obj.results.length;i++){
	        		var oTr = $("<tr></tr>");
		        	var timeSettled;
		        	if(obj.results[i].strTimeSettled){
		        		timeSettled = obj.results[i].strTimeSettled;
		        	}else{
		        		timeSettled = "";
		        	};
			    	oTr.html("<td>"+obj.results[i].title+"</td><td>"+obj.results[i].rate+(obj.results[i].addRate?"+"+obj.results[i].addRate:"")+"</td><td>"+obj.results[i].months+"</td><td>"+obj.results[i].amount+"</td><td>"+obj.results[i].dueInterest+"</td><td>"+obj.results[i].repayMethod+"</td><td>"+obj.results[i].investTime+"</td><td>"+timeSettled+"</td><td>"+obj.results[i].loanStatus+"</td>");
			    	if(obj.results[i].del){
			    		var oTd = $("<td><a href='/front/userAccount/downLoadContract?investId="+obj.results[i].investId+"' target='_blank' >下载合同</a></td>");
			    	}else{
			    		var oTd = $("<td>未生成</td>");
			    	};
			    	oTd.appendTo(oTr);
					oTr.appendTo($(".lately tbody"));
	        	};
	        };
        };
        accountListAjax({      // 账户总览里的资金记录
			page:1,
		    num:5,
			params:{
				type: ''
			},
		    url: projectUrl + "/front/userAccount/userFundRecordList",
		    callBack:function(obj){fundRecordZ(obj.pageRecords)}
		});
		function fundRecordZ(obj){    // 账户总览里的资金记录
        	$(".fundRecord tbody").html("");
			if(!obj.results || obj.results.length<1){
	        	$(".fundRecord tbody").html("<td colspan='6'>暂无数据</td>");
	        }else{
	        	for(var i=0; i<obj.results.length; i++){
	        		var oTr = $("<tr></tr>");
		        	var description,name,amount;
		        	amount = parseFloat(obj.results[i].amount).toFixed(2);
		        	if(obj.results[i].operation == "OUT"){
		        		amount = "-"+amount;
		        	};
		        	if(obj.results[i].description){
		        		description = obj.results[i].description;
		        	}else{
		        		description = "";
		        	};
		        	if(obj.results[i].status == "成功"){
		        		name = 'success';
		        	}else{
		        		name = "";
		        	};
			    	oTr.html("<td>"+obj.results[i].orderid+"</td><td>"+obj.results[i].type+"</td><td>"+amount+"</td><td>"+obj.results[i].strTimeRecorded+"</td><td><span class="+name+">"+obj.results[i].status+"</span></td><td>"+description+"</td>");
					oTr.appendTo($(".fundRecord tbody"));
	        	};
	        };
        };
		/* 左侧导航 */
		loadAccountData();
		$('.rechargeMain .sidebarNav li').eq(0).addClass('active');
		$('.accountContent .Tabl').eq(0).show();
		$('.rechargeMain .sidebarNav li').click(function(){
			$('.rechargeMain .sidebarNav li').removeClass('active');
			$(this).addClass('active');
			$('.accountContent .Tabl').hide().eq($(this).index()).show();
			loadAccountData();
		});
        $("#latelyMore").click(function(){   //更多
        	$('.rechargeMain .sidebarNav li').removeClass('active').eq(2).addClass('active');
			$('.accountContent .Tabl').hide().eq(2).show();
			loadAccountData();
        });
        $("#fundRecordMore").click(function(){   //更多
        	$('.rechargeMain .sidebarNav li').removeClass('active').eq(3).addClass('active');
			$('.accountContent .Tabl').hide().eq(3).show();
			loadAccountData();
        });
        function loadAccountData(){
        	var hash = window.location.hash.substring(1);
        	if($(".investmentPro").css("display") == "block" || hash == "2"){                    // 已投项目
				$(".investmentPro .proQuery .left span").removeClass("on").eq(0).addClass("on");
				$("#proWrapInpS").val("");
				$("#proWrapInpE").val("");
				accountListAjax({
					page:1,
				    num:10,
				    params:{
						loanStatus: ''
					},
				    id:"proWrapPaging",
				    url: projectUrl + "/front/userAccount/userInvestLoanList",
				    callBack:function(obj){investLoan(obj.userInvestLoanVo)}
				});
			};
			if($(".backPlanBar").css("display") == "block" || hash == "5"){         // 回款计划
				$(".backPlanBar .repayment span").removeClass("on").eq(0).addClass("on");
				$("#dataStar").val("");
				$("#dataEnd").val("");
				accountListAjax({
					page:1,
				    num:10,
				    id:"backPlanPaging",
				    params:{
				    	"repayStatus":"UNDUE','OVERDUE",
				    	"status":"UN"
				    },
				    url: projectUrl + "/front/userAccount/userInvestRepaymentList",
				    callBack:function(obj){investRecord(obj.userInvestRecordVo)}
				});
			};
			if($(".fundRecordBar").css("display") == "block" || hash == "3"){           // 资金记录
				$(".fundRecordBar .left span").html("全部资金类型").attr("code","");
				$("#fundRecorS").val("");
				$("#fundRecorE").val("");
				accountListAjax({
					page:1,
				    num:10,
					params:{
						type: ''
					},
					id: "fundRecordPaging",
				    url: projectUrl + "/front/userAccount/userFundRecordList",
				    callBack:function(obj){fundRecord(obj.pageRecords)}
				});
			};
			if($(".InviteRebateBar").css("display") == "block" || hash == "6"){           // 邀请返利
		        accountListAjax({
					page:1,
				    num:10,
				    id:"InviteRebatePaging",
				    url: projectUrl + "/front/userAccount/queryInviterInvest",
				    callBack:function(obj){
				    	$("#InviteRebatePaging").html("");
				    	$(".InviteInvestment tbody").html("");
				    	var data = obj.pageInvest.results;
		                if(!data || data.length<1){
		                	$(".InviteInvestment tbody").html("<td colspan='4'>暂无数据</td>");
		                }else{
		                	for(var i=0;i<data.length;i++){
			                	var oTr = $("<tr></tr>");
			                	oTr.html("<td>"+data[i].userName+"</td><td>"+(data[i].loanTitle?data[i].loanTitle:"无")+"</td><td>"+(data[i].amount?data[i].amount:0)+"</td><td>"+(data[i].strSubmitTime?data[i].strSubmitTime:"无")+"</td>");
								oTr.appendTo($(".InviteInvestment tbody"));
			                }
		                }
				    }
				});
			};
        };
        $(".invitedList li").click(function(){
        	$(this).addClass("active").siblings().removeClass("active");
        	if($(this).index() == 0){
        		$(".InviteInvestment").show();
        		$(".InvestmentList").hide();
        		accountListAjax({
					page:1,
				    num:10,
				    id:"InviteRebatePaging",
				    url: projectUrl + "/front/userAccount/queryInviterInvest",
				    callBack:function(obj){
				    	$("#InviteRebatePaging").html("");
				    	$(".InviteInvestment tbody").html("");
				    	var data = obj.pageInvest.results;
		                if(!data || data.length<1){
		                	$(".InviteInvestment tbody").html("<td colspan='4'>暂无数据</td>");
		                }else{
		                	for(var i=0;i<data.length;i++){
			                	var oTr = $("<tr></tr>");
			                	oTr.html("<td>"+data[i].userName+"</td><td>"+(data[i].loanTitle?data[i].loanTitle:"无")+"</td><td>"+(data[i].amount?data[i].amount:0)+"</td><td>"+(data[i].strSubmitTime?data[i].strSubmitTime:"无")+"</td>");
								oTr.appendTo($(".InviteInvestment tbody"));
			                }
		                }
				    }
				});
        	}else if($(this).index() == 1){
        		$(".InviteInvestment").hide();
        		$(".InvestmentList").show();
        		accountListAjax({
					page:1,
				    num:10,
				    id:"InviteRebatePaging",
				    url: projectUrl + "/front/userAccount/queryInviterList",
				    callBack:function(obj){
				    	$("#InviteRebatePaging").html("");
				    	$(".InvestmentList tbody").html("");
				    	var data = obj.pageUser.results;
		                if(!data || data.length<1){
		                	$(".InvestmentList tbody").html("<td colspan='4'>暂无数据</td>");
		                }else{
		                	for(var i=0;i<data.length;i++){
			                	var oTr = $("<tr></tr>");
			                	oTr.html("<td>"+data[i].mobile+"</td><td>"+(data[i].userName?data[i].userName:"")+"</td><td>"+(data[i].strRegisterDate?data[i].strRegisterDate:"")+"</td><td>"+(data[i].strLastLoginDate?data[i].strLastLoginDate:"")+"</td>");
								oTr.appendTo($(".InvestmentList tbody"));
			                }
		                }
				    }
				});
        	};
        });
		$(".backPlanBar .repayment span").click(function(){   // 回款计划查询
			var status = "",sta="";
			$(".backPlanBar .repayment span").removeClass("on");
			$(this).addClass("on");
			if($(this).html() == "未还"){
				status = "UNDUE','OVERDUE";
				sta = "UN";
			}else if($(this).html() == "已还"){
				status = "PREPAY','REPAYED";
				sta = "PR";
			};
			accountListAjax({
				page:1,
			    num:10,
			    id:"backPlanPaging",
			    params:{
			    	"repayStatus":status,
			    	"status":sta
			    },
			    start: $("#dataStar").val(),
			    end: $("#dataEnd").val(),
			    url: projectUrl + "/front/userAccount/userInvestRepaymentList",
			    callBack:function(obj){investRecord(obj.userInvestRecordVo)}
			});
		});
		$(".investmentPro .proQuery .left span").click(function(){
			var status = "";
			$(".investmentPro .proQuery .left span").removeClass("active");
			$(this).addClass("active");
			if($(this).html() == "全部"){
				status = "";
			}else if($(this).html() == "未起息"){
				status = "'FINISHED','OPENED'";
			}else if($(this).html() == "回款中"){
				status = "'SETTLED'";
			}else if($(this).html() == "已回款"){
				status = "'CLEARED'";
			};
			accountListAjax({
				page:1,
			    num:10,
			    params:{
			    	"loanStatus":status
			    },
			    id:"proWrapPaging",
			    start: $("#proWrapInpS").val(),
				end: $("#proWrapInpE").val(),
			    url: projectUrl + "/front/userAccount/userInvestLoanList",
				callBack:function(obj){investLoan(obj.userInvestLoanVo)}
			});
		});
		$(".investmentPro .proQuery .btn").click(function(){
			var status = "";
			if($(".investmentPro .proQuery .left span").eq(0).hasClass("active")){
				status = ""
			}else if($(".investmentPro .proQuery .left span").eq(1).hasClass("active")){
				status = "FINISHED"
			}else if($(".investmentPro .proQuery .left span").eq(2).hasClass("active")){
				status = "SETTLED"
			}else if($(".investmentPro .proQuery .left span").eq(3).hasClass("active")){
				status = "CLEARED"
			}
			accountListAjax({
				page:1,
			    num:10,
			    params:{
			    	"loanStatus":status
			    },
		    	start: $("#proWrapInpS").val(),
				end: $("#proWrapInpE").val(),
			    id:"proWrapPaging",
			    url: projectUrl + "/front/userAccount/userInvestLoanList",
				callBack:function(obj){investLoan(obj.userInvestLoanVo)}
			});
		});
		$(".backPlanBar .export").click(function(){
			var status = "";
			if($(".backPlanBar .repayment span").eq(0).hasClass("on")){
				status = "UNDUE','OVERDUE";
				sta = "UN";
			}else if($(".backPlanBar .repayment span").eq(1).hasClass("on")){
				status = "PREPAY','REPAYED";
				sta = "PR";
			}else{
				status = ""
			}
			accountListAjax({
				page:1,
			    num:10,
			    id:"backPlanPaging",
			    params:{
			    	"repayStatus":status,
			    	"status":sta
			    },
			    start: $("#dataStar").val(),
			    end: $("#dataEnd").val(),
			    url: projectUrl + "/front/userAccount/userInvestRepaymentList",
			    callBack:function(obj){investRecord(obj.userInvestRecordVo)}
			});
		});
		$(".fundRecordBar .export").click(function(){
			accountListAjax({
				page:1,
			    num:10,
				params:{
					type: $(".fundRecordBar .left span").attr("code")
				},
				start: $("#fundRecorS").val(),
				end: $("#fundRecorE").val(),
				id: "fundRecordPaging",
			    url: projectUrl + "/front/userAccount/userFundRecordList",
			    callBack:function(obj){fundRecord(obj.pageRecords)}
			});
		});
        function accountListAjax(opt){
        	var settings = {
				page:1,
				num:10,
				start: '',
				end: '',
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
				    "params":settings.params,
				    "strStart": settings.start,
				    "strEnd": settings.end
				    },
	            dataType:"json",
	            success:function(str){
	            	settings.callBack(str);
	            	if (str.message.code === 2002) {
	            		window.location.href = 'zyfyLogin.html';
	            	};
	            	if(str.totalPage > 1){
				    	page({
							id:settings.id,
							nowNum:settings.page,
							allNum: str.totalPage,
							callBack:function(now,all){
								accountListAjax({
									page:now,
								    num:10,
								    params:settings.params,
								    strStart: settings.start,
			    					strEnd: settings.end,
								    id:settings.id,
								    url:settings.url,
				                    callBack:settings.callBack
								});
							}
						});
				    };
				}
	        });
        };
        function investLoan(obj){    // 已投项目
        	$(".proWrap tbody").html("");
			$("#proWrapPaging").html("");
			if(!obj.results.length || obj.results.length<1){
	        	$(".proWrap tbody").html("<td colspan='10'>暂无数据</td>");
	        }else{
	        	for(var i=0;i<obj.results.length;i++){
	        		var oTr = $("<tr></tr>");
		        	var timeSettled;
		        	if(obj.results[i].strTimeSettled){
		        		timeSettled = obj.results[i].strTimeSettled;
		        	}else{
		        		timeSettled = "";
		        	};
			    	oTr.html("<td>"+obj.results[i].title+"</td><td>"+obj.results[i].rate+(obj.results[i].addRate?"+"+obj.results[i].addRate:"")+"</td><td>"+obj.results[i].months+"</td><td>"+obj.results[i].amount+"</td><td>"+obj.results[i].dueInterest+"</td><td>"+obj.results[i].repayMethod+"</td><td>"+obj.results[i].investTime+"</td><td>"+timeSettled+"</td><td>"+obj.results[i].loanStatus+"</td>");
			    	if(obj.results[i].del){
			    		var oTd = $("<td><a href='/front/userAccount/downLoadContract?investId="+obj.results[i].investId+"' target='_blank' >下载合同</a></td>");
			    	}else{
			    		var oTd = $("<td>未生成</td>");
			    	};
			    	oTd.appendTo(oTr);
					oTr.appendTo($(".investmentPro .proWrap tbody"));
	        	};
	        };
        };
        function investRecord(obj){    // 回款计划
        	$(".backPlanBar tbody").html("");
			$("#backPlanPaging").html(""); 
			if(!obj.results.length || obj.results.length<1){
	        	$(".backPlanBar tbody").html("<td colspan='6'>暂无数据</td>");
	        }else{
	        	for(var i=0;i<obj.results.length;i++){
	        		var oTr = $("<tr></tr>");
			    	var repayDate,currentPeriod,amount,status;
			    	if(obj.results[i].repayDate){
			    		repayDate = new Date(parseInt(obj.results[i].repayDate,10)).toLocaleDateString();
			    	}else{
			    		repayDate = "";
			    	};
			    	if(obj.results[i].amountPrincipal != "0"){
			    		currentPeriod = "本金+利息";
			    	}else{
			    		currentPeriod = "利息";
			    	};
			    	amount = parseFloat(obj.results[i].amountInterest)+parseFloat(obj.results[i].amountPrincipal);
			    	oTr.html("<td>"+obj.results[i].dueDate+"</td><td>"+amount+"</td><td>"+currentPeriod+"</td><td title='"+obj.results[i].title+"--"+obj.results[i].currentPeriod+"期"+"'>"+obj.results[i].title+"--"+obj.results[i].currentPeriod+"期"+"</td><td>"+repayDate+"</td><td>"+obj.results[i].repayStatus+"</td>");
					oTr.appendTo($(".backPlanBar tbody"));
	        	};
	        };
        };
        function fundRecord(obj){    // 资金记录
        	$(".fundRecordBar tbody").html("");
			$("#fundRecordPaging").html("");
			if(!obj.results.length || obj.results.length<1){
	        	$(".fundRecordBar tbody").html("<td colspan='6'>暂无数据</td>");
	        }else{
	        	for(var i=0;i<obj.results.length;i++){
	        		var oTr = $("<tr></tr>");
		        	var description,name,amount;
		        	amount = parseFloat(obj.results[i].amount).toFixed(2);
		        	if(obj.results[i].operation == "OUT"){
		        		amount = "-"+amount;
		        	};
		        	if(obj.results[i].description){
		        		description = obj.results[i].description;
		        	}else{
		        		description = "";
		        	};
		        	if(obj.results[i].status == "成功"){
		        		name = 'success';
		        	}else{
		        		name = "";
		        	};
			    	oTr.html("<td>"+obj.results[i].orderid+"</td><td>"+obj.results[i].type+"</td><td>"+amount+"</td><td>"+obj.results[i].strTimeRecorded+"</td><td><span class="+name+">"+obj.results[i].status+"</span></td><td>"+description+"</td>");
					oTr.appendTo($(".fundRecordBar tbody"));
	        	};
	        };
        };
		$(".messageBar .messagelist p:even").css('background','#edf7ff');
		/* 右边Tab */
		$('.investmentPro .frameTab li').click(function(){
			$('.investmentPro .frameTab li').removeClass('active');
			$(this).addClass('active');
			switch ($(this).index()){
				case 0:
					$('.investmentPro .proWrap').show();
					$('.investmentPro .assignment').hide();
				break;
				case 1:
					$('.investmentPro .proWrap').hide();
					$('.investmentPro .assignment').show();
				break;
			};
		});
		/*$('.messageBar .frameTab li').click(function(){
			$('.messageBar .frameTab li').removeClass('active');
			$(this).addClass('active');
			switch ($(this).index()){
				case 0:
					$('.messageBar .messagewrap').show();
					$('.messageBar .messageNotice').hide();
				break;
				case 1:
					$('.messageBar .messagewrap').hide();
					$('.messageBar .messageNotice').show();
				break;
			};
		});*/
		/* 修改 */
		$(".personalSetting .passwordBtn").click(function(){
			window.location.href = '../html/account/modifyPassword.html'
		});
		$(".personalSetting .mobileBtn").click(function(){
			window.location.href = '../html/account/mobileMumber.html'
		});
		$(".personalSetting .emailBtn").click(function(){
			window.location.href = '../html/account/email.html'
		});
	    
	    /* 资金记录 下拉列表 */
	    $('.fundRecordBar .function .left span').click(function(){
	    	$('.fundRecordBar .function ul').toggle();
	    });
	    $('.fundRecordBar .function li').click(function(){
	    	$('.fundRecordBar .function .left span').html($(this).html());
	    	$('.fundRecordBar .function .left span').attr("code",$(this).attr("code"));
	    	$('.fundRecordBar .function ul').hide();
	    });
	    // 复制链接
	    $('.InviteRebateBar .copyBtn').click(function(){
	    	zycfZ.copy();
	    });
	},
	calendarRecord:function(){
		var myDate = new Date();
		$(".calendar .year").html( myDate.getFullYear() );
		$(".calendar .month").html( myDate.getMonth()+1 );

		var month = $(".calendar .month").html(),year = $(".calendar .year").html();
		creatDateList(year,month);
		$(".calendar .prev").click(function(){
			month--;
			if(month<=0){
				month=12;
				year--;
				$(".calendar .year").html(year);
			};
			$(".calendar .month").html(month);
			creatDateList(year,month);
		});
		$(".calendar .next").click(function(){
			month++;
			if(month>12){
				month = 1;
				year++;
				$(".calendar .year").html(year);
			};
			$(".calendar .month").html(month);
			creatDateList(year,month);
		});
		function creatDateList(Year,Month){
			$(".calendar td").removeClass("active today");
			$(".calendar td span").html("");
			$(".calendar td em").remove();
			$(".weekList li").removeClass("active");
			var monthDay = DayNumOfMonth(Year,Month);
			var oneDay = getDateMonth(Year,Month);
			for(var i=0,len=$(".calendar tbody tr").size();i<len;i++){
				if(i>=Math.ceil( (monthDay+oneDay)/7 )){
					$(".calendar tbody tr").eq(i).css("display","none");
				}else{
					$(".calendar tbody tr").eq(i).css("display","block");
				};
			};
			for(var i=oneDay,len=(monthDay+oneDay);i<len;i++){
				$(".calendar td span").eq(i).html(i-oneDay+1);
			};
			if( myDate.getFullYear() == $(".calendar .year").html() && (myDate.getMonth()+1) == $(".calendar .month").html() ){
				$(".calendar td span").each(function(){
					if( myDate.getDate() == $(this).html() ){
						$(this).parent().addClass("today");
						var em = $("<em>今</em>");
						em.appendTo($(this).parent());
						$(".weekList li").eq(myDate.getDay()).addClass("active");
					};
				});
			};
			getCalendarDate(Year,Month);
		};
		function DayNumOfMonth(Year,Month){
		    Month--;
		    var d = new Date(Year,Month,1);
		    d.setDate(d.getDate()+32-d.getDate());
		    return (32-d.getDate());
		};
		function getDateMonth(Year,Month){
			var d = new Date(Year,(Month-1),1);
			return d.getDay();
		};
		function getCalendarDate(Year,Month){
			$.ajax({
				type:"POST",
				url:  projectUrl + "/front/userAccount/userFundRecordCalendar",
				data:{
					"newData": Year+"-"+((Month<10)?"0"+Month:Month)
				},
				success:function(str){
					$(".calendar td").each(function(){
						$(this).removeClass().unbind("mouseenter").unbind("mouseleave");
					});
					var str = str.fundRecordCalenderVo;
					if(str.length && str.length>0){
						$(".calendar td span").each(function(){
							for (var j = 0,len=str.length; j < len; j++) {
								if( (($(this).html()<10)?"0"+$(this).html():$(this).html()) == str[j].dates && !$(this).parent().hasClass("active") ){
									$(this).parent().addClass("active");
								}
							};
						});
					};
					$(".calendar td span").each(function(){
						if($(this).parent().hasClass("active")){
							$(this).parent().hover(function(){
								$(".calendarRecord .recordList").html("");
								$(".calendarRecord h3").html(Year+"年"+Month+"月"+$(this).find("span").html()+"日");
								for (var j = 0,len=str.length; j < len; j++) {
									var type;
									if(str[j].type == "还款"){
										type = "repay"
									}else if(str[j].type == "投资"){
										type = "invest"
									}
									else if(str[j].type == "回款"){
										type = "pay"
									}
									else if(str[j].type == "提现"){
										type = "withdrawals"
									}
									else if(str[j].type == "充值"){
										type = "recharge"
									}
									else if(str[j].type == "放款"){
										type = "loan"
									};
									if( (($(this).find("span").html()<10)?"0"+$(this).find("span").html():$(this).find("span").html()) == str[j].dates){
										if(type == "pay"){
											var p = $('<p class="'+type+'"><em></em><span class="time">'+str[j].dudate+'</span><span>回款本金：'+str[j].amountPrincipal+'</span><span>回款利息：'+str[j].amountInterest+'</span><span>回款总计：'+(parseFloat(str[j].amountPrincipal)+parseFloat(str[j].amountInterest))+'</span></p>');
										}else{
											var p = $('<p class="'+type+'"><em></em><span class="time">'+str[j].timeRecordeds+'</span><span>'+str[j].type+"："+str[j].amount+'</span><span>状态：'+str[j].statuss+'</span></p>');
										}
										p.appendTo($(".calendarRecord .recordList"));
									}
								};
								$(".calendarRecord").show().css("margin-left","-"+$(".calendarRecord").outerWidth()/2);
							},function(){
								$(".calendarRecord").hide();
							});
						};
					});
					$(".calendarRecord").hover(function(){
						$(this).show();
					},function(){
						$(this).hide();
					});
				}
			});
		};
	},
	modifyPassword:function(){    // 修改密码
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		$(".passwordFrame .oldPassWord input").focus(function(){
			$(".passwordFrame .oldPassWord .msg").css("display","none");
			$(".passwordFrame .oldPassWord .msgInfo").css("display","none");
		});
		$(".passwordFrame .newPssWord input").focus(function(){
			if($(this).val() == "6~16位数字与字母组合"){
				$(this).val("");
			};
			$(".passwordFrame .newPssWord .msg").css("display","none");
			$(".passwordFrame .newPssWord .msgInfo").css("display","none");
		});
		$(".passwordFrame .newPssWord input").blur(function(){
			if($(this).val() == ""){
				$(".passwordFrame .newPssWord .msgInfo").css("display","inline-block");
			};
			if(testNum($(this).val())){
				$(".passwordFrame .newPssWord .msg").css("display","inline-block");
			}else{
				$(".passwordFrame .newPssWord .msgInfo").css("display","inline-block").html("6~16位数字与字母组合");
			}
		});
		$(".passwordFrame .newPssWord2 input").focus(function(){
			if($(this).val() == "与新密码保持一致"){
				$(this).val("");
			};
			$(".passwordFrame .newPssWord2 .msg").css("display","none");
			$(".passwordFrame .newPssWord2 .msgInfo").css("display","none");
		});
		$(".passwordFrame .newPssWord2 input").blur(function(){
			if($(this).val() == ""){
				$(".passwordFrame .newPssWord2 .msgInfo").css("display","inline-block");
			};
			if($(".passwordFrame .newPssWord input").val() == $(this).val() && $(this).val() != ""){
				$(".passwordFrame .newPssWord2 .msg").css("display","inline-block");
			}else{
				$(".passwordFrame .newPssWord2 .msgInfo").css("display","inline-block");
			}
		});
		var btn = true;
		if(btn){
			$(".passwordFrame .reviseBtn input").click(function(){
				if(testNum($(".passwordFrame .newPssWord input").val()) && $(".passwordFrame .newPssWord input").val() == $(".passwordFrame .newPssWord2 input").val()){
					btn = false;
					$.ajax({	
						url: projectUrl + '/front/user/modifyPassWord',  
						type:'post',  //请求方式  get||post
						data:{
							"oldPassWord": $(".passwordFrame .oldPassWord input").val(),
							"newPassWord": $(".passwordFrame .newPssWord input").val()
						},
						success:function(str){
							btn = true;
							if (str.message.code == 1000) {
								alert(str.message.message);
								window.history.go(-1);
							} else if (str.message.code == 2002){
								alert("未登录，请先登录后再尝试更改密码");
							} else {
								alert(str.message.message);
							};
						},
						error: function () {
							btn = true;
						}
					});
				};
				if(!testNum($(".passwordFrame .newPssWord input").val())){
					$(".passwordFrame .newPssWord .msgInfo").css("display","inline-block").html("6~16位数字与字母组合");
				}else if($(".passwordFrame .newPssWord input").val() != $(".passwordFrame .newPssWord2 input").val()){
					$(".passwordFrame .newPssWord2 .msgInfo").css("display","inline-block");
				};
			});
		}else{
			alert("正在连接服务器，请稍后")
		}
		
	},
	identity:function(){
		selectionFn('bank');
		selectionFn('bankType');
		function selectionFn (str) {
			$("."+str+" i").click(function(){
				$(this).next().toggle();
				return false
			});
			$('.'+str+' ul').click(function(ev){
				if(ev.target.nodeName === 'LI'){
					$(this).siblings('.choosed').html($(ev.target).html());
					$(this).siblings('.choosed').attr('forSort', $(ev.target).attr('code'));
					$(this).hide();
				}
				return false;
			});
			$(document).click(function(){
				$('.'+str+' ul').hide();
				$('.'+str+' ul').hide();
			});
		};
		zycfZ.getBankList("01",function(obj){
            var html = '';
			for (var i = 0, len = obj.length; i < len; i++) {
				html += '<li code="'+obj[i].bankCode+'">'+obj[i].bankShortName+'</li>'
			};
			$(".bank .bankList").html(html);
			$(".bank .selectedBank").attr("forSort", obj[0].bankCode).html(obj[0].bankShortName);
		});
		var btn = true;
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		var bankCode = false;
		$("input[name='cardNum']").blur(function(){
			bankCode = false;
		});
		$("input[name='cardNum']").blur(function(){
			if($(this).val()){
				var bankObj = distinctionBankCard( $(this).val() );
		    	if( !bankObj || !bankObj.bank || $(".bank .selectedBank").html() != bankObj.bank ){
		    		$(this).siblings('.msgInfo').html('您填写的卡号与发卡银行不一致，请重新选择发卡银行').css('display','inline-block');
		    		return;
		    	} else {
		    		bankCode = true
		    	};
			}
		});
		if(btn){
			$('.idCodeFrame .openNow span').click(function(){    // 个人设置里实名认证
				if(checkCard($("input[name='nameId']").val()) && checkChinese($(".name input").val()) && zycfZ.eighteen($("input[name='nameId']").val()) && bankCode && checkMobile($("input[name='reserveNum']").val()) ){
					btn = false;
					$.ajax({	
						url: projectUrl + '/front/bmAccount/registeApply',  //实名验证
						type:'post',  //请求方式  get||post
						data:{
						    "bmaccount.userName": $(".name input").val(),
						    "bmaccount.idCode": $("input[name='nameId']").val(),
						    "bmaccount.openBranch": $(".selectedBank").attr("forSort"),
						    "bmaccount.cardNo": $("input[name='cardNum']").val(),
						    "bmaccount.preMobile": $("input[name='reserveNum']").val(),
						    "bmaccount.cardType": $(".bankTypeChoose").attr("forSort")
						},
						success:function(str){
							btn = true;
							if(str.message.code == 1000){   // 
								makesureFn()
								$('.idCodeFrame').hide();
							} else{
								$('.msgInfo').html(str.message.message);
							};
							if (str.message.code == 2002) {
								setTimeout(function () {
									window.location.href = '../zyfyLogin.html'
								}, 1000)
							}
						},
						error: function () {
							btn = true;
						}
					});
				}else if(!checkCard($("input[name='nameId']").val())){
					$('.msgInfo').html("请输入正确的身份证号码！");
				}else if( !checkChinese($(".name input").val()) ){
					$('.msgInfo').html("请输入中文姓名");
				}else if( !zycfZ.eighteen($("input[name='nameId']").val()) ){
					$('.msgInfo').html("用户未满18岁，不能认证");
				} else if( !bankCode ){
					$('.msgInfo').html("您填写的卡号与发卡银行不一致，请重新选择发卡银行");
				} else if( !checkMobile($("input[name='reserveNum']").val()) ){
					$('.msgInfo').html("预留手机号格式不正确");
				};
			});
		}else{
			alert("正在连接服务器，请稍后")
		};
		function makesureFn () {
        	$('.makesureInfo').show();
        	$('.callbackMessage').html('');
        	$(".makesureInfo .username .msg").html( $(".name input").val() );
        	$(".makesureInfo .idCode .msg").html( $("input[name='nameId']").val() );
        	$(".makesureInfo .bank .msg").html( $(".selectedBank").html() );
        	$(".makesureInfo .bankType .msg").html( $(".bankTypeChoose").html() );
        	$(".makesureInfo .bankCode .msg").html( $("input[name='cardNum']").val() );
        	$(".makesureInfo .mobile .msg").html( $("input[name='reserveNum']").val() );
        };
        $(".makesureInfo .btn input").click(function () {
        	var This = this;
			if (This.btn) {
				return;
			}
			This.btn = true;
        	if($("input[name='shortMessageCode']").val() != ''){
				$.ajax({	
					url: projectUrl + '/front/bmAccount/registeAffirm',  //信息确认
					type:'post',  //请求方式  get||post
					data:{
					    "bmaccount.userName": $(".name input").val(),
					    "bmaccount.idCode": $("input[name='nameId']").val(),
					    "bmaccount.openBranch": $(".selectedBank").attr("forSort"),
					    "bmaccount.cardNo": $("input[name='cardNum']").val(),
					    "bmaccount.preMobile": $("input[name='reserveNum']").val(),
					    "bmaccount.cardType": $(".bankTypeChoose").attr("forSort"),
					    "bmaccount.identifyingCode": $("input[name='shortMessageCode']").val()
					},
					success:function(str){
						This.btn = false;
						$('.callbackMessage').html(str.message.message);
						if(str.message.code == 1000){
							setTimeout(function(){
								window.history.go(-1);
							},1500);          
						};
						if (str.message.code == 2002) {
							setTimeout(function () {
								window.location.href = '../zyfyLogin.html'
							}, 1000)
						}
					},
					error: function () {
						This.btn = false;
					}
				});
			}else if($("input[name='shortMessageCode']").val() == ''){
				This.btn = false;
				$('.callbackMessage').html('您输入短信验证码');
			}
        });
	},
	agreement:function(){
		var linkURL = null;
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$("#RechargeResults .title i").click(function(){
			$("#RechargeResults").hide();
			window.location.reload();
		});
		$.ajax({
            type:"POST",
            url:"/user/userSettings",
            dataType:"json",
            success:function(str){
				if(str.debit && str.instant){
					$(".agreement .quickPay input").val("解约协议");
				};
				if(str.invest){
					$(".agreement .invest input").val("解约协议");
				};
				if(str.repay){
					$(".agreement .repayment input").val("解约协议");
				};
				$(".agreement .invest .btn").click(function(){
					thirdParty(str.invest?"/user/cancleAgreement":"/user/agreement",[3]);
					linkMessage(linkURL);
				});
				$(".agreement .repayment .btn").click(function(){
					thirdParty(str.repay?"/user/cancleAgreement":"/user/agreement",[4]);
					linkMessage(linkURL);
				});
				$(".agreement .quickPay .btn").click(function(){
					thirdParty((str.debit&&str.instant)?"/user/cancleAgreement":"/user/agreement",[1,2]);
					linkMessage(linkURL);
				});
				function linkMessage(linkURL){
					if(typeof linkURL === "string"){
						$("#RechargeResults").show();
						window.open(linkURL,'_blank');
					}else if(linkURL.code == 1){
						$("#RechargeResults").show();
						window.open(linkURL.message,'_blank');
					}else{
						alert(linkURL.message);
					};
				};
			}
        });
		function thirdParty(URL,arr){
			$.ajax({
	            type:"POST",
	            url:URL,
	            data:{"agreements":arr},
	            dataType:"json",
	            async: false,
	            success:function(str){
					linkURL = str;
				}
	        });
		};
	},
	bank:function(){
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		var off = false,onOff=false;
		var bindCard = false;
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$.ajax({
            type:"POST",
            url: projectUrl + "/front/userAccount/getUserSetting",
            dataType:"json",
            async:false,
            success:function(str){
            	bindCard = str.cardauthenticated;
				if(!str.userInfo.userName){
					$(".bankBar .yourName .backgroundColor").html("");
					$(".bankBar .userID .backgroundColor").html("");
					onOff=false;
					alert("请先实名认证，再绑定银行卡，谢谢！");
				}else{
					$(".bankBar .yourName .backgroundColor").html(str.userInfo.userName);
					$(".bankBar .userID .backgroundColor").html(str.userInfo.idCode);
					onOff=true;
				};
			}
        });
        zycfZ.getBankList("01",function(obj){
            $(".bankList").html("");
            $(".selectedBank").attr("forSort",obj[0].bankCode).html(obj[0].bankShortName);
        	for(var i=0;i<obj.length;i++){
        		var oLi = $("<li forSort="+obj[i].bankCode+">"+obj[i].bankShortName+"</li>")
        		oLi.appendTo($(".bankList"));
        	}
        	$('.bankBar .bankList li').click(function(){
		    	$('.bankBar .selectedBank').html($(this).html()).attr('forSort',$(this).attr('forSort'));
		    	$('.bankBar .bankList').hide();
		    });
		});
		/* 绑定银行卡 下拉列表 */
	    $('.bankBar .selectedBank').click(function(){
	    	$('.bankBar .bankList').toggle();
	    	$(document).click(function(){
				$('.bankBar .bankList').hide();
			});
			return false;
	    });
	    $(".bankBar .cardNum input").focus(function(){
	    	$(".bankBar .cardNum .msg").css("display","none");
	    	$(".bankBar .cardNum .msgInfo").css("display","none");
	    	off = false;
	    });
	    $(".bankBar .cardNum input").blur(function(){
	    	var reg = /^(\d{16}|\d{19})$/;
	    	if(reg.test($(this).val())){
	    		$(".bankBar .cardNum .msg").css("display","inline-block");
	    		off = true;
	    	}else{
	    		$(".bankBar .cardNum .msgInfo").css("display","inline-block");
	    		off = false;
	    	};
	    });
	    $("#RechargeResults .title i").click(function(){
			$("#RechargeResults").hide();
			window.location.reload();
		});
	    $(".bankBar .reviseBtn input").click(function(){
	    	var linkURL = null;
	    	var cardURL = bindCard?"/modifyBankCard":"/bankCardBinding";  //修改银行卡、绑定银行卡URL
	    	var bankObj = distinctionBankCard( $(".bankBar .cardNum input").val() );
	    	if( $(".bankBar .selectedBank").html() != bankObj.bank ){
	    		alert("您填写的卡号与发卡银行不一致，请重新选择发卡银行");
	    		return;
	    	};
	    	if(!off){
	    		$(".bankBar .cardNum .msgInfo").css("display","inline-block");
	    		return;
	    	}else if(!onOff){
	    		alert("请先实名认证，再绑定银行卡，谢谢！");
	    		return;
	    	};
	    	if(off && onOff){
	    		$.ajax({    // 绑定银行卡
		            type:"POST",
		            url:cardURL,
		            data:{"bank":$(".bankBar .selectedBank").attr("forSort"),"account":$(".bankBar .cardNum input").val()},
		            dataType:"json",
		            async: false,
		            success:function(str){
		            	if(str.code == 1){
		            		$("#RechargeResults").show();
		            		linkURL = str.message;
		            	}else{
		            		alert(str.message)
		            	};
					}
		        });
	    	};
	    	if(linkURL){
				window.open(linkURL,'_blank');
			};
	    });
	},
	companyIdentity:function(){
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		$("input[name='applyBtn']").click(function () {
	        if ($("input[name='companyName']").val() != '' && ($("input[name='businessNumber']").val() != '' || $("input[name='socialCreditCode']").val() != '')) {
	            $.ajax({    
	                url: projectUrl + '/front/bmAccount/companyOpenAccont',  //企业名称
	                type:'post', 
	                data:{
	                    "bmCompany.org_name": $("input[name='companyName']").val(),
	                    "bmCompany.business_license": $("input[name='businessNumber']").val(),
	                    "bmCompany.bank_license": $("input[name='socialCreditCode']").val()
	                }, 
	                success:function(str){
	                	var floatWindow = new PopUpBox();
                        var content = "<p style='text-align:center'>"+str.message.message+"</p>";
                        floatWindow.init({
                            w:300,
                            h:60,
                            iNow:0,          // 确保一个对象只创建一次
                            tBar:false,  
                            time:1500,  
                            content:content,     // 内容
                            workBar:false,
                            makesure: false,
                            cancel: false
                        });
	                    if(str.message.code == 1000){
	                        setTimeout(function () {
	                    		window.history.go(-1);
	                    	}, 2000);
	                    };
	                }
	            });
	        } else {
	            var floatWindow = new PopUpBox();
	            var content = "";
	            if ($("input[name='companyName']").val() == '') {
	                content = "<p style='text-align:center'>企业名称不能为空</p>";
	            } else if ($("input[name='businessNumber']").val() == '' && $("input[name='socialCreditCode']").val() == '') {
	                content = "<p style='text-align:center'>营业执照编号或社会信用代码证必须填一个</p>";
	            }
	            floatWindow.init({
	                w:300,
	                h:60,
	                iNow:0,          // 确保一个对象只创建一次
	                tBar:false,  
	                time:1500,  
	                content:content,     // 内容
	                workBar:false,
	                makesure: false,
	                cancel: false
	            });
	        }
	    });
	},
	companyCord:function(){
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		selectionFn('bank');
	    function selectionFn (str) {
	        $("."+str+" i").click(function(){
	            $(this).next().toggle();
	            return false
	        });
	        $('.'+str+' ul').click(function(ev){
	            if(ev.target.nodeName === 'LI'){
	                $(this).siblings('.choosed').html($(ev.target).html());
	                $(this).siblings('.choosed').attr('forSort', $(ev.target).attr('code'));
	                $(this).hide();
	            }
	            return false;
	        });
	        $(document).click(function(){
	            $('.'+str+' ul').hide();
	            $('.'+str+' ul').hide();
	        });
	    };
	    getAllBank(function(obj){
	        var html = '';
	        for (var i = 0, len = obj.banks.length; i < len; i++) {
	            html += '<li code="'+obj.banks[i].bankCode+'">'+obj.banks[i].bankShortName+'</li>'
	        };
	        $(".bank ul").html(html);
	        $(".bank .selectedBank").attr("forSort", obj.banks[0].bankCode).html(obj.banks[0].bankShortName);
	    });
	    function getAllBank (fn) {
	        $.ajax({
	            url: projectUrl + '/front/util/searchAllBanks',  //获取银行列表
	            type: 'post',  //请求方式  get||post
	            data: {},
	            success:function(str){
	                if (str.message.code === 1000) {
	                    fn && fn(str);
	                } else {
	                    alert(str.message.message)
	                }
	            }
	        });
	    };
	    $("input[name='applyBankBtn']").click(function () {
	        if ($("input[name='org_no']").val() != '' && $("input[name='acct_name']").val() != '' && $("input[name='acct_no']").val() != '') {
	            $.ajax({    
	                url: projectUrl + '/front/bmAccount/companybindCard',  
	                type:'post', 
	                data:{
	                    "bmCompany.acct_name": $("input[name='acct_name']").val(),
	                    "bmCompany.acct_no": $("input[name='acct_no']").val(),
	                    "bmCompany.open_branch": $(".selectedBank").attr("forSort"),
	                    "bmCompany.org_no": $("input[name='org_no']").val()
	                }, 
	                success:function(str){
	                	var floatWindow = new PopUpBox();
                        var content = "<p style='text-align:center'>"+str.message.message+"</p>";
                        floatWindow.init({
                            w:300,
                            h:60,
                            iNow:0,          // 确保一个对象只创建一次
                            tBar:false,  
                            time:1500,  
                            content:content,     // 内容
                            workBar:false,
                            makesure: false,
                            cancel: false
                        });
	                    if(str.message.code == 1000){
	                    	setTimeout(function () {
	                    		window.history.go(-1);
	                    	}, 2000);
	                    };
	                }
	            });
	        } else {
	            var floatWindow = new PopUpBox();
	            var content = "";
	            if ($("input[name='org_no']").val() == '') {
	                content = "<p style='text-align:center'>组织机构代码</p>";
	            } else if ($("input[name='acct_name']").val() == '') {
	                content = "<p style='text-align:center'>对公账户名称</p>";
	            } else if ($("input[name='acct_no']").val() == '') {
	                content = "<p style='text-align:center'>对公账号</p>";
	            }
	            floatWindow.init({
	                w:300,
	                h:60,
	                iNow:0,          // 确保一个对象只创建一次
	                tBar:false,  
	                time:1500,  
	                content:content,     // 内容
	                workBar:false,
	                makesure: false,
	                cancel: false
	            });
	        }
	    });
	},
	mobileNum:function(){
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
	},
	copy:function(){
		var clip = null; 
		function init() { 
			clip = new ZeroClipboard.Client(); 
			ZeroClipboard.setMoviePath("../html/ZeroClipboard.swf");
			clip.setHandCursor( true ); 
			clip.addEventListener('load', function (client) { 
			    debugstr("Flash movie loaded and ready."); 
			}); 
			clip.addEventListener('mouseOver', function (client) { 
				clip.setText( $('#copyInfo').val() ); 
			}); 
			clip.addEventListener( "complete", function(){ 
		        alert("复制成功，可以发给你的好朋友了！");  
		    }); 
			clip.glue( 'copyBtn' ); 
		} 
		setTimeout(function(){ 
		    init(); 
		},1500); 
	},
	getBankList:function(num,fn){
		$.ajax({
			type:"POST",
			url: projectUrl + '/front/util/searchAllBanks',
			data:{},
			success:function(str){
				fn && fn(str.banks);
			}
		});
	},
	recharge:function(){
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		var name = decodeURI(window.location.search.substring(1));
		$('.recharge .userName span').html(name);
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		zycfZ.getBankList("01",function(obj){
            $(".bankListAll").html("");
            if(!obj.length || obj.length<1){
            	$(".bankListAll").html("<span>请刷新重试</span>");
            }else{
            	for(var i=0;i<obj.length;i++){
            		var oSpan = $("<span class="+obj[i].enName+" title="+obj[i].bankName+" abbr="+obj[i].bankId+"></span>");
            		if(i==0){
            			oSpan.addClass("active");
            		}
            		oSpan.appendTo($(".bankListAll"));
            	}
            	$('.bankListAll span').click(function(){
					$('.bankListAll span').removeClass('active').eq($(this).toggleClass('active'));
				});
            }
		});
		$('.recharge .addMoney input').focus(function(){
			$('.recharge .msg').css("color","#A9A9A9");
		});
		$("#RechargeResults .title i").click(function(){
			$("#RechargeResults").hide();
			window.location.reload();
		});
		$("#RechargeResults .rechargeAgain").click(function(){
			$("#RechargeResults").hide();
			window.location.reload();
		});
		var minrecharge = 100;
		$.ajax({
            type:"POST",
            url: projectUrl + "/front/userAccount/userAccountInfo",
            dataType:"json",
            success:function(str){
				if(str.userAccountVo.availableAmount){
					$('.recharge .title strong').html(str.userAccountVo.availableAmount);
				    $('.recharge .balance strong').html(str.userAccountVo.availableAmount);
				}else{
					$('.recharge .title strong').html(0);
				    $('.recharge .balance strong').html(0);
				};
				$('.recharge .msg').html("单笔充值金额应大于等于"+minrecharge);
			}
        });
		
		$('.recharge .btn input').click(function(){
			var This = this;
			if (This.btn) {
				return;
			}
			This.btn = true;
			var re=/^[0-9]*$/;
			var num = parseFloat($('.recharge .addMoney input').val());
			if(num == 0){
				alert("输入金额不能为0");
				This.btn = false;
				return;
			};
			if($( '.recharge .bankListAll span.active').attr('abbr') != '' && num >= minrecharge){
				$.ajax({
	                type:"POST",
	                data : {
	                	"amt": num,
	                	"bankcode": $('.recharge .bankListAll span.active').attr('abbr')
	                },
	                url: projectUrl + "/front/fund/accountGatewayRecharge",
	                dataType:"json",
	                success:function(data){
	                	This.btn = false;
	                	if (data.message.code === 1000) {
	                		$(".confirm-wrap").show();
	                		$(".amount span").html(num);
	                		$(".formData").html(data.rep)
	                	} else {
	                		alert(data.message.message)
	                	}
	                },
	                error: function () {
	                	This.btn = false;
	                }
	            });
			}else{
				This.btn = false;
				$('.recharge .msg').css("color","red");
			};
		});
		$(".payBtn span").click(function() {
			$(".formData input[type='submit']").click();
		});
	},
	loanManagement: function(){
		//数据初始化

		/* 下拉列表 */
	    $('.loanManage .function .left span').click(function(e){
	    	//$('.loanManage .function ul').toggle();
	    	e.stopPropagation();
	    	$(this).parent().find("ul").toggle();
	    });
	    $('.loanManage .function li').click(function(e){
	    	e.stopPropagation();
	    	
	    	$(this).parent().parent().find("span").html($(this).html());
	    	$(this).parent().parent().find("span").attr("code",$(this).attr("code"));
	    	$(this).parent().hide();
	    });
	    //时间插件
	    laydate({elem: '#fundRecorLs'});
	    laydate({elem: '#fundRecorLe'});
	    laydate({elem: '#fundRecorRs'});
	    laydate({elem: '#fundRecorRe'});
	    //查询
	    function accountListAjax(opt){
        	var settings = {
				page:1,
				num:10,
				start: '',
				end: '',
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
				    "params":settings.params,
				    "strStart": settings.start,
				    "strEnd": settings.end
				    },
	            dataType:"json",
	            success:function(str){
	            	settings.callBack(str);
	            	if (str.message.code === 2002) {
	            		window.location.href = 'zyfyLogin.html';
	            	};
	            	if(str.totalPage > 1){
				    	page({
							id:settings.id,
							nowNum:settings.page,
							allNum: str.totalPage,
							callBack:function(now,all){
								accountListAjax({
									page:now,
								    num:10,
								    params:settings.params,
								    strStart: settings.start,
			    					strEnd: settings.end,
								    id:settings.id,
								    url:settings.url,
				                    callBack:settings.callBack
								});
							}
						});
				    };
				}
	        });
        };
	    $(".loanBox:eq(0) .export").click(function(){
	    	var status = $(this).parent().parent().children().eq(0).find("span").attr("code");
	    	var startTime =$("#fundRecorLs").val();
	    	var endTime = $("#fundRecorLe").val();
	    	if(startTime == '' && endTime != ''){
	    		return;
	    	}
	    	if(startTime != '' && endTime == ''){
	    		return;
	    	}
	    	accountListAjax({
				page:1,
			    num:10,
			    params:{
			    	"status":status
			    },
		    	start: startTime,
				end: endTime,
			    id:"loanManagementPage",
			    url: projectUrl + '/front/userAccount/loanManagement',
				callBack:function(obj){loanManagementData(obj.pageLoan)}
			});
	    });
		function loanManagementData(obj){   
        	$(".loanBox").eq(0).find("tbody").html("");
			$("#loanManagementPage").html("");
			if(!obj.results.length || obj.results.length<1){
	        	$("#loadTab").html("<tr><td colspan='6'>暂无数据</td></tr>");
	        }else{
				for(var i = 0; i < obj.results.length; i++){
					var tr = $("<tr></tr>");
					tr.html("");
					tr.html("<td>"+obj.results[i].title+"</td><td>"+obj.results[i].amount+"</td><td>"+obj.results[i].rate+"%"+(obj.results[i].addRate?"+"+obj.results[i].addRate+"%":"")+"</td><td>"+obj.results[i].months+"个月</td><td>"+obj.results[i].strMethod+"</td><td>"+obj.results[i].strStatus+"</td>");
					$(".loanBox").eq(0).find("tbody").append(tr);
				}
	        };
        };
        function loanRepaymentPlanData(obj){   
        	$(".loanBox").eq(1).find("tbody").html("");
			$("#loanRepaymentPlanPage").html("");
			if(!obj.results.length || obj.results.length<1){
	        	$("#repayTab").html("<tr><td colspan='8'>暂无数据</td></tr>");
	        }else{
	        	var repayAmount = 0;
				for(var i = 0; i < obj.results.length; i++){
					if(obj.results[i].status == "已还"){
						continue;
					}
					repayAmount += obj.results[i].toTleAmount;
				}
				$("#repayDetail span:eq(0)").html("共"+obj.results.length+"笔");
				$("#repayDetail span:eq(1)").html("待还"+parseFloat(repayAmount).toFixed(2)+"元");
				for(var i = 0; i < obj.results.length; i++){
					var tr = $("<tr></tr>");
					tr.html("");
					tr.html("<td title='"+obj.results[i].loanTitle+"#"+obj.results[i].currentPeriod+"期'>"+obj.results[i].loanTitle+"#"+obj.results[i].currentPeriod+"期</td><td>"+obj.results[i].strDueDate+"</td><td>"+obj.results[i].amountInterest+"</td><td>"+obj.results[i].amountPrincipal+"</td><td>"+(obj.results[i].amountInterest+obj.results[i].amountPrincipal)+"</td><td>"+obj.results[i].strStatus+"</td><td>"+(obj.results[i].isRepay?'已还':'未还')+"</td><td class='repay'><a code='"+obj.results[i].id+"' href='javascript:'>"+(obj.results[i].isRepay?'':'还款')+"</a></td>");
					$(".loanBox").eq(1).find("tbody").append(tr);
				}
	        };
        };
        $(".loanBox").eq(1).find("tbody").click(function (e) {
        	var This = this;
        	if (e.target.innerHTML === '还款') {
	        	if (This.isClick) {
	        		alert("正在还款中...");
	        		return;
	        	};
	        	This.isClick = true;
        		$(this)
        		$.ajax({
					type: 'post',
					url: projectUrl + '/front/loan/borrowerRepay',
					data: {
						repaymentId: $(e.target).attr('code')
					},
					success: function(str){
						This.isClick = false;
						alert(str.message.message);
						if (str.message.code === 1000) {
							accountListAjax({
								page:1,
							    num:10,
							    params:{
							    	"status": $("#isRePay").attr("code")
							    },
							    id:"loanRepaymentPlanPage",
							    url: projectUrl + '/front/userAccount/loanRepaymentPlant',
								callBack:function(obj){loanRepaymentPlanData(obj.pageLoanRepayment)}
							});	
						}
					},
					error: function (err) {
						This.isClick = false;
						console.log(err);
					}
				});
        	}
        });
	     $(".loanBox:eq(1) .export").click(function(){
	    	 	
	    	 	var status = $(this).parent().parent().children().eq(0).find("span").attr("code").toUpperCase();
		    	var startTime = $("#fundRecorRs").val();
		    	var endTime = $("#fundRecorRe").val();
		    	if(startTime == '' && endTime != ''){
		    		return;
		    	}
		    	if(startTime != '' && endTime == ''){
		    		return;
		    	}
		    accountListAjax({
				page:1,
			    num:10,
			    params:{
			    	"status":status
			    },
		    	start: startTime,
				end: endTime,
			    id:"loanRepaymentPlanPage",
			    url: projectUrl + '/front/userAccount/loanRepaymentPlant',
				callBack:function(obj){loanRepaymentPlanData(obj.pageLoanRepayment)}
			});	
	    });

	    $(".loanBox:eq(2) .export").click(function(){
	    	 	
	    	 	var status = $(this).parent().parent().children().eq(0).find("span").attr("code").toUpperCase();
		    	var startTime = $("#tranStartTime").val();
		    	var endTime = $("#tranEndTime").val();
		    	if(startTime == '' && endTime != ''){
		    		return;
		    	}
		    	if(startTime != '' && endTime == ''){
		    		return;
		    	}
		    accountListAjax({
				page:1,
			    num:10,
			    params:{
			    	"status":status
			    },
		    	start: startTime,
				end: endTime,
			    id:"tranPage",
			    url: projectUrl + '/front/userAccount/loanRepaymentCompentsate',
				callBack:function(obj){tranPageData(obj.pageLoanRepayment)}
			});	
	    });
	    function tranPageData(obj){   
        	$("#tranTab").html("");
			$("#tranPage").html("");
			if(!obj.results.length || obj.results.length<1){
	        	$("#tranTab").html("<tr><td colspan='8'>暂无数据</td></tr>");
	        }else{
				for(var i = 0; i < obj.results.length; i++){
					var tr = $("<tr></tr>");
					tr.html("");
					tr.html("<td title='"+obj.results[i].loanTitle+"#"+obj.results[i].currentPeriod+"期'>"+obj.results[i].loanTitle+"#"+obj.results[i].currentPeriod+"期</td><td>"+obj.results[i].strDueDate+"</td><td>"+obj.results[i].amountInterest+"</td><td>"+obj.results[i].amountPrincipal+"</td><td>"+(obj.results[i].amountInterest+obj.results[i].amountPrincipal)+"</td><td>"+obj.results[i].strStatus+"</td><td>"+(obj.results[i].isRepay?'已还':'未还')+"</td><td class='repay'><a code='"+obj.results[i].id+"' href='javascript:'>"+((!obj.results[i].isRepay && !obj.results[i].repaySource)?'代偿':'')+"</a></td>");
					$("#tranTab").append(tr);
				}
	        };
        };
        $("#tranTab").click(function (e) {
        	var This = this;
        	if (e.target.innerHTML === '代偿') {
	        	if (This.isClick) {
	        		alert("代偿处理中...");
	        		return;
	        	};
	        	This.isClick = true;
        		$(this)
        		$.ajax({
					type: 'post',
					url: projectUrl + '/front/loan/compensateRepay',
					data: {
						repaymentId: $(e.target).attr('code')
					},
					success: function(str){
						This.isClick = false;
						alert(str.message.message);
						if (str.message.code === 1000) {
							$(e.target).html('')
						}
					},
					error: function (err) {
						This.isClick = false;
						console.log(err);
					}
				});
        	}
        });
	    	//1借款记录
		$(".sidebarNav ul li:eq(4)").click(function(){
			accountListAjax({
				page:1,
			    num:10,
			    params:{
			    	"status":"ALLSTATUS"
			    },
			    id:"loanManagementPage",
			    url: projectUrl + '/front/userAccount/loanManagement',
				callBack:function(obj){loanManagementData(obj.pageLoan)}
			});
		});
		
		$(".loanManage ul.nav li").eq(0).click(function(){
			accountListAjax({
				page:1,
			    num:10,
			    params:{
			    	"status":"ALLSTATUS"
			    },
			    id:"loanManagementPage",
			    url: projectUrl + '/front/userAccount/loanManagement',
				callBack:function(obj){loanManagementData(obj.pageLoan)}
			});
		});
		
		//2还款计划
		$(".loanManage ul.nav li").eq(1).click(function(){
			accountListAjax({
				page:1,
			    num:10,
			    params:{
			    	"status":''
			    },
			    id:"loanRepaymentPlanPage",
			    url: projectUrl + '/front/userAccount/loanRepaymentPlant',
				callBack:function(obj){loanRepaymentPlanData(obj.pageLoanRepayment)}
			});	
		});
		
		//3账户金额
		$(".loanManage ul.nav li").eq(2).click(function(){
			$(".loanBox:eq(2) .export").click()
		});
		
		$(".loanManage ul.nav li").eq(0).addClass("act");
		$(".loanBox").eq(0).show();
		$(".loanManage ul.nav li").click(function(){
			$(".loanManage ul.nav li").removeClass("act");
			$(this).addClass("act");
			$(".loanBox").hide();
			$(".loanBox").eq($(this).index()).show();
		});
	},
	withdrawals:function(){
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		var f = null,orNot=false;
		$.ajax({
	        type:"POST",
	        url: projectUrl + "/front/userAccount/queryFundAccount",
	        dataType:"json",
	        async: false,
	        success:function(data){
	        	if(!data.userAccountVo.cardNo){
	        		var content = "<p style='text-align:center;line-height:50px;font-size:14px;margin:20px 0;'>你还未绑定银行卡，请先<a href='../myAccount.html#1' style='color:blue;'>绑卡</a></p>";
					var addStaff = new PopUpBox();
					addStaff.init({
						w:300,
						h:200,
						iNow:1,          // 确保一个对象只创建一次
						opacity:0.7,
						content:content,
						makesure:false, 
			            cancel:false
					});
					$('.rechargeContent .balance strong').html("");
					$('.rechargeContent .bankCard .bankId').html("");
					$('.rechargeContent .bankCard .bank').html("");
	        	};
	        	$('.rechargeContent .balance strong').html(data.userAccountVo.availableAmount);
	        	$('.rechargeContent .bankCard .bankId').html(data.userAccountVo.cardNo);
	        	$('.rechargeContent .bankCard .bank').html(data.userAccountVo.cardName);
	        	f = data.userAccountVo.availableAmount;
	        	orNot=true;
	        }
	    });
		var timeOut = null;
		var isSend = false;
		$(".reAbtain").click(function() {
			var This = this;
			if (This.btn) {
				return;
			}
			This.btn = true;
			$.ajax({
		        type:"POST",
		        url: projectUrl + "/front/util/sendMSgCodeApply",
		        dataType:"json",
		        success:function(data){
		        	This.btn = false;
		        	if (data.message.code === 1000) {
		        		isSend = true;
		        		var num = 120;
						$('.reSend').css('display','inline-block').html(num+'秒后重新发送');
						$('.reAbtain').css('display','none');
						timeOut = setInterval(function(){
							num--;
							if(num <= 0){
								num = 0
								clearInterval(timeOut);
								$('.reAbtain').css('display','inline-block');
								$('.reSend').css('display','none');
							}
							$('.reSend').html(num+'秒后重新发送');
						},1000);
		        	} else {
		        		alert(data.message.message)
		        	}
		        },
		        error: function () {
		        	This.btn = false;
		        }
		    });
		});
	    $('.rechargeContent .addMoney input').focus(function(){
	    	if($(this).val()=='请输入提现金额'){
	    		$(this).val('');
	    	};
	    	$('.rechargeContent .msgInfo').hide();
	    });
	    $('.rechargeContent .addMoney input').blur(function(){
	    	if($(this).val()==''){
	    		$(this).val('请输入提现金额');
	    	};
	    });
	    $("#RechargeResults .title i").click(function(){
			$("#RechargeResults").hide();
			window.location.reload();
		});
		$('.rechargeContent .btn input').click(function(){
			var This = this;
			if (This.btn) {
				return;
			}
			This.btn = true;
			if( !!isNaN( $(".rechargeContent .addMoney input").val() ) ){
				$('.rechargeContent .msgInfo').show();
				This.btn = false;
				return;
			};
			var withdrawLimit = parseFloat($(".rechargeContent .addMoney input").val()); 
			var re = /^[0-9]*$/;
	  	    if( withdrawLimit<=f && f!=0 && orNot && isSend && $("input[name='idnumber']").val() != '' ){
	  	    	$.ajax({
	                type:"POST",
	                data : {
	                	"amount":withdrawLimit,
	                	"sms_code": $("input[name='idnumber']").val()
	                },
	                url: projectUrl + "/front/fund/personWithdraw",
	                dataType:"json",
	                async: false,
	                success:function(data){
	                	This.btn = false;
	                	$("#RechargeResults").show();
	                	if (data.message.code === 1000) {
	                		$("#RechargeResults .success").show();
	                		$("#RechargeResults .failed").hide();
	                		$("#RechargeResults .reason").hide();
	                	}else {
	                		$("#RechargeResults .success").hide();
	                		$("#RechargeResults .failed").show();
	                		$("#RechargeResults .reason").show().html('原因：' + data.message.message);
	                	}
	                },
	                error: function () {
	                	This.btn = false;
	                }
	            });
	  	    }else if (withdrawLimit>f){
	  	    	This.btn = false;
	  	    	$('.rechargeContent .msgInfo').html('输入的金额应小于账户余额！');
	  	    } else if (f == 0) {
	  	    	This.btn = false;
	  	    	$('.rechargeContent .msgInfo').html('账户余额为0');
	  	    } else if (!orNot) {
	  	    	This.btn = false;
	  	    	$('.rechargeContent .msgInfo').html('未能获取到账户余额信息，请刷新页面');
	  	    } else if (!isSend) {
	  	    	This.btn = false;
	  	    	$('.rechargeContent .msgInfo').html('请先获取短信验证码');
	  	    } else if ($("input[name='idnumber']").val() == '') {
	  	    	This.btn = false;
	  	    	$('.rechargeContent .msgInfo').html('请输入短信验证码');
	  	    };
		});
	},
	eighteen:function(str){
		var number = str;
		var year = String(number).substring(6,10);
		if(str.length<=15){
			year = "19"+String(number).substring(6,8);
		};
		var month = String(number).substring(10,12);
		var day = String(number).substring(12,14);
		var myDate = new Date();
		if( (parseInt(year)+18)<myDate.getFullYear() ){
			return true;
		}else if( (parseInt(year)+18)===myDate.getFullYear() ){
			if( parseInt(month)<(myDate.getMonth()+1) ){
				return true;
			}else if( parseInt(month)===(myDate.getMonth()+1) ){
				if( parseInt(day)<=myDate.getDate() ){
					return true;
				}else{
					return false;
				};
			}else{
				return false;
			};
		}else{
			return false;
		};
	},
	myData:function(){
		!function(){
			laydate({elem: '#dataStar'});//绑定元素
		}();
		!function(){
			laydate({elem: '#dataEnd'});//绑定元素
		}();
		!function(){
			laydate({elem: '#proWrapInpS'});//绑定元素
		}();
		!function(){
			laydate({elem: '#proWrapInpE'});//绑定元素
		}();
		!function(){
			laydate({elem: '#fundRecorS'});//绑定元素
		}();
		!function(){
			laydate({elem: '#fundRecorE'});//绑定元素
		}();
		!function(){
			laydate({elem: '#tranStartTime'});//绑定元素
		}();
		!function(){
			laydate({elem: '#tranEndTime'});//绑定元素
		}();
	}
};

