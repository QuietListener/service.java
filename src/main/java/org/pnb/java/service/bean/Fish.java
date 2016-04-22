package org.pnb.java.service.bean;

import java.util.Date;

public class Fish {
	
	private long id ;
	private String from;
	private String content;
	private Date date;
	
	public Fish() {}
	
	public Fish(String from,  String url, String date) 
	{
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
