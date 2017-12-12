function getStyle(obj,attr){
	 return  obj.currentStyle?obj.currentStyle[attr]:getComputedStyle(obj)[attr];
    };



function shake(obj,attr,endFn){
		if(obj.mytimer){return;}
		obj.mytimer = true;
	    var pos = parseInt(getStyle(obj,attr));
		var arr = [];
		var num = 0;
		for (i=20;i>0;i-=2){
		  arr.push(i,-i)
		};
		arr.push(0);
		obj.mytimer = setInterval(function(){
		     obj.style[attr] = pos + arr[num] + 'px';
			 num++;
			 if(num==arr.length){
				  clearInterval(obj.mytimer);
				  endFn&&endFn();
				  obj.mytimer = false;
				};
		},40);
	};
//setCookie('sideNav', $(this).html(),{"path":"/manage"});
function setCookie(name, value, opts){
    //opts maxAge, path, domain, secure
    if(name && value){
        var cookie = escape(name) + '=' + escape(value);
        //可选参数
        if(opts){
            if(opts.maxAge){
                cookie += '; max-age=' + opts.maxAge; 
            }
            if(opts.path){
                cookie += '; path=' + opts.path;
            }
            if(opts.domain){
                cookie += '; domain=' + opts.domain;
            }
            if(opts.secure){
                cookie += '; secure';
            }
        }
        document.cookie = cookie;
        return cookie;
    }else{
        return '';
   }
}

function getCookie(c_name){
	if (document.cookie.length>0){
		c_start=document.cookie.indexOf(c_name + "=");
		if (c_start!=-1){ 
		    c_start=c_start + c_name.length+1 ;
		    c_end=document.cookie.indexOf(";",c_start);
		    if (c_end==-1) c_end=document.cookie.length
		    return unescape(document.cookie.substring(c_start,c_end));
		} ;
	};
	return "";
};

function removeCookie(key) {
	setCookie(key, '', -1);
};

//拖拽
function drag(obj,obj1) {
	obj.onmousedown = function(ev) {
		var ev = ev || window.event;
		var disX = ev.clientX - obj1.offsetLeft;
		var disY = ev.clientY - obj1.offsetTop;
		if ( obj.setCapture ) {
			obj.setCapture();
		};
		document.onmousemove = function(ev) {
			var ev = ev || window.event;
			obj1.style.left = ev.clientX - disX + 'px';
			obj1.style.top = ev.clientY - disY + 'px';
		};
		document.onmouseup = function() {
			document.onmousemove = document.onmouseup = null;
			if ( obj.releaseCapture ) {
				obj.releaseCapture();
			};
		};
		return false;
	};
};

