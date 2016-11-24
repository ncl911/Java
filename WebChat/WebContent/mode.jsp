<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:300px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">修改密码</h4>
			</div>
			<div class="input-group">
				<span class="input-group-addon">旧密码</span> <input type="text"
					class="form-control" id="ps" placeholder="旧密码">
			</div>
			<div class="input-group">
				<span class="input-group-addon">新密码</span> <input type="text"
					class="form-control"  id="pswd" placeholder="新密码">
			</div>


			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary" id="btn_change1" data-dismiss="modal">提交更改</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
</div>
<!-- /.modal -->

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:300px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">修改用户名</h4>
			</div>
			<div class="input-group">
				<span class="input-group-addon">输入密码</span> <input type="text"
					class="form-control" id="paswd" placeholder="密码">
			</div>
			<div class="input-group">
				<span class="input-group-addon">新用户名</span> <input type="text"
					class="form-control"  id="nam" placeholder="用户名">
			</div>


			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary" id="btn_change2" data-dismiss="modal">提交更改</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
</div>
<!-- /.modal -->