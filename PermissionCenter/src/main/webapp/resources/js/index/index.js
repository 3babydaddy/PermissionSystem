 
if (!Array.prototype.indexOf)
{
  Array.prototype.indexOf = function(elt /*, from*/)
  {
    var len = this.length >>> 0;
    var from = Number(arguments[1]) || 0;
    from = (from < 0)
         ? Math.ceil(from)
         : Math.floor(from);
    if (from < 0)
      from += len;
    for (; from < len; from++)
    {
      if (from in this &&
          this[from] === elt)
        return from;
    }
    return -1;
  };
}
var _menus = {
             "menus":[
			           {"menuid":"1","icon":"icon-sys","menuname":"系统菜单",
				      "menus":[
						
						{"menuid":"1","menuname":"用户管理","icon":"icon-edit","url":"user/query"},
						{"menuid":"2","menuname":"角色管理","icon":"icon-edit","url":"role/query"},
						{"menuid":"3","menuname":"部门管理","icon":"icon-edit","url":"department/query"},
						{"menuid":"4","menuname":"系统管理","icon":"icon-edit","url":"system/query"},
						{"menuid":"5","menuname":"资源管理","icon":"icon-edit","url":"resource/query"},
						{"menuid":"7","menuname":"资源变更推送配置","icon":"icon-edit","url":"notification/init"},
						{"menuid":"8","menuname":"操作日志","icon":"icon-edit","url":"log/logQuery"},
						{"menuid":"6","menuname":"查询日志","icon":"icon-search","url":"logs/list"}
					    ]}
	]};
 
 var _GLOBALDATA = {};

$(document).ready(function() {
	
	 InitLeftMenu();
	 tabClose();
	 tabCloseEven();
	 
	 
	 $('#editpass').click(function() {
	    	//表单清空
	    		$("#fm_password")[0].reset();
	    		//显示div
	    		$("#w").show();
	    		//加载显示
	    		$("#w").dialog({
	    			title: "修改密码",//标题
	    	    	iconCls: "icon-add",//图标
	    	    	width: 400,//窗体的宽度
	    	    	height: 230,//窗体的高度
	    	    	modal: true //遮罩层
	    	    });
	        
	 });
	 $('#btnEp').click(function() {
	         serverLogin();
	     })

	$('#btnCancel').click(function(){closePwd();})
})

//设置登录窗口

//关闭登录窗口
function closePwd() {
    $('#w').window('close');
}



//修改密码
function serverLogin() {
	
	var $oldpwd    = $('#oldpwd');
    var $password1 = $('#password1');
    var $password2 = $('#password2');

    if ($oldpwd.val() == '') {
        msgShow('系统提示', '输入原密码！', 'warning');
        return false;
    }
    if ($password1.val() == '') {
        msgShow('系统提示', '输入新密码！', 'warning');
        return false;
    }
    if ($password2.val() == '') {
        msgShow('系统提示', '输入确认新密码！', 'warning');
        return false;
    }
    if ($password1.val() != $password2.val()) {
        msgShow('系统提示', '两次输入密码不一致', 'warning');
        return false;
    }
    if ($oldpwd.val() == $password2.val()) {
        msgShow('系统提示', '原密码和新密码一致', 'warning');
        return false;
    }
    $.post('userInfoContorller/update_password?newpass=' + $password1.val()+"&oldpwd="+$oldpwd.val(), function(msg) {
        msgShow('系统提示', msg.mes, 'info');
        $password1.val('');
        $password2.val('');
        if(msg.mes=="更新成功"){
        	closePwd();
        }
    })
    
}


function saveGlobalData(key,value) {
	
	_GLOBALDATA[key] = value;
}

function getGlobalData(key) {
	
	return _GLOBALDATA[key];
}

//初始化左侧
function InitLeftMenu() {
	
	//为id为nav的div增加手风琴效果，并去除动态滑动效果
	$("#nav").accordion({animate:false});
	
	//$.each 遍历_menu中的元素
    $.each(_menus.menus, function(i, n) {
//		if (hasPermissionModule.indexOf(n.menuid) <0) {
//		    		
//    		return true;
//    	}
		var menulist ='';
		menulist +='<ul>';
        $.each(n.menus, function(j, o) {
//        	alert(o.menuid+"<>"+j+"<>"+o.url);
//        	alert('<li><div><a ref="'+o.menuid+'" href="#" rel="' + o.url + '" ><span class="icon '+o.icon+'" >&nbsp;</span><span class="nav">' + o.menuname + '</span></a></div></li> ');
        	if (hasPermissionFunction.indexOf(o.menuid) <0) {
			        		
			    return true;
        	}
			menulist += '<li><div><a ref="'+o.menuid+'" href="#" rel="' + o.url + '" ><span class="icon '+o.icon+'" >&nbsp;</span><span class="nav">' + o.menuname + '</span></a></div></li> ';
        })
		menulist += '</ul>';

		$('#nav').accordion('add', {
            title: n.menuname,
            content: menulist,
            iconCls: 'icon ' + n.icon
        });
    });

    //当单击菜单某个选项时，在右边出现对用的内容
	$('.easyui-accordion li a').click(function(){
		
		var tabTitle = $(this).children('.nav').text();//获取超链里span中的内容作为新打开tab的标题

		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");//获取超链接属性中ref中的内容
		var icon = getIcon(menuid,icon);

		addTab(tabTitle,url,icon);//增加tab
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});

	//选中第一个
	var panels = $('#nav').accordion('panels');
	var t = panels[0].panel('options').title;
    $('#nav').accordion('select', t);
}

//获取左侧导航的图标
function getIcon(menuid){
	var icon = 'icon ';
	$.each(_menus.menus, function(i, n) {
		 $.each(n.menus, function(j, o) {
		 	if(o.menuid==menuid){
				icon += o.icon;
			}
		 })
	})

	return icon;
}

function addTab(subtitle,url,icon){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			icon:icon
		});
	}else{
		$('#tabs').tabs('select',subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function createFrame(url)
{
	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose()
{
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}
//绑定右键菜单事件
function tabCloseEven()
{
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update',{
			tab:currTab,
			options:{
				content:createFrame(url)
			}
		})
	})
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			$('#tabs').tabs('close',t);
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			//msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}


//设置登录窗口

//关闭登录窗口
function closePwd() {
    $('#w').window('close');
}
