/*!
 * blogedit.html 页面脚本.
 * 
 * @since: 1.0.0 2017-03-26
 * @author Way Lau <https://waylau.com>
 */
"use strict";
// # sourceURL=blogedit.js

// DOM 加载完再执行
$(function() {

	// 初始化下拉
	$('.form-control-chosen').chosen();

	// 初始化标签
	$('.form-control-tag').tagsInput({
		'defaultText' : '输入标签'
	});

	// 发布博客
	$("#submitBlog").click(function() {

		$.ajax({
			url : '/u/' + $(this).attr("userName") + '/blogs/edit',
			type : 'POST',
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify({
				"id" : Number($('#blogId').val()),
				"title" : $('#title').val(),
				"summary" : $('#summary').val(),
				"content" : $('#md').val(),
				"catalog" : {
					"id" : $('#catalogSelect').val()
				},
				"tags" : $('.form-control-tag').val()
			}),
			success : function(data) {
				if (data.success) {
					// 成功后，重定向
					window.location = data.body;
				} else {
					toastr.error("标题,摘要,内容不能为空,且至少是2个字符");
				}

			},
			error : function() {
				toastr.error("error!");
			}
		})
	})

});