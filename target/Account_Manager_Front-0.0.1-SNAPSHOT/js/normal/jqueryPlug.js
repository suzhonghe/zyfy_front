//input默认文字
//Example
//<div class="placeholder">
//    <input id="自定" type="text" name="自定" placeholder="自定"/>
//    <label class="posi" for="自定"></label>
//</div>




$.fn.inputDefault = function(){
    var input = document.createElement('input');
    if("placeholder" in input){

    }else{
        $(this).each(function(){
            $(this).find("label.posi").html($(this).find("input").attr("placeholder"));
            $(this).find("input").focus(function(){
                $(this).keyup(function(){
                    if($(this).val()){
                        $(this).parent().find("label.posi").hide();
                    }else {
                        $(this).parent().find("label.posi").show();
                    }
                });
            });


        });
    }
};
//input校验
$.fn.inputCheck = function(){
    //username
    //$(this).find("input[name=username]");
    //password
    //$(this).find("input[name=password]");
    //验证码
   // $(this).find("input[name=verificationCode]");
    if($(this).find("input[name=verificationCode]").is(":hidden")){
        if($(this).find("input[name=username]").val() == "" || $(this).find("input[name=password]").val() == ""){
            return false;
        }else {
            return true;
        }
    }else {
        $(this).find(".fresh").click(function(){
            $(this).find('img').attr('src','/zycfMarket/imgValalidate'+'?'+ Math.random())
        });
        if($(this).find("input[name=username]").val() == "" || $(this).find("input[name=password]").val() == "" || $(this).find("input[name=verificationCode]").val() == ""){
            return false;
        }else {
            $.ajax({
                url:'/zycfMarket/codeValalidate',
                type:'post',
                success:function(str){
                    if($(this).find("input[name=verificationCode]").val() !== str.code.toLocaleLowerCase()){
                        $(".tipBox").html("验证码错误");
                    }
                },
                error:function(){
                    alert('验证码加载失败');
                }
            });
            return true;
        }
    }

};
$.fn.check = function(){
    //手机号
    //$(this).find("input[name=mobileNumber]").val()
    //手机验证码
    //$(this).find("input[name=verCode]").val()
    //新密码
   // $(this).find("input[name=mobileNumber]").val()
    var reg = /([a-z][A-Z][0-9]){6,16}/;
    if(!reg.test($(this).find("input[name=mobileNumber]").val())){
        $(this).find("input[name=mobileNumber]").parent().find(".tip").show().html("登陆密码格式不正确");
    }
    //2次密码
    //$(this).find("input[name=mobileNumber]").val()
    if($(this).find("input[name=rePass]").val() !== $(this).find("input[name=mobileNumber]").val()){
        $(this).find("input[name=rePass]").parent().find(".tip").show().html("2次输入密码不一致");
    }
};
//判断是否登陆
$.fn.isLogin = function(){
    $.ajax({
        type: "",
        url: ""
    });
};

