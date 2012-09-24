<?php
    /** 
     * apns_example.php
     * @apns 推送服务 PHP api
     * 详细文档见: www.push-notification.org
     * @version: 1.0.0 
     **/

	require_once("./apns.php");
	
	/**
	 * 使用前请修改 apns.php 中的  ch id 和 apiKey.
	 */
	$rs = push2dev("devId","hello world");
	
	/**  处理返回结果 **/
	if($rs === false){
		echo "网络错误,无法发送请求到指定 url\n";
	}else{
		switch($rs){
			case "-1":
				echo "服务器连接失败\n";
				break;
			case "0":
				echo "发送成功\n";
				break;
			case "1":
				echo "无权发送\n";
				break;
			case "2":
				echo "权限被阻止\n";
				break;
			case "3":
				echo "设备不在线\n";
				break;
			case "12":
				echo "api 过期\n";
				break;
			case "13":
				echo "hash 不匹配\n";
				break;
			case "14":
				echo "参数不合法\n";
				break;
			case "15":
				echo "意外错误\n";
				break;
		}	
	}
?>