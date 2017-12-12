$(function(){
    var projectUrl = "";
    $(".placeholder").inputDefault();
    var flag = false;
    var onOff = false;
    var mima = false;
    var confMima = false;

    $("input[name='mobileNumber']").focus(function(){
    	$(".mobileNum .tip").css("display","none");
    	$(".mobileNum i").css("display","none");
    });
    $("input[name='mobileNumber']").blur(function(){
    	if($(this).val() == ""){
    		$(".mobileNum .tip").css("display","inline-block").html("请输入手机号")
    	}
    });

    $(".getMobCode").click(function(){
    	if($("input[name='mobileNumber']").val() != "" && checkMobile($("input[name='mobileNumber']").val())){
    		$.ajax({	
				url: projectUrl + '/front/util/resetPasswdMsg',  //发短信
				type:'post',
				data:{"mobile":$("input[name='mobileNumber']").val()},
				success:function(str){
					if(str.code == 2001){   // 手机号不存在
						$(".mobileNum .tip").css("display","inline-block").html("手机号还未在平台注册")
				    }else if(str.code == 1000){  // 成功，已发短信
				    	flag = true;
				    	$(".mobileNum i").css("display","inline-block");
                        $(".getMobCode").hide();
                        var num = 60;
                        var myTime = null;
                        $(".reGet").show().html(num+"秒后可重新获取");
                        myTime = setInterval(function(){
                            num--;
                            if(num<=0){
                                clearInterval(myTime);
                                $(".reGet").hide();
                                $(".getMobCode").show().html("重新获取验证码");
                            };
                            $(".reGet").html(num+"秒后可重新获取");
                        },1000)
				    }else if(str.code == 3001){  // 请求次数过多
				    	$(".mobileNum .tip").css("display","inline-block").html("请求次数过多，每天不能多于2次");
                        $(".getMobCode").hide();
                        $(".mobileNum i").css("display","none");
				    } else {
                        $(".mobileNum .tip").css("display","inline-block").html(str.message);
                    };
				}
			});
    	};
    	if($("input[name='mobileNumber']").val() == ""){
    		$(".mobileNum .tip").css("display","inline-block").html("请输入手机号")
    	}else if(!checkMobile($("input[name='mobileNumber']").val())){
    		$(".mobileNum .tip").css("display","inline-block").html("请输入正确手机号")
    	};
    });

    $("input[name='verCode']").focus(function(){
    	$(".verifyCode .tip").css("display","none");
    	$(".verifyCode i").css("display","none");
    });
    $("input[name='verCode']").blur(function(){
    	if($(this).val() == ""){
    		$(".verifyCode .tip").css("display","inline-block").html("验证码不能为空")
    	}else if(flag){
    		$.ajax({   // 验证短信验证码
    			type:"POST",
    			url: projectUrl + "/front/util/resetPasswdValidate",
    			data:{"resetCode":$(this).val()},
    			success:function(str){
    				if(str.code == 1000){
    					onOff = true;
    					$(".verifyCode i").css("display","inline-block");
    				} else {
                        $(".verifyCode .tip").css("display","inline-block").html(str.message)
                    }
    			}
    		});
    	}else{
    		$(".verifyCode .tip").css("display","inline-block").html("请先获取手机验证码")
    	}
    })

    $("input[name='newPass']").focus(function(){
    	$(".newPassword .tip").css("display","none");
    	$(".newPassword i").css("display","none");
    });
    $("input[name='newPass']").blur(function(){
    	if($(this).val == ""){
    		$(".newPassword .tip").css("display","inline-block").html("密码不能为空");
    	}else if( testNum($(this).val()) ){
    		mima = true;
    		$(".newPassword i").css("display","inline-block");
    	}else{
    		$(".newPassword .tip").css("display","inline-block").html("登陆密码格式不正确");
    	}
    });

    $("input[name='rePass']").focus(function(){
    	$(".confPassword .tip").css("display","none");
    	$(".confPassword i").css("display","none");
    });
    $("input[name='rePass']").blur(function(){
    	if($(this).val() == $("input[name='newPass']").val()){
    		$(".confPassword i").css("display","inline-block");
    		confMima = true;
    	}else{
    		$(".confPassword .tip").css("display","inline-block").html("2次输入密码不一致");
    	}
    });

    $("#alertFloor").click(function(){
    	if(onOff && mima && confMima){
    		$.ajax({
    			type:"POST",
    			url: projectUrl + "/front/user/resetPassWord",
    			data:{
                    "user.passphrase": $("input[name='newPass']").val()
                },
    			success:function(str){
    				if (str.message.code == 1000) {
    					$(".nextFloor").show();
    				} else {
                        alert(str.message.message);
                    }
    			}
    		});
    	}else if(!onOff){
    		$(".verifyCode .tip").css("display","inline-block").html("验证码不正确")
    	}else if(!mima){
    		$(".newPassword .tip").css("display","inline-block").html("登陆密码格式不正确");
    	}else if(!confMima){
    		$(".confPassword .tip").css("display","inline-block").html("2次输入密码不一致");
    	}
    });
    $("#login").click(function(){
        window.location.href = "zyfyLogin.html";
    });
});
