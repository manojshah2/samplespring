package com.manoj.app.sampleproject.sdk;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OneSignal {
	
	private final String baseDomain = "https://onesignal.com/api/v1/notifications";
	private final String REST_API_KEY ="N2UzMDA4NDAtNDYxMS00YTdiLWFkNmUtMjdmYTYyMzI5NDJm";
	private final String APP_ID ="413a9197-8c9e-4b4f-a633-f8ddaa8d86fb";
	public OneSignal() {
		
	}
	
	public String SendPushNotification(String heading,String message,List<String> deviceIds) {
		try {
			URL url = new URL(baseDomain);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization","Basic "+REST_API_KEY);
            con.setRequestMethod("POST");
            
            String strJsonBody = "{"
                    +   "\"app_id\": \""+ APP_ID +"\","
                    +   "\"include_player_ids\": [\""+ String.join(",", deviceIds) +"\"],"
                    +   "\"headings\": {\"en\": \""+ heading +"\"},"
                    +   "\"contents\": {\"en\": \""+ message +"\"}"
                    + "}";
            
            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);
            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            //System.out.println("httpResponse: " + httpResponse);
            if(httpResponse==200)
            	log.info("Notification Sent");

            String jsonResponse = mountResponseRequest(con, httpResponse);
            return jsonResponse;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private static String mountResponseRequest(HttpURLConnection con, int httpResponse) throws IOException {
        String jsonResponse;
        if (  httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        else {
            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        return jsonResponse;
    }

}
