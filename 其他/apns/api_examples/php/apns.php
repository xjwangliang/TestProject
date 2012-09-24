<?php
    /** 
     * apns.php
     * @apns 推送服务 PHP api
     * 详细文档见: www.push-notification.org
     * @version: 1.0.0 
     **/
	if(!defined("_APNS_PHP")){
		define("_APNS_PHP",1);
		
		define("API","http://www.push-notification.org/handlers/apns_v1.php");
		/** channel id, 修改为你自己的ch id, 注意:是id,不是名称 **/
		define("CH","1");
		/** api key, 修改为你自己的api key **/
		define("API_KEY","1234567");
		
		/**
		 * @ 推送消息到设备
		 * @ 参数:
		 *     $devId: 设备id
		 *     $msg:   消息,最大长度128,保留字符[":"]
		 */
		function push2dev($devId,$msg){
			set_time_limit(60);
			$random = rand();
			$hash = md5(CH . $devId . $msg . $random . API_KEY);
			$response = @file_get_contents(API . "?ch=" . CH . "&devId=$devId&msg=" . urlencode($msg) . "&random=" . $random . "&hash=" . $hash);
			if($response !== false){
				$xml = new DOMDocument();
				$xml->loadXML($response);
				$root = $xml->getElementsByTagName("response")->item(0);
				return $root->getAttribute("result");
			}else{
				return false;
			}	
		}
	}
?>