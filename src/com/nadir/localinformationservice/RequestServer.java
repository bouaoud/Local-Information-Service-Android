package com.nadir.localinformationservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.util.List;
import java.util.Timer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;


public class RequestServer {
private HttpPost httppost ;
private List<NameValuePair> nameValuePairs ;//= new ArrayList<NameValuePair>();







/**
 * @param httppost
 * @param nameValuePairs
 */
public RequestServer(String url , List<NameValuePair> nameValuePairs) {
	super();
	this.httppost = new HttpPost(url);
	this.nameValuePairs = nameValuePairs;
	try {
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public String sendRequest(){
    HttpClient httpclient = new DefaultHttpClient();
    String s = ""; 
    try {
    	HttpResponse response = httpclient.execute(httppost);
    	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    	String line ;
    	while((line=reader.readLine()) !=null){
    		s+=line ;
    	}
    	return s;
    	
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return e.getMessage();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return e.getMessage();
	} //Voila, la requête est envoyée
    
    
}


/**
 * @return the httppost
 */
public HttpPost getHttppost() {
	return httppost;
}
/**
 * @param httppost the httppost to set
 */
public void setHttppost(HttpPost httppost) {
	this.httppost = httppost;
}
/**
 * @return the nameValuePairs
 */
public List<NameValuePair> getNameValuePairs() {
	return nameValuePairs;
}
/**
 * @param nameValuePairs the nameValuePairs to set
 */
public void setNameValuePairs(List<NameValuePair> nameValuePairs) {
	this.nameValuePairs = nameValuePairs;
}




}
