package eu.cudan.snapshotCrawler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class PostCommitter {
	
	private ParameterSet param;
	private String url;
	private String htmlSnapshot;
	
	public PostCommitter(ParameterSet param, String url, String htmlSnapshot) {
		this.param = param;
		this.url = url;
		this.htmlSnapshot = htmlSnapshot;
		if(!param.getPostUrl().isEmpty())
			callUrlWithPost();
	}

	private void callUrlWithPost() {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(param.getPostUrl());

			// Request parameters and other properties.
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("link", url));
			params.add(new BasicNameValuePair("html", htmlSnapshot));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			//Execute and get the response.
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
			    InputStream instream = entity.getContent();
			    try {
			        //TODO: Check if Servlet Response from Server after POST is OK
			    } finally {
			        instream.close();
			    }
			}
		} catch (Exception e) {
			System.err.println("ERROR: Occured while uploading to POST Server");
			e.printStackTrace();
		}
		
	}
}
