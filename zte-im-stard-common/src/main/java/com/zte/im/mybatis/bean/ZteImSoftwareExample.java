package com.zte.im.mybatis.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ZteImSoftwareExample {
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public ZteImSoftwareExample() {
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

		protected void addCriterionForJDBCDate(String condition, Date value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value.getTime()),
					property);
		}

		protected void addCriterionForJDBCDate(String condition,
				List<Date> values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
			Iterator<Date> iter = values.iterator();
			while (iter.hasNext()) {
				dateList.add(new java.sql.Date(iter.next().getTime()));
			}
			addCriterion(condition, dateList, property);
		}

		protected void addCriterionForJDBCDate(String condition, Date value1,
				Date value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value1.getTime()),
					new java.sql.Date(value2.getTime()), property);
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

		public Criteria andVersionIsNull() {
			addCriterion("VERSION is null");
			return (Criteria) this;
		}

		public Criteria andVersionIsNotNull() {
			addCriterion("VERSION is not null");
			return (Criteria) this;
		}

		public Criteria andVersionEqualTo(Integer value) {
			addCriterion("VERSION =", value, "version");
			return (Criteria) this;
		}

		public Criteria andVersionNotEqualTo(Integer value) {
			addCriterion("VERSION <>", value, "version");
			return (Criteria) this;
		}

		public Criteria andVersionGreaterThan(Integer value) {
			addCriterion("VERSION >", value, "version");
			return (Criteria) this;
		}

		public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
			addCriterion("VERSION >=", value, "version");
			return (Criteria) this;
		}

		public Criteria andVersionLessThan(Integer value) {
			addCriterion("VERSION <", value, "version");
			return (Criteria) this;
		}

		public Criteria andVersionLessThanOrEqualTo(Integer value) {
			addCriterion("VERSION <=", value, "version");
			return (Criteria) this;
		}

		public Criteria andVersionIn(List<Integer> values) {
			addCriterion("VERSION in", values, "version");
			return (Criteria) this;
		}

		public Criteria andVersionNotIn(List<Integer> values) {
			addCriterion("VERSION not in", values, "version");
			return (Criteria) this;
		}

		public Criteria andVersionBetween(Integer value1, Integer value2) {
			addCriterion("VERSION between", value1, value2, "version");
			return (Criteria) this;
		}

		public Criteria andVersionNotBetween(Integer value1, Integer value2) {
			addCriterion("VERSION not between", value1, value2, "version");
			return (Criteria) this;
		}

		public Criteria andVersionNameIsNull() {
			addCriterion("VERSION_NAME is null");
			return (Criteria) this;
		}

		public Criteria andVersionNameIsNotNull() {
			addCriterion("VERSION_NAME is not null");
			return (Criteria) this;
		}

		public Criteria andVersionNameEqualTo(String value) {
			addCriterion("VERSION_NAME =", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameNotEqualTo(String value) {
			addCriterion("VERSION_NAME <>", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameGreaterThan(String value) {
			addCriterion("VERSION_NAME >", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameGreaterThanOrEqualTo(String value) {
			addCriterion("VERSION_NAME >=", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameLessThan(String value) {
			addCriterion("VERSION_NAME <", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameLessThanOrEqualTo(String value) {
			addCriterion("VERSION_NAME <=", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameLike(String value) {
			addCriterion("VERSION_NAME like", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameNotLike(String value) {
			addCriterion("VERSION_NAME not like", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameIn(List<String> values) {
			addCriterion("VERSION_NAME in", values, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameNotIn(List<String> values) {
			addCriterion("VERSION_NAME not in", values, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameBetween(String value1, String value2) {
			addCriterion("VERSION_NAME between", value1, value2, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameNotBetween(String value1, String value2) {
			addCriterion("VERSION_NAME not between", value1, value2,
					"versionName");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlIsNull() {
			addCriterion("UPDATE_URL is null");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlIsNotNull() {
			addCriterion("UPDATE_URL is not null");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlEqualTo(String value) {
			addCriterion("UPDATE_URL =", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlNotEqualTo(String value) {
			addCriterion("UPDATE_URL <>", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlGreaterThan(String value) {
			addCriterion("UPDATE_URL >", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlGreaterThanOrEqualTo(String value) {
			addCriterion("UPDATE_URL >=", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlLessThan(String value) {
			addCriterion("UPDATE_URL <", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlLessThanOrEqualTo(String value) {
			addCriterion("UPDATE_URL <=", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlLike(String value) {
			addCriterion("UPDATE_URL like", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlNotLike(String value) {
			addCriterion("UPDATE_URL not like", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlIn(List<String> values) {
			addCriterion("UPDATE_URL in", values, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlNotIn(List<String> values) {
			addCriterion("UPDATE_URL not in", values, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlBetween(String value1, String value2) {
			addCriterion("UPDATE_URL between", value1, value2, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlNotBetween(String value1, String value2) {
			addCriterion("UPDATE_URL not between", value1, value2, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andClientTypeIsNull() {
			addCriterion("CLIENT_TYPE is null");
			return (Criteria) this;
		}

		public Criteria andClientTypeIsNotNull() {
			addCriterion("CLIENT_TYPE is not null");
			return (Criteria) this;
		}

		public Criteria andClientTypeEqualTo(String value) {
			addCriterion("CLIENT_TYPE =", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeNotEqualTo(String value) {
			addCriterion("CLIENT_TYPE <>", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeGreaterThan(String value) {
			addCriterion("CLIENT_TYPE >", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeGreaterThanOrEqualTo(String value) {
			addCriterion("CLIENT_TYPE >=", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeLessThan(String value) {
			addCriterion("CLIENT_TYPE <", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeLessThanOrEqualTo(String value) {
			addCriterion("CLIENT_TYPE <=", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeLike(String value) {
			addCriterion("CLIENT_TYPE like", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeNotLike(String value) {
			addCriterion("CLIENT_TYPE not like", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeIn(List<String> values) {
			addCriterion("CLIENT_TYPE in", values, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeNotIn(List<String> values) {
			addCriterion("CLIENT_TYPE not in", values, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeBetween(String value1, String value2) {
			addCriterion("CLIENT_TYPE between", value1, value2, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeNotBetween(String value1, String value2) {
			addCriterion("CLIENT_TYPE not between", value1, value2,
					"clientType");
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
			addCriterionForJDBCDate("UPDATE_TIME =", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("UPDATE_TIME <>", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("UPDATE_TIME >", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("UPDATE_TIME >=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThan(Date value) {
			addCriterionForJDBCDate("UPDATE_TIME <", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("UPDATE_TIME <=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIn(List<Date> values) {
			addCriterionForJDBCDate("UPDATE_TIME in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("UPDATE_TIME not in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("UPDATE_TIME between", value1, value2,
					"updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("UPDATE_TIME not between", value1, value2,
					"updateTime");
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