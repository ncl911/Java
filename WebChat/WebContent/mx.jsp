<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>大锤聊天室</title>
<meta content="WebSocket 大锤聊天室" name="description">
<meta content="WebSocket 大锤聊天室" name="keywords">
<link rel="stylesheet" href="css/bootstrap.css" />
<link rel="stylesheet" href="css/index.css" /> 
</head>
<body>
	<div id="zone_left" onselectstart="return false;">
		<div style="height: 150px; margin-left: 10px;">
			<img style="float: left;" src="/WebChat/image/logo.png" />
			<div
				style="float: left; color: orange; font-size: 35px; font-weight: 900; margin-left: 25px; margin-top: 30px;">大锤聊天室</div>
			<div style="clear: both; color: gray; text-align: center;">但关键是——你说啥？</div>
		</div>
		<div style="height: 75px;">
			<div class="input-group">
				<span class="input-group-addon" style="color: orange;">你的昵称</span> <input
					id="inp_nickname" type="text" class="form-control"> <span
					id="btn_getnick" class="input-group-btn"><button
						class="btn btn-default" type="button">
						<span style="color: orange;">设置</span>
					</button></span>
			</div>
			<div id="user_count" class="list-group-item list-group-item-success"
				style="cursor: pointer;">
				<span id="nowusers_count" class="badge"
					style="background-color: green;"></span><span
					style="margin-right: 5px;" class="glyphicon glyphicon-comment"></span>当前在线人数
			</div>
		</div>
		<div id="user_list" class="list-group" style="overflow-y: auto;"></div>
	</div>
	<div id="zone_center"
		style="display: none; float: left; margin-left: 10px; margin-top: 10px;">
		<div id="div_msgpanel" class="panel panel-success">
			<div class="panel-heading">
				<div class="panel-title">
					<span id="msg_title">公共聊天记录</span>
				</div>
				<audio id="msg_sound" src="/WebChat/sourse/qq.mp3"></audio>
			</div>
			<div id="div_msgbox" class="panel-body" style="overflow-y: auto;"></div>
			<div id="div_privmsg" class="panel-body"
				style="overflow-y: auto; display: none;"></div>
		</div>
		<div style="position: relative; left: 0px; bottom: 10px;">
			<div style="float: left; margin-top: 7px;">
				<a class="emoji" style="cursor: pointer; margin-right: 10px;"
					data-toggle="popover" data-placement="top" title="表情"><img
					style="outline-width: 40px;" class="img_emoji"
					src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/5c/huanglianwx_thumb.gif" /></a>
				<a id="a_uploadimg" title="上传图片"><img
					src="/WebChat/image/uploadpic.png" height="22" width="22" /></a>
			</div>
			<div style="float: right;">
				<button id="btn_conn" type="button" class="btn btn-warning">连接</button>
				<button id="btn_say" type="button" class="btn btn-success">发送消息</button>
			</div>
		</div>
		<div>
			<textarea id="inp_say" maxlength="500" placeholder="点击用户可私聊"
				class="form-control" style="height: 125px; resize: none;"></textarea>
		</div>
	</div>
	<div id="zone_right">
		<div style="margin: 20px; float: left; color: maroon;">
		<br/>
		<br/><br/>
			该聊天室使用Html5的websocket传输协议<br /> 请使用支持Html5及websocket的浏览器<br /> <br />服务端采用事件异步java版websocket<br />
			<br />由于数据没有加密<br /> 请勿在该聊天室发送账号,密码等敏感信息。<br />
			<br/><br/><br/>
			友情链接：&nbsp;<a href="http://121.42.208.2:8080/MyStore/" target="_blank"><span style="font-weight: 900; font-size: 16px;">旭彬杂货店</span></a>
		</div>
	</div>
	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="js/layer.js"></script>
	<script type="text/javascript" src="js/laypage.js"></script>
	<script type="text/javascript" src="js/index.js?v=20160602"></script>
	<script type="text/javascript" src="js/conn.js"></script>
</body>
</html>
