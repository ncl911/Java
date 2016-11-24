/**
 * 
 */
function init_control() {
	$("#zone_left").height(window.innerHeight), $("#zone_left .list-group").height(window.innerHeight - 225), $("#div_msgpanel").width(.45 * (window.innerWidth - 100)), $("#div_msgpanel").height(window.innerHeight - 200), $("#div_msgbox").height(window.innerHeight - 274), $("#div_privmsg").height(window.innerHeight - 274), $("#nav_action").width(window.innerWidth - $("#div_msgpanel").width() - $("#zone_left").width() - 40), $("#zone_left").show(), $("#zone_center").show()
}

var ws,dic_userlist,sel_userid,isloading, index_loading,isopenEmoji, index_emoji,tick_heartpac = null, tick_titletips, isconning, isforceOut, isFocus ,isconning,index_img,isopenImg; 
 var ws_adrr = "ws://121.42.208.2:8080/WebChat/chat";
function fun_initWebSocket() {
	window.console.log("开始连接---");
	return isconning ? (layer.msg("正在连接,请稍后..."), void 0) : (
			isconning = !0, 
	ws = new WebSocket(ws_adrr),
	// 监听消息
	ws.onmessage = function(event) {		
		log(event);
	},ws.onopen = function(event) {	
		isconning = !1,
		ws.send(JSON.stringify({
			head : "add",
			id:$.cookie("user_id"),
			name :$.cookie("user_name"),
		}));
		$.cookie("isonline", "online", {
			expires: 2
		})
		$("#btn_conn").attr("disabled", !0), layer.msg("欢迎登录~");
	},ws.onclose = function(event) {
		isconning = !1, $("#btn_conn").attr("disabled", !1), layer.alert("连接断开...", {
			icon: 5,
			title: "提示"
		})
	  $.cookie("isonline", "offline", {
			expires: 30
		})
	},void 0)
}
var log = function(s) {
	if (document.readyState !== "complete") {
		log.buffer.pust(s);
	} else {
		window.console.log("收到数据："+s.data);
		var b = $.parseJSON(s.data);
		switch (b.code) {
		case 0:
			layer.msg(b.msg), void 0;
			break;
		case 1:
			fun_bindList(b);
			break;
		case 2:
			fun_newuser(b);
			break;
		case 3:
			fun_userlogout(b);
			break;
		case 4:
			fun_userchgname(b);
			break;
		case 5:
			fun_recbrodata(b);
			break;
		case 6:
			fun_bindtoallmsg(b);
			break;
		case 7:
			fun_fromusermsg(b);
			break;
		case 8:
			fun_getprivmsg(b);
			break;

		}
	}
}
function sendJson(a, b, c) {	
		ws.send(JSON.stringify({
	    head: a,
		id: $.cookie("user_id"),
		toid:b,
		msg: c
	}))
}
function sendMesage(a, b, c,d) {	
	ws.send(JSON.stringify({
    head: a,
    fromid:$.cookie("user_id"),
	fromname:$.cookie("user_name"),
	toid:b,
	toname: c,
	msg: d
}))
}

// 在线用户列表
function fun_bindList(a) {
	var d, b = a.userlist,
		c = "";
	for (d = 0; d < b.length; d++) b[d].id != $.cookie("user_id") && (dic_userlist[b[d].id] = b[d].name,c += "<a id='userid_" + b[d].id + "' class='list-group-item' onfocus='this.blur()' onclick='click_userlist(id);'><span class='badge' style='background-color: red;'></span><span style='margin-right:5px;' class='glyphicon glyphicon-user'></span><span class='nickname'>" + b[d].name + "<span></a>");
	$("#user_list").html(c), $("#nowusers_count").text(b.length+1)
}
// 插入新上线用户
function fun_newuser(a) {
	dic_userlist[a.user.id] || ($("#user_list").prepend("<a id='userid_" + a.user.id + "' class='list-group-item' onfocus='this.blur()' onclick='click_userlist(id);'><span class='badge'></span><span style='margin-right:5px;' class='glyphicon glyphicon-user'></span><span class='nickname'>" + a.user.name + "</span></a>"), dic_userlist[a.user.id] = a.user.name, $("#nowusers_count").text(parseInt($("#nowusers_count").text()) + 1))
}
// 用户下线更新
function fun_userlogout(a) {
	$("#userid_" + a.user.id).remove(), dic_userlist[a.user.id] && (delete dic_userlist[a.user.id], $("#nowusers_count").text(parseInt($("#nowusers_count").text()) - 1), sel_userid == a.user.id && fun_showPublicZone())
}
// 修改昵称
function fun_userchgname(a) {
	dic_userlist[a.user.id] && (dic_userlist[a.user.id] = a.user.name, $("#userid_" + a.user.id+" .nickname").text(a.user.name)), $("#div_msgbox").append('<div class="chat_msg"><div>' + a.user.time + "</div>" + a.user.beforename + "->" + a.user.name + "</div>"), $("#div_msgbox").scrollTop($("#div_msgbox")[0].scrollHeight)
}
// 显示群聊消息
function fun_recbrodata(a) {
	haveTitleTips("新消息"),$("#div_msgbox").append(fun_chatbox(a.user)), $("#div_msgbox").scrollTop($("#div_msgbox")[0].scrollHeight)
}
// 公共聊天记录
function fun_bindtoallmsg(a) {
	var b, c;
	for ($("#div_msgbox").html(""), b = a.contents_list, c = 0; c < b.length; c++) $("#div_msgbox").append(fun_chatbox(b[c]));
	$("#div_msgbox").scrollTop($("#div_msgbox")[0].scrollHeight)
}
// 显示私聊消息
function fun_fromusermsg(a) {
	var d, b = a.fromuser;
	a.touser, haveTitleTips("新私信"),a.touser,$.cookie("user_id")== b.id ? ($("#div_privmsg").append(fun_chatbox(b)), $("#div_privmsg").scrollTop($("#div_privmsg")[0].scrollHeight)) : sel_userid != b.id ? (d = ($("#userid_" + b.id + " .badge").text() ? parseInt($("#userid_" + b.id + " .badge").text()) : 0) + 1, $("#userid_" + b.id + " .badge").text(d), $("#userid_" + b.id).insertBefore($("#user_list")[0].childNodes[0])) : ($("#div_privmsg").append(fun_chatbox(b)), $("#div_privmsg").scrollTop($("#div_privmsg")[0].scrollHeight))
}
// 私聊记录
function fun_getprivmsg(a) {
	var c, b = a.contents_list;
	for ($("#div_privmsg").html(""), c = 0; c < b.length; c++) $("#div_privmsg").append(fun_chatbox(b[c]));
	$("#div_msgbox").hide(), $("#div_privmsg").show(), $("#div_privmsg").scrollTop($("#div_privmsg")[0].scrollHeight)
}
function fun_chatbox(a) {
	var c, d, b = "";
	switch (a.code) {
		case 4:
			b = '<div class="chat_msg"><div>' + a.time + "</div>" + a.name + " --> " + a.content + "</div>";// 显示改名通知
			break;
		default:
			c = a.id == $.cookie("user_id"), d = c ? "<span style='color:#d2d2d2;'>" + a.time + "</span> <span style='font-weight:900;color:#6d6d6d'>" + a.name + "</span>" : "<span style='font-weight:900;color:#6d6d6d'>" + a.name + "</span> " + "<span style='color:#d2d2d2;'>" + a.time + "</span>", b = "<div class='chatbox' style='" + (c ? "float:right" : "float:left") + "' ><div style='text-align:" + (c ? "right" : "left") + "'>" + d + "</div>" + "<div class='chatarrow' style='" + (c ? "right:5px;border-bottom: 8px solid #ffe6b8;" : "left:5px;border-bottom: 8px solid #cfffcf;") + "'></div><div class='chat" + (c ? " selfchat" : "") + "' style='max-width:" + ($("#div_msgpanel").width() - 50) + "px;'>" + chg_emoji(a.content, a.id) + "<br />" + "</div>" + "</div><div class='clearboth'></div>"
	}
	return b
}
// 在线用户点击监听
function click_userlist(a) {
	a = a.split("_")[1], sel_userid != a ? ($("#userid_" + sel_userid).removeClass("active"), $("#userid_" + a).addClass("active"), $("#msg_title").html('和[<span style="color:orange">' + dic_userlist[a] + "</span>]的聊天记录"), sel_userid = a, ws && 1 == ws.readyState && (sendJson("getprivate", sel_userid, "get"), $("#userid_" + sel_userid + " .badge").text(""))) : fun_showPublicZone()
}
function fun_showPublicZone() {
	$("#userid_" + sel_userid).removeClass("active"), sel_userid = "", $("#msg_title").html("公共聊天记录"), $("#div_msgbox").show(), $("#div_privmsg").hide(), $("#div_msgbox").scrollTop($("#div_msgbox")[0].scrollHeight)
}
function haveTitleTips(a) {
	isFocus || tick_titletips ? isFocus || haveSoundTips() : (haveSoundTips(), tick_titletips = setInterval(function() {
		document.title = -1 == document.title.indexOf(a) ? "[" + a + "]" : "[　　　]-大锤聊天室", isFocus && (clearInterval(tick_titletips), tick_titletips = null, document.title = "大锤聊天室")
	}, 500))
}
function haveSoundTips() {
		var a = document.getElementById("msg_sound");
		a.play()	
}

$(function() {
	 ws_adrr = "ws://121.42.208.2:8080/WebChat/chat";
	$("#btn_conn").attr("disabled", !0);
	sel_userid = "",isloading = null,dic_userlist = {},isconning = !1,tick_titletips = null,isFocus = !0, isforceOut = !1,isopenEmoji = !1,index_img=0,isopenImg=!1;
	fun_initWebSocket();
	window.onmousedown = function() {
		var a = window.event,
			b = a.target.className,
			c = a.target.id;
		if (-1 == b.indexOf("emoji")) {
			if ("layui-layer-content" == b) return;
			isopenEmoji = !1, layer.close(index_emoji)
		}
		if (-1 == c.indexOf("uploadimg")) {
			if ("absFileInput" == c || "javascript:" == a.target.protocol || "layui-layer-content" == b || "laypage_curr" == b || -1 != c.indexOf("laypage") || "" == c) return;
			isopenImg = !1, layer.close(index_img)
		}
	},
	window.onblur = function() {
		isFocus = !1
	}, window.onfocus = function() {
		isFocus = !0
	},$("#inp_nickname").val($.cookie("user_name")),
	$("#btn_conn").click(function() {
	fun_initWebSocket();
}),$("#btn_say").click(function() {
	var a = $.trim($("#inp_say").val());
	if (ws && 1 == ws.readyState) {
		if (!a) return layer.msg("不能输入空字符"), void 0;
			sel_userid ? sendMesage("hide", sel_userid, dic_userlist[sel_userid],a) :sendJson("groupchat", "1", a), $("#inp_say").val("")
	} else layer.msg("你已经离线，请先连接")
}),$("#btn_getnick").click(function() {
	var a=$("#inp_nickname").val();
	if (!a) return layer.msg("不能输入空字符"), void 0;
	if(a==$.cookie("user_name"))
		 return layer.msg("不能重复设置"), void 0;
	ws.send(JSON.stringify({		
	    head:"changename",
		id: $.cookie("user_id"),
		name: $.cookie("user_name"),
		chgname:a,
	}))
}), $("#user_count").click(function() {
	fun_showPublicZone()
}), $("#inp_say").keydown(function(a) {
	return 13 == a.keyCode && a.ctrlKey ? ($("#btn_say").trigger("click"), !1) : void 0
}), $("#a_uploadimg").click(function() {
	init_uploadPic()
}), $("#exit").click(function() {
	window.console.log("exit");
	window.opener=null;window.close();	
}), $("#btn_change1").click(function() {
	window.console.log("exit");	
	ws.send(JSON.stringify({
		head : "changepswd",
		id: $.cookie("user_id"),
		ps:$("#ps").val(),
		pswd :$("#pswd").val(),
	}));
}), $("#btn_change2").click(function() {
	window.console.log("exit");	
	ws.send(JSON.stringify({
		head : "changena",
		id: $.cookie("user_id"),
		ps:$("#paswd").val(),
		pswd :$("#nam").val(),
	}));
})
},
window.onbeforeunload = function(){
    ws.close();
    $.cookie("isonline", "offline", {
		expires: 2
	})
}
)
function init_initEmoji() {
	var b, a = "";
	for (b in emoji) a += "<span class='item_emoji' id='emoji_" + b + "' onclick='click_emojiItems(id);'  title='" + b + "'><img class='imgitem_emoji' src='" + emoji[b] + "' /></span>";
	$(".emoji").click(function() {
		isopenEmoji ? (isopenEmoji = !1, layer.close(index_emoji)) : (isopenEmoji = !0, index_emoji = layer.tips("<div  class='list_emoji'>" + a + "</div>", ".emoji", {
			tips: [1, "#fff"],
			time: 0,
			area: "auto",
			maxWidth: 405,
			shift: 5
		}))
	})
}
function click_emojiItems(a) {
	$("#inp_say").insertAtCaret(a.split("_")[1])
}
/*function fun_loading(a) {
	a ? isloading = setTimeout(function() {
		index_loading = layer.load()
	}, 5) : (layer.close(index_loading), clearTimeout(isloading))
}*/
function init_uploadPic() {
	if (isopenImg) isopenImg = !1, layer.close(index_img);
	else {
		isopenImg = !0, index_img = layer.tips("<div id='uploadimg_list' style='height:0px;'></div><button id='uploadimg_upload' class='btn btn-default' type='button'><span style='color:orange;margin-right:5px;' class='glyphicon glyphicon-picture'></span><span class='sp_uploadtxt'>上传</span></button><input id='uploading_file' type='file' style='display:none;' /><div id='uploadimg_pages' style='float:right;margin-top:5px;'></div>", "#a_uploadimg", {
			tips: [1, "#fff"],
			time: 0,
			area: ["20px", "20px"],
			shift: 5
		}), bind_imglist(1), bind_imgpage();
		var a = !1;
		$("#uploadimg_upload").click(function() {
			a = !1, document.getElementById("uploading_file").click()
		}), $("#uploading_file").change(function() {
			var b, c, d;
			if (!a) {
				if (b = document.getElementById("uploading_file").files[0], c = b.size / 1024, c > 3072) return a = !0, $("#uploading_file").val(""), layer.alert("图片上传失败,当前最大限制3MB", {
					icon: 5,
					title: "上传结果"
				}), !1;
				d = new FormData, d.append("upload_file", b), $("#uploadimg_upload").attr("disabled", !0), $(".sp_uploadtxt").html("上传中..."), $.ajax({
					url: "/WebChat/fileupload",
					type: "POST",
					data: d,
					cache: !1,
					contentType: !1,
					processData: !1,
					success: function(b) {
						var c, d, e;
						window.console.log(b+"收到数据=："+b);
						
						if ($("#uploadimg_upload").attr("disabled", !1), $(".sp_uploadtxt").text("上传"), a = !0, $("#uploading_file").val(""), c = $.parseJSON(b), d = !1, "Fail" == c.state) return layer.alert(c.msg, {
							icon: 5,
							title: "上传结果"
						}), void 0;
						var url=$("#inp_say").val();
						$("#inp_say").val(url+"["+c.imgurl+"]");
						layer.close(index_img);
						for (e = 0; e < uploadPic.length; e++)
							if (uploadPic[e] == c.msg) {
								d = !0;
								break
							}
						d || uploadPic.splice(0, 0, c.msg), bind_imglist(1), bind_imgpage()
					}
				})
			}
		})
	}
}
