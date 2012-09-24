import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;


public class APNS {

	public static final String BASE_URL = "http://www.push-notification.org/handlers/apns_v1.php";
	private  String _CHId = "";
	private  String _apiKey = "";

	
	public APNS(String chId, String apiKey){
		_CHId = chId;
		_apiKey = apiKey;
	}
	
	
	public String push(String devId, String msg){
		if(_CHId == "" || _apiKey == "")
			return "channel id or apiKey required";
		String url = BASE_URL;
		String random = String.valueOf(Math.random());
		String hash = getMd5(_CHId + devId + msg + random + _apiKey);
		url += "?ch=" + _CHId + "&devId=" + devId + "&msg=" + URLEncoder.encode(msg) + "&random=" + random + "&hash=" + hash;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 15 * 1000);
		HttpConnectionParams.setSoTimeout(httpParameters, 30 * 1000);
		HttpClient client = new DefaultHttpClient(httpParameters);
		client.getParams().setParameter(
				HttpProtocolParams.HTTP_CONTENT_CHARSET, "UTF-8");
		HttpGet httpRequest = new HttpGet(url);
		String response = "";
		try {
			HttpResponse httpResponse = client.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				response = EntityUtils.toString(httpResponse.getEntity());
			} else {
				//TODO
			}
		} catch (Exception e) {
			//TODO
		}
		return response;
	}
	
	
	public static String getMd5(String str) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(str.getBytes(), 0, str.length());
			return new BigInteger(1, m.digest()).toString(16);
		} catch (Exception e) {
			return "";
		}
	}
}
