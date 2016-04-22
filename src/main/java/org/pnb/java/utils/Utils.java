package org.pnb.java.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
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
	
	public static FResponse getResponse(String url,int timeout) throws ClientProtocolException, IOException
	{
		timeout = timeout*1000;
		
		RequestConfig defaultRequestConfig = RequestConfig.custom()
		        .setConnectTimeout(timeout)
		        .setSocketTimeout(timeout)
		        .setConnectionRequestTimeout(timeout)
		        .build();

		CloseableHttpClient httpClient = HttpClients.custom()
		        .setDefaultRequestConfig(defaultRequestConfig)
		        .build();
		
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(httpget);
		try {
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
		} finally {
		    response.close();
		}
		return null;
	}
	
	
	public static void main(String [] args) throws ClientProtocolException, IOException
	{
		Utils.FResponse fr = Utils.getResponse("http://www.baidu.com", 10);
		System.out.println(fr);
	}
}
