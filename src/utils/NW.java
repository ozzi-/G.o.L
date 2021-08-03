package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class NW {
	public static String doGETRequest(String url) throws IOException {

		CloseableHttpClient hc = HttpClients.createDefault();

		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse httpResponse = hc.execute(httpGet);

		int responseCode = httpResponse.getStatusLine().getStatusCode();
		if (responseCode == 404) {
			throw new NoSuchElementException();

		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

		String inputLine;
		StringBuffer responseBody = new StringBuffer();

		while ((inputLine = reader.readLine()) != null) {
			responseBody.append(inputLine + System.lineSeparator());
		}

		reader.close();
		hc.close();
		return responseBody.toString();
	}
}
