package com.zte.databinder;

public class NewsBinder {

	private String news_id;
	private String news_title;
	private String news_type;
	private String news_con;
	private String news_con_text;
	private String news_imgUrl;
	private String news_videoUrl;

	public String getNews_id() {
		return news_id;
	}

	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}
	
	public String getNews_con_text() {
		return news_con_text;
	}

	public void setNews_con_text(String news_con_text) {
		this.news_con_text = news_con_text;
	}

	public String getNews_title() {
		return news_title;
	}

	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}

	public String getNews_type() {
		return news_type;
	}

	public void setNews_type(String news_type) {
		this.news_type = news_type;
	}

	public String getNews_con() {
		return news_con;
	}

	public void setNews_con(String news_con) {
		this.news_con = news_con;
	}

	public String getNews_imgUrl() {
		return news_imgUrl;
	}

	public void setNews_imgUrl(String news_imgUrl) {
		this.news_imgUrl = news_imgUrl;
	}

	public String getNews_videoUrl() {
		return news_videoUrl;
	}

	public void setNews_videoUrl(String news_videoUrl) {
		this.news_videoUrl = news_videoUrl;
	}

}
