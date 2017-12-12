$(function(){
    var projectUrl = '';

    $('.regsiterList .code .Refresh').click(function(){
        $('.regsiterList .code').find('img').attr('src',projectUrl+'/front/util/imgValalidate'+'?'+ Math.random());
        $('.code input').val("");
    });

    var isPho = false;
    var vf = false;
    var timeOut = null;

    var oPhoTestCon = $('#phoTest .phoTestCon');
    var t = (document.documentElement.clientHeight-oPhoTestCon[0].offsetHeight)/2;
    var w = (document.documentElement.clientWidth-oPhoTestCon[0].offsetWidth)/2;
    oPhoTestCon[0].style.left = w+'px';
    oPhoTestCon[0].style.top = t+'px';

    $("input[name='pho']").focus(function () {
        var isPho = false;
    });
    $("input[name='pho']").blur(function () {
        if (checkMobile($(this).val())) {
            $.ajax({    
                url: projectUrl+'/front/user/searchUser',  //请求路径
                type: 'post',  //请求方式  get||post
                data: {'user.mobile': $(this).val()},  //请求数据
                success: function(str){
                    if(str.message.code == 1000){
                        isPho = false;
                        var floatWindow = new PopUpBox();
                        var content = "<p style='text-align:center'>该手机号已在平台注册</p>";
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
                    } else {  // 用户不存在
                        isPho = true;
                    };
                }
            });
        } else {
            var floatWindow = new PopUpBox();
            var content = "<p style='text-align:center'>请输入正确的手机号</p>";
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
    $("input[name='password']").blur(function () {
        if ( !testNum($(this).val()) ) {
            var floatWindow = new PopUpBox();
            var content = "<p style='text-align:center'>请输入6~16位数字与字母组合</p>";
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
    $("input[name='VerificationCode']").blur(function () {
        var re = /^\w{4}$/;
        if ( re.test($(this).val()) ) {
            $.ajax({    
                url: projectUrl+'/front/util/codeValalidate',  //图片验证码
                type: 'post',
                data: {
                    "imgCode": $(this).val()
                },
                success: function(str){
                    if(str.code == 1000){
                        vf = true;
                    } else {
                        var floatWindow = new PopUpBox();
                        var content = "<p style='text-align:center'>图片验证码不正确</p>";
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
                    };
                }
            });
        }
    });
    $('.nextPage input').click(function(){
        if(isPho && vf && testNum($("input[name='password']").val()) && $("input[type='checkbox']").is(':checked')){
            $('#phoTest').show();
            $(".phoForm .changStar").html($("input[name='pho']").val().replace(/(\d{3})\d{4}(\d{4})/,"$1****$2"));
            getMessage();
        } else {
            var floatWindow = new PopUpBox();
            var content = "";
            if (!isPho) {
                content = "<p style='text-align:center'>手机号不正确或已在平台注册</p>";
            } else if (!vf) {
                content = "<p style='text-align:center'>图片验证码不正确</p>";
            } else if (!testNum($("input[name='password']").val())) {
                content = "<p style='text-align:center'>密码6~16位数字与字母组合</p>";
            } else if ($("input[type='checkbox']").is(':checked') == false) {
                content = "<p style='text-align:center'>请勾选协议</p>";
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
    $('.phoForm .reAbtain').click(function () {
        getMessage()
    });
    function getMessage () {
        $.ajax({    
            url: projectUrl + '/front/util/mobolileCertWeb',  //手机短信发送
            type: 'post',
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
    $('.finishRegsiter input').click(function(){
        if( $("input[name='sortMessage']").val() == '' ){
            $('.phoForm .msg').html("请输入短信验证码").css("visibility","visible");
        }else{
            $.ajax({    
                url: projectUrl + '/front/user/companyRegist',  //注册提交
                type:'post', 
                data:{
                    "mobile": $("input[name='pho']").val(),
                    "passphrase": $("input[name='password']").val(),
                    "referralMobile": '15311340737',
                    "regcode": $("input[name='sortMessage']").val()
                },  //请求数据
                success:function(str){
                    if(str.message.code == 1000){   // 注册成功
                        $('#phoTest').hide();
                        $('.regsiterList').hide();
                        $('.applyFrame').show();
                        $('.regsiterStep li').addClass('bg');
                    }else{
                        $('.phoForm .msg').html(str.message.message).css("visibility","visible");
                    };
                }
            });
        };
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
                    if(str.message.code == 1000){
                        $('.applyFrame').hide();
                        $(".bankFrame").show();
                    }else{
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
                    if(str.message.code == 1000){
                       $('.bankFrame').hide();
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
});
