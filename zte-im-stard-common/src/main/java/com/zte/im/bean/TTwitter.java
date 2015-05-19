package com.zte.im.bean;

import com.google.gson.annotations.Expose;

public class TTwitter extends Base {
	@Expose
	private Long twitterId;// 微博标识

	@Expose
	private Long sourceId;// 源微博标识

	@Expose
	private Long userId;// 用户标识

	@Expose
	private String twitterContent;// 微博内容
	
	@Expose
	private String searchContent;//微博内容，不带用户ID等特殊字符

	@Expose
	private String twitterType;// 微博类型：01、原创；02、转发

	@Expose
	private String twitterStatus;// 微博状态：00、发布；99、删除；11、草稿

	@Expose
	private String visibleRange;// 可见范围：01、公开；02、好友圈；03、仅自己可见

	@Expose
	private String locationFlag;// 定位标识：00、开启；99、关闭

	@Expose
	private String lng;// 经度

	@Expose
	private String lat;// 纬度

	@Expose
	private String country;// 国家

	@Expose
	private String province;// 省份

	@Expose
	private String city;// 城市

	@Expose
	private String county;// 区县

	@Expose
	private String street;// 街道

	@Expose
	private String address;// 地址

	@Expose
	private Long forwardNum;// 转发次数

	@Expose
	private Long collectionNum;// 收藏次数

	@Expose
	private Long commentNum;// 评论次数

	@Expose
	private Long supportNum;// 点赞次数

	@Expose
	private String imgSrc;// 图片地址

	@Expose
	private String userName;// 发布人姓名

	@Expose
	private String twitterAttr;// 微博属性：01、普通；02、系统',

	@Expose
	private String hreflag; // 00：未插入；01：插入表情；02：插入话题

	@Expose
	private String minipicurl;// head portrait 缩写，用户头像
	@Expose
	private String bigpicurl;// source head portrait 缩写，用户头像原图

	@Expose
	private String isCollect = "00";// 是否收藏 00：未收藏； 01：已收藏
	
	@Expose
	private String isSupport = "00";// 是否赞 00：未赞； 01：已赞

	@Expose
	private Long sourceUserId; // 源微博作者标识

	@Expose
	private String sourceUserName; // 源微博作者

	@Expose
	private String sourceMinipicurl; // 源微博作者头像地址

	@Expose
	private String sourceBigpicurl; // 源微博作者头像原图地址

	@Expose
	private String sourceContent; // 源微博内容

	@Expose
	private Long sourceCreateTime; // 源微博创建时间

	@Expose
	private String sourceImgSrc; // 源微博图片地址

	@Expose
	private String sourceIsCollect = "00";// 是否收藏 00：未收藏； 01：已收藏
	
	@Expose
	private String sourceIsSupport = "00";// 是否赞 00：未赞； 01：已赞

	@Expose
	private String sourceHreflag; // 00：未插入；01：插入表情；02：插入话题

	@Expose
	private Long sourceForwardNum;// 转发次数

	@Expose
	private Long sourceCollectionNum;// 收藏次数

	@Expose
	private Long sourceCommentNum;// 评论次数

	@Expose
	private Long sourceSupportNum;// 点赞次数
	
	@Expose
	private String sourceCountry;// 国家

	@Expose
	private String sourceProvince;// 省份

	@Expose
	private String sourceCity;// 城市

	@Expose
	private String sourceCounty;// 区县

	@Expose
	private String sourceStreet;// 街道

	@Expose
	private String sourceAddress;// 地址
	
	@Expose
	private String sourceTwitterType;// 微博类型：01、原创；02、转发

	@Expose
	private String sourceTwitterStatus;// 微博状态：00、发布；99、删除；11、草稿
	
	@Expose
	private String sourceVisibleRange;
	
	@Expose
	private TTwitter sourceTTwitter;//源微博信息

	/**
	 * @return the minipicurl
	 */
	public String getMinipicurl() {
		return minipicurl;
	}

	/**
	 * @param minipicurl
	 *            the minipicurl to set
	 */
	public void setMinipicurl(String minipicurl) {
		this.minipicurl = minipicurl;
	}

	/**
	 * @return the bigpicurl
	 */
	public String getBigpicurl() {
		return bigpicurl;
	}

	/**
	 * @param bigpicurl
	 *            the bigpicurl to set
	 */
	public void setBigpicurl(String bigpicurl) {
		this.bigpicurl = bigpicurl;
	}

	/**
	 * @return the hreflag
	 */
	public String getHreflag() {
		return hreflag;
	}

	/**
	 * @param hreflag
	 *            the hreflag to set
	 */
	public void setHreflag(String hreflag) {
		this.hreflag = hreflag;
	}

	/**
	 * @return the twitterId
	 */
	public Long getTwitterId() {
		return twitterId;
	}

	/**
	 * @param twitterId
	 *            the twitterId to set
	 */
	public void setTwitterId(Long twitterId) {
		this.twitterId = twitterId;
	}

	/**
	 * @return the sourceId
	 */
	public Long getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId
	 *            the sourceId to set
	 */
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the twitterContent
	 */
	public String getTwitterContent() {
		return twitterContent;
	}

	/**
	 * @param twitterContent
	 *            the twitterContent to set
	 */
	public void setTwitterContent(String twitterContent) {
		this.twitterContent = twitterContent;
	}

	/**
	 * @return the twitterType
	 */
	public String getTwitterType() {
		return twitterType;
	}

	/**
	 * @param twitterType
	 *            the twitterType to set
	 */
	public void setTwitterType(String twitterType) {
		this.twitterType = twitterType;
	}

	/**
	 * @return the twitterStatus
	 */
	public String getTwitterStatus() {
		return twitterStatus;
	}

	/**
	 * @param twitterStatus
	 *            the twitterStatus to set
	 */
	public void setTwitterStatus(String twitterStatus) {
		this.twitterStatus = twitterStatus;
	}

	/**
	 * @return the visibleRange
	 */
	public String getVisibleRange() {
		return visibleRange;
	}

	/**
	 * @param visibleRange
	 *            the visibleRange to set
	 */
	public void setVisibleRange(String visibleRange) {
		this.visibleRange = visibleRange;
	}

	/**
	 * @return the locationFlag
	 */
	public String getLocationFlag() {
		return locationFlag;
	}

	/**
	 * @param locationFlag
	 *            the locationFlag to set
	 */
	public void setLocationFlag(String locationFlag) {
		this.locationFlag = locationFlag;
	}

	/**
	 * @return the lng
	 */
	public String getLng() {
		return lng;
	}

	/**
	 * @param lng
	 *            the lng to set
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}

	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * @param lat
	 *            the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * @param county
	 *            the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street
	 *            the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the forwardNum
	 */
	public Long getForwardNum() {
		return forwardNum;
	}

	/**
	 * @param forwardNum
	 *            the forwardNum to set
	 */
	public void setForwardNum(Long forwardNum) {
		this.forwardNum = forwardNum;
	}

	/**
	 * @return the collectionNum
	 */
	public Long getCollectionNum() {
		return collectionNum;
	}

	/**
	 * @param collectionNum
	 *            the collectionNum to set
	 */
	public void setCollectionNum(Long collectionNum) {
		this.collectionNum = collectionNum;
	}

	/**
	 * @return the commentNum
	 */
	public Long getCommentNum() {
		return commentNum;
	}

	/**
	 * @param commentNum
	 *            the commentNum to set
	 */
	public void setCommentNum(Long commentNum) {
		this.commentNum = commentNum;
	}

	/**
	 * @return the supportNum
	 */
	public Long getSupportNum() {
		return supportNum;
	}

	/**
	 * @param supportNum
	 *            the supportNum to set
	 */
	public void setSupportNum(Long supportNum) {
		this.supportNum = supportNum;
	}

	/**
	 * @return the imgSrc
	 */
	public String getImgSrc() {
		return imgSrc;
	}

	/**
	 * @param imgSrc
	 *            the imgSrc to set
	 */
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTwitterAttr() {
		return twitterAttr;
	}

	public void setTwitterAttr(String twitterAttr) {
		this.twitterAttr = twitterAttr;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	public String getSourceUserName() {
		return sourceUserName;
	}

	public void setSourceUserName(String sourceUserName) {
		this.sourceUserName = sourceUserName;
	}

	public String getSourceMinipicurl() {
		return sourceMinipicurl;
	}

	public void setSourceMinipicurl(String sourceMinipicurl) {
		this.sourceMinipicurl = sourceMinipicurl;
	}

	public String getSourceBigpicurl() {
		return sourceBigpicurl;
	}

	public void setSourceBigpicurl(String sourceBigpicurl) {
		this.sourceBigpicurl = sourceBigpicurl;
	}

	public String getSourceContent() {
		return sourceContent;
	}

	public void setSourceContent(String sourceContent) {
		this.sourceContent = sourceContent;
	}

	public Long getSourceUserId() {
		return sourceUserId;
	}

	public void setSourceUserId(Long sourceUserId) {
		this.sourceUserId = sourceUserId;
	}

	public Long getSourceCreateTime() {
		return sourceCreateTime;
	}

	public void setSourceCreateTime(Long sourceCreateTime) {
		this.sourceCreateTime = sourceCreateTime;
	}

	public String getSourceImgSrc() {
		return sourceImgSrc;
	}

	public void setSourceImgSrc(String sourceImgSrc) {
		this.sourceImgSrc = sourceImgSrc;
	}

	public String getSourceIsCollect() {
		return sourceIsCollect;
	}

	public void setSourceIsCollect(String sourceIsCollect) {
		this.sourceIsCollect = sourceIsCollect;
	}

	public String getSourceHreflag() {
		return sourceHreflag;
	}

	public void setSourceHreflag(String sourceHreflag) {
		this.sourceHreflag = sourceHreflag;
	}

	public Long getSourceForwardNum() {
		return sourceForwardNum;
	}

	public void setSourceForwardNum(Long sourceForwardNum) {
		this.sourceForwardNum = sourceForwardNum;
	}

	public Long getSourceCollectionNum() {
		return sourceCollectionNum;
	}

	public void setSourceCollectionNum(Long sourceCollectionNum) {
		this.sourceCollectionNum = sourceCollectionNum;
	}

	public Long getSourceCommentNum() {
		return sourceCommentNum;
	}

	public void setSourceCommentNum(Long sourceCommentNum) {
		this.sourceCommentNum = sourceCommentNum;
	}

	public Long getSourceSupportNum() {
		return sourceSupportNum;
	}

	public void setSourceSupportNum(Long sourceSupportNum) {
		this.sourceSupportNum = sourceSupportNum;
	}

	public String getIsSupport() {
		return isSupport;
	}

	public void setIsSupport(String isSupport) {
		this.isSupport = isSupport;
	}

	public String getSourceIsSupport() {
		return sourceIsSupport;
	}

	public void setSourceIsSupport(String sourceIsSupport) {
		this.sourceIsSupport = sourceIsSupport;
	}

	public String getSourceCountry() {
		return sourceCountry;
	}

	public void setSourceCountry(String sourceCountry) {
		this.sourceCountry = sourceCountry;
	}

	public String getSourceProvince() {
		return sourceProvince;
	}

	public void setSourceProvince(String sourceProvince) {
		this.sourceProvince = sourceProvince;
	}

	public String getSourceCity() {
		return sourceCity;
	}

	public void setSourceCity(String sourceCity) {
		this.sourceCity = sourceCity;
	}

	public String getSourceCounty() {
		return sourceCounty;
	}

	public void setSourceCounty(String sourceCounty) {
		this.sourceCounty = sourceCounty;
	}

	public String getSourceStreet() {
		return sourceStreet;
	}

	public void setSourceStreet(String sourceStreet) {
		this.sourceStreet = sourceStreet;
	}

	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public String getSourceTwitterType() {
		return sourceTwitterType;
	}

	public void setSourceTwitterType(String sourceTwitterType) {
		this.sourceTwitterType = sourceTwitterType;
	}

	public String getSourceTwitterStatus() {
		return sourceTwitterStatus;
	}

	public void setSourceTwitterStatus(String sourceTwitterStatus) {
		this.sourceTwitterStatus = sourceTwitterStatus;
	}

	public String getSourceVisibleRange() {
		return sourceVisibleRange;
	}

	public void setSourceVisibleRange(String sourceVisibleRange) {
		this.sourceVisibleRange = sourceVisibleRange;
	}

	public TTwitter getSourceTTwitter() {
		return sourceTTwitter;
	}

	public void setSourceTTwitter(TTwitter sourceTTwitter) {
		this.sourceTTwitter = sourceTTwitter;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
}
