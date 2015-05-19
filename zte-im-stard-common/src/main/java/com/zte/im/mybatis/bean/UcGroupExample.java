package com.zte.im.mybatis.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UcGroupExample {
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public UcGroupExample() {
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

		public Criteria andIdEqualTo(String value) {
			addCriterion("id =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(String value) {
			addCriterion("id <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(String value) {
			addCriterion("id >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(String value) {
			addCriterion("id >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(String value) {
			addCriterion("id <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(String value) {
			addCriterion("id <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLike(String value) {
			addCriterion("id like", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotLike(String value) {
			addCriterion("id not like", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<String> values) {
			addCriterion("id in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<String> values) {
			addCriterion("id not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(String value1, String value2) {
			addCriterion("id between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(String value1, String value2) {
			addCriterion("id not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andTenantIdIsNull() {
			addCriterion("tenant_id is null");
			return (Criteria) this;
		}

		public Criteria andTenantIdIsNotNull() {
			addCriterion("tenant_id is not null");
			return (Criteria) this;
		}

		public Criteria andTenantIdEqualTo(String value) {
			addCriterion("tenant_id =", value, "tenantId");
			return (Criteria) this;
		}

		public Criteria andTenantIdNotEqualTo(String value) {
			addCriterion("tenant_id <>", value, "tenantId");
			return (Criteria) this;
		}

		public Criteria andTenantIdGreaterThan(String value) {
			addCriterion("tenant_id >", value, "tenantId");
			return (Criteria) this;
		}

		public Criteria andTenantIdGreaterThanOrEqualTo(String value) {
			addCriterion("tenant_id >=", value, "tenantId");
			return (Criteria) this;
		}

		public Criteria andTenantIdLessThan(String value) {
			addCriterion("tenant_id <", value, "tenantId");
			return (Criteria) this;
		}

		public Criteria andTenantIdLessThanOrEqualTo(String value) {
			addCriterion("tenant_id <=", value, "tenantId");
			return (Criteria) this;
		}

		public Criteria andTenantIdLike(String value) {
			addCriterion("tenant_id like", value, "tenantId");
			return (Criteria) this;
		}

		public Criteria andTenantIdNotLike(String value) {
			addCriterion("tenant_id not like", value, "tenantId");
			return (Criteria) this;
		}

		public Criteria andTenantIdIn(List<String> values) {
			addCriterion("tenant_id in", values, "tenantId");
			return (Criteria) this;
		}

		public Criteria andTenantIdNotIn(List<String> values) {
			addCriterion("tenant_id not in", values, "tenantId");
			return (Criteria) this;
		}

		public Criteria andTenantIdBetween(String value1, String value2) {
			addCriterion("tenant_id between", value1, value2, "tenantId");
			return (Criteria) this;
		}

		public Criteria andTenantIdNotBetween(String value1, String value2) {
			addCriterion("tenant_id not between", value1, value2, "tenantId");
			return (Criteria) this;
		}

		public Criteria andTypeIdIsNull() {
			addCriterion("type_id is null");
			return (Criteria) this;
		}

		public Criteria andTypeIdIsNotNull() {
			addCriterion("type_id is not null");
			return (Criteria) this;
		}

		public Criteria andTypeIdEqualTo(String value) {
			addCriterion("type_id =", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdNotEqualTo(String value) {
			addCriterion("type_id <>", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdGreaterThan(String value) {
			addCriterion("type_id >", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdGreaterThanOrEqualTo(String value) {
			addCriterion("type_id >=", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdLessThan(String value) {
			addCriterion("type_id <", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdLessThanOrEqualTo(String value) {
			addCriterion("type_id <=", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdLike(String value) {
			addCriterion("type_id like", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdNotLike(String value) {
			addCriterion("type_id not like", value, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdIn(List<String> values) {
			addCriterion("type_id in", values, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdNotIn(List<String> values) {
			addCriterion("type_id not in", values, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdBetween(String value1, String value2) {
			addCriterion("type_id between", value1, value2, "typeId");
			return (Criteria) this;
		}

		public Criteria andTypeIdNotBetween(String value1, String value2) {
			addCriterion("type_id not between", value1, value2, "typeId");
			return (Criteria) this;
		}

		public Criteria andPidIsNull() {
			addCriterion("pid is null");
			return (Criteria) this;
		}

		public Criteria andPidIsNotNull() {
			addCriterion("pid is not null");
			return (Criteria) this;
		}

		public Criteria andPidEqualTo(String value) {
			addCriterion("pid =", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidNotEqualTo(String value) {
			addCriterion("pid <>", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidGreaterThan(String value) {
			addCriterion("pid >", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidGreaterThanOrEqualTo(String value) {
			addCriterion("pid >=", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidLessThan(String value) {
			addCriterion("pid <", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidLessThanOrEqualTo(String value) {
			addCriterion("pid <=", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidLike(String value) {
			addCriterion("pid like", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidNotLike(String value) {
			addCriterion("pid not like", value, "pid");
			return (Criteria) this;
		}

		public Criteria andPidIn(List<String> values) {
			addCriterion("pid in", values, "pid");
			return (Criteria) this;
		}

		public Criteria andPidNotIn(List<String> values) {
			addCriterion("pid not in", values, "pid");
			return (Criteria) this;
		}

		public Criteria andPidBetween(String value1, String value2) {
			addCriterion("pid between", value1, value2, "pid");
			return (Criteria) this;
		}

		public Criteria andPidNotBetween(String value1, String value2) {
			addCriterion("pid not between", value1, value2, "pid");
			return (Criteria) this;
		}

		public Criteria andNameIsNull() {
			addCriterion("name is null");
			return (Criteria) this;
		}

		public Criteria andNameIsNotNull() {
			addCriterion("name is not null");
			return (Criteria) this;
		}

		public Criteria andNameEqualTo(String value) {
			addCriterion("name =", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotEqualTo(String value) {
			addCriterion("name <>", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameGreaterThan(String value) {
			addCriterion("name >", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameGreaterThanOrEqualTo(String value) {
			addCriterion("name >=", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameLessThan(String value) {
			addCriterion("name <", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameLessThanOrEqualTo(String value) {
			addCriterion("name <=", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameLike(String value) {
			addCriterion("name like", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotLike(String value) {
			addCriterion("name not like", value, "name");
			return (Criteria) this;
		}

		public Criteria andNameIn(List<String> values) {
			addCriterion("name in", values, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotIn(List<String> values) {
			addCriterion("name not in", values, "name");
			return (Criteria) this;
		}

		public Criteria andNameBetween(String value1, String value2) {
			addCriterion("name between", value1, value2, "name");
			return (Criteria) this;
		}

		public Criteria andNameNotBetween(String value1, String value2) {
			addCriterion("name not between", value1, value2, "name");
			return (Criteria) this;
		}

		public Criteria andPinyinIsNull() {
			addCriterion("pinyin is null");
			return (Criteria) this;
		}

		public Criteria andPinyinIsNotNull() {
			addCriterion("pinyin is not null");
			return (Criteria) this;
		}

		public Criteria andPinyinEqualTo(String value) {
			addCriterion("pinyin =", value, "pinyin");
			return (Criteria) this;
		}

		public Criteria andPinyinNotEqualTo(String value) {
			addCriterion("pinyin <>", value, "pinyin");
			return (Criteria) this;
		}

		public Criteria andPinyinGreaterThan(String value) {
			addCriterion("pinyin >", value, "pinyin");
			return (Criteria) this;
		}

		public Criteria andPinyinGreaterThanOrEqualTo(String value) {
			addCriterion("pinyin >=", value, "pinyin");
			return (Criteria) this;
		}

		public Criteria andPinyinLessThan(String value) {
			addCriterion("pinyin <", value, "pinyin");
			return (Criteria) this;
		}

		public Criteria andPinyinLessThanOrEqualTo(String value) {
			addCriterion("pinyin <=", value, "pinyin");
			return (Criteria) this;
		}

		public Criteria andPinyinLike(String value) {
			addCriterion("pinyin like", value, "pinyin");
			return (Criteria) this;
		}

		public Criteria andPinyinNotLike(String value) {
			addCriterion("pinyin not like", value, "pinyin");
			return (Criteria) this;
		}

		public Criteria andPinyinIn(List<String> values) {
			addCriterion("pinyin in", values, "pinyin");
			return (Criteria) this;
		}

		public Criteria andPinyinNotIn(List<String> values) {
			addCriterion("pinyin not in", values, "pinyin");
			return (Criteria) this;
		}

		public Criteria andPinyinBetween(String value1, String value2) {
			addCriterion("pinyin between", value1, value2, "pinyin");
			return (Criteria) this;
		}

		public Criteria andPinyinNotBetween(String value1, String value2) {
			addCriterion("pinyin not between", value1, value2, "pinyin");
			return (Criteria) this;
		}

		public Criteria andFullIdIsNull() {
			addCriterion("full_id is null");
			return (Criteria) this;
		}

		public Criteria andFullIdIsNotNull() {
			addCriterion("full_id is not null");
			return (Criteria) this;
		}

		public Criteria andFullIdEqualTo(String value) {
			addCriterion("full_id =", value, "fullId");
			return (Criteria) this;
		}

		public Criteria andFullIdNotEqualTo(String value) {
			addCriterion("full_id <>", value, "fullId");
			return (Criteria) this;
		}

		public Criteria andFullIdGreaterThan(String value) {
			addCriterion("full_id >", value, "fullId");
			return (Criteria) this;
		}

		public Criteria andFullIdGreaterThanOrEqualTo(String value) {
			addCriterion("full_id >=", value, "fullId");
			return (Criteria) this;
		}

		public Criteria andFullIdLessThan(String value) {
			addCriterion("full_id <", value, "fullId");
			return (Criteria) this;
		}

		public Criteria andFullIdLessThanOrEqualTo(String value) {
			addCriterion("full_id <=", value, "fullId");
			return (Criteria) this;
		}

		public Criteria andFullIdLike(String value) {
			addCriterion("full_id like", value, "fullId");
			return (Criteria) this;
		}

		public Criteria andFullIdNotLike(String value) {
			addCriterion("full_id not like", value, "fullId");
			return (Criteria) this;
		}

		public Criteria andFullIdIn(List<String> values) {
			addCriterion("full_id in", values, "fullId");
			return (Criteria) this;
		}

		public Criteria andFullIdNotIn(List<String> values) {
			addCriterion("full_id not in", values, "fullId");
			return (Criteria) this;
		}

		public Criteria andFullIdBetween(String value1, String value2) {
			addCriterion("full_id between", value1, value2, "fullId");
			return (Criteria) this;
		}

		public Criteria andFullIdNotBetween(String value1, String value2) {
			addCriterion("full_id not between", value1, value2, "fullId");
			return (Criteria) this;
		}

		public Criteria andFullNameIsNull() {
			addCriterion("full_name is null");
			return (Criteria) this;
		}

		public Criteria andFullNameIsNotNull() {
			addCriterion("full_name is not null");
			return (Criteria) this;
		}

		public Criteria andFullNameEqualTo(String value) {
			addCriterion("full_name =", value, "fullName");
			return (Criteria) this;
		}

		public Criteria andFullNameNotEqualTo(String value) {
			addCriterion("full_name <>", value, "fullName");
			return (Criteria) this;
		}

		public Criteria andFullNameGreaterThan(String value) {
			addCriterion("full_name >", value, "fullName");
			return (Criteria) this;
		}

		public Criteria andFullNameGreaterThanOrEqualTo(String value) {
			addCriterion("full_name >=", value, "fullName");
			return (Criteria) this;
		}

		public Criteria andFullNameLessThan(String value) {
			addCriterion("full_name <", value, "fullName");
			return (Criteria) this;
		}

		public Criteria andFullNameLessThanOrEqualTo(String value) {
			addCriterion("full_name <=", value, "fullName");
			return (Criteria) this;
		}

		public Criteria andFullNameLike(String value) {
			addCriterion("full_name like", value, "fullName");
			return (Criteria) this;
		}

		public Criteria andFullNameNotLike(String value) {
			addCriterion("full_name not like", value, "fullName");
			return (Criteria) this;
		}

		public Criteria andFullNameIn(List<String> values) {
			addCriterion("full_name in", values, "fullName");
			return (Criteria) this;
		}

		public Criteria andFullNameNotIn(List<String> values) {
			addCriterion("full_name not in", values, "fullName");
			return (Criteria) this;
		}

		public Criteria andFullNameBetween(String value1, String value2) {
			addCriterion("full_name between", value1, value2, "fullName");
			return (Criteria) this;
		}

		public Criteria andFullNameNotBetween(String value1, String value2) {
			addCriterion("full_name not between", value1, value2, "fullName");
			return (Criteria) this;
		}

		public Criteria andMailIsNull() {
			addCriterion("mail is null");
			return (Criteria) this;
		}

		public Criteria andMailIsNotNull() {
			addCriterion("mail is not null");
			return (Criteria) this;
		}

		public Criteria andMailEqualTo(String value) {
			addCriterion("mail =", value, "mail");
			return (Criteria) this;
		}

		public Criteria andMailNotEqualTo(String value) {
			addCriterion("mail <>", value, "mail");
			return (Criteria) this;
		}

		public Criteria andMailGreaterThan(String value) {
			addCriterion("mail >", value, "mail");
			return (Criteria) this;
		}

		public Criteria andMailGreaterThanOrEqualTo(String value) {
			addCriterion("mail >=", value, "mail");
			return (Criteria) this;
		}

		public Criteria andMailLessThan(String value) {
			addCriterion("mail <", value, "mail");
			return (Criteria) this;
		}

		public Criteria andMailLessThanOrEqualTo(String value) {
			addCriterion("mail <=", value, "mail");
			return (Criteria) this;
		}

		public Criteria andMailLike(String value) {
			addCriterion("mail like", value, "mail");
			return (Criteria) this;
		}

		public Criteria andMailNotLike(String value) {
			addCriterion("mail not like", value, "mail");
			return (Criteria) this;
		}

		public Criteria andMailIn(List<String> values) {
			addCriterion("mail in", values, "mail");
			return (Criteria) this;
		}

		public Criteria andMailNotIn(List<String> values) {
			addCriterion("mail not in", values, "mail");
			return (Criteria) this;
		}

		public Criteria andMailBetween(String value1, String value2) {
			addCriterion("mail between", value1, value2, "mail");
			return (Criteria) this;
		}

		public Criteria andMailNotBetween(String value1, String value2) {
			addCriterion("mail not between", value1, value2, "mail");
			return (Criteria) this;
		}

		public Criteria andSequIsNull() {
			addCriterion("sequ is null");
			return (Criteria) this;
		}

		public Criteria andSequIsNotNull() {
			addCriterion("sequ is not null");
			return (Criteria) this;
		}

		public Criteria andSequEqualTo(Long value) {
			addCriterion("sequ =", value, "sequ");
			return (Criteria) this;
		}

		public Criteria andSequNotEqualTo(Long value) {
			addCriterion("sequ <>", value, "sequ");
			return (Criteria) this;
		}

		public Criteria andSequGreaterThan(Long value) {
			addCriterion("sequ >", value, "sequ");
			return (Criteria) this;
		}

		public Criteria andSequGreaterThanOrEqualTo(Long value) {
			addCriterion("sequ >=", value, "sequ");
			return (Criteria) this;
		}

		public Criteria andSequLessThan(Long value) {
			addCriterion("sequ <", value, "sequ");
			return (Criteria) this;
		}

		public Criteria andSequLessThanOrEqualTo(Long value) {
			addCriterion("sequ <=", value, "sequ");
			return (Criteria) this;
		}

		public Criteria andSequIn(List<Long> values) {
			addCriterion("sequ in", values, "sequ");
			return (Criteria) this;
		}

		public Criteria andSequNotIn(List<Long> values) {
			addCriterion("sequ not in", values, "sequ");
			return (Criteria) this;
		}

		public Criteria andSequBetween(Long value1, Long value2) {
			addCriterion("sequ between", value1, value2, "sequ");
			return (Criteria) this;
		}

		public Criteria andSequNotBetween(Long value1, Long value2) {
			addCriterion("sequ not between", value1, value2, "sequ");
			return (Criteria) this;
		}

		public Criteria andCreatorIsNull() {
			addCriterion("creator is null");
			return (Criteria) this;
		}

		public Criteria andCreatorIsNotNull() {
			addCriterion("creator is not null");
			return (Criteria) this;
		}

		public Criteria andCreatorEqualTo(String value) {
			addCriterion("creator =", value, "creator");
			return (Criteria) this;
		}

		public Criteria andCreatorNotEqualTo(String value) {
			addCriterion("creator <>", value, "creator");
			return (Criteria) this;
		}

		public Criteria andCreatorGreaterThan(String value) {
			addCriterion("creator >", value, "creator");
			return (Criteria) this;
		}

		public Criteria andCreatorGreaterThanOrEqualTo(String value) {
			addCriterion("creator >=", value, "creator");
			return (Criteria) this;
		}

		public Criteria andCreatorLessThan(String value) {
			addCriterion("creator <", value, "creator");
			return (Criteria) this;
		}

		public Criteria andCreatorLessThanOrEqualTo(String value) {
			addCriterion("creator <=", value, "creator");
			return (Criteria) this;
		}

		public Criteria andCreatorLike(String value) {
			addCriterion("creator like", value, "creator");
			return (Criteria) this;
		}

		public Criteria andCreatorNotLike(String value) {
			addCriterion("creator not like", value, "creator");
			return (Criteria) this;
		}

		public Criteria andCreatorIn(List<String> values) {
			addCriterion("creator in", values, "creator");
			return (Criteria) this;
		}

		public Criteria andCreatorNotIn(List<String> values) {
			addCriterion("creator not in", values, "creator");
			return (Criteria) this;
		}

		public Criteria andCreatorBetween(String value1, String value2) {
			addCriterion("creator between", value1, value2, "creator");
			return (Criteria) this;
		}

		public Criteria andCreatorNotBetween(String value1, String value2) {
			addCriterion("creator not between", value1, value2, "creator");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIsNull() {
			addCriterion("create_time is null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIsNotNull() {
			addCriterion("create_time is not null");
			return (Criteria) this;
		}

		public Criteria andCreateTimeEqualTo(Date value) {
			addCriterionForJDBCDate("create_time =", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("create_time <>", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("create_time >", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("create_time >=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThan(Date value) {
			addCriterionForJDBCDate("create_time <", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("create_time <=", value, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeIn(List<Date> values) {
			addCriterionForJDBCDate("create_time in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("create_time not in", values, "createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("create_time between", value1, value2,
					"createTime");
			return (Criteria) this;
		}

		public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("create_time not between", value1, value2,
					"createTime");
			return (Criteria) this;
		}

		public Criteria andModifierIsNull() {
			addCriterion("modifier is null");
			return (Criteria) this;
		}

		public Criteria andModifierIsNotNull() {
			addCriterion("modifier is not null");
			return (Criteria) this;
		}

		public Criteria andModifierEqualTo(String value) {
			addCriterion("modifier =", value, "modifier");
			return (Criteria) this;
		}

		public Criteria andModifierNotEqualTo(String value) {
			addCriterion("modifier <>", value, "modifier");
			return (Criteria) this;
		}

		public Criteria andModifierGreaterThan(String value) {
			addCriterion("modifier >", value, "modifier");
			return (Criteria) this;
		}

		public Criteria andModifierGreaterThanOrEqualTo(String value) {
			addCriterion("modifier >=", value, "modifier");
			return (Criteria) this;
		}

		public Criteria andModifierLessThan(String value) {
			addCriterion("modifier <", value, "modifier");
			return (Criteria) this;
		}

		public Criteria andModifierLessThanOrEqualTo(String value) {
			addCriterion("modifier <=", value, "modifier");
			return (Criteria) this;
		}

		public Criteria andModifierLike(String value) {
			addCriterion("modifier like", value, "modifier");
			return (Criteria) this;
		}

		public Criteria andModifierNotLike(String value) {
			addCriterion("modifier not like", value, "modifier");
			return (Criteria) this;
		}

		public Criteria andModifierIn(List<String> values) {
			addCriterion("modifier in", values, "modifier");
			return (Criteria) this;
		}

		public Criteria andModifierNotIn(List<String> values) {
			addCriterion("modifier not in", values, "modifier");
			return (Criteria) this;
		}

		public Criteria andModifierBetween(String value1, String value2) {
			addCriterion("modifier between", value1, value2, "modifier");
			return (Criteria) this;
		}

		public Criteria andModifierNotBetween(String value1, String value2) {
			addCriterion("modifier not between", value1, value2, "modifier");
			return (Criteria) this;
		}

		public Criteria andModifiedTimeIsNull() {
			addCriterion("modified_time is null");
			return (Criteria) this;
		}

		public Criteria andModifiedTimeIsNotNull() {
			addCriterion("modified_time is not null");
			return (Criteria) this;
		}

		public Criteria andModifiedTimeEqualTo(Date value) {
			addCriterionForJDBCDate("modified_time =", value, "modifiedTime");
			return (Criteria) this;
		}

		public Criteria andModifiedTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("modified_time <>", value, "modifiedTime");
			return (Criteria) this;
		}

		public Criteria andModifiedTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("modified_time >", value, "modifiedTime");
			return (Criteria) this;
		}

		public Criteria andModifiedTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("modified_time >=", value, "modifiedTime");
			return (Criteria) this;
		}

		public Criteria andModifiedTimeLessThan(Date value) {
			addCriterionForJDBCDate("modified_time <", value, "modifiedTime");
			return (Criteria) this;
		}

		public Criteria andModifiedTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("modified_time <=", value, "modifiedTime");
			return (Criteria) this;
		}

		public Criteria andModifiedTimeIn(List<Date> values) {
			addCriterionForJDBCDate("modified_time in", values, "modifiedTime");
			return (Criteria) this;
		}

		public Criteria andModifiedTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("modified_time not in", values,
					"modifiedTime");
			return (Criteria) this;
		}

		public Criteria andModifiedTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("modified_time between", value1, value2,
					"modifiedTime");
			return (Criteria) this;
		}

		public Criteria andModifiedTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("modified_time not between", value1,
					value2, "modifiedTime");
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