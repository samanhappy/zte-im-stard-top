package com.zte.im.mybatis.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TdDeskSoftwareInfoExample {
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public TdDeskSoftwareInfoExample() {
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
			addCriterion("id is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("id is not null");
			return (Criteria) this;
		}

		public Criteria andIdEqualTo(Integer value) {
			addCriterion("id =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(Integer value) {
			addCriterion("id <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(Integer value) {
			addCriterion("id >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("id >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(Integer value) {
			addCriterion("id <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(Integer value) {
			addCriterion("id <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<Integer> values) {
			addCriterion("id in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<Integer> values) {
			addCriterion("id not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(Integer value1, Integer value2) {
			addCriterion("id between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(Integer value1, Integer value2) {
			addCriterion("id not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andSoftwareVersionIsNull() {
			addCriterion("software_version is null");
			return (Criteria) this;
		}

		public Criteria andSoftwareVersionIsNotNull() {
			addCriterion("software_version is not null");
			return (Criteria) this;
		}

		public Criteria andSoftwareVersionEqualTo(Integer value) {
			addCriterion("software_version =", value, "softwareVersion");
			return (Criteria) this;
		}

		public Criteria andSoftwareVersionNotEqualTo(Integer value) {
			addCriterion("software_version <>", value, "softwareVersion");
			return (Criteria) this;
		}

		public Criteria andSoftwareVersionGreaterThan(Integer value) {
			addCriterion("software_version >", value, "softwareVersion");
			return (Criteria) this;
		}

		public Criteria andSoftwareVersionGreaterThanOrEqualTo(Integer value) {
			addCriterion("software_version >=", value, "softwareVersion");
			return (Criteria) this;
		}

		public Criteria andSoftwareVersionLessThan(Integer value) {
			addCriterion("software_version <", value, "softwareVersion");
			return (Criteria) this;
		}

		public Criteria andSoftwareVersionLessThanOrEqualTo(Integer value) {
			addCriterion("software_version <=", value, "softwareVersion");
			return (Criteria) this;
		}

		public Criteria andSoftwareVersionIn(List<Integer> values) {
			addCriterion("software_version in", values, "softwareVersion");
			return (Criteria) this;
		}

		public Criteria andSoftwareVersionNotIn(List<Integer> values) {
			addCriterion("software_version not in", values, "softwareVersion");
			return (Criteria) this;
		}

		public Criteria andSoftwareVersionBetween(Integer value1, Integer value2) {
			addCriterion("software_version between", value1, value2,
					"softwareVersion");
			return (Criteria) this;
		}

		public Criteria andSoftwareVersionNotBetween(Integer value1,
				Integer value2) {
			addCriterion("software_version not between", value1, value2,
					"softwareVersion");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlIsNull() {
			addCriterion("update_url is null");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlIsNotNull() {
			addCriterion("update_url is not null");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlEqualTo(String value) {
			addCriterion("update_url =", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlNotEqualTo(String value) {
			addCriterion("update_url <>", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlGreaterThan(String value) {
			addCriterion("update_url >", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlGreaterThanOrEqualTo(String value) {
			addCriterion("update_url >=", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlLessThan(String value) {
			addCriterion("update_url <", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlLessThanOrEqualTo(String value) {
			addCriterion("update_url <=", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlLike(String value) {
			addCriterion("update_url like", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlNotLike(String value) {
			addCriterion("update_url not like", value, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlIn(List<String> values) {
			addCriterion("update_url in", values, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlNotIn(List<String> values) {
			addCriterion("update_url not in", values, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlBetween(String value1, String value2) {
			addCriterion("update_url between", value1, value2, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andUpdateUrlNotBetween(String value1, String value2) {
			addCriterion("update_url not between", value1, value2, "updateUrl");
			return (Criteria) this;
		}

		public Criteria andForcetwoIsNull() {
			addCriterion("forcetwo is null");
			return (Criteria) this;
		}

		public Criteria andForcetwoIsNotNull() {
			addCriterion("forcetwo is not null");
			return (Criteria) this;
		}

		public Criteria andForcetwoEqualTo(Integer value) {
			addCriterion("forcetwo =", value, "forcetwo");
			return (Criteria) this;
		}

		public Criteria andForcetwoNotEqualTo(Integer value) {
			addCriterion("forcetwo <>", value, "forcetwo");
			return (Criteria) this;
		}

		public Criteria andForcetwoGreaterThan(Integer value) {
			addCriterion("forcetwo >", value, "forcetwo");
			return (Criteria) this;
		}

		public Criteria andForcetwoGreaterThanOrEqualTo(Integer value) {
			addCriterion("forcetwo >=", value, "forcetwo");
			return (Criteria) this;
		}

		public Criteria andForcetwoLessThan(Integer value) {
			addCriterion("forcetwo <", value, "forcetwo");
			return (Criteria) this;
		}

		public Criteria andForcetwoLessThanOrEqualTo(Integer value) {
			addCriterion("forcetwo <=", value, "forcetwo");
			return (Criteria) this;
		}

		public Criteria andForcetwoIn(List<Integer> values) {
			addCriterion("forcetwo in", values, "forcetwo");
			return (Criteria) this;
		}

		public Criteria andForcetwoNotIn(List<Integer> values) {
			addCriterion("forcetwo not in", values, "forcetwo");
			return (Criteria) this;
		}

		public Criteria andForcetwoBetween(Integer value1, Integer value2) {
			addCriterion("forcetwo between", value1, value2, "forcetwo");
			return (Criteria) this;
		}

		public Criteria andForcetwoNotBetween(Integer value1, Integer value2) {
			addCriterion("forcetwo not between", value1, value2, "forcetwo");
			return (Criteria) this;
		}

		public Criteria andUpdateContentIsNull() {
			addCriterion("update_content is null");
			return (Criteria) this;
		}

		public Criteria andUpdateContentIsNotNull() {
			addCriterion("update_content is not null");
			return (Criteria) this;
		}

		public Criteria andUpdateContentEqualTo(String value) {
			addCriterion("update_content =", value, "updateContent");
			return (Criteria) this;
		}

		public Criteria andUpdateContentNotEqualTo(String value) {
			addCriterion("update_content <>", value, "updateContent");
			return (Criteria) this;
		}

		public Criteria andUpdateContentGreaterThan(String value) {
			addCriterion("update_content >", value, "updateContent");
			return (Criteria) this;
		}

		public Criteria andUpdateContentGreaterThanOrEqualTo(String value) {
			addCriterion("update_content >=", value, "updateContent");
			return (Criteria) this;
		}

		public Criteria andUpdateContentLessThan(String value) {
			addCriterion("update_content <", value, "updateContent");
			return (Criteria) this;
		}

		public Criteria andUpdateContentLessThanOrEqualTo(String value) {
			addCriterion("update_content <=", value, "updateContent");
			return (Criteria) this;
		}

		public Criteria andUpdateContentLike(String value) {
			addCriterion("update_content like", value, "updateContent");
			return (Criteria) this;
		}

		public Criteria andUpdateContentNotLike(String value) {
			addCriterion("update_content not like", value, "updateContent");
			return (Criteria) this;
		}

		public Criteria andUpdateContentIn(List<String> values) {
			addCriterion("update_content in", values, "updateContent");
			return (Criteria) this;
		}

		public Criteria andUpdateContentNotIn(List<String> values) {
			addCriterion("update_content not in", values, "updateContent");
			return (Criteria) this;
		}

		public Criteria andUpdateContentBetween(String value1, String value2) {
			addCriterion("update_content between", value1, value2,
					"updateContent");
			return (Criteria) this;
		}

		public Criteria andUpdateContentNotBetween(String value1, String value2) {
			addCriterion("update_content not between", value1, value2,
					"updateContent");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIsNull() {
			addCriterion("update_time is null");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIsNotNull() {
			addCriterion("update_time is not null");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeEqualTo(Date value) {
			addCriterionForJDBCDate("update_time =", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("update_time <>", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("update_time >", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("update_time >=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThan(Date value) {
			addCriterionForJDBCDate("update_time <", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("update_time <=", value, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeIn(List<Date> values) {
			addCriterionForJDBCDate("update_time in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("update_time not in", values, "updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("update_time between", value1, value2,
					"updateTime");
			return (Criteria) this;
		}

		public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("update_time not between", value1, value2,
					"updateTime");
			return (Criteria) this;
		}

		public Criteria andClientTypeIsNull() {
			addCriterion("client_type is null");
			return (Criteria) this;
		}

		public Criteria andClientTypeIsNotNull() {
			addCriterion("client_type is not null");
			return (Criteria) this;
		}

		public Criteria andClientTypeEqualTo(String value) {
			addCriterion("client_type =", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeNotEqualTo(String value) {
			addCriterion("client_type <>", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeGreaterThan(String value) {
			addCriterion("client_type >", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeGreaterThanOrEqualTo(String value) {
			addCriterion("client_type >=", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeLessThan(String value) {
			addCriterion("client_type <", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeLessThanOrEqualTo(String value) {
			addCriterion("client_type <=", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeLike(String value) {
			addCriterion("client_type like", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeNotLike(String value) {
			addCriterion("client_type not like", value, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeIn(List<String> values) {
			addCriterion("client_type in", values, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeNotIn(List<String> values) {
			addCriterion("client_type not in", values, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeBetween(String value1, String value2) {
			addCriterion("client_type between", value1, value2, "clientType");
			return (Criteria) this;
		}

		public Criteria andClientTypeNotBetween(String value1, String value2) {
			addCriterion("client_type not between", value1, value2,
					"clientType");
			return (Criteria) this;
		}

		public Criteria andUserIdIsNull() {
			addCriterion("user_id is null");
			return (Criteria) this;
		}

		public Criteria andUserIdIsNotNull() {
			addCriterion("user_id is not null");
			return (Criteria) this;
		}

		public Criteria andUserIdEqualTo(Integer value) {
			addCriterion("user_id =", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotEqualTo(Integer value) {
			addCriterion("user_id <>", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThan(Integer value) {
			addCriterion("user_id >", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("user_id >=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThan(Integer value) {
			addCriterion("user_id <", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdLessThanOrEqualTo(Integer value) {
			addCriterion("user_id <=", value, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdIn(List<Integer> values) {
			addCriterion("user_id in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotIn(List<Integer> values) {
			addCriterion("user_id not in", values, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdBetween(Integer value1, Integer value2) {
			addCriterion("user_id between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
			addCriterion("user_id not between", value1, value2, "userId");
			return (Criteria) this;
		}

		public Criteria andIsActiveIsNull() {
			addCriterion("is_active is null");
			return (Criteria) this;
		}

		public Criteria andIsActiveIsNotNull() {
			addCriterion("is_active is not null");
			return (Criteria) this;
		}

		public Criteria andIsActiveEqualTo(Integer value) {
			addCriterion("is_active =", value, "isActive");
			return (Criteria) this;
		}

		public Criteria andIsActiveNotEqualTo(Integer value) {
			addCriterion("is_active <>", value, "isActive");
			return (Criteria) this;
		}

		public Criteria andIsActiveGreaterThan(Integer value) {
			addCriterion("is_active >", value, "isActive");
			return (Criteria) this;
		}

		public Criteria andIsActiveGreaterThanOrEqualTo(Integer value) {
			addCriterion("is_active >=", value, "isActive");
			return (Criteria) this;
		}

		public Criteria andIsActiveLessThan(Integer value) {
			addCriterion("is_active <", value, "isActive");
			return (Criteria) this;
		}

		public Criteria andIsActiveLessThanOrEqualTo(Integer value) {
			addCriterion("is_active <=", value, "isActive");
			return (Criteria) this;
		}

		public Criteria andIsActiveIn(List<Integer> values) {
			addCriterion("is_active in", values, "isActive");
			return (Criteria) this;
		}

		public Criteria andIsActiveNotIn(List<Integer> values) {
			addCriterion("is_active not in", values, "isActive");
			return (Criteria) this;
		}

		public Criteria andIsActiveBetween(Integer value1, Integer value2) {
			addCriterion("is_active between", value1, value2, "isActive");
			return (Criteria) this;
		}

		public Criteria andIsActiveNotBetween(Integer value1, Integer value2) {
			addCriterion("is_active not between", value1, value2, "isActive");
			return (Criteria) this;
		}

		public Criteria andVersionNameIsNull() {
			addCriterion("version_name is null");
			return (Criteria) this;
		}

		public Criteria andVersionNameIsNotNull() {
			addCriterion("version_name is not null");
			return (Criteria) this;
		}

		public Criteria andVersionNameEqualTo(String value) {
			addCriterion("version_name =", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameNotEqualTo(String value) {
			addCriterion("version_name <>", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameGreaterThan(String value) {
			addCriterion("version_name >", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameGreaterThanOrEqualTo(String value) {
			addCriterion("version_name >=", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameLessThan(String value) {
			addCriterion("version_name <", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameLessThanOrEqualTo(String value) {
			addCriterion("version_name <=", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameLike(String value) {
			addCriterion("version_name like", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameNotLike(String value) {
			addCriterion("version_name not like", value, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameIn(List<String> values) {
			addCriterion("version_name in", values, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameNotIn(List<String> values) {
			addCriterion("version_name not in", values, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameBetween(String value1, String value2) {
			addCriterion("version_name between", value1, value2, "versionName");
			return (Criteria) this;
		}

		public Criteria andVersionNameNotBetween(String value1, String value2) {
			addCriterion("version_name not between", value1, value2,
					"versionName");
			return (Criteria) this;
		}

		public Criteria andIsEnableIsNull() {
			addCriterion("is_enable is null");
			return (Criteria) this;
		}

		public Criteria andIsEnableIsNotNull() {
			addCriterion("is_enable is not null");
			return (Criteria) this;
		}

		public Criteria andIsEnableEqualTo(Integer value) {
			addCriterion("is_enable =", value, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableNotEqualTo(Integer value) {
			addCriterion("is_enable <>", value, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableGreaterThan(Integer value) {
			addCriterion("is_enable >", value, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableGreaterThanOrEqualTo(Integer value) {
			addCriterion("is_enable >=", value, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableLessThan(Integer value) {
			addCriterion("is_enable <", value, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableLessThanOrEqualTo(Integer value) {
			addCriterion("is_enable <=", value, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableIn(List<Integer> values) {
			addCriterion("is_enable in", values, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableNotIn(List<Integer> values) {
			addCriterion("is_enable not in", values, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableBetween(Integer value1, Integer value2) {
			addCriterion("is_enable between", value1, value2, "isEnable");
			return (Criteria) this;
		}

		public Criteria andIsEnableNotBetween(Integer value1, Integer value2) {
			addCriterion("is_enable not between", value1, value2, "isEnable");
			return (Criteria) this;
		}

		public Criteria andSoftware1IsNull() {
			addCriterion("software1 is null");
			return (Criteria) this;
		}

		public Criteria andSoftware1IsNotNull() {
			addCriterion("software1 is not null");
			return (Criteria) this;
		}

		public Criteria andSoftware1EqualTo(String value) {
			addCriterion("software1 =", value, "software1");
			return (Criteria) this;
		}

		public Criteria andSoftware1NotEqualTo(String value) {
			addCriterion("software1 <>", value, "software1");
			return (Criteria) this;
		}

		public Criteria andSoftware1GreaterThan(String value) {
			addCriterion("software1 >", value, "software1");
			return (Criteria) this;
		}

		public Criteria andSoftware1GreaterThanOrEqualTo(String value) {
			addCriterion("software1 >=", value, "software1");
			return (Criteria) this;
		}

		public Criteria andSoftware1LessThan(String value) {
			addCriterion("software1 <", value, "software1");
			return (Criteria) this;
		}

		public Criteria andSoftware1LessThanOrEqualTo(String value) {
			addCriterion("software1 <=", value, "software1");
			return (Criteria) this;
		}

		public Criteria andSoftware1Like(String value) {
			addCriterion("software1 like", value, "software1");
			return (Criteria) this;
		}

		public Criteria andSoftware1NotLike(String value) {
			addCriterion("software1 not like", value, "software1");
			return (Criteria) this;
		}

		public Criteria andSoftware1In(List<String> values) {
			addCriterion("software1 in", values, "software1");
			return (Criteria) this;
		}

		public Criteria andSoftware1NotIn(List<String> values) {
			addCriterion("software1 not in", values, "software1");
			return (Criteria) this;
		}

		public Criteria andSoftware1Between(String value1, String value2) {
			addCriterion("software1 between", value1, value2, "software1");
			return (Criteria) this;
		}

		public Criteria andSoftware1NotBetween(String value1, String value2) {
			addCriterion("software1 not between", value1, value2, "software1");
			return (Criteria) this;
		}

		public Criteria andSoftware2IsNull() {
			addCriterion("software2 is null");
			return (Criteria) this;
		}

		public Criteria andSoftware2IsNotNull() {
			addCriterion("software2 is not null");
			return (Criteria) this;
		}

		public Criteria andSoftware2EqualTo(String value) {
			addCriterion("software2 =", value, "software2");
			return (Criteria) this;
		}

		public Criteria andSoftware2NotEqualTo(String value) {
			addCriterion("software2 <>", value, "software2");
			return (Criteria) this;
		}

		public Criteria andSoftware2GreaterThan(String value) {
			addCriterion("software2 >", value, "software2");
			return (Criteria) this;
		}

		public Criteria andSoftware2GreaterThanOrEqualTo(String value) {
			addCriterion("software2 >=", value, "software2");
			return (Criteria) this;
		}

		public Criteria andSoftware2LessThan(String value) {
			addCriterion("software2 <", value, "software2");
			return (Criteria) this;
		}

		public Criteria andSoftware2LessThanOrEqualTo(String value) {
			addCriterion("software2 <=", value, "software2");
			return (Criteria) this;
		}

		public Criteria andSoftware2Like(String value) {
			addCriterion("software2 like", value, "software2");
			return (Criteria) this;
		}

		public Criteria andSoftware2NotLike(String value) {
			addCriterion("software2 not like", value, "software2");
			return (Criteria) this;
		}

		public Criteria andSoftware2In(List<String> values) {
			addCriterion("software2 in", values, "software2");
			return (Criteria) this;
		}

		public Criteria andSoftware2NotIn(List<String> values) {
			addCriterion("software2 not in", values, "software2");
			return (Criteria) this;
		}

		public Criteria andSoftware2Between(String value1, String value2) {
			addCriterion("software2 between", value1, value2, "software2");
			return (Criteria) this;
		}

		public Criteria andSoftware2NotBetween(String value1, String value2) {
			addCriterion("software2 not between", value1, value2, "software2");
			return (Criteria) this;
		}

		public Criteria andSoftware3IsNull() {
			addCriterion("software3 is null");
			return (Criteria) this;
		}

		public Criteria andSoftware3IsNotNull() {
			addCriterion("software3 is not null");
			return (Criteria) this;
		}

		public Criteria andSoftware3EqualTo(String value) {
			addCriterion("software3 =", value, "software3");
			return (Criteria) this;
		}

		public Criteria andSoftware3NotEqualTo(String value) {
			addCriterion("software3 <>", value, "software3");
			return (Criteria) this;
		}

		public Criteria andSoftware3GreaterThan(String value) {
			addCriterion("software3 >", value, "software3");
			return (Criteria) this;
		}

		public Criteria andSoftware3GreaterThanOrEqualTo(String value) {
			addCriterion("software3 >=", value, "software3");
			return (Criteria) this;
		}

		public Criteria andSoftware3LessThan(String value) {
			addCriterion("software3 <", value, "software3");
			return (Criteria) this;
		}

		public Criteria andSoftware3LessThanOrEqualTo(String value) {
			addCriterion("software3 <=", value, "software3");
			return (Criteria) this;
		}

		public Criteria andSoftware3Like(String value) {
			addCriterion("software3 like", value, "software3");
			return (Criteria) this;
		}

		public Criteria andSoftware3NotLike(String value) {
			addCriterion("software3 not like", value, "software3");
			return (Criteria) this;
		}

		public Criteria andSoftware3In(List<String> values) {
			addCriterion("software3 in", values, "software3");
			return (Criteria) this;
		}

		public Criteria andSoftware3NotIn(List<String> values) {
			addCriterion("software3 not in", values, "software3");
			return (Criteria) this;
		}

		public Criteria andSoftware3Between(String value1, String value2) {
			addCriterion("software3 between", value1, value2, "software3");
			return (Criteria) this;
		}

		public Criteria andSoftware3NotBetween(String value1, String value2) {
			addCriterion("software3 not between", value1, value2, "software3");
			return (Criteria) this;
		}

		public Criteria andApkSizeIsNull() {
			addCriterion("apk_size is null");
			return (Criteria) this;
		}

		public Criteria andApkSizeIsNotNull() {
			addCriterion("apk_size is not null");
			return (Criteria) this;
		}

		public Criteria andApkSizeEqualTo(Integer value) {
			addCriterion("apk_size =", value, "apkSize");
			return (Criteria) this;
		}

		public Criteria andApkSizeNotEqualTo(Integer value) {
			addCriterion("apk_size <>", value, "apkSize");
			return (Criteria) this;
		}

		public Criteria andApkSizeGreaterThan(Integer value) {
			addCriterion("apk_size >", value, "apkSize");
			return (Criteria) this;
		}

		public Criteria andApkSizeGreaterThanOrEqualTo(Integer value) {
			addCriterion("apk_size >=", value, "apkSize");
			return (Criteria) this;
		}

		public Criteria andApkSizeLessThan(Integer value) {
			addCriterion("apk_size <", value, "apkSize");
			return (Criteria) this;
		}

		public Criteria andApkSizeLessThanOrEqualTo(Integer value) {
			addCriterion("apk_size <=", value, "apkSize");
			return (Criteria) this;
		}

		public Criteria andApkSizeIn(List<Integer> values) {
			addCriterion("apk_size in", values, "apkSize");
			return (Criteria) this;
		}

		public Criteria andApkSizeNotIn(List<Integer> values) {
			addCriterion("apk_size not in", values, "apkSize");
			return (Criteria) this;
		}

		public Criteria andApkSizeBetween(Integer value1, Integer value2) {
			addCriterion("apk_size between", value1, value2, "apkSize");
			return (Criteria) this;
		}

		public Criteria andApkSizeNotBetween(Integer value1, Integer value2) {
			addCriterion("apk_size not between", value1, value2, "apkSize");
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