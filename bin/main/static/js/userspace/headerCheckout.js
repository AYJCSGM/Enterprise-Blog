$(function(){
	// 首页最新\最热切换事件
	$(".nav-item .header-nav-link").click(function() {
		var url = $(this).attr("url");
		window.location=url;
	});

	// 关键字搜索
	$("#indexsearch").click(function() {
		window.location="/blogs?keyword="+$("#indexkeyword").val();
	});
})
	
