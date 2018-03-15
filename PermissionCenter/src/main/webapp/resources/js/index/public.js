$(function(){
	$.ajaxSetup({
        type: "POST",  
        error: function(jqXHR, textStatus, errorThrown){ 
        	window.top.location.href="sysError";
        }
	})
	
	if ($.fn.datagrid){
		$.fn.datagrid.defaults.onLoadError = function() {
			
			window.top.location.href="sysError";
		};
	}
})

//字符串以xx结尾
String.prototype.endWith=function(endStr){
	  var d=this.length-endStr.length;
	  return (d>=0&&this.lastIndexOf(endStr)==d);
};

//父窗口调用此方法
function openWin(winTitle,winURL,width,height,callback){
    var winWidth = width==null?"90%":""+width;
    var winHeight = height==null?"90%":""+height;
    if(!winWidth.endWith("%"))winWidth+="px";
    if(!winHeight.endWith("%"))winHeight+="px";
    
    top.layer.open({
        type: 2,
        maxmin: false,
        title: winTitle,
        shade: [0.5,'#333'],
        offset: ['50px', ''], //上边距50像素
        area: [winWidth, winHeight],
        content:winURL
    }); 
    
    //保存callback，根据子窗口的index来索引
    if(callback && typeof(callback) === "function"){
        share.data(top.layer.index,callback);
    }
 }
 
 //此方法由子窗口调用，故window.name是iframe页面的name
 function closeWin(data){
     
     //取得callback
     var callback = share.remove(top.layer.getFrameIndex(window.name));
     if(callback && typeof(callback) === "function"){
         //callback初始是由父窗口创建的，所以此时回调时，仍然是父窗口执行的。
         callback(data);
     }
     
     //关闭窗口
     top.layer.close(top.layer.getFrameIndex(window.name));
 }
 
 
 var share = {

     /**
      * 跨框架数据共享接口
      * @param    {String}    存储的数据名
      * @param    {Any}        将要存储的任意数据(无此项则返回被查询的数据)
      */
     data: function (name, value) {
         var top = window.top,
             cache = top['_CACHE'] || {};
         top['_CACHE'] = cache;
         
         return value !== undefined ? cache[name] = value : cache[name];
     },
     
     /**
      * 数据共享删除接口
      * @param    {String}    删除的数据名
      */
     remove: function (name) {
         var cache = window.top['_CACHE'];
         var value = null;
         if (cache && cache[name]){
             value = cache[name];
             delete cache[name];
         }
         return value;
     }
     
 };

 Date.prototype.format = function(format) {
     var o = {
         "M+": this.getMonth() + 1, // month
         "d+": this.getDate(), // day
         "h+": this.getHours(), // hour
         "m+": this.getMinutes(), // minute
         "s+": this.getSeconds(), // second
         "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
         "S": this.getMilliseconds()
         // millisecond
     };
     if (/(y+)/.test(format))
         format = format.replace(RegExp.$1, (this.getFullYear() + "")
             .substr(4 - RegExp.$1.length));
     for (var k in o)
         if (new RegExp("(" + k + ")").test(format))
             format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
     return format;
};

 
 function roundFun(numberRound,roundDigit)   //四舍五入，保留位数为roundDigit     
{   
	if (numberRound>=0)   
	{   
		var   tempNumber   =   parseInt((numberRound   *   Math.pow(10,roundDigit)+0.5))/Math.pow(10,roundDigit);   
		return   tempNumber;   
	}   
	else     
	{   
		numberRound1=-numberRound ;  
		var   tempNumber   =   parseInt((numberRound1   *   Math.pow(10,roundDigit)+0.5))/Math.pow(10,roundDigit);   
		return   -tempNumber;   
	}   
} 
 
 
 var Common = {

		    //EasyUI用DataGrid用日期格式化
		    TimeFormatter: function (value, rec, index) {
		        if (value == undefined) {
		            return "";
		        }
		        if(jQuery.isNumeric(value)){
		        	return new Date(value).format("yyyy-MM-dd hh:mm:ss");
		        }
		        /*json格式时间转js时间格式*/
		        value = value.substr(1, value.length - 2);
		        var obj = eval('(' + "{Date: new " + value + "}" + ')');
		        var dateValue = obj["Date"];
		        if (dateValue.getFullYear() < 1900) {
		            return "";
		        }
		        var val = dateValue.format("yyyy-MM-dd hh:mm:ss");
		        return val.substr(11, 5);
		    },
		    DateTimeFormatter: function (value, rec, index) {
		    	
		        if (value == undefined || value == "" || value =="null") {
		            return "";
		        }
		        if(jQuery.isNumeric(value)){
//		        	return new Date(value).format("yyyy-MM-dd hh:mm:ss");
		        	return new Date(value).format("yyyy-MM-dd");
		        }
		        /*json格式时间转js时间格式*/
		        value = value.substr(1, value.length - 2);
		        var obj = eval('(' + "{Date: new " + value + "}" + ')');
		        var dateValue = obj["Date"];
		        if (dateValue.getFullYear() < 1900) {
		            return "";
		        }

		        return dateValue.format("yyyy-MM-dd");
		    },

		    //EasyUI用DataGrid用日期格式化
		    DateFormatter: function (value, rec, index) {
		    	if (value == undefined || value == "" || value =="null") {
		            return "";
		        }
		        if(jQuery.isNumeric(value)){
		        	return new Date(value).format("yyyy-MM-dd");
		        }
		        /*json格式时间转js时间格式*/
		        value = value.substr(1, value.length - 2);
		        var obj = eval('(' + "{Date: new " + value + "}" + ')');
		        var dateValue = obj["Date"];
		        if (dateValue.getFullYear() < 1900) {
		            return "";
		        }

		        return dateValue.format("yyyy-MM-dd");
		    }
		};
 
 $.extend($.fn.validatebox.defaults.rules, {    
	    length: {    
	        validator: function(value, param){    
	            return value.length == param[0];    
	        },    
	        message: '请录入 {0} 位数字.'   
	    },
	    maxLength : {
	    	validator:function(value,param){
	    		return value.length <= param[0];
	    	},
	    	message:'长度不能大于 {0} 位.'
	    }
	});