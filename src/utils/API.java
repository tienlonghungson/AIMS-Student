package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;

/**
 * provides methods helping wth sending request to server and handle returned values
 * Date 9/12/2021
 * @author tienlonghungson
 */
public class API {

	/**
	 * format of date value
	 */
	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * attribute to log information to console
	 */
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());

	/**
	 * method to call GET API
	 * @param url link to requested server
	 * @param token hash code to verify user
	 * @return response : response from server (String type)
	 * @throws Exception
	 */
	public static String get(String url, String token) throws Exception {

		// set up
		HttpURLConnection conn = getHttpURLConnection(url,"GET", token);

		// read returned data from server
		StringBuilder response = getStringBuilder(conn);
		return response.substring(0, response.length() - 1);
	}

	/**
	 * read returned data from server
	 * @param conn connection to server
	 * @return response : response from server
	 * @throws IOException
	 */
	private static StringBuilder getStringBuilder(HttpURLConnection conn) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuilder respone = new StringBuilder(); // ising StringBuilder for the sake of memory and performance
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		respone.append(inputLine + "\n");
		in.close();
		LOGGER.info("Respone Info: " + respone.substring(0, respone.length() - 1).toString());
		return respone;
	}

	/**
	 * set up connection to server
	 * @param url link to requested server
	 * @param method : API protocol
	 * @param token: hash code to verify user
	 * @return connection
	 * @throws IOException
	 */
	private static HttpURLConnection getHttpURLConnection(String url,String method, String token) throws IOException {
		LOGGER.info("Request URL: " + url + "\n");
		URL line_api_url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		return conn;
	}

	int var;

	/**
	 * method to call POST API
	 * @param url link to requested server
	 * @param data data that is brought to server (json type)
	 * @return response : response from server (string type)
	 * @throws IOException
	 */
	public static String post(String url, String data
//			, String token
	) throws IOException {
		allowMethods("PATCH");

		// set up
		URL line_api_url = new URL(url);
		String payload = data;
		LOGGER.info("Request Info:\nRequest URL: " + url + "\n" + "Payload Data: " + payload + "\n");
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("PATCH");
		conn.setRequestProperty("Content-Type", "application/json");
//		conn.setRequestProperty("Authorization", "Bearer " + token);

		// send data
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(payload);
		writer.close();

		// read returned data from server
		StringBuilder response = getStringBuilder(conn);
		return response.toString();
	}

	/**
	 * method allows calling to different API : PUT, PATCH
	 * @deprecated since Java 11
	 * @param methods methods need to be allowed (PUT, PATCH,...)
	 */
	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

}