(function(){
function minMaxRandom(under,over){
	switch(arguments.length){
		case 1: return parseInt(Math.random()*under);
		case 2: return (parseInt(Math.random()*(over-under+1))+parseInt(under));
		default:return 0;
	};
};

function Request() {
};
Request.prototype = {
	ajax : function(args) {
		this.options = {
			type : 'GET',
			dataType : 'text',
			async : true,
			avatar : null,
			contentType : 'application/x-www-form-urlencoded',
			url : 'about:blank',
			data : {},
			success : {},
			error : {}
		};
		if (!args) {
			console.error('please fill in any parameters first!');
			return;
		} else if (!args.url) {
			console.error('url is required parameters, please check your parameters!');
			return;
		} else if (!args.success || typeof args.success != 'function') {
			console.error('the callback function is lost!');
			return;
		}
		this.shift(this.options, args);
		this.send();
	},
	jsonp : function(args) {
		this.options = {
			type : 'JSONP',
			jsonpName : '',
			dataType : 'text',
			async : true,
			avatar : null,
			url : 'about:blank',
			success : function(){},
			data : {}
		};
		if (!args) {
			console.error('please fill in any parameters first!');
			return;
		} else if (!args.url) {
			console.error('url is required parameters, please check your parameters!');
			return;
		} else if (!args.jsonpName) {
			args.jsonpName = 'jsonpCallbackFunctionNo' + new Date().getTime() + minMaxRandom(0, 9999);
		};

		this.shift(this.options, args);
		if (window[this.options.jsonpName] && window[this.options.jsonpName] !== this.options.success) {
			console.error('jsonpName already exists!');
			return;
		}
		window[this.options.jsonpName] = this.options.success;
		this.create();
	},
	create : function() {
		var script = document.createElement('script'), argStr = /[\?]/g.test(this.options.url) ? '&' : '?';
		for (var key in this.options.data) {
			argStr += key + '=' + this.options.data[key] + '&';
		}
		argStr = argStr + 'callback=' + this.options.jsonpName;
		script.async = this.options.async;
		script.src = this.options.url + (argStr == '?' ? '' : argStr);
		document.getElementsByTagName('head')[0].appendChild(script);
	},
	XmlHttp : function() {
		var xmlHttp;
		try {
			xmlhttp = new XMLHttpRequest();
		} catch(e) {
			try {
				xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
			} catch(e) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
		};
		if (!xmlhttp) {
			return false;
		};
		return xmlhttp;
	},
	send : function() {
		var xmlHttp = this.XmlHttp(), linkSign = /[\?]/g.test(this.options.url) ? '&' : '?', argStr = '', _this = this, length = this.options.data ? this.options.data.length : 0;
		for (var key in this.options.data) {
			argStr += key + '=' + this.options.data[key] + '&';
		}
		argStr = argStr.replace(/\&$/g, '');
		if (this.options.type.toUpperCase() == 'GET') {
			xmlHttp.open(this.options.type, this.options.url + (argStr == '' ? '' : linkSign + argStr), this.options.async);
		} else {
			xmlHttp.open(this.options.type, this.options.url, this.options.async);
		}
		xmlHttp.setRequestHeader('Content-Type', this.options.contentType);
		xmlHttp.onreadystatechange = function() {
			if (xmlHttp.readyState == 4) {
				if (xmlHttp.status == 200 || xmlHttp.status == 0) {
					if ( typeof _this.options.success == 'function') {
						var responseData = _this.options.dataType == 'text' ? xmlHttp.responseText : xmlHttp.responseXML;
						_this.options.success(responseData, _this.options.dataType,_this.options.avatar);
					}
					xmlHttp = null;
				} else {
					if ( typeof _this.options.error == 'function')
						_this.options.error('Server Status: ' + xmlHttp.status);
				}
			};
		};
		xmlHttp.send(this.options.type.toUpperCase() == 'POST' ? argStr : null);
	},
	shift : function(o, args) {
		for (var i in args) {
			o[i] = args[i];
		};
		return o;
	}
}; 
	
 window['Request'] = function(){
 	return new Request();
 };
})();

/*用法：
var req = Request();  //方法实例化
req.jsonp({
	url:iUrl+'&content='+str, //请求路径及数据 
	success:function(d){
	    //请求成功
	}
})

req.ajax({	
	url:iUrl,  //请求路径
	type:'post',  //请求方式  get||post
	data:{proID:proID,type:type},  //请求数据
	success:function(str){
	    //请求成功
	},
	error:function(){
	    alert('加载失败')
	}
})
*/

/* 这个会让消失的图片在1秒内回来 */
function startMove(obj, json, fnEnd){
	clearInterval(obj.timer);
	obj.timer=setInterval(function (){
		var bStop=true;
		for(var attr in json){
		    var cur=0;
		    if(attr=='opacity'){
		        cur=Math.round(parseFloat(getStyle(obj, attr))*100);
		    }else{
		        cur=parseInt(getStyle(obj, attr));
		    }
		    var speed=(json[attr]-cur)/6;
		    speed=speed>0?Math.ceil(speed):Math.floor(speed);
		    if(cur!=json[attr])
		    bStop=false;
		    if(attr=='opacity'){
				obj.style.filter='alpha(opacity:'+(cur+speed)+')';
				obj.style.opacity=(cur+speed)/100;
		    }else{
		        obj.style[attr]=cur+speed+'px';
		    } ;
		};
		if(bStop){
		    clearInterval(obj.timer);
		    if(fnEnd) fnEnd();
		};
	}, 30); 
};

function addClass(obj, sClass) { 
    var aClass = obj.className.split(' ');
    if (!obj.className) {
        obj.className = sClass;
        return;
    };
    for (var i = 0; i < aClass.length; i++) {
        if (aClass[i] === sClass) return;
    };
    obj.className += ' ' + sClass;
};

function removeClass(obj, sClass) { 
    var aClass = obj.className.split(' ');
    if (!obj.className) return;
    for (var i = 0; i < aClass.length; i++) {
        if (aClass[i] === sClass) {
            aClass.splice(i, 1);
            obj.className = aClass.join(' ');
            break;
        }
    };
};

function getByClass(oParent, sClass)
{
	var aEle=oParent.getElementsByTagName('*');
	var aResult=[];
	var re=new RegExp('\\b'+sClass+'\\b', 'i');
	var i=0;
	 
	for(i=0;i<aEle.length;i++)
	{
	    if(re.test(aEle[i].className))
	    {
	        aResult.push(aEle[i]);
	    }
	};
	 
	return aResult;
};

/* 输入框里的字符添加千位符 */
function perThousand(){
    this.value = this.value.replace(/\,/ig,'').replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, "$1,");
};
/*oInput.onfocus = function(){
	this.value = this.value.replace(/,|\s/g, '');
}
oInput.onblur = function(){
	this.value = Convert(this.value);
}
function Convert(money) {
    var s = money; //获取小数型数据
    s += "";
    if (s.indexOf(".") == -1) s += ".0"; //如果没有小数点，在后面补个小数点和0
    if (/\.\d$/.test(s)) s += "0"; //正则判断
    while (/\d{4}(\.|,)/.test(s)) //符合条件则进行替换
        s = s.replace(/(\d)(\d{3}(\.|,))/, "$1,$2"); //每隔3位添加一个
    return s;
}*/

function getArrayItems(arr, num) {
    //新建一个数组,将传入的数组复制过来,用于运算,而不要直接操作传入的数组;
    var temp_array = new Array();
    for (var index in arr) {
        temp_array.push(arr[index]);
    };
    //取出的数值项,保存在此数组
    var return_array = new Array();
    for (var i = 0; i<num; i++) {
        //判断如果数组还有可以取出的元素,以防下标越界
        if (temp_array.length>0) {
            //在数组中产生一个随机索引
            var arrIndex = Math.floor(Math.random()*temp_array.length);
            //将此随机索引的对应的数组元素值复制出来
            return_array[i] = temp_array[arrIndex];
            //然后删掉此索引的数组元素,这时候temp_array变为新的数组
            temp_array.splice(arrIndex, 1);
        } else {
            //数组中数据项取完后,退出循环,比如数组本来只有10项,但要求取出20项.
            break;
        };
    };
    return return_array;
};

function bind(obj, evname, fn) {
	if (obj.addEventListener) {
		obj.addEventListener(evname, fn, false);
	} else {
		obj.attachEvent('on' + evname, function() {
			fn.call(obj);
		});
	};
};
//对象绑定函数  用法：
/*bind(document, 'click', fn1);*/

//判断手机号
function checkMobile(s){
	var regu =/^[1][34578][0-9]{9}$/;
	return regu.test(s);
};
//判断6-16位字符
function testNum(s){
	var reg=/^(([a-zA-Z]+[0-9]+)|([0-9]+[a-zA-Z]+))[a-zA-Z0-9]*$/;
	var reg1=/^\S{6,16}$/;
	return reg.test(s) && reg1.test(s);
};
//判断身份证号码
function checkCard(s) {
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
    return reg.test(s);
};
//验证中文
function checkChinese(s) {
    var re = /^[\u4e00-\u9fa5]+$/; 
    return re.test(s);
};
/*
用途：检查输入对象的值是否符合E-Mail格式
输入：str 输入的字符串
返回：如果通过验证返回true,否则返回false
*/
function isEmail( str ){
	var myReg = /^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
	return myReg.test(str);
}
//判断11位字符
function lessThan(s){
	var reg=/^(0|[1-9]\d{0,10})$/;
	return reg.test(s);
};
function checkUserName(s){
	var regu =/^[a-zA-Z_][a-zA-Z0-9_]{1,29}$/;
	return regu.test(s);
};
function illegalCharactors(s){     // 只包含数字、字母和下划线
	var reg = /^[a-zA-Z0-9_]+$/;
	return reg.test(s);
};
// 分页
// 用法：当前页className="on"
/*page({
	id:'div1',
	nowNum:1,
	allNum:10,
	callBack:function(now,all){
		alert('当前页:'+ now + ',总共页:'+ all);
	}
});*/
function page(opt){
    if(!opt.id){return false};
    var obj = document.getElementById(opt.id);
    var nowNum = opt.nowNum || 1;
    var allNum = opt.allNum || 5;
    var callBack = opt.callBack || function(){};
    if(nowNum>=2){
    	var oA = document.createElement('a');
    	oA.href = '#' + (nowNum - 1);
    	oA.innerHTML = '上一页';
    	obj.appendChild(oA);
    };
    if(nowNum>=4 && allNum>=6){
        var oA = document.createElement('a');
        oA.href = '#1';
        oA.innerHTML = '1';
        obj.appendChild(oA);
        if(nowNum>=5){
            var oSpan = document.createElement('span');
            oSpan.innerHTML = '...';
            obj.appendChild(oSpan);
        };
    };
    if(allNum<=5){
    	for(var i=1;i<=allNum;i++){
    		var oA = document.createElement('a');
    		oA.href = '#' + i;
            oA.innerHTML = i;
    		obj.appendChild(oA);
    	};
    }else{
    	for(var i=1;i<=5;i++){
    		var oA = document.createElement('a');
    		if(nowNum == 1 || nowNum == 2){
    			oA.href = '#' + i;
                oA.innerHTML = i;
    		}else if((allNum - nowNum) == 0 || (allNum - nowNum) == 1){
    			oA.href = '#' + (allNum - 5 + i);
                oA.innerHTML = (allNum - 5 + i);
    		}else{
    			oA.href = '#' + (nowNum - 3 + i);
                oA.innerHTML = (nowNum - 3 + i);
    		};
    		obj.appendChild(oA);
    	};
    };
    if((allNum - nowNum) >= 3 && allNum >= 6){
        if(allNum >= 7 && (allNum - nowNum) >= 4){
            var oSpan = document.createElement('span');
            oSpan.innerHTML = '...';
            obj.appendChild(oSpan);
        };
        var oA = document.createElement('a');
        oA.href = '#' + allNum;
        oA.innerHTML = allNum;
        obj.appendChild(oA);
    };
    if((allNum - nowNum) >= 1){
    	var oA = document.createElement('a');
    	oA.href = '#' + (nowNum + 1);
    	oA.innerHTML = '下一页';
    	obj.appendChild(oA);
    };
    var aA = obj.getElementsByTagName('a');
    for(var i=0;i<aA.length;i++){
        if(aA[i].innerHTML == nowNum){
            aA[i].className = "on";
        }
    	aA[i].onclick = function(){
    		var nowNum = parseInt(this.getAttribute('href').substring(1));
    		obj.innerHTML = '';
    		page({
    			id:opt.id,
    			nowNum:nowNum,
    			allNum:allNum,
    			callBack:opt.callBack
    		});
    		callBack(nowNum,allNum);
    		return false;
    	};
    };
};

/*var addStaff = new PopUpBox();
addStaff.init({
	w:400,
	h:600,
	iNow:0,          // 确保一个对象只创建一次
	title : '我是标题!!!',
	opacity:0.7,
	mark:false,
	tBar:true,
	content:login,
	callback:function(){alert(1)}
});*/
function PopUpBox(){
	this.t = null;
	this.oParent = null;
	this.oDiv = null;
	this.oTDiv = null;
	this.settings = {   // 默认参数
		w:600,          // 弹窗宽
		h:300,          // 弹窗高
		mark : true,    // 是否有遮罩，默认有
		opacity:0.5,    // 遮罩默认透明度
		drag:true,      // 是否可拖动，默认可以拖到
		dir:'center',   // 弹窗位置，默认居中
		time:null,      // 自动关闭窗口的时间 为空或者'undefined'则不关闭
		tBar:true,      // 是否有标题条，默认有标题条
		title : '',     // 标题名称
		close:true,     // 是否有关闭按钮，默认有关闭按钮
		cBar:true,      // 是否有内容，默认有内容
		content:'',     // 内容
		makesure:true,  // 是否有确定按钮，默认有确定按钮
		cancel:true,    // 是否有取消按钮，默认有取消按钮
		callback:null,   //弹窗显示后回调函数
		btnAlign: "right"
	};
};
PopUpBox.prototype = {
	constructor:PopUpBox,
	json:{},
	init:function(opt){
		this.extend(this.settings,opt);
		if( this.json[opt.iNow] == undefined ){
			this.json[opt.iNow] = true;
		};
		if( this.json[opt.iNow] ){
			this.createFrame();
			this.json[opt.iNow] = false;
		};
	},
	createFrame:function(){    // 创建大外框
		this.oParent = document.createElement('div');
		this.oDiv = document.createElement('div');
		this.oDiv.style.cssText = "width:"+this.settings.w+"px"+";height:"+this.settings.h+"px"+";position:fixed;border:1px solid #666;z-index:2;background-color:#fff;z-index:9999999;"
        this.oDiv.style.left = (this.viewWidth() - this.settings.w)/2 + "px";
        this.oDiv.style.top = (this.viewHeight() - this.settings.h)/2 + "px";
		this.oParent.appendChild(this.oDiv);
		document.body.appendChild(this.oParent);
		if(this.settings.mark){
			this.createMark();
		};
		if(this.settings.tBar){
			this.createTitle();
		};
		if(this.settings.cBar){
			this.createContent();
		};
		if(this.settings.makesure || this.settings.cancel){
			this.createBtn();
		};
		if(this.settings.time && this.settings.time != 0){
			var This = this;
			this.t && clearTimeout(this.t);
			this.t = setTimeout(function(){
				document.body.removeChild(This.oParent);
				This.json[This.settings.iNow] = true;
			},This.settings.time);
		}
	},
	createTitle:function(){     // 创建标题
		this.oTDiv = document.createElement('div');
		this.oTDiv.style.cssText = "height:45px;background-color:#d7dee4;overflow:hidden;line-height:45px;font-size:14px;";
        this.oTDiv.innerHTML = "<span style='float:left;padding-left:20px;'>"+this.settings.title+"</span><span style='float:right;margin-right:10px;cursor:pointer;font-size:16px;'></span>";
        if(this.settings.close){
        	this.createClose();
        };
        if(this.settings.drag){
        	this.drag(this.oTDiv,this.oDiv);
        }
        if(this.oDiv.children[0]){
        	this.oDiv.insertBefore(this.oTDiv,this.oDiv.children[0]);
        }else{
        	this.oDiv.appendChild(this.oTDiv);
        }
	},
	createContent:function(){    // 内容
		this.oCDiv = document.createElement('div');
		this.oCDiv.style.cssText = "margin-bottom:50px;padding:10px 20px;";
        this.oCDiv.innerHTML = this.settings.content;
        this.oDiv.appendChild(this.oCDiv);
	},
	createBtn:function(){    // 确定和取消按钮
		var This = this;
		this.oBDiv = document.createElement('div');
        this.oBDiv.style.cssText = "height:50px;background-color:#f6f6f6;bottom:0;position:absolute;width:90%;text-align:"+This.settings.btnAlign+";padding:0 5%;line-height:50px;";
        if(this.settings.makesure){
        	this.makesureBtn = document.createElement('span');
        	this.makesureBtn.style.cssText = "display:inline-block;height:34px;width:60px;color:#fff;background-color:#4eb6e4;vertical-align: middle;margin:8px;line-height:36px;border-radius:4px;text-align:center;cursor:pointer;";
        	this.makesureBtn.innerHTML = "确定";
        	this.oBDiv.appendChild(this.makesureBtn);
        	this.makesureBtn.onclick = function(){
        		This.settings.callback && This.settings.callback();
        		document.body.removeChild(This.oParent);
        		This.json[This.settings.iNow] = true;
        	};
        };
        if(this.settings.cancel){
        	this.cancelBtn = document.createElement('span');
        	this.cancelBtn.style.cssText = "display:inline-block;height:34px;width:60px;color:#fff;background-color:#4eb6e4;vertical-align: middle;margin:8px;line-height:36px;border-radius:4px;text-align:center;cursor:pointer;";
        	this.cancelBtn.innerHTML = "取消";
        	this.oBDiv.appendChild(this.cancelBtn);
        	this.cancelBtn.onclick = function(){
        		document.body.removeChild(This.oParent);
        		This.json[This.settings.iNow] = true;
        	};
        };
        this.oDiv.appendChild(this.oBDiv);
	},
	createClose:function(){    // 右上角关闭按钮
		var This = this;
		this.oSpan = document.createElement('span');
    	this.oSpan.id = "close";
    	this.oSpan.style.cssText = "float:right;margin-right:10px;cursor:pointer;font-size:16px;";
    	this.oSpan.innerHTML = "X";
    	this.oTDiv.appendChild(this.oSpan);
    	this.oSpan.onclick = function(){
			document.body.removeChild(This.oParent);
			This.json[This.settings.iNow] = true;
		};
	},
	createMark:function(){    // 创建遮罩层
		this.oMark = document.createElement('div');
		this.oMark.style.cssText = "width:"+document.documentElement.scrollWidth+"px"+";height:"+document.documentElement.scrollHeight+"px"+";opacity:"+this.settings.opacity+";filter:alpha(opacity="+this.settings.opacity*100+");background-color:#000;position:absolute;left:0;top:0;z-index:1;";
		this.oParent.appendChild(this.oMark);
	},
	viewWidth:function(){    // 可视区宽
		return document.documentElement.clientWidth;
	},
	viewHeight:function(){   // 可视区高
		return document.documentElement.clientHeight;
	},
	extend:function(obj1,obj2){
		for(var i in obj2){
			obj1[i]=obj2[i];
		};
	},
	drag:function(obj,obj1){  // 拖拽
		obj.onmousedown = function(ev) {
			var ev = ev || window.event;
			var disX = ev.clientX - obj1.offsetLeft;
			var disY = ev.clientY - obj1.offsetTop;
			if ( obj.setCapture ) {
				obj.setCapture();
			};
			document.onmousemove = function(ev) {
				var ev = ev || window.event;
				obj1.style.left = ev.clientX - disX + 'px';
				obj1.style.top = ev.clientY - disY + 'px';
			};
			document.onmouseup = function() {
				document.onmousemove = document.onmouseup = null;
				if ( obj.releaseCapture ) {
					obj.releaseCapture();
				};
			};
			return false;
		};
	}
};



