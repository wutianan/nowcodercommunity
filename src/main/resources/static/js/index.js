$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");

	//向服务器发送请求
 	var title =$("#recipient-name").val();
 	var content = $("#message-text").val();
 	$.post(
 		CONTEXT_PATH + "/discuss/add",
		{"title":title,"content":content},
		function (data){
 			data = $.parseJSON(data);
 			//在提示框中显示提示消息
			$("#hintBody").text(data.msg);
			//显示提示框
			$("#hintModal").modal("show");
			setTimeout(function(){
				$("#hintModal").modal("hide");
				//刷新页面
				if(data.code == 0){
					window.location.reload();
				}
			}, 2000);
		}
	);

}