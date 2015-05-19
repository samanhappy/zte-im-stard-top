package com.zte.im.bean;


import com.google.gson.annotations.Expose;

public class News {

	@Expose
	private String id;
	@Expose
	private String title;
	@Expose
	private String content;
	@Expose
	private String content4Text;
	@Expose
	private String imgUrl;
	@Expose
	private String videoUrl;
	@Expose
	private String author;
	@Expose
	private String type;// 分类，0是普通新闻，1是封面新闻
	@Expose
	private Long date;
	@Expose
	private String flag;// 发布状态，0是未发布，1是已发布
	@Expose
	private String htmlFilePath;//html文件路径
	
	public String getContent4Text() {
		return content4Text;
	}

	public void setContent4Text(String content4Text) {
		this.content4Text = content4Text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	

	/**
	 * @return the htmlFilePath
	 */
	public String getHtmlFilePath() {
		return htmlFilePath;
	}

	/**
	 * @param htmlFilePath the htmlFilePath to set
	 */
	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}

}
