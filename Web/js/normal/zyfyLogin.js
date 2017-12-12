$(function(){
    //读取cookie
    if($.cookie("loginname")){
        $("#loginName").val($.cookie("loginname"));
    }
    if($.cookie("status")){
        $("#rem").addClass("checked")
    }
    //input默认文字
    $(".placeholder").inputDefault();

    //是否记住用户名
    $(".remAcc span").click(function(){
        $("#rem").toggleClass("checked");
    });

    $("#loginName").focus(function(){
        $(".tipBox p.tip").hide();
    });
    $("#loginPass").focus(function(){
        $(".tipBox p.tip").hide();
    });

    //发送ajax请求，返回后台json
    function ajaxRequest(ajaxJson){
        var url = ajaxJson.url || "";
        var data = ajaxJson.data || "";
        var result = null;
        $.ajax({
            url: url,
            type: "post",
            data: data,
            async: false,
            success: function(ajaxStr){
                if(ajaxStr){
                    result = ajaxStr;
                }else {
                    result = "加载超时";
                }
            },
            //timeout: 0.1,
            error: function(){
                result = "加载失败";
            }
        });
        return result;
    }
    //登陆按钮

    $(document).keydown(function(e){
      if(e.keyCode==13){
         $(".loginBtn").click();
      }
    });
    var flag = true;
    if(flag){
        $(".loginBtn").click(function(){
            flag = false;
            var name = $("#loginName").val();
            var pass = $("#loginPass").val();

            if(name == "" || pass == ""){
                $(".tipBox p.tip").show().html("用户名或密码不能为空");
                flag = true;
                return;
            }
            var loginStatus;
            loginStatus = ajaxRequest({
                "url":"/front/user/login",
                "data": {"userVo.loginName": name,"userVo.passphrase": pass}
            });
            if(!loginStatus){
                flag = true;
            };
            if(loginStatus == "加载超时"){
                $(".tipBox p.tip").show().html("连接超时");
            }else if(loginStatus == "加载失败"){
                $(".tipBox p.tip").show().html("连接失败");
            }else{
                if(loginStatus.message.code == 1000){
                    if($("#rem").hasClass("checked")){
                        $.cookie("loginname",name,{ expires: 7 });
                        $.cookie("status","1");
                    }
                    window.location.href="/index.html";
                }else{
                    $(".tipBox p.tip").show().html( loginStatus.message.message );
                }
            };
        })
    };
});
