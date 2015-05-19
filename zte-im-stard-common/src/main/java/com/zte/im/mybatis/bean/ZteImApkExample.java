package com.zte.im.mybatis.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ZteImApkExample {
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public ZteImApkExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andIdIsNull() {
			addCriterion("ID is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("ID is not null");
			return (Criteria) this;
		}

		public Criteria andIdEqualTo(Integer value) {
			addCriterion("ID =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(Integer value) {
			addCriterion("ID <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(Integer value) {
			addCriterion("ID >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("ID >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(Integer value) {
			addCriterion("ID <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(Integer value) {
			addCriterion("ID <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<Integer> values) {
			addCriterion("ID in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<Integer> values) {
			addCriterion("ID not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(Integer value1, Integer value2) {
			addCriterion("ID between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(Integer value1, Integer value2) {
			addCriterion("ID not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andCateIdIsNull() {
			addCriterion("CATE_ID is null");
			return (Criteria) this;
		}

		public Criteria andCateIdIsNotNull() {
			addCriterion("CATE_ID is not null");
			return (Criteria) this;
		}

		public Criteria andCateIdEqualTo(Integer value) {
			addCriterion("CATE_ID =", value, "cateId");
			return (Criteria) this;
		}

		public Criteria andCateIdNotEqualTo(Integer value) {
			addCriterion("CATE_ID <>", value, "cateId");
			return (Criteria) this;
		}

		public Criteria andCateIdGreaterThan(Integer value) {
			addCriterion("CATE_ID >", value, "cateId");
			return (Criteria) this;
		}

		public Criteria andCateIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("CATE_ID >=", value, "cateId");
			return (Criteria) this;
		}

		public Criteria andCateIdLessThan(Integer value) {
			addCriterion("CATE_ID <", value, "cateId");
			return (Criteria) this;
		}

		public Criteria andCateIdLessThanOrEqualTo(Integer value) {
			addCriterion("CATE_ID <=", value, "cateId");
			return (Criteria) this;
		}

		public Criteria andCateIdIn(List<Integer> values) {
			addCriterion("CATE_ID in", values, "cateId");
			return (Criteria) this;
		}

		public Criteria andCateIdNotIn(List<Integer> values) {
			addCriterion("CATE_ID not in", values, "cateId");
			return (Criteria) this;
		}

		public Criteria andCateIdBetween(Integer value1, Integer value2) {
			addCriterion("CATE_ID between", value1, value2, "cateId");
			return (Criteria) this;
		}

		public Criteria andCateIdNotBetween(Integer value1, Integer value2) {
			addCriterion("CATE_ID not between", value1, value2, "cateId");
			return (Criteria) this;
		}

		public Criteria andNameIsNull() {
			addCriterion("NAME is null");
			return (Criteria) this;
		}

		public Criteria andNameIsNotNull() {
			addCriterion("NAME is not null");
			return (Criteria) this;
		}

		public Criteria andNameEqualTo(String value) {
			addCriterion("NAME =", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotEqualTo(String value) {
			addCriterion("NAME <>", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameGreaterThan(String value) {
			addCriterion("NAME >", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameGreaterThanOrEqualTo(String value) {
			addCriterion("NAME >=", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameLessThan(String value) {
			addCriterion("NAME <", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameLessThanOrEqualTo(String value) {
			addCriterion("NAME <=", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameLike(String value) {
			addCriterion("NAME like", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotLike(String value) {
			addCriterion("NAME not like", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameIn(List<String> values) {
			addCriterion("NAME in", values, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotIn(List<String> values) {
			addCriterion("NAME not in", values, "name");
			return (Criteria) this;
		}

		public Criteria andNameBetween(String value1, String value2) {
			addCriterion("NAME between", value1, value2, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotBetween(String value1, String value2) {
			addCriterion("NAME not between", value1, value2, "name");
			return (Criteria) this;
		}

		public Criteria andImgIsNull() {
			addCriterion("IMG is null");
			return (Criteria) this;
		}

		public Criteria andImgIsNotNull() {
			addCriterion("IMG is not null");
			return (Criteria) this;
		}

		public Criteria andImgEqualTo(String value) {
			addCriterion("IMG =", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgNotEqualTo(String value) {
			addCriterion("IMG <>", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgGreaterThan(String value) {
			addCriterion("IMG >", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgGreaterThanOrEqualTo(String value) {
			addCriterion("IMG >=", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgLessThan(String value) {
			addCriterion("IMG <", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgLessThanOrEqualTo(String value) {
			addCriterion("IMG <=", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgLike(String value) {
			addCriterion("IMG like", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgNotLike(String value) {
			addCriterion("IMG not like", value, "img");
			return (Criteria) this;
		}

		public Criteria andImgIn(List<String> values) {
			addCriterion("IMG in", values, "img");
			return (Criteria) this;
		}

		public Criteria andImgNotIn(List<String> values) {
			addCriterion("IMG not in", values, "img");
			return (Criteria) this;
		}

		public Criteria andImgBetween(String value1, String value2) {
			addCriterion("IMG between", value1, value2, "img");
			return (Criteria) this;
		}

		public Criteria andImgNotBetween(String value1, String value2) {
			addCriterion("IMG not between", value1, value2, "img");
			return (Criteria) this;
		}

		public Criteria andApkUrlIsNull() {
			addCriterion("APK_URL is null");
			return (Criteria) this;
		}

		public Criteria andApkUrlIsNotNull() {
			addCriterion("APK_URL is not null");
			return (Criteria) this;
		}

		public Criteria andApkUrlEqualTo(String value) {
			addCriterion("APK_URL =", value, "apkUrl");
			return (Criteria) this;
		}

		public Criteria andApkUrlNotEqualTo(String value) {
			addCriterion("APK_URL <>", value, "apkUrl");
			return (Criteria) this;
		}

		public Criteria andApkUrlGreaterThan(String value) {
			addCriterion("APK_URL >", value, "apkUrl");
			return (Criteria) this;
		}

		public Criteria andApkUrlGreaterThanOrEqualTo(String value) {
			addCriterion("APK_URL >=", value, "apkUrl");
			return (Criteria) this;
		}

		public Criteria andApkUrlLessThan(String value) {
			addCriterion("APK_URL <", value, "apkUrl");
			return (Criteria) this;
		}

		public Criteria andApkUrlLessThanOrEqualTo(String value) {
			addCriterion("APK_URL <=", value, "apkUrl");
			return (Criteria) this;
		}

		public Criteria andApkUrlLike(String value) {
			addCriterion("APK_URL like", value, "apkUrl");
			return (Criteria) this;
		}

		public Criteria andApkUrlNotLike(String value) {
			addCriterion("APK_URL not like", value, "apkUrl");
			return (Criteria) this;
		}

		public Criteria andApkUrlIn(List<String> values) {
			addCriterion("APK_URL in", values, "apkUrl");
			return (Criteria) this;
		}

		public Criteria andApkUrlNotIn(List<String> values) {
			addCriterion("APK_URL not in", values, "apkUrl");
			return (Criteria) this;
		}

		public Criteria andApkUrlBetween(String value1, String value2) {
			addCriterion("APK_URL between", value1, value2, "apkUrl");
			return (Criteria) this;
		}

		public Criteria andApkUrlNotBetween(String value1, String value2) {
			addCriterion("APK_URL not between", value1, value2, "apkUrl");
			return (Criteria) this;
		}

		public Criteria andIsEnableIsNull() {
			addCriterion("IS_ENABLE is null");
			return (Criteria) this;
		}

		public Criteria andIsEnableIsNotNull() {
			addCriterion("IS_ENABLE is not null");
			return (Criteria) this;
		}

		public Criteria andIsEnableEqualTo(Integer value) {
			addCriterion("IS_ENABLE =", value, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableNotEqualTo(Integer value) {
			addCriterion("IS_ENABLE <>", value, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableGreaterThan(Integer value) {
			addCriterion("IS_ENABLE >", value, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableGreaterThanOrEqualTo(Integer value) {
			addCriterion("IS_ENABLE >=", value, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableLessThan(Integer value) {
			addCriterion("IS_ENABLE <", value, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableLessThanOrEqualTo(Integer value) {
			addCriterion("IS_ENABLE <=", value, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableIn(List<Integer> values) {
			addCriterion("IS_ENABLE in", values, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableNotIn(List<Integer> values) {
			addCriterion("IS_ENABLE not in", values, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableBetween(Integer value1, Integer value2) {
			addCriterion("IS_ENABLE between", value1, value2, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableNotBetween(Integer value1, Integer value2) {
			addCriterion("IS_ENABLE not between", value1, value2, "isEnable");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIsNull() {
			addCriterion("UPDATE_TIME is null");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIsNotNull() {
			addCriterion("UPDATE_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeEqualTo(Date value) {
			addCriterion("UPDATE_TIME =", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotEqualTo(Date value) {
			addCriterion("UPDATE_TIME <>", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThan(Date value) {
			addCriterion("UPDATE_TIME >", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("UPDATE_TIME >=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThan(Date value) {
			addCriterion("UPDATE_TIME <", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
			addCriterion("UPDATE_TIME <=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIn(List<Date> values) {
			addCriterion("UPDATE_TIME in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotIn(List<Date> values) {
			addCriterion("UPDATE_TIME not in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeBetween(Date value1, Date value2) {
			addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
			addCriterion("UPDATE_TIME not between", value1, value2,
					"updateTime");
			return (Criteria) this;
		}

		public Criteria andUserIdIsNull() {
			addCriterion("USER_ID is null");
			return (Criteria) this;
		}

		public Criteria andUserIdIsNotNull() {
			addCriterion("USER_ID is not null");
			return (Criteria) this;
		}

		public Criteria andUserIdEqualTo(Integer value) {
			addCriterion("USER_ID =", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotEqualTo(Integer value) {
			addCriterion("USER_ID <>", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThan(Integer value) {
			addCriterion("USER_ID >", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("USER_ID >=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThan(Integer value) {
			addCriterion("USER_ID <", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThanOrEqualTo(Integer value) {
			addCriterion("USER_ID <=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdIn(List<Integer> values) {
			addCriterion("USER_ID in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotIn(List<Integer> values) {
			addCriterion("USER_ID not in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdBetween(Integer value1, Integer value2) {
			addCriterion("USER_ID between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
			addCriterion("USER_ID not between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andBannerIsNull() {
			addCriterion("BANNER is null");
			return (Criteria) this;
		}

		public Criteria andBannerIsNotNull() {
			addCriterion("BANNER is not null");
			return (Criteria) this;
		}

		public Criteria andBannerEqualTo(String value) {
			addCriterion("BANNER =", value, "banner");
			return (Criteria) this;
		}

		public Criteria andBannerNotEqualTo(String value) {
			addCriterion("BANNER <>", value, "banner");
			return (Criteria) this;
		}

		public Criteria andBannerGreaterThan(String value) {
			addCriterion("BANNER >", value, "banner");
			return (Criteria) this;
		}

		public Criteria andBannerGreaterThanOrEqualTo(String value) {
			addCriterion("BANNER >=", value, "banner");
			return (Criteria) this;
		}

		public Criteria andBannerLessThan(String value) {
			addCriterion("BANNER <", value, "banner");
			return (Criteria) this;
		}

		public Criteria andBannerLessThanOrEqualTo(String value) {
			addCriterion("BANNER <=", value, "banner");
			return (Criteria) this;
		}

		public Criteria andBannerLike(String value) {
			addCriterion("BANNER like", value, "banner");
			return (Criteria) this;
		}

		public Criteria andBannerNotLike(String value) {
			addCriterion("BANNER not like", value, "banner");
			return (Criteria) this;
		}

		public Criteria andBannerIn(List<String> values) {
			addCriterion("BANNER in", values, "banner");
			return (Criteria) this;
		}

		public Criteria andBannerNotIn(List<String> values) {
			addCriterion("BANNER not in", values, "banner");
			return (Criteria) this;
		}

		public Criteria andBannerBetween(String value1, String value2) {
			addCriterion("BANNER between", value1, value2, "banner");
			return (Criteria) this;
		}

		public Criteria andBannerNotBetween(String value1, String value2) {
			addCriterion("BANNER not between", value1, value2, "banner");
			return (Criteria) this;
		}

		public Criteria andIsChoiceIsNull() {
			addCriterion("IS_CHOICE is null");
			return (Criteria) this;
		}

		public Criteria andIsChoiceIsNotNull() {
			addCriterion("IS_CHOICE is not null");
			return (Criteria) this;
		}

		public Criteria andIsChoiceEqualTo(Integer value) {
			addCriterion("IS_CHOICE =", value, "isChoice");
			return (Criteria) this;
		}

		public Criteria andIsChoiceNotEqualTo(Integer value) {
			addCriterion("IS_CHOICE <>", value, "isChoice");
			return (Criteria) this;
		}

		public Criteria andIsChoiceGreaterThan(Integer value) {
			addCriterion("IS_CHOICE >", value, "isChoice");
			return (Criteria) this;
		}

		public Criteria andIsChoiceGreaterThanOrEqualTo(Integer value) {
			addCriterion("IS_CHOICE >=", value, "isChoice");
			return (Criteria) this;
		}

		public Criteria andIsChoiceLessThan(Integer value) {
			addCriterion("IS_CHOICE <", value, "isChoice");
			return (Criteria) this;
		}

		public Criteria andIsChoiceLessThanOrEqualTo(Integer value) {
			addCriterion("IS_CHOICE <=", value, "isChoice");
			return (Criteria) this;
		}

		public Criteria andIsChoiceIn(List<Integer> values) {
			addCriterion("IS_CHOICE in", values, "isChoice");
			return (Criteria) this;
		}

		public Criteria andIsChoiceNotIn(List<Integer> values) {
			addCriterion("IS_CHOICE not in", values, "isChoice");
			return (Criteria) this;
		}

		public Criteria andIsChoiceBetween(Integer value1, Integer value2) {
			addCriterion("IS_CHOICE between", value1, value2, "isChoice");
			return (Criteria) this;
		}

		public Criteria andIsChoiceNotBetween(Integer value1, Integer value2) {
			addCriterion("IS_CHOICE not between", value1, value2, "isChoice");
			return (Criteria) this;
		}

		public Criteria andIsRecommendIsNull() {
			addCriterion("IS_RECOMMEND is null");
			return (Criteria) this;
		}

		public Criteria andIsRecommendIsNotNull() {
			addCriterion("IS_RECOMMEND is not null");
			return (Criteria) this;
		}

		public Criteria andIsRecommendEqualTo(Integer value) {
			addCriterion("IS_RECOMMEND =", value, "isRecommend");
			return (Criteria) this;
		}

		public Criteria andIsRecommendNotEqualTo(Integer value) {
			addCriterion("IS_RECOMMEND <>", value, "isRecommend");
			return (Criteria) this;
		}

		public Criteria andIsRecommendGreaterThan(Integer value) {
			addCriterion("IS_RECOMMEND >", value, "isRecommend");
			return (Criteria) this;
		}

		public Criteria andIsRecommendGreaterThanOrEqualTo(Integer value) {
			addCriterion("IS_RECOMMEND >=", value, "isRecommend");
			return (Criteria) this;
		}

		public Criteria andIsRecommendLessThan(Integer value) {
			addCriterion("IS_RECOMMEND <", value, "isRecommend");
			return (Criteria) this;
		}

		public Criteria andIsRecommendLessThanOrEqualTo(Integer value) {
			addCriterion("IS_RECOMMEND <=", value, "isRecommend");
			return (Criteria) this;
		}

		public Criteria andIsRecommendIn(List<Integer> values) {
			addCriterion("IS_RECOMMEND in", values, "isRecommend");
			return (Criteria) this;
		}

		public Criteria andIsRecommendNotIn(List<Integer> values) {
			addCriterion("IS_RECOMMEND not in", values, "isRecommend");
			return (Criteria) this;
		}

		public Criteria andIsRecommendBetween(Integer value1, Integer value2) {
			addCriterion("IS_RECOMMEND between", value1, value2, "isRecommend");
			return (Criteria) this;
		}

		public Criteria andIsRecommendNotBetween(Integer value1, Integer value2) {
			addCriterion("IS_RECOMMEND not between", value1, value2,
					"isRecommend");
			return (Criteria) this;
		}
	}

	public static class Criteria extends GeneratedCriteria {

		protected Criteria() {
			super();
		}
	}

	public static class Criterion {
		private String condition;

		private Object value;

		private Object secondValue;

		private boolean noValue;

		private boolean singleValue;

		private boolean betweenValue;

		private boolean listValue;

		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}
}