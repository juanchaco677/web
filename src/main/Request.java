package main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Request {
	public static  List<Registro> getList(String url,Map<String, Object>parametros) throws URISyntaxException{
		HttpClient httpClient = HttpClientBuilder.create().build();

		URIBuilder uri = null;
		try {
			uri = new URIBuilder(url);
			if(parametros!=null) {
				for (Entry<String, Object> entry : parametros.entrySet()) {
					uri.setParameter(entry.getKey(),  entry.getValue().toString());
				}
			}
			HttpGet method = new HttpGet(uri.build());
			method.setHeader("Accept","application/json");


			List<Registro>lista=null;
			try {
				HttpResponse response = httpClient.execute(method);
				HttpEntity entity = response.getEntity();
				try {
					String content = EntityUtils.toString(entity);	
			
					JsonParser parser = new JsonParser();
					JsonElement arrayElement = parser.parse(content.replace("{\"Entries\":{\"Entry\":", "").replace("}}",""));

					lista=new ArrayList<>();

					for (int i = 0; i < arrayElement.getAsJsonArray().size(); i++) {
						JSONObject json=new JSONObject(arrayElement.getAsJsonArray().get(i).toString());
						Registro registro=new Registro();
						for (int j = 0; j < json.length(); j++) {					
							registro.add(json.names().getString(j),json.get(json.names().getString(j)));					
						}
						lista.add(registro);
					}
				} catch (Exception e) {
					System.out.println("error");
				}
				return lista;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;
	}
	public static  Registro get(String url,Map<String, Object>parametros) throws URISyntaxException{
		HttpClient httpClient = HttpClientBuilder.create().build();

		URIBuilder uri = null;
		try {
			uri = new URIBuilder(url);
			if(parametros!=null) {
				for (Entry<String, Object> entry : parametros.entrySet()) {
					uri.setParameter(entry.getKey(),  entry.getValue().toString());
				}
			}
			HttpGet method = new HttpGet(uri.build());
			method.setHeader("Accept","application/json");

		
			try {
				HttpResponse response = httpClient.execute(method);
				HttpEntity entity = response.getEntity();
				try {
					String content = EntityUtils.toString(entity);	
					System.out.println(content);
					JsonParser parser = new JsonParser();
					JsonElement arrayElement = parser.parse(content.replace("{\"Entries\":{\"Entry\":", "").replace("}}",""));


					JSONObject json=new JSONObject(arrayElement.getAsJsonArray().get(0).toString());
					Registro registro=new Registro();
					for (int j = 0; j < json.length(); j++) {					
						registro.add(json.names().getString(j),json.get(json.names().getString(j)));					
					}
					return registro;

				} catch (Exception e) {
					System.out.println("error");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;
	}

	public static Registro save(String url,Map<String,Object>parametros) throws UnsupportedEncodingException {

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader(HttpHeaders.ACCEPT,"application/json");

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if(parametros!=null) {
			for (Entry<String, Object> entry : parametros.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// writing error to Log
			e.printStackTrace();
		}

		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity respEntity = response.getEntity();		   
			if (respEntity != null) {
				Registro registro=new Registro();
				String content =  EntityUtils.toString(respEntity);
				try {
					JSONObject json = new JSONObject(content.replace("{\"GeneratedKeys\":{\"Entry\":[", "").replace("]}}",""));

					for (int j = 0; j < json.length(); j++) {					
						registro.add(json.names().getString(j),json.get(json.names().getString(j)));					
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return registro;		        
			}
		} catch (ClientProtocolException e) {
			// writing exception to log
			e.printStackTrace();
		} catch (IOException e) {
			// writing exception to log
			e.printStackTrace();
		}
		return null;
	}

	public static int update(String url,Map<String, Object>parametros) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpPut httpPost = new HttpPut(url);
		httpPost.setHeader(HttpHeaders.ACCEPT,"application/json");

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if(parametros!=null) {
			for (Entry<String, Object> entry : parametros.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// writing error to Log
			e.printStackTrace();
		}

		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity respEntity = response.getEntity();		   
			if (respEntity != null) {
				Registro registro=new Registro();
				String content =  EntityUtils.toString(respEntity);		     
				try {
					JSONObject json = new JSONObject(content.replace("{\"UpdatedRowCount\":", "").replace("}}","}"));

					for (int j = 0; j < json.length(); j++) {					
						registro.add(json.names().getString(j),json.get(json.names().getString(j)));					
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return Integer.parseInt(registro.getCampos().get("CONTAR").toString());		        
			}
		} catch (ClientProtocolException e) {
			// writing exception to log
			e.printStackTrace();
		} catch (IOException e) {
			// writing exception to log
			e.printStackTrace();
		}
		return 0;
	}

	public static int delete(String url,Map<String, Object>parametros) {
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPost deleteRequest = new HttpPost(url);
			//			deleteRequest.setHeader(HttpHeaders.ACCEPT, "application/json");
			deleteRequest.setHeader(HttpHeaders.ACCEPT, "application/json");
			//			deleteRequest.setHeader("Accept-Charset", "UTF_8"); //$NON-NLS-1$
			//			deleteRequest.addHeader("KEY_ID","1");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if(parametros!=null) {
				for (Entry<String, Object> entry : parametros.entrySet()) {
					params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
				}
			}
			try {
				deleteRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// writing error to Log
				e.printStackTrace();
			}
			HttpResponse response = httpClient.execute(deleteRequest);
			String status = response.getStatusLine().toString();
			HttpEntity respEntity = response.getEntity();
			String content =  EntityUtils.toString(respEntity);
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}
}
