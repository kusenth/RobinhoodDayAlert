package senthil.jayaraman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;


public class PushNotify {

	@Value("${AUTHKEY}")
	private String AUTH_KEY_FCM;
	

	@Value("${APIURL}")
	private String API_URL_FCM;
	
	public void sendPushNotification(String deviceToken, String message) throws IOException {

		if (message != null && deviceToken != null & !message.equalsIgnoreCase("")
				&& !deviceToken.equalsIgnoreCase("")) {

			String result = "";
			URL url = new URL(API_URL_FCM);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
			conn.setRequestProperty("Content-Type", "application/json");

			JSONObject json = new JSONObject();

			json.put("to", deviceToken);
			json.put("messageid", new Random().nextInt());
			JSONObject info = new JSONObject();
			info.put("body", "message body"); // Notification
			info.put("message", message);
			info.put("messageid", new Random().nextInt());
			json.put("data", info);

			try {
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(json.toString());
				wr.flush();
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				String output;
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
				result = "SUCCESS";
			} catch (Exception e) {
				e.printStackTrace();
				result = "FAILURE";
			}
			System.out.println("GCM Notification is sent successfully");
		}

	}

}
