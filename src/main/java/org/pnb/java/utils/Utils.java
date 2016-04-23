package org.pnb.java.utils;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Utils 
{
	public static class FResponse
	{
		public int status = -1;
		public String content;
		
		@Override
		public String toString() {
			return status + "  :  " + content;
		}
	}
	
	static class MyHostnameVerifier implements org.apache.http.conn.ssl.X509HostnameVerifier
	{
	    public boolean verify(String host, SSLSession session) {
	        String sslHost = session.getPeerHost();
	        System.out.println("Host=" + host);
	        System.out.println("SSL Host=" + sslHost);    
	        if (host.equals(sslHost)) {
	            return true;
	        } else {
	            return false;
	        }
	    }

	    public void verify(String host, SSLSocket ssl) throws IOException {
	        String sslHost = ssl.getInetAddress().getHostName();
	        System.out.println("Host=" + host);
	        System.out.println("SSL Host=" + sslHost);    
	        if (host.equals(sslHost)) {
	            return;
	        } else {
	            throw new IOException("hostname in certificate didn't match: " + host + " != " + sslHost);
	        }
	    }

	    public void verify(String host, X509Certificate cert) throws SSLException {
	        throw new SSLException("Hostname verification 1 not implemented");
	    }

	    public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
	        throw new SSLException("Hostname verification 2 not implemented");
	    }
	}
    
	@SuppressWarnings("deprecation")
	public static FResponse getResponse(String url,int timeout) throws ClientProtocolException, IOException, NoSuchAlgorithmException, KeyManagementException
	{
		timeout = timeout*1000;
		
		RequestConfig defaultRequestConfig = RequestConfig.custom()
		        .setConnectTimeout(timeout)
		        .setSocketTimeout(timeout)
		        .setConnectionRequestTimeout(timeout)
		        .build();
	
		org.apache.http.conn.ssl.SSLSocketFactory sf = org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory();
		sf.setHostnameVerifier(new MyHostnameVerifier());
	    org.apache.http.conn.scheme.Scheme sch = new Scheme("https", 443, sf);
		org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
	    httpClient.getConnectionManager().getSchemeRegistry().register(sch);
		
		HttpGet httpGet= new HttpGet(url);
		httpGet.setConfig(defaultRequestConfig);
		
		HttpResponse response = httpClient.execute(httpGet);
	    HttpEntity entity = response.getEntity();
	   
	    if (entity != null) {
	        //long len = entity.getContentLength();
            String content = EntityUtils.toString(entity);
            int status = response.getStatusLine().getStatusCode();
            
            FResponse fr = new FResponse();
            fr.status = status;
            fr.content = content;
            return fr;
	        
	    }
		
		return null;
	}
	
	public static void main (String[] args) throws Exception {
	  System.out.println(Utils.getResponse("https://www.googleapis.com/customsearch/v1element?key=AIzaSyCVAXiUzRYsML1Pv6RwSG1gunmMikTzQqY&rsz=filtered_cse&num=10&hl=zh_CN&prettyPrint=false&source=gcsc&gss=.com&sig=374942b9f66402975ad19789cb8ab24c&cx=015520382618724669377:fkqzivuiqx8&q=java&sort=&googlehost=www.google.com&oq=java&gs_l=partner.12...0.0.0.16294.0.0.0.0.0.0.0.0..0.0.gsnos%2Cn%3D13...0.0..1ac..25.partner..0.0.0.&nocache=1461331442126", 10));
	}
}
