$(function(){
	$.ajaxSetup({
        type: "POST",  
        error: function(jqXHR, textStatus, errorThrown){ 
        	//window.top.location.href="/ordersystem/";
        }
	})
	
	if ($.fn.datagrid){
		$.fn.datagrid.defaults.onLoadError = function() {
			
			//window.top.location.href="/ordersystem/";
		};
	}
});

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