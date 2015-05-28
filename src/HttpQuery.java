/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
import javax.net.ssl.HttpsURLConnection;
/**
 *
 * @author Николай
 */
public class HttpQuery {
    private final String USER_AGENT = "Mozilla/5.0";
 
	public static void main(String[] args) throws Exception {
 
		HttpQuery http = new HttpQuery();
 
		System.out.println("Testing 1 - Send Http GET request");
		http.sendGet("http://router.project-osrm.org/viaroute?loc=52.503033,13.420526&loc=52.516582,13.429290&instructions=true");
 
		//System.out.println("\nTesting 2 - Send Http POST request");
		//http.sendPost();
 
	}
 
        public String send(String query) throws Exception{
            return sendGet(query);
        }
	// HTTP GET request
	private String sendGet(String urlQuery) throws Exception {
 
		//String url = "http://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&sensor=true_or_false";
                //String url="http://maps.googleapis.com/maps/api/geocode/json?latlng=50.5180900,30.8067100&sensor=true_or_false";
		//String url=" http://geocode-maps.yandex.ru/1.x/?format=json&geocode=Тверская+6.";
                String url="http://router.project-osrm.org/viaroute?loc=52.503033,13.420526&loc=52.516582,13.429290&instructions=true";
                URL obj = new URL(urlQuery);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		//con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
                        //response.append("\n");
		}
		in.close();
 
		//print result
		//System.out.println(response.toString());
                return response.toString();
 
	}
 
	// HTTP POST request
        /*
	private void sendPost() throws Exception {
 
		String url = "https://selfsolve.apple.com/wcResults.do";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
 
	}
*/
}
