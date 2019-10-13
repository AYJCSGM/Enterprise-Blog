/*!
 * blog.html 页面脚本.
 * 
 * @since: 1.0.0 2017-03-26
 * @author Way Lau <https://waylau.com>
 */
"use strict";
//# sourceURL=blog.js

// DOM 加载完再执行
$(function() {
	$.catalog("#catalog", ".post-content");
	
	// 处理删除博客事件
	
	$(".blog-content-container").on("click",".blog-delete-blog", function () { 		
		$.ajax({ 
			 url: blogUrl, 
			 type: 'DELETE', 
			 success: function(data){
				 if (data.success) {
					 // 成功后，重定向
					 window.location = data.body;
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	// 获取评论列表
	function getCommnet(blogId) {
		$.ajax({ 
			 url: '/comments', 
			 type: 'GET', 
			 data:{"blogId":blogId},
			 success: function(data){
				$("#mainContainer").html(data);
	
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	}
	
	// 提交评论
	$(".blog-content-container").on("click","#submitComment", function () {  		
		$.ajax({ 
			 url: '/comments', 
			 type: 'POST', 
			 data:{"blogId":blogId, "commentContent":$('#commentContent').val()},
			 success: function(data){
				 if (data.success) {
					 // 清空评论框
					 $('#commentContent').val('');
					 // 获取评论列表
					 getCommnet(blogId);
				 } else {
					 if($("#username").val()==null){
						 toastr.error("请先登录再评论!")
					 }
					 if($("#username").val()!=null && $("commentContent").val()==null){
						 toastr.error("评论内容不能为空!")
					 }	
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	// 删除评论
	$(".blog-content-container").on("click",".blog-delete-comment", function () { 
		$.ajax({ 
			 url: '/comments/'+$(this).attr("commentId")+'?blogId='+blogId, 
			 type: 'DELETE', 
			 success: function(data){
				 if (data.success) {
					 // 获取评论列表
					 getCommnet(blogId);
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	
	// 提交点赞
	$(".blog-content-container").on("click","#submitVote", function () { 
		if($("#username").val()==null){
			 toastr.error("请先登录再点赞!");
		}else{
			 $.ajax({ 
				 url: '/votes', 
				 type: 'POST', 
				 data:{"blogId":blogId},
				 success: function(data){
					 if (data.success) {
						 toastr.info(data.message);
						 window.location = blogUrl;
					 }
			     },
			     error : function() {
			    	 toastr.error("error!");
			     }
			 });
		}
	});
	
	// 取消点赞
	$(".blog-content-container").on("click","#cancelVote", function () { 
		$.ajax({ 
			 url: '/votes/'+$(this).attr('voteId')+'?blogId='+blogId, 
			 type: 'DELETE', 
			 success: function(data){
				 if (data.success) {
					 toastr.info(data.message);
					 window.location = blogUrl;
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	// 初始化 博客评论
	getCommnet(blogId);
	
});