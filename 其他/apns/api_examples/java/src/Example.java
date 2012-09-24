/**
 * Example.java, APNS Server-Side Java Demo
 * @see http://www.push-notification.org
 */
public class Example {

	public static void main(String[] args) {
		// Change the chId and apiKey to yourself
		// 修改 APNS 初始参数: ChId, apiKey 为你自己的
		APNS apns = new APNS(yourChId,yourApiKey);
		//send msg
		String response = apns.push("devId", "msg");
		//print response, the response is XML format string
		System.out.println(response);
	}

}
