$(function() {
			$("#username").blur(function() {
				$.ajax({
					type : 'POST',
					url : 'register',
					data : {
						"username" : $("#username").val()
					},
					dataType : 'json',
					success : function(r) {
						$("#usernameTips").text(r.message);
					},
					error : function() {
						$("#usernameTips").text("");
					}
				});
			});

			$("#email").blur(function() {
				$.ajax({
					type : 'POST',
					url : 'register',
					data : {
						"email" : $("#email").val()
					},
					dataType : 'json',
					success : function(r) {
						$("#emailTips").text(r.message);
					},
					error : function() {
						$("#emailTips").text("");
					}
				});
			});
			
			$("#edit_email").blur(function() {
				$.ajax({
					type : 'POST',
					url : 'profile',
					data : {
						"id"	: $("#userId").val(),
						"email" : $("#edit_email").val()
					},
					dataType : 'json',
					success : function(r) {
						$("#edit_emailTips").text(r.message);
					},
					error : function() {
						$("#edit_emailTips").text("");
					}
				});
			});

			$("#registerForm").validate({
				rules : {
					username : {
						required : true,
						minlength : 3,
						maxlength : 20
					},
					password : {
						required : true,
						minlength : 5
					},
					email : {
						required : true,
						email : true
					},
					name  : {
						required : true,
						minlength : 3,
						maxlength : 20
					}
				},
				messages : {
					username : {
						required : "*请输入用户名",
						minlength : "*用户名最少3个字符",
						maxlength : "*用户名最多20个字符"
					},
					password : {
						required : "*请输入密码",
						minlength : "*密码长度不能小于 5 个字母"
					},
					email : "*请输入一个正确的邮箱",
					name  : {
						required : "*请输入姓名",
						minlength : "*姓名最少2个字符",
						maxlength : "*姓名最多20个字符"
					}
				}
			})
			
			$("#loginForm").validate({
				rules : {
					username : {
						required : true,
						minlength : 3,
						maxlength : 20
					},
					password : {
						required : true,
						minlength : 5
					}
				},
				messages : {
					username : {
						required : "*请输入用户名",
						minlength : "*用户名最少3个字符",
						maxlength : "*用户名最多20个字符"
					},
					password : {
						required : "*请输入密码",
						minlength : "*密码长度不能小于 5 个字母"
					}
				}
			});

			$("#userForm").validate({
				rules : {
					password : {
						required : true,
						minlength : 5
					},
					email : {
						required : true,
						email : true
					},
					name  : {
						required : true,
						minlength : 3,
						maxlength : 20
					}
				},
				messages : {
					password : {
						required : "*请输入密码",
						minlength : "*密码长度不能小于 5 个字母"
					},
					email : "*请输入一个正确的邮箱",
					name  : {
						required : "*请输入姓名",
						minlength : "*姓名最少2个字符",
						maxlength : "*姓名最多20个字符"
					}
				}
			});
			
		})