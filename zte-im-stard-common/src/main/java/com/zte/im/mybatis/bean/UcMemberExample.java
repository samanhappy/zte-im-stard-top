package com.zte.im.mybatis.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UcMemberExample {
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public UcMemberExample() {
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

		public Criteria andCnIsNull() {
			addCriterion("cn is null");
			return (Criteria) this;
		}

		public Criteria andCnIsNotNull() {
			addCriterion("cn is not null");
			return (Criteria) this;
		}

		public Criteria andCnEqualTo(String value) {
			addCriterion("cn =", value, "cn");
			return (Criteria) this;
		}

		public Criteria andCnNotEqualTo(String value) {
			addCriterion("cn <>", value, "cn");
			return (Criteria) this;
		}

		public Criteria andCnGreaterThan(String value) {
			addCriterion("cn >", value, "cn");
			return (Criteria) this;
		}

		public Criteria andCnGreaterThanOrEqualTo(String value) {
			addCriterion("cn >=", value, "cn");
			return (Criteria) this;
		}

		public Criteria andCnLessThan(String value) {
			addCriterion("cn <", value, "cn");
			return (Criteria) this;
		}

		public Criteria andCnLessThanOrEqualTo(String value) {
			addCriterion("cn <=", value, "cn");
			return (Criteria) this;
		}

		public Criteria andCnLike(String value) {
			addCriterion("cn like", value, "cn");
			return (Criteria) this;
		}

		public Criteria andCnNotLike(String value) {
			addCriterion("cn not like", value, "cn");
			return (Criteria) this;
		}

		public Criteria andCnIn(List<String> values) {
			addCriterion("cn in", values, "cn");
			return (Criteria) this;
		}

		public Criteria andCnNotIn(List<String> values) {
			addCriterion("cn not in", values, "cn");
			return (Criteria) this;
		}

		public Criteria andCnBetween(String value1, String value2) {
			addCriterion("cn between", value1, value2, "cn");
			return (Criteria) this;
		}

		public Criteria andCnNotBetween(String value1, String value2) {
			addCriterion("cn not between", value1, value2, "cn");
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

		public Criteria andT9PinyinIsNull() {
			addCriterion("t9_pinyin is null");
			return (Criteria) this;
		}

		public Criteria andT9PinyinIsNotNull() {
			addCriterion("t9_pinyin is not null");
			return (Criteria) this;
		}

		public Criteria andT9PinyinEqualTo(String value) {
			addCriterion("t9_pinyin =", value, "t9Pinyin");
			return (Criteria) this;
		}

		public Criteria andT9PinyinNotEqualTo(String value) {
			addCriterion("t9_pinyin <>", value, "t9Pinyin");
			return (Criteria) this;
		}

		public Criteria andT9PinyinGreaterThan(String value) {
			addCriterion("t9_pinyin >", value, "t9Pinyin");
			return (Criteria) this;
		}

		public Criteria andT9PinyinGreaterThanOrEqualTo(String value) {
			addCriterion("t9_pinyin >=", value, "t9Pinyin");
			return (Criteria) this;
		}

		public Criteria andT9PinyinLessThan(String value) {
			addCriterion("t9_pinyin <", value, "t9Pinyin");
			return (Criteria) this;
		}

		public Criteria andT9PinyinLessThanOrEqualTo(String value) {
			addCriterion("t9_pinyin <=", value, "t9Pinyin");
			return (Criteria) this;
		}

		public Criteria andT9PinyinLike(String value) {
			addCriterion("t9_pinyin like", value, "t9Pinyin");
			return (Criteria) this;
		}

		public Criteria andT9PinyinNotLike(String value) {
			addCriterion("t9_pinyin not like", value, "t9Pinyin");
			return (Criteria) this;
		}

		public Criteria andT9PinyinIn(List<String> values) {
			addCriterion("t9_pinyin in", values, "t9Pinyin");
			return (Criteria) this;
		}

		public Criteria andT9PinyinNotIn(List<String> values) {
			addCriterion("t9_pinyin not in", values, "t9Pinyin");
			return (Criteria) this;
		}

		public Criteria andT9PinyinBetween(String value1, String value2) {
			addCriterion("t9_pinyin between", value1, value2, "t9Pinyin");
			return (Criteria) this;
		}

		public Criteria andT9PinyinNotBetween(String value1, String value2) {
			addCriterion("t9_pinyin not between", value1, value2, "t9Pinyin");
			return (Criteria) this;
		}

		public Criteria andNicknameIsNull() {
			addCriterion("nickname is null");
			return (Criteria) this;
		}

		public Criteria andNicknameIsNotNull() {
			addCriterion("nickname is not null");
			return (Criteria) this;
		}

		public Criteria andNicknameEqualTo(String value) {
			addCriterion("nickname =", value, "nickname");
			return (Criteria) this;
		}

		public Criteria andNicknameNotEqualTo(String value) {
			addCriterion("nickname <>", value, "nickname");
			return (Criteria) this;
		}

		public Criteria andNicknameGreaterThan(String value) {
			addCriterion("nickname >", value, "nickname");
			return (Criteria) this;
		}

		public Criteria andNicknameGreaterThanOrEqualTo(String value) {
			addCriterion("nickname >=", value, "nickname");
			return (Criteria) this;
		}

		public Criteria andNicknameLessThan(String value) {
			addCriterion("nickname <", value, "nickname");
			return (Criteria) this;
		}

		public Criteria andNicknameLessThanOrEqualTo(String value) {
			addCriterion("nickname <=", value, "nickname");
			return (Criteria) this;
		}

		public Criteria andNicknameLike(String value) {
			addCriterion("nickname like", value, "nickname");
			return (Criteria) this;
		}

		public Criteria andNicknameNotLike(String value) {
			addCriterion("nickname not like", value, "nickname");
			return (Criteria) this;
		}

		public Criteria andNicknameIn(List<String> values) {
			addCriterion("nickname in", values, "nickname");
			return (Criteria) this;
		}

		public Criteria andNicknameNotIn(List<String> values) {
			addCriterion("nickname not in", values, "nickname");
			return (Criteria) this;
		}

		public Criteria andNicknameBetween(String value1, String value2) {
			addCriterion("nickname between", value1, value2, "nickname");
			return (Criteria) this;
		}

		public Criteria andNicknameNotBetween(String value1, String value2) {
			addCriterion("nickname not between", value1, value2, "nickname");
			return (Criteria) this;
		}

		public Criteria andSexIsNull() {
			addCriterion("sex is null");
			return (Criteria) this;
		}

		public Criteria andSexIsNotNull() {
			addCriterion("sex is not null");
			return (Criteria) this;
		}

		public Criteria andSexEqualTo(String value) {
			addCriterion("sex =", value, "sex");
			return (Criteria) this;
		}

		public Criteria andSexNotEqualTo(String value) {
			addCriterion("sex <>", value, "sex");
			return (Criteria) this;
		}

		public Criteria andSexGreaterThan(String value) {
			addCriterion("sex >", value, "sex");
			return (Criteria) this;
		}

		public Criteria andSexGreaterThanOrEqualTo(String value) {
			addCriterion("sex >=", value, "sex");
			return (Criteria) this;
		}

		public Criteria andSexLessThan(String value) {
			addCriterion("sex <", value, "sex");
			return (Criteria) this;
		}

		public Criteria andSexLessThanOrEqualTo(String value) {
			addCriterion("sex <=", value, "sex");
			return (Criteria) this;
		}

		public Criteria andSexLike(String value) {
			addCriterion("sex like", value, "sex");
			return (Criteria) this;
		}

		public Criteria andSexNotLike(String value) {
			addCriterion("sex not like", value, "sex");
			return (Criteria) this;
		}

		public Criteria andSexIn(List<String> values) {
			addCriterion("sex in", values, "sex");
			return (Criteria) this;
		}

		public Criteria andSexNotIn(List<String> values) {
			addCriterion("sex not in", values, "sex");
			return (Criteria) this;
		}

		public Criteria andSexBetween(String value1, String value2) {
			addCriterion("sex between", value1, value2, "sex");
			return (Criteria) this;
		}

		public Criteria andSexNotBetween(String value1, String value2) {
			addCriterion("sex not between", value1, value2, "sex");
			return (Criteria) this;
		}

		public Criteria andBirthdayIsNull() {
			addCriterion("birthday is null");
			return (Criteria) this;
		}

		public Criteria andBirthdayIsNotNull() {
			addCriterion("birthday is not null");
			return (Criteria) this;
		}

		public Criteria andBirthdayEqualTo(Date value) {
			addCriterionForJDBCDate("birthday =", value, "birthday");
			return (Criteria) this;
		}

		public Criteria andBirthdayNotEqualTo(Date value) {
			addCriterionForJDBCDate("birthday <>", value, "birthday");
			return (Criteria) this;
		}

		public Criteria andBirthdayGreaterThan(Date value) {
			addCriterionForJDBCDate("birthday >", value, "birthday");
			return (Criteria) this;
		}

		public Criteria andBirthdayGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("birthday >=", value, "birthday");
			return (Criteria) this;
		}

		public Criteria andBirthdayLessThan(Date value) {
			addCriterionForJDBCDate("birthday <", value, "birthday");
			return (Criteria) this;
		}

		public Criteria andBirthdayLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("birthday <=", value, "birthday");
			return (Criteria) this;
		}

		public Criteria andBirthdayIn(List<Date> values) {
			addCriterionForJDBCDate("birthday in", values, "birthday");
			return (Criteria) this;
		}

		public Criteria andBirthdayNotIn(List<Date> values) {
			addCriterionForJDBCDate("birthday not in", values, "birthday");
			return (Criteria) this;
		}

		public Criteria andBirthdayBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("birthday between", value1, value2,
					"birthday");
			return (Criteria) this;
		}

		public Criteria andBirthdayNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("birthday not between", value1, value2,
					"birthday");
			return (Criteria) this;
		}

		public Criteria andPhotoIsNull() {
			addCriterion("photo is null");
			return (Criteria) this;
		}

		public Criteria andPhotoIsNotNull() {
			addCriterion("photo is not null");
			return (Criteria) this;
		}

		public Criteria andPhotoEqualTo(String value) {
			addCriterion("photo =", value, "photo");
			return (Criteria) this;
		}

		public Criteria andPhotoNotEqualTo(String value) {
			addCriterion("photo <>", value, "photo");
			return (Criteria) this;
		}

		public Criteria andPhotoGreaterThan(String value) {
			addCriterion("photo >", value, "photo");
			return (Criteria) this;
		}

		public Criteria andPhotoGreaterThanOrEqualTo(String value) {
			addCriterion("photo >=", value, "photo");
			return (Criteria) this;
		}

		public Criteria andPhotoLessThan(String value) {
			addCriterion("photo <", value, "photo");
			return (Criteria) this;
		}

		public Criteria andPhotoLessThanOrEqualTo(String value) {
			addCriterion("photo <=", value, "photo");
			return (Criteria) this;
		}

		public Criteria andPhotoLike(String value) {
			addCriterion("photo like", value, "photo");
			return (Criteria) this;
		}

		public Criteria andPhotoNotLike(String value) {
			addCriterion("photo not like", value, "photo");
			return (Criteria) this;
		}

		public Criteria andPhotoIn(List<String> values) {
			addCriterion("photo in", values, "photo");
			return (Criteria) this;
		}

		public Criteria andPhotoNotIn(List<String> values) {
			addCriterion("photo not in", values, "photo");
			return (Criteria) this;
		}

		public Criteria andPhotoBetween(String value1, String value2) {
			addCriterion("photo between", value1, value2, "photo");
			return (Criteria) this;
		}

		public Criteria andPhotoNotBetween(String value1, String value2) {
			addCriterion("photo not between", value1, value2, "photo");
			return (Criteria) this;
		}

		public Criteria andDescriptionIsNull() {
			addCriterion("description is null");
			return (Criteria) this;
		}

		public Criteria andDescriptionIsNotNull() {
			addCriterion("description is not null");
			return (Criteria) this;
		}

		public Criteria andDescriptionEqualTo(String value) {
			addCriterion("description =", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotEqualTo(String value) {
			addCriterion("description <>", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionGreaterThan(String value) {
			addCriterion("description >", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
			addCriterion("description >=", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionLessThan(String value) {
			addCriterion("description <", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionLessThanOrEqualTo(String value) {
			addCriterion("description <=", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionLike(String value) {
			addCriterion("description like", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotLike(String value) {
			addCriterion("description not like", value, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionIn(List<String> values) {
			addCriterion("description in", values, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotIn(List<String> values) {
			addCriterion("description not in", values, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionBetween(String value1, String value2) {
			addCriterion("description between", value1, value2, "description");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotBetween(String value1, String value2) {
			addCriterion("description not between", value1, value2,
					"description");
			return (Criteria) this;
		}

		public Criteria andIdcardIsNull() {
			addCriterion("idcard is null");
			return (Criteria) this;
		}

		public Criteria andIdcardIsNotNull() {
			addCriterion("idcard is not null");
			return (Criteria) this;
		}

		public Criteria andIdcardEqualTo(String value) {
			addCriterion("idcard =", value, "idcard");
			return (Criteria) this;
		}

		public Criteria andIdcardNotEqualTo(String value) {
			addCriterion("idcard <>", value, "idcard");
			return (Criteria) this;
		}

		public Criteria andIdcardGreaterThan(String value) {
			addCriterion("idcard >", value, "idcard");
			return (Criteria) this;
		}

		public Criteria andIdcardGreaterThanOrEqualTo(String value) {
			addCriterion("idcard >=", value, "idcard");
			return (Criteria) this;
		}

		public Criteria andIdcardLessThan(String value) {
			addCriterion("idcard <", value, "idcard");
			return (Criteria) this;
		}

		public Criteria andIdcardLessThanOrEqualTo(String value) {
			addCriterion("idcard <=", value, "idcard");
			return (Criteria) this;
		}

		public Criteria andIdcardLike(String value) {
			addCriterion("idcard like", value, "idcard");
			return (Criteria) this;
		}

		public Criteria andIdcardNotLike(String value) {
			addCriterion("idcard not like", value, "idcard");
			return (Criteria) this;
		}

		public Criteria andIdcardIn(List<String> values) {
			addCriterion("idcard in", values, "idcard");
			return (Criteria) this;
		}

		public Criteria andIdcardNotIn(List<String> values) {
			addCriterion("idcard not in", values, "idcard");
			return (Criteria) this;
		}

		public Criteria andIdcardBetween(String value1, String value2) {
			addCriterion("idcard between", value1, value2, "idcard");
			return (Criteria) this;
		}

		public Criteria andIdcardNotBetween(String value1, String value2) {
			addCriterion("idcard not between", value1, value2, "idcard");
			return (Criteria) this;
		}

		public Criteria andMobileIsNull() {
			addCriterion("mobile is null");
			return (Criteria) this;
		}

		public Criteria andMobileIsNotNull() {
			addCriterion("mobile is not null");
			return (Criteria) this;
		}

		public Criteria andMobileEqualTo(String value) {
			addCriterion("mobile =", value, "mobile");
			return (Criteria) this;
		}

		public Criteria andMobileNotEqualTo(String value) {
			addCriterion("mobile <>", value, "mobile");
			return (Criteria) this;
		}

		public Criteria andMobileGreaterThan(String value) {
			addCriterion("mobile >", value, "mobile");
			return (Criteria) this;
		}

		public Criteria andMobileGreaterThanOrEqualTo(String value) {
			addCriterion("mobile >=", value, "mobile");
			return (Criteria) this;
		}

		public Criteria andMobileLessThan(String value) {
			addCriterion("mobile <", value, "mobile");
			return (Criteria) this;
		}

		public Criteria andMobileLessThanOrEqualTo(String value) {
			addCriterion("mobile <=", value, "mobile");
			return (Criteria) this;
		}

		public Criteria andMobileLike(String value) {
			addCriterion("mobile like", value, "mobile");
			return (Criteria) this;
		}

		public Criteria andMobileNotLike(String value) {
			addCriterion("mobile not like", value, "mobile");
			return (Criteria) this;
		}

		public Criteria andMobileIn(List<String> values) {
			addCriterion("mobile in", values, "mobile");
			return (Criteria) this;
		}

		public Criteria andMobileNotIn(List<String> values) {
			addCriterion("mobile not in", values, "mobile");
			return (Criteria) this;
		}

		public Criteria andMobileBetween(String value1, String value2) {
			addCriterion("mobile between", value1, value2, "mobile");
			return (Criteria) this;
		}

		public Criteria andMobileNotBetween(String value1, String value2) {
			addCriterion("mobile not between", value1, value2, "mobile");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberIsNull() {
			addCriterion("telephonenumber is null");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberIsNotNull() {
			addCriterion("telephonenumber is not null");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberEqualTo(String value) {
			addCriterion("telephonenumber =", value, "telephonenumber");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberNotEqualTo(String value) {
			addCriterion("telephonenumber <>", value, "telephonenumber");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberGreaterThan(String value) {
			addCriterion("telephonenumber >", value, "telephonenumber");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberGreaterThanOrEqualTo(String value) {
			addCriterion("telephonenumber >=", value, "telephonenumber");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberLessThan(String value) {
			addCriterion("telephonenumber <", value, "telephonenumber");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberLessThanOrEqualTo(String value) {
			addCriterion("telephonenumber <=", value, "telephonenumber");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberLike(String value) {
			addCriterion("telephonenumber like", value, "telephonenumber");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberNotLike(String value) {
			addCriterion("telephonenumber not like", value, "telephonenumber");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberIn(List<String> values) {
			addCriterion("telephonenumber in", values, "telephonenumber");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberNotIn(List<String> values) {
			addCriterion("telephonenumber not in", values, "telephonenumber");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberBetween(String value1, String value2) {
			addCriterion("telephonenumber between", value1, value2,
					"telephonenumber");
			return (Criteria) this;
		}

		public Criteria andTelephonenumberNotBetween(String value1,
				String value2) {
			addCriterion("telephonenumber not between", value1, value2,
					"telephonenumber");
			return (Criteria) this;
		}

		public Criteria andHometelephoneIsNull() {
			addCriterion("hometelephone is null");
			return (Criteria) this;
		}

		public Criteria andHometelephoneIsNotNull() {
			addCriterion("hometelephone is not null");
			return (Criteria) this;
		}

		public Criteria andHometelephoneEqualTo(String value) {
			addCriterion("hometelephone =", value, "hometelephone");
			return (Criteria) this;
		}

		public Criteria andHometelephoneNotEqualTo(String value) {
			addCriterion("hometelephone <>", value, "hometelephone");
			return (Criteria) this;
		}

		public Criteria andHometelephoneGreaterThan(String value) {
			addCriterion("hometelephone >", value, "hometelephone");
			return (Criteria) this;
		}

		public Criteria andHometelephoneGreaterThanOrEqualTo(String value) {
			addCriterion("hometelephone >=", value, "hometelephone");
			return (Criteria) this;
		}

		public Criteria andHometelephoneLessThan(String value) {
			addCriterion("hometelephone <", value, "hometelephone");
			return (Criteria) this;
		}

		public Criteria andHometelephoneLessThanOrEqualTo(String value) {
			addCriterion("hometelephone <=", value, "hometelephone");
			return (Criteria) this;
		}

		public Criteria andHometelephoneLike(String value) {
			addCriterion("hometelephone like", value, "hometelephone");
			return (Criteria) this;
		}

		public Criteria andHometelephoneNotLike(String value) {
			addCriterion("hometelephone not like", value, "hometelephone");
			return (Criteria) this;
		}

		public Criteria andHometelephoneIn(List<String> values) {
			addCriterion("hometelephone in", values, "hometelephone");
			return (Criteria) this;
		}

		public Criteria andHometelephoneNotIn(List<String> values) {
			addCriterion("hometelephone not in", values, "hometelephone");
			return (Criteria) this;
		}

		public Criteria andHometelephoneBetween(String value1, String value2) {
			addCriterion("hometelephone between", value1, value2,
					"hometelephone");
			return (Criteria) this;
		}

		public Criteria andHometelephoneNotBetween(String value1, String value2) {
			addCriterion("hometelephone not between", value1, value2,
					"hometelephone");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneIsNull() {
			addCriterion("othertelephone is null");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneIsNotNull() {
			addCriterion("othertelephone is not null");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneEqualTo(String value) {
			addCriterion("othertelephone =", value, "othertelephone");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneNotEqualTo(String value) {
			addCriterion("othertelephone <>", value, "othertelephone");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneGreaterThan(String value) {
			addCriterion("othertelephone >", value, "othertelephone");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneGreaterThanOrEqualTo(String value) {
			addCriterion("othertelephone >=", value, "othertelephone");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneLessThan(String value) {
			addCriterion("othertelephone <", value, "othertelephone");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneLessThanOrEqualTo(String value) {
			addCriterion("othertelephone <=", value, "othertelephone");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneLike(String value) {
			addCriterion("othertelephone like", value, "othertelephone");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneNotLike(String value) {
			addCriterion("othertelephone not like", value, "othertelephone");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneIn(List<String> values) {
			addCriterion("othertelephone in", values, "othertelephone");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneNotIn(List<String> values) {
			addCriterion("othertelephone not in", values, "othertelephone");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneBetween(String value1, String value2) {
			addCriterion("othertelephone between", value1, value2,
					"othertelephone");
			return (Criteria) this;
		}

		public Criteria andOthertelephoneNotBetween(String value1, String value2) {
			addCriterion("othertelephone not between", value1, value2,
					"othertelephone");
			return (Criteria) this;
		}

		public Criteria andFaxIsNull() {
			addCriterion("fax is null");
			return (Criteria) this;
		}

		public Criteria andFaxIsNotNull() {
			addCriterion("fax is not null");
			return (Criteria) this;
		}

		public Criteria andFaxEqualTo(String value) {
			addCriterion("fax =", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxNotEqualTo(String value) {
			addCriterion("fax <>", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxGreaterThan(String value) {
			addCriterion("fax >", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxGreaterThanOrEqualTo(String value) {
			addCriterion("fax >=", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxLessThan(String value) {
			addCriterion("fax <", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxLessThanOrEqualTo(String value) {
			addCriterion("fax <=", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxLike(String value) {
			addCriterion("fax like", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxNotLike(String value) {
			addCriterion("fax not like", value, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxIn(List<String> values) {
			addCriterion("fax in", values, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxNotIn(List<String> values) {
			addCriterion("fax not in", values, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxBetween(String value1, String value2) {
			addCriterion("fax between", value1, value2, "fax");
			return (Criteria) this;
		}

		public Criteria andFaxNotBetween(String value1, String value2) {
			addCriterion("fax not between", value1, value2, "fax");
			return (Criteria) this;
		}

		public Criteria andShortcodeIsNull() {
			addCriterion("shortcode is null");
			return (Criteria) this;
		}

		public Criteria andShortcodeIsNotNull() {
			addCriterion("shortcode is not null");
			return (Criteria) this;
		}

		public Criteria andShortcodeEqualTo(String value) {
			addCriterion("shortcode =", value, "shortcode");
			return (Criteria) this;
		}

		public Criteria andShortcodeNotEqualTo(String value) {
			addCriterion("shortcode <>", value, "shortcode");
			return (Criteria) this;
		}

		public Criteria andShortcodeGreaterThan(String value) {
			addCriterion("shortcode >", value, "shortcode");
			return (Criteria) this;
		}

		public Criteria andShortcodeGreaterThanOrEqualTo(String value) {
			addCriterion("shortcode >=", value, "shortcode");
			return (Criteria) this;
		}

		public Criteria andShortcodeLessThan(String value) {
			addCriterion("shortcode <", value, "shortcode");
			return (Criteria) this;
		}

		public Criteria andShortcodeLessThanOrEqualTo(String value) {
			addCriterion("shortcode <=", value, "shortcode");
			return (Criteria) this;
		}

		public Criteria andShortcodeLike(String value) {
			addCriterion("shortcode like", value, "shortcode");
			return (Criteria) this;
		}

		public Criteria andShortcodeNotLike(String value) {
			addCriterion("shortcode not like", value, "shortcode");
			return (Criteria) this;
		}

		public Criteria andShortcodeIn(List<String> values) {
			addCriterion("shortcode in", values, "shortcode");
			return (Criteria) this;
		}

		public Criteria andShortcodeNotIn(List<String> values) {
			addCriterion("shortcode not in", values, "shortcode");
			return (Criteria) this;
		}

		public Criteria andShortcodeBetween(String value1, String value2) {
			addCriterion("shortcode between", value1, value2, "shortcode");
			return (Criteria) this;
		}

		public Criteria andShortcodeNotBetween(String value1, String value2) {
			addCriterion("shortcode not between", value1, value2, "shortcode");
			return (Criteria) this;
		}

		public Criteria andIpphoneIsNull() {
			addCriterion("ipphone is null");
			return (Criteria) this;
		}

		public Criteria andIpphoneIsNotNull() {
			addCriterion("ipphone is not null");
			return (Criteria) this;
		}

		public Criteria andIpphoneEqualTo(String value) {
			addCriterion("ipphone =", value, "ipphone");
			return (Criteria) this;
		}

		public Criteria andIpphoneNotEqualTo(String value) {
			addCriterion("ipphone <>", value, "ipphone");
			return (Criteria) this;
		}

		public Criteria andIpphoneGreaterThan(String value) {
			addCriterion("ipphone >", value, "ipphone");
			return (Criteria) this;
		}

		public Criteria andIpphoneGreaterThanOrEqualTo(String value) {
			addCriterion("ipphone >=", value, "ipphone");
			return (Criteria) this;
		}

		public Criteria andIpphoneLessThan(String value) {
			addCriterion("ipphone <", value, "ipphone");
			return (Criteria) this;
		}

		public Criteria andIpphoneLessThanOrEqualTo(String value) {
			addCriterion("ipphone <=", value, "ipphone");
			return (Criteria) this;
		}

		public Criteria andIpphoneLike(String value) {
			addCriterion("ipphone like", value, "ipphone");
			return (Criteria) this;
		}

		public Criteria andIpphoneNotLike(String value) {
			addCriterion("ipphone not like", value, "ipphone");
			return (Criteria) this;
		}

		public Criteria andIpphoneIn(List<String> values) {
			addCriterion("ipphone in", values, "ipphone");
			return (Criteria) this;
		}

		public Criteria andIpphoneNotIn(List<String> values) {
			addCriterion("ipphone not in", values, "ipphone");
			return (Criteria) this;
		}

		public Criteria andIpphoneBetween(String value1, String value2) {
			addCriterion("ipphone between", value1, value2, "ipphone");
			return (Criteria) this;
		}

		public Criteria andIpphoneNotBetween(String value1, String value2) {
			addCriterion("ipphone not between", value1, value2, "ipphone");
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

		public Criteria andOthermailIsNull() {
			addCriterion("othermail is null");
			return (Criteria) this;
		}

		public Criteria andOthermailIsNotNull() {
			addCriterion("othermail is not null");
			return (Criteria) this;
		}

		public Criteria andOthermailEqualTo(String value) {
			addCriterion("othermail =", value, "othermail");
			return (Criteria) this;
		}

		public Criteria andOthermailNotEqualTo(String value) {
			addCriterion("othermail <>", value, "othermail");
			return (Criteria) this;
		}

		public Criteria andOthermailGreaterThan(String value) {
			addCriterion("othermail >", value, "othermail");
			return (Criteria) this;
		}

		public Criteria andOthermailGreaterThanOrEqualTo(String value) {
			addCriterion("othermail >=", value, "othermail");
			return (Criteria) this;
		}

		public Criteria andOthermailLessThan(String value) {
			addCriterion("othermail <", value, "othermail");
			return (Criteria) this;
		}

		public Criteria andOthermailLessThanOrEqualTo(String value) {
			addCriterion("othermail <=", value, "othermail");
			return (Criteria) this;
		}

		public Criteria andOthermailLike(String value) {
			addCriterion("othermail like", value, "othermail");
			return (Criteria) this;
		}

		public Criteria andOthermailNotLike(String value) {
			addCriterion("othermail not like", value, "othermail");
			return (Criteria) this;
		}

		public Criteria andOthermailIn(List<String> values) {
			addCriterion("othermail in", values, "othermail");
			return (Criteria) this;
		}

		public Criteria andOthermailNotIn(List<String> values) {
			addCriterion("othermail not in", values, "othermail");
			return (Criteria) this;
		}

		public Criteria andOthermailBetween(String value1, String value2) {
			addCriterion("othermail between", value1, value2, "othermail");
			return (Criteria) this;
		}

		public Criteria andOthermailNotBetween(String value1, String value2) {
			addCriterion("othermail not between", value1, value2, "othermail");
			return (Criteria) this;
		}

		public Criteria andQqIsNull() {
			addCriterion("qq is null");
			return (Criteria) this;
		}

		public Criteria andQqIsNotNull() {
			addCriterion("qq is not null");
			return (Criteria) this;
		}

		public Criteria andQqEqualTo(String value) {
			addCriterion("qq =", value, "qq");
			return (Criteria) this;
		}

		public Criteria andQqNotEqualTo(String value) {
			addCriterion("qq <>", value, "qq");
			return (Criteria) this;
		}

		public Criteria andQqGreaterThan(String value) {
			addCriterion("qq >", value, "qq");
			return (Criteria) this;
		}

		public Criteria andQqGreaterThanOrEqualTo(String value) {
			addCriterion("qq >=", value, "qq");
			return (Criteria) this;
		}

		public Criteria andQqLessThan(String value) {
			addCriterion("qq <", value, "qq");
			return (Criteria) this;
		}

		public Criteria andQqLessThanOrEqualTo(String value) {
			addCriterion("qq <=", value, "qq");
			return (Criteria) this;
		}

		public Criteria andQqLike(String value) {
			addCriterion("qq like", value, "qq");
			return (Criteria) this;
		}

		public Criteria andQqNotLike(String value) {
			addCriterion("qq not like", value, "qq");
			return (Criteria) this;
		}

		public Criteria andQqIn(List<String> values) {
			addCriterion("qq in", values, "qq");
			return (Criteria) this;
		}

		public Criteria andQqNotIn(List<String> values) {
			addCriterion("qq not in", values, "qq");
			return (Criteria) this;
		}

		public Criteria andQqBetween(String value1, String value2) {
			addCriterion("qq between", value1, value2, "qq");
			return (Criteria) this;
		}

		public Criteria andQqNotBetween(String value1, String value2) {
			addCriterion("qq not between", value1, value2, "qq");
			return (Criteria) this;
		}

		public Criteria andMsnIsNull() {
			addCriterion("msn is null");
			return (Criteria) this;
		}

		public Criteria andMsnIsNotNull() {
			addCriterion("msn is not null");
			return (Criteria) this;
		}

		public Criteria andMsnEqualTo(String value) {
			addCriterion("msn =", value, "msn");
			return (Criteria) this;
		}

		public Criteria andMsnNotEqualTo(String value) {
			addCriterion("msn <>", value, "msn");
			return (Criteria) this;
		}

		public Criteria andMsnGreaterThan(String value) {
			addCriterion("msn >", value, "msn");
			return (Criteria) this;
		}

		public Criteria andMsnGreaterThanOrEqualTo(String value) {
			addCriterion("msn >=", value, "msn");
			return (Criteria) this;
		}

		public Criteria andMsnLessThan(String value) {
			addCriterion("msn <", value, "msn");
			return (Criteria) this;
		}

		public Criteria andMsnLessThanOrEqualTo(String value) {
			addCriterion("msn <=", value, "msn");
			return (Criteria) this;
		}

		public Criteria andMsnLike(String value) {
			addCriterion("msn like", value, "msn");
			return (Criteria) this;
		}

		public Criteria andMsnNotLike(String value) {
			addCriterion("msn not like", value, "msn");
			return (Criteria) this;
		}

		public Criteria andMsnIn(List<String> values) {
			addCriterion("msn in", values, "msn");
			return (Criteria) this;
		}

		public Criteria andMsnNotIn(List<String> values) {
			addCriterion("msn not in", values, "msn");
			return (Criteria) this;
		}

		public Criteria andMsnBetween(String value1, String value2) {
			addCriterion("msn between", value1, value2, "msn");
			return (Criteria) this;
		}

		public Criteria andMsnNotBetween(String value1, String value2) {
			addCriterion("msn not between", value1, value2, "msn");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageIsNull() {
			addCriterion("wwwhomepage is null");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageIsNotNull() {
			addCriterion("wwwhomepage is not null");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageEqualTo(String value) {
			addCriterion("wwwhomepage =", value, "wwwhomepage");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageNotEqualTo(String value) {
			addCriterion("wwwhomepage <>", value, "wwwhomepage");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageGreaterThan(String value) {
			addCriterion("wwwhomepage >", value, "wwwhomepage");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageGreaterThanOrEqualTo(String value) {
			addCriterion("wwwhomepage >=", value, "wwwhomepage");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageLessThan(String value) {
			addCriterion("wwwhomepage <", value, "wwwhomepage");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageLessThanOrEqualTo(String value) {
			addCriterion("wwwhomepage <=", value, "wwwhomepage");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageLike(String value) {
			addCriterion("wwwhomepage like", value, "wwwhomepage");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageNotLike(String value) {
			addCriterion("wwwhomepage not like", value, "wwwhomepage");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageIn(List<String> values) {
			addCriterion("wwwhomepage in", values, "wwwhomepage");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageNotIn(List<String> values) {
			addCriterion("wwwhomepage not in", values, "wwwhomepage");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageBetween(String value1, String value2) {
			addCriterion("wwwhomepage between", value1, value2, "wwwhomepage");
			return (Criteria) this;
		}

		public Criteria andWwwhomepageNotBetween(String value1, String value2) {
			addCriterion("wwwhomepage not between", value1, value2,
					"wwwhomepage");
			return (Criteria) this;
		}

		public Criteria andCoIsNull() {
			addCriterion("co is null");
			return (Criteria) this;
		}

		public Criteria andCoIsNotNull() {
			addCriterion("co is not null");
			return (Criteria) this;
		}

		public Criteria andCoEqualTo(String value) {
			addCriterion("co =", value, "co");
			return (Criteria) this;
		}

		public Criteria andCoNotEqualTo(String value) {
			addCriterion("co <>", value, "co");
			return (Criteria) this;
		}

		public Criteria andCoGreaterThan(String value) {
			addCriterion("co >", value, "co");
			return (Criteria) this;
		}

		public Criteria andCoGreaterThanOrEqualTo(String value) {
			addCriterion("co >=", value, "co");
			return (Criteria) this;
		}

		public Criteria andCoLessThan(String value) {
			addCriterion("co <", value, "co");
			return (Criteria) this;
		}

		public Criteria andCoLessThanOrEqualTo(String value) {
			addCriterion("co <=", value, "co");
			return (Criteria) this;
		}

		public Criteria andCoLike(String value) {
			addCriterion("co like", value, "co");
			return (Criteria) this;
		}

		public Criteria andCoNotLike(String value) {
			addCriterion("co not like", value, "co");
			return (Criteria) this;
		}

		public Criteria andCoIn(List<String> values) {
			addCriterion("co in", values, "co");
			return (Criteria) this;
		}

		public Criteria andCoNotIn(List<String> values) {
			addCriterion("co not in", values, "co");
			return (Criteria) this;
		}

		public Criteria andCoBetween(String value1, String value2) {
			addCriterion("co between", value1, value2, "co");
			return (Criteria) this;
		}

		public Criteria andCoNotBetween(String value1, String value2) {
			addCriterion("co not between", value1, value2, "co");
			return (Criteria) this;
		}

		public Criteria andStIsNull() {
			addCriterion("st is null");
			return (Criteria) this;
		}

		public Criteria andStIsNotNull() {
			addCriterion("st is not null");
			return (Criteria) this;
		}

		public Criteria andStEqualTo(String value) {
			addCriterion("st =", value, "st");
			return (Criteria) this;
		}

		public Criteria andStNotEqualTo(String value) {
			addCriterion("st <>", value, "st");
			return (Criteria) this;
		}

		public Criteria andStGreaterThan(String value) {
			addCriterion("st >", value, "st");
			return (Criteria) this;
		}

		public Criteria andStGreaterThanOrEqualTo(String value) {
			addCriterion("st >=", value, "st");
			return (Criteria) this;
		}

		public Criteria andStLessThan(String value) {
			addCriterion("st <", value, "st");
			return (Criteria) this;
		}

		public Criteria andStLessThanOrEqualTo(String value) {
			addCriterion("st <=", value, "st");
			return (Criteria) this;
		}

		public Criteria andStLike(String value) {
			addCriterion("st like", value, "st");
			return (Criteria) this;
		}

		public Criteria andStNotLike(String value) {
			addCriterion("st not like", value, "st");
			return (Criteria) this;
		}

		public Criteria andStIn(List<String> values) {
			addCriterion("st in", values, "st");
			return (Criteria) this;
		}

		public Criteria andStNotIn(List<String> values) {
			addCriterion("st not in", values, "st");
			return (Criteria) this;
		}

		public Criteria andStBetween(String value1, String value2) {
			addCriterion("st between", value1, value2, "st");
			return (Criteria) this;
		}

		public Criteria andStNotBetween(String value1, String value2) {
			addCriterion("st not between", value1, value2, "st");
			return (Criteria) this;
		}

		public Criteria andLIsNull() {
			addCriterion("l is null");
			return (Criteria) this;
		}

		public Criteria andLIsNotNull() {
			addCriterion("l is not null");
			return (Criteria) this;
		}

		public Criteria andLEqualTo(String value) {
			addCriterion("l =", value, "l");
			return (Criteria) this;
		}

		public Criteria andLNotEqualTo(String value) {
			addCriterion("l <>", value, "l");
			return (Criteria) this;
		}

		public Criteria andLGreaterThan(String value) {
			addCriterion("l >", value, "l");
			return (Criteria) this;
		}

		public Criteria andLGreaterThanOrEqualTo(String value) {
			addCriterion("l >=", value, "l");
			return (Criteria) this;
		}

		public Criteria andLLessThan(String value) {
			addCriterion("l <", value, "l");
			return (Criteria) this;
		}

		public Criteria andLLessThanOrEqualTo(String value) {
			addCriterion("l <=", value, "l");
			return (Criteria) this;
		}

		public Criteria andLLike(String value) {
			addCriterion("l like", value, "l");
			return (Criteria) this;
		}

		public Criteria andLNotLike(String value) {
			addCriterion("l not like", value, "l");
			return (Criteria) this;
		}

		public Criteria andLIn(List<String> values) {
			addCriterion("l in", values, "l");
			return (Criteria) this;
		}

		public Criteria andLNotIn(List<String> values) {
			addCriterion("l not in", values, "l");
			return (Criteria) this;
		}

		public Criteria andLBetween(String value1, String value2) {
			addCriterion("l between", value1, value2, "l");
			return (Criteria) this;
		}

		public Criteria andLNotBetween(String value1, String value2) {
			addCriterion("l not between", value1, value2, "l");
			return (Criteria) this;
		}

		public Criteria andStreetaddressIsNull() {
			addCriterion("streetaddress is null");
			return (Criteria) this;
		}

		public Criteria andStreetaddressIsNotNull() {
			addCriterion("streetaddress is not null");
			return (Criteria) this;
		}

		public Criteria andStreetaddressEqualTo(String value) {
			addCriterion("streetaddress =", value, "streetaddress");
			return (Criteria) this;
		}

		public Criteria andStreetaddressNotEqualTo(String value) {
			addCriterion("streetaddress <>", value, "streetaddress");
			return (Criteria) this;
		}

		public Criteria andStreetaddressGreaterThan(String value) {
			addCriterion("streetaddress >", value, "streetaddress");
			return (Criteria) this;
		}

		public Criteria andStreetaddressGreaterThanOrEqualTo(String value) {
			addCriterion("streetaddress >=", value, "streetaddress");
			return (Criteria) this;
		}

		public Criteria andStreetaddressLessThan(String value) {
			addCriterion("streetaddress <", value, "streetaddress");
			return (Criteria) this;
		}

		public Criteria andStreetaddressLessThanOrEqualTo(String value) {
			addCriterion("streetaddress <=", value, "streetaddress");
			return (Criteria) this;
		}

		public Criteria andStreetaddressLike(String value) {
			addCriterion("streetaddress like", value, "streetaddress");
			return (Criteria) this;
		}

		public Criteria andStreetaddressNotLike(String value) {
			addCriterion("streetaddress not like", value, "streetaddress");
			return (Criteria) this;
		}

		public Criteria andStreetaddressIn(List<String> values) {
			addCriterion("streetaddress in", values, "streetaddress");
			return (Criteria) this;
		}

		public Criteria andStreetaddressNotIn(List<String> values) {
			addCriterion("streetaddress not in", values, "streetaddress");
			return (Criteria) this;
		}

		public Criteria andStreetaddressBetween(String value1, String value2) {
			addCriterion("streetaddress between", value1, value2,
					"streetaddress");
			return (Criteria) this;
		}

		public Criteria andStreetaddressNotBetween(String value1, String value2) {
			addCriterion("streetaddress not between", value1, value2,
					"streetaddress");
			return (Criteria) this;
		}

		public Criteria andPostaladdressIsNull() {
			addCriterion("postaladdress is null");
			return (Criteria) this;
		}

		public Criteria andPostaladdressIsNotNull() {
			addCriterion("postaladdress is not null");
			return (Criteria) this;
		}

		public Criteria andPostaladdressEqualTo(String value) {
			addCriterion("postaladdress =", value, "postaladdress");
			return (Criteria) this;
		}

		public Criteria andPostaladdressNotEqualTo(String value) {
			addCriterion("postaladdress <>", value, "postaladdress");
			return (Criteria) this;
		}

		public Criteria andPostaladdressGreaterThan(String value) {
			addCriterion("postaladdress >", value, "postaladdress");
			return (Criteria) this;
		}

		public Criteria andPostaladdressGreaterThanOrEqualTo(String value) {
			addCriterion("postaladdress >=", value, "postaladdress");
			return (Criteria) this;
		}

		public Criteria andPostaladdressLessThan(String value) {
			addCriterion("postaladdress <", value, "postaladdress");
			return (Criteria) this;
		}

		public Criteria andPostaladdressLessThanOrEqualTo(String value) {
			addCriterion("postaladdress <=", value, "postaladdress");
			return (Criteria) this;
		}

		public Criteria andPostaladdressLike(String value) {
			addCriterion("postaladdress like", value, "postaladdress");
			return (Criteria) this;
		}

		public Criteria andPostaladdressNotLike(String value) {
			addCriterion("postaladdress not like", value, "postaladdress");
			return (Criteria) this;
		}

		public Criteria andPostaladdressIn(List<String> values) {
			addCriterion("postaladdress in", values, "postaladdress");
			return (Criteria) this;
		}

		public Criteria andPostaladdressNotIn(List<String> values) {
			addCriterion("postaladdress not in", values, "postaladdress");
			return (Criteria) this;
		}

		public Criteria andPostaladdressBetween(String value1, String value2) {
			addCriterion("postaladdress between", value1, value2,
					"postaladdress");
			return (Criteria) this;
		}

		public Criteria andPostaladdressNotBetween(String value1, String value2) {
			addCriterion("postaladdress not between", value1, value2,
					"postaladdress");
			return (Criteria) this;
		}

		public Criteria andPostalcodeIsNull() {
			addCriterion("postalcode is null");
			return (Criteria) this;
		}

		public Criteria andPostalcodeIsNotNull() {
			addCriterion("postalcode is not null");
			return (Criteria) this;
		}

		public Criteria andPostalcodeEqualTo(String value) {
			addCriterion("postalcode =", value, "postalcode");
			return (Criteria) this;
		}

		public Criteria andPostalcodeNotEqualTo(String value) {
			addCriterion("postalcode <>", value, "postalcode");
			return (Criteria) this;
		}

		public Criteria andPostalcodeGreaterThan(String value) {
			addCriterion("postalcode >", value, "postalcode");
			return (Criteria) this;
		}

		public Criteria andPostalcodeGreaterThanOrEqualTo(String value) {
			addCriterion("postalcode >=", value, "postalcode");
			return (Criteria) this;
		}

		public Criteria andPostalcodeLessThan(String value) {
			addCriterion("postalcode <", value, "postalcode");
			return (Criteria) this;
		}

		public Criteria andPostalcodeLessThanOrEqualTo(String value) {
			addCriterion("postalcode <=", value, "postalcode");
			return (Criteria) this;
		}

		public Criteria andPostalcodeLike(String value) {
			addCriterion("postalcode like", value, "postalcode");
			return (Criteria) this;
		}

		public Criteria andPostalcodeNotLike(String value) {
			addCriterion("postalcode not like", value, "postalcode");
			return (Criteria) this;
		}

		public Criteria andPostalcodeIn(List<String> values) {
			addCriterion("postalcode in", values, "postalcode");
			return (Criteria) this;
		}

		public Criteria andPostalcodeNotIn(List<String> values) {
			addCriterion("postalcode not in", values, "postalcode");
			return (Criteria) this;
		}

		public Criteria andPostalcodeBetween(String value1, String value2) {
			addCriterion("postalcode between", value1, value2, "postalcode");
			return (Criteria) this;
		}

		public Criteria andPostalcodeNotBetween(String value1, String value2) {
			addCriterion("postalcode not between", value1, value2, "postalcode");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdIsNull() {
			addCriterion("superior_id is null");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdIsNotNull() {
			addCriterion("superior_id is not null");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdEqualTo(String value) {
			addCriterion("superior_id =", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdNotEqualTo(String value) {
			addCriterion("superior_id <>", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdGreaterThan(String value) {
			addCriterion("superior_id >", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdGreaterThanOrEqualTo(String value) {
			addCriterion("superior_id >=", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdLessThan(String value) {
			addCriterion("superior_id <", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdLessThanOrEqualTo(String value) {
			addCriterion("superior_id <=", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdLike(String value) {
			addCriterion("superior_id like", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdNotLike(String value) {
			addCriterion("superior_id not like", value, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdIn(List<String> values) {
			addCriterion("superior_id in", values, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdNotIn(List<String> values) {
			addCriterion("superior_id not in", values, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdBetween(String value1, String value2) {
			addCriterion("superior_id between", value1, value2, "superiorId");
			return (Criteria) this;
		}

		public Criteria andSuperiorIdNotBetween(String value1, String value2) {
			addCriterion("superior_id not between", value1, value2,
					"superiorId");
			return (Criteria) this;
		}

		public Criteria andInfoIsNull() {
			addCriterion("info is null");
			return (Criteria) this;
		}

		public Criteria andInfoIsNotNull() {
			addCriterion("info is not null");
			return (Criteria) this;
		}

		public Criteria andInfoEqualTo(String value) {
			addCriterion("info =", value, "info");
			return (Criteria) this;
		}

		public Criteria andInfoNotEqualTo(String value) {
			addCriterion("info <>", value, "info");
			return (Criteria) this;
		}

		public Criteria andInfoGreaterThan(String value) {
			addCriterion("info >", value, "info");
			return (Criteria) this;
		}

		public Criteria andInfoGreaterThanOrEqualTo(String value) {
			addCriterion("info >=", value, "info");
			return (Criteria) this;
		}

		public Criteria andInfoLessThan(String value) {
			addCriterion("info <", value, "info");
			return (Criteria) this;
		}

		public Criteria andInfoLessThanOrEqualTo(String value) {
			addCriterion("info <=", value, "info");
			return (Criteria) this;
		}

		public Criteria andInfoLike(String value) {
			addCriterion("info like", value, "info");
			return (Criteria) this;
		}

		public Criteria andInfoNotLike(String value) {
			addCriterion("info not like", value, "info");
			return (Criteria) this;
		}

		public Criteria andInfoIn(List<String> values) {
			addCriterion("info in", values, "info");
			return (Criteria) this;
		}

		public Criteria andInfoNotIn(List<String> values) {
			addCriterion("info not in", values, "info");
			return (Criteria) this;
		}

		public Criteria andInfoBetween(String value1, String value2) {
			addCriterion("info between", value1, value2, "info");
			return (Criteria) this;
		}

		public Criteria andInfoNotBetween(String value1, String value2) {
			addCriterion("info not between", value1, value2, "info");
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

		public Criteria andCustomField1IsNull() {
			addCriterion("custom_field1 is null");
			return (Criteria) this;
		}

		public Criteria andCustomField1IsNotNull() {
			addCriterion("custom_field1 is not null");
			return (Criteria) this;
		}

		public Criteria andCustomField1EqualTo(String value) {
			addCriterion("custom_field1 =", value, "customField1");
			return (Criteria) this;
		}

		public Criteria andCustomField1NotEqualTo(String value) {
			addCriterion("custom_field1 <>", value, "customField1");
			return (Criteria) this;
		}

		public Criteria andCustomField1GreaterThan(String value) {
			addCriterion("custom_field1 >", value, "customField1");
			return (Criteria) this;
		}

		public Criteria andCustomField1GreaterThanOrEqualTo(String value) {
			addCriterion("custom_field1 >=", value, "customField1");
			return (Criteria) this;
		}

		public Criteria andCustomField1LessThan(String value) {
			addCriterion("custom_field1 <", value, "customField1");
			return (Criteria) this;
		}

		public Criteria andCustomField1LessThanOrEqualTo(String value) {
			addCriterion("custom_field1 <=", value, "customField1");
			return (Criteria) this;
		}

		public Criteria andCustomField1Like(String value) {
			addCriterion("custom_field1 like", value, "customField1");
			return (Criteria) this;
		}

		public Criteria andCustomField1NotLike(String value) {
			addCriterion("custom_field1 not like", value, "customField1");
			return (Criteria) this;
		}

		public Criteria andCustomField1In(List<String> values) {
			addCriterion("custom_field1 in", values, "customField1");
			return (Criteria) this;
		}

		public Criteria andCustomField1NotIn(List<String> values) {
			addCriterion("custom_field1 not in", values, "customField1");
			return (Criteria) this;
		}

		public Criteria andCustomField1Between(String value1, String value2) {
			addCriterion("custom_field1 between", value1, value2,
					"customField1");
			return (Criteria) this;
		}

		public Criteria andCustomField1NotBetween(String value1, String value2) {
			addCriterion("custom_field1 not between", value1, value2,
					"customField1");
			return (Criteria) this;
		}

		public Criteria andCustomField2IsNull() {
			addCriterion("custom_field2 is null");
			return (Criteria) this;
		}

		public Criteria andCustomField2IsNotNull() {
			addCriterion("custom_field2 is not null");
			return (Criteria) this;
		}

		public Criteria andCustomField2EqualTo(String value) {
			addCriterion("custom_field2 =", value, "customField2");
			return (Criteria) this;
		}

		public Criteria andCustomField2NotEqualTo(String value) {
			addCriterion("custom_field2 <>", value, "customField2");
			return (Criteria) this;
		}

		public Criteria andCustomField2GreaterThan(String value) {
			addCriterion("custom_field2 >", value, "customField2");
			return (Criteria) this;
		}

		public Criteria andCustomField2GreaterThanOrEqualTo(String value) {
			addCriterion("custom_field2 >=", value, "customField2");
			return (Criteria) this;
		}

		public Criteria andCustomField2LessThan(String value) {
			addCriterion("custom_field2 <", value, "customField2");
			return (Criteria) this;
		}

		public Criteria andCustomField2LessThanOrEqualTo(String value) {
			addCriterion("custom_field2 <=", value, "customField2");
			return (Criteria) this;
		}

		public Criteria andCustomField2Like(String value) {
			addCriterion("custom_field2 like", value, "customField2");
			return (Criteria) this;
		}

		public Criteria andCustomField2NotLike(String value) {
			addCriterion("custom_field2 not like", value, "customField2");
			return (Criteria) this;
		}

		public Criteria andCustomField2In(List<String> values) {
			addCriterion("custom_field2 in", values, "customField2");
			return (Criteria) this;
		}

		public Criteria andCustomField2NotIn(List<String> values) {
			addCriterion("custom_field2 not in", values, "customField2");
			return (Criteria) this;
		}

		public Criteria andCustomField2Between(String value1, String value2) {
			addCriterion("custom_field2 between", value1, value2,
					"customField2");
			return (Criteria) this;
		}

		public Criteria andCustomField2NotBetween(String value1, String value2) {
			addCriterion("custom_field2 not between", value1, value2,
					"customField2");
			return (Criteria) this;
		}

		public Criteria andCustomField3IsNull() {
			addCriterion("custom_field3 is null");
			return (Criteria) this;
		}

		public Criteria andCustomField3IsNotNull() {
			addCriterion("custom_field3 is not null");
			return (Criteria) this;
		}

		public Criteria andCustomField3EqualTo(String value) {
			addCriterion("custom_field3 =", value, "customField3");
			return (Criteria) this;
		}

		public Criteria andCustomField3NotEqualTo(String value) {
			addCriterion("custom_field3 <>", value, "customField3");
			return (Criteria) this;
		}

		public Criteria andCustomField3GreaterThan(String value) {
			addCriterion("custom_field3 >", value, "customField3");
			return (Criteria) this;
		}

		public Criteria andCustomField3GreaterThanOrEqualTo(String value) {
			addCriterion("custom_field3 >=", value, "customField3");
			return (Criteria) this;
		}

		public Criteria andCustomField3LessThan(String value) {
			addCriterion("custom_field3 <", value, "customField3");
			return (Criteria) this;
		}

		public Criteria andCustomField3LessThanOrEqualTo(String value) {
			addCriterion("custom_field3 <=", value, "customField3");
			return (Criteria) this;
		}

		public Criteria andCustomField3Like(String value) {
			addCriterion("custom_field3 like", value, "customField3");
			return (Criteria) this;
		}

		public Criteria andCustomField3NotLike(String value) {
			addCriterion("custom_field3 not like", value, "customField3");
			return (Criteria) this;
		}

		public Criteria andCustomField3In(List<String> values) {
			addCriterion("custom_field3 in", values, "customField3");
			return (Criteria) this;
		}

		public Criteria andCustomField3NotIn(List<String> values) {
			addCriterion("custom_field3 not in", values, "customField3");
			return (Criteria) this;
		}

		public Criteria andCustomField3Between(String value1, String value2) {
			addCriterion("custom_field3 between", value1, value2,
					"customField3");
			return (Criteria) this;
		}

		public Criteria andCustomField3NotBetween(String value1, String value2) {
			addCriterion("custom_field3 not between", value1, value2,
					"customField3");
			return (Criteria) this;
		}

		public Criteria andCustomField4IsNull() {
			addCriterion("custom_field4 is null");
			return (Criteria) this;
		}

		public Criteria andCustomField4IsNotNull() {
			addCriterion("custom_field4 is not null");
			return (Criteria) this;
		}

		public Criteria andCustomField4EqualTo(String value) {
			addCriterion("custom_field4 =", value, "customField4");
			return (Criteria) this;
		}

		public Criteria andCustomField4NotEqualTo(String value) {
			addCriterion("custom_field4 <>", value, "customField4");
			return (Criteria) this;
		}

		public Criteria andCustomField4GreaterThan(String value) {
			addCriterion("custom_field4 >", value, "customField4");
			return (Criteria) this;
		}

		public Criteria andCustomField4GreaterThanOrEqualTo(String value) {
			addCriterion("custom_field4 >=", value, "customField4");
			return (Criteria) this;
		}

		public Criteria andCustomField4LessThan(String value) {
			addCriterion("custom_field4 <", value, "customField4");
			return (Criteria) this;
		}

		public Criteria andCustomField4LessThanOrEqualTo(String value) {
			addCriterion("custom_field4 <=", value, "customField4");
			return (Criteria) this;
		}

		public Criteria andCustomField4Like(String value) {
			addCriterion("custom_field4 like", value, "customField4");
			return (Criteria) this;
		}

		public Criteria andCustomField4NotLike(String value) {
			addCriterion("custom_field4 not like", value, "customField4");
			return (Criteria) this;
		}

		public Criteria andCustomField4In(List<String> values) {
			addCriterion("custom_field4 in", values, "customField4");
			return (Criteria) this;
		}

		public Criteria andCustomField4NotIn(List<String> values) {
			addCriterion("custom_field4 not in", values, "customField4");
			return (Criteria) this;
		}

		public Criteria andCustomField4Between(String value1, String value2) {
			addCriterion("custom_field4 between", value1, value2,
					"customField4");
			return (Criteria) this;
		}

		public Criteria andCustomField4NotBetween(String value1, String value2) {
			addCriterion("custom_field4 not between", value1, value2,
					"customField4");
			return (Criteria) this;
		}

		public Criteria andCustomField5IsNull() {
			addCriterion("custom_field5 is null");
			return (Criteria) this;
		}

		public Criteria andCustomField5IsNotNull() {
			addCriterion("custom_field5 is not null");
			return (Criteria) this;
		}

		public Criteria andCustomField5EqualTo(String value) {
			addCriterion("custom_field5 =", value, "customField5");
			return (Criteria) this;
		}

		public Criteria andCustomField5NotEqualTo(String value) {
			addCriterion("custom_field5 <>", value, "customField5");
			return (Criteria) this;
		}

		public Criteria andCustomField5GreaterThan(String value) {
			addCriterion("custom_field5 >", value, "customField5");
			return (Criteria) this;
		}

		public Criteria andCustomField5GreaterThanOrEqualTo(String value) {
			addCriterion("custom_field5 >=", value, "customField5");
			return (Criteria) this;
		}

		public Criteria andCustomField5LessThan(String value) {
			addCriterion("custom_field5 <", value, "customField5");
			return (Criteria) this;
		}

		public Criteria andCustomField5LessThanOrEqualTo(String value) {
			addCriterion("custom_field5 <=", value, "customField5");
			return (Criteria) this;
		}

		public Criteria andCustomField5Like(String value) {
			addCriterion("custom_field5 like", value, "customField5");
			return (Criteria) this;
		}

		public Criteria andCustomField5NotLike(String value) {
			addCriterion("custom_field5 not like", value, "customField5");
			return (Criteria) this;
		}

		public Criteria andCustomField5In(List<String> values) {
			addCriterion("custom_field5 in", values, "customField5");
			return (Criteria) this;
		}

		public Criteria andCustomField5NotIn(List<String> values) {
			addCriterion("custom_field5 not in", values, "customField5");
			return (Criteria) this;
		}

		public Criteria andCustomField5Between(String value1, String value2) {
			addCriterion("custom_field5 between", value1, value2,
					"customField5");
			return (Criteria) this;
		}

		public Criteria andCustomField5NotBetween(String value1, String value2) {
			addCriterion("custom_field5 not between", value1, value2,
					"customField5");
			return (Criteria) this;
		}

		public Criteria andCustomField6IsNull() {
			addCriterion("custom_field6 is null");
			return (Criteria) this;
		}

		public Criteria andCustomField6IsNotNull() {
			addCriterion("custom_field6 is not null");
			return (Criteria) this;
		}

		public Criteria andCustomField6EqualTo(String value) {
			addCriterion("custom_field6 =", value, "customField6");
			return (Criteria) this;
		}

		public Criteria andCustomField6NotEqualTo(String value) {
			addCriterion("custom_field6 <>", value, "customField6");
			return (Criteria) this;
		}

		public Criteria andCustomField6GreaterThan(String value) {
			addCriterion("custom_field6 >", value, "customField6");
			return (Criteria) this;
		}

		public Criteria andCustomField6GreaterThanOrEqualTo(String value) {
			addCriterion("custom_field6 >=", value, "customField6");
			return (Criteria) this;
		}

		public Criteria andCustomField6LessThan(String value) {
			addCriterion("custom_field6 <", value, "customField6");
			return (Criteria) this;
		}

		public Criteria andCustomField6LessThanOrEqualTo(String value) {
			addCriterion("custom_field6 <=", value, "customField6");
			return (Criteria) this;
		}

		public Criteria andCustomField6Like(String value) {
			addCriterion("custom_field6 like", value, "customField6");
			return (Criteria) this;
		}

		public Criteria andCustomField6NotLike(String value) {
			addCriterion("custom_field6 not like", value, "customField6");
			return (Criteria) this;
		}

		public Criteria andCustomField6In(List<String> values) {
			addCriterion("custom_field6 in", values, "customField6");
			return (Criteria) this;
		}

		public Criteria andCustomField6NotIn(List<String> values) {
			addCriterion("custom_field6 not in", values, "customField6");
			return (Criteria) this;
		}

		public Criteria andCustomField6Between(String value1, String value2) {
			addCriterion("custom_field6 between", value1, value2,
					"customField6");
			return (Criteria) this;
		}

		public Criteria andCustomField6NotBetween(String value1, String value2) {
			addCriterion("custom_field6 not between", value1, value2,
					"customField6");
			return (Criteria) this;
		}

		public Criteria andCustomField7IsNull() {
			addCriterion("custom_field7 is null");
			return (Criteria) this;
		}

		public Criteria andCustomField7IsNotNull() {
			addCriterion("custom_field7 is not null");
			return (Criteria) this;
		}

		public Criteria andCustomField7EqualTo(String value) {
			addCriterion("custom_field7 =", value, "customField7");
			return (Criteria) this;
		}

		public Criteria andCustomField7NotEqualTo(String value) {
			addCriterion("custom_field7 <>", value, "customField7");
			return (Criteria) this;
		}

		public Criteria andCustomField7GreaterThan(String value) {
			addCriterion("custom_field7 >", value, "customField7");
			return (Criteria) this;
		}

		public Criteria andCustomField7GreaterThanOrEqualTo(String value) {
			addCriterion("custom_field7 >=", value, "customField7");
			return (Criteria) this;
		}

		public Criteria andCustomField7LessThan(String value) {
			addCriterion("custom_field7 <", value, "customField7");
			return (Criteria) this;
		}

		public Criteria andCustomField7LessThanOrEqualTo(String value) {
			addCriterion("custom_field7 <=", value, "customField7");
			return (Criteria) this;
		}

		public Criteria andCustomField7Like(String value) {
			addCriterion("custom_field7 like", value, "customField7");
			return (Criteria) this;
		}

		public Criteria andCustomField7NotLike(String value) {
			addCriterion("custom_field7 not like", value, "customField7");
			return (Criteria) this;
		}

		public Criteria andCustomField7In(List<String> values) {
			addCriterion("custom_field7 in", values, "customField7");
			return (Criteria) this;
		}

		public Criteria andCustomField7NotIn(List<String> values) {
			addCriterion("custom_field7 not in", values, "customField7");
			return (Criteria) this;
		}

		public Criteria andCustomField7Between(String value1, String value2) {
			addCriterion("custom_field7 between", value1, value2,
					"customField7");
			return (Criteria) this;
		}

		public Criteria andCustomField7NotBetween(String value1, String value2) {
			addCriterion("custom_field7 not between", value1, value2,
					"customField7");
			return (Criteria) this;
		}

		public Criteria andCustomField8IsNull() {
			addCriterion("custom_field8 is null");
			return (Criteria) this;
		}

		public Criteria andCustomField8IsNotNull() {
			addCriterion("custom_field8 is not null");
			return (Criteria) this;
		}

		public Criteria andCustomField8EqualTo(String value) {
			addCriterion("custom_field8 =", value, "customField8");
			return (Criteria) this;
		}

		public Criteria andCustomField8NotEqualTo(String value) {
			addCriterion("custom_field8 <>", value, "customField8");
			return (Criteria) this;
		}

		public Criteria andCustomField8GreaterThan(String value) {
			addCriterion("custom_field8 >", value, "customField8");
			return (Criteria) this;
		}

		public Criteria andCustomField8GreaterThanOrEqualTo(String value) {
			addCriterion("custom_field8 >=", value, "customField8");
			return (Criteria) this;
		}

		public Criteria andCustomField8LessThan(String value) {
			addCriterion("custom_field8 <", value, "customField8");
			return (Criteria) this;
		}

		public Criteria andCustomField8LessThanOrEqualTo(String value) {
			addCriterion("custom_field8 <=", value, "customField8");
			return (Criteria) this;
		}

		public Criteria andCustomField8Like(String value) {
			addCriterion("custom_field8 like", value, "customField8");
			return (Criteria) this;
		}

		public Criteria andCustomField8NotLike(String value) {
			addCriterion("custom_field8 not like", value, "customField8");
			return (Criteria) this;
		}

		public Criteria andCustomField8In(List<String> values) {
			addCriterion("custom_field8 in", values, "customField8");
			return (Criteria) this;
		}

		public Criteria andCustomField8NotIn(List<String> values) {
			addCriterion("custom_field8 not in", values, "customField8");
			return (Criteria) this;
		}

		public Criteria andCustomField8Between(String value1, String value2) {
			addCriterion("custom_field8 between", value1, value2,
					"customField8");
			return (Criteria) this;
		}

		public Criteria andCustomField8NotBetween(String value1, String value2) {
			addCriterion("custom_field8 not between", value1, value2,
					"customField8");
			return (Criteria) this;
		}

		public Criteria andCustomField9IsNull() {
			addCriterion("custom_field9 is null");
			return (Criteria) this;
		}

		public Criteria andCustomField9IsNotNull() {
			addCriterion("custom_field9 is not null");
			return (Criteria) this;
		}

		public Criteria andCustomField9EqualTo(String value) {
			addCriterion("custom_field9 =", value, "customField9");
			return (Criteria) this;
		}

		public Criteria andCustomField9NotEqualTo(String value) {
			addCriterion("custom_field9 <>", value, "customField9");
			return (Criteria) this;
		}

		public Criteria andCustomField9GreaterThan(String value) {
			addCriterion("custom_field9 >", value, "customField9");
			return (Criteria) this;
		}

		public Criteria andCustomField9GreaterThanOrEqualTo(String value) {
			addCriterion("custom_field9 >=", value, "customField9");
			return (Criteria) this;
		}

		public Criteria andCustomField9LessThan(String value) {
			addCriterion("custom_field9 <", value, "customField9");
			return (Criteria) this;
		}

		public Criteria andCustomField9LessThanOrEqualTo(String value) {
			addCriterion("custom_field9 <=", value, "customField9");
			return (Criteria) this;
		}

		public Criteria andCustomField9Like(String value) {
			addCriterion("custom_field9 like", value, "customField9");
			return (Criteria) this;
		}

		public Criteria andCustomField9NotLike(String value) {
			addCriterion("custom_field9 not like", value, "customField9");
			return (Criteria) this;
		}

		public Criteria andCustomField9In(List<String> values) {
			addCriterion("custom_field9 in", values, "customField9");
			return (Criteria) this;
		}

		public Criteria andCustomField9NotIn(List<String> values) {
			addCriterion("custom_field9 not in", values, "customField9");
			return (Criteria) this;
		}

		public Criteria andCustomField9Between(String value1, String value2) {
			addCriterion("custom_field9 between", value1, value2,
					"customField9");
			return (Criteria) this;
		}

		public Criteria andCustomField9NotBetween(String value1, String value2) {
			addCriterion("custom_field9 not between", value1, value2,
					"customField9");
			return (Criteria) this;
		}

		public Criteria andCustomField10IsNull() {
			addCriterion("custom_field10 is null");
			return (Criteria) this;
		}

		public Criteria andCustomField10IsNotNull() {
			addCriterion("custom_field10 is not null");
			return (Criteria) this;
		}

		public Criteria andCustomField10EqualTo(String value) {
			addCriterion("custom_field10 =", value, "customField10");
			return (Criteria) this;
		}

		public Criteria andCustomField10NotEqualTo(String value) {
			addCriterion("custom_field10 <>", value, "customField10");
			return (Criteria) this;
		}

		public Criteria andCustomField10GreaterThan(String value) {
			addCriterion("custom_field10 >", value, "customField10");
			return (Criteria) this;
		}

		public Criteria andCustomField10GreaterThanOrEqualTo(String value) {
			addCriterion("custom_field10 >=", value, "customField10");
			return (Criteria) this;
		}

		public Criteria andCustomField10LessThan(String value) {
			addCriterion("custom_field10 <", value, "customField10");
			return (Criteria) this;
		}

		public Criteria andCustomField10LessThanOrEqualTo(String value) {
			addCriterion("custom_field10 <=", value, "customField10");
			return (Criteria) this;
		}

		public Criteria andCustomField10Like(String value) {
			addCriterion("custom_field10 like", value, "customField10");
			return (Criteria) this;
		}

		public Criteria andCustomField10NotLike(String value) {
			addCriterion("custom_field10 not like", value, "customField10");
			return (Criteria) this;
		}

		public Criteria andCustomField10In(List<String> values) {
			addCriterion("custom_field10 in", values, "customField10");
			return (Criteria) this;
		}

		public Criteria andCustomField10NotIn(List<String> values) {
			addCriterion("custom_field10 not in", values, "customField10");
			return (Criteria) this;
		}

		public Criteria andCustomField10Between(String value1, String value2) {
			addCriterion("custom_field10 between", value1, value2,
					"customField10");
			return (Criteria) this;
		}

		public Criteria andCustomField10NotBetween(String value1, String value2) {
			addCriterion("custom_field10 not between", value1, value2,
					"customField10");
			return (Criteria) this;
		}

		public Criteria andPhotoSignIsNull() {
			addCriterion("photo_sign is null");
			return (Criteria) this;
		}

		public Criteria andPhotoSignIsNotNull() {
			addCriterion("photo_sign is not null");
			return (Criteria) this;
		}

		public Criteria andPhotoSignEqualTo(String value) {
			addCriterion("photo_sign =", value, "photoSign");
			return (Criteria) this;
		}

		public Criteria andPhotoSignNotEqualTo(String value) {
			addCriterion("photo_sign <>", value, "photoSign");
			return (Criteria) this;
		}

		public Criteria andPhotoSignGreaterThan(String value) {
			addCriterion("photo_sign >", value, "photoSign");
			return (Criteria) this;
		}

		public Criteria andPhotoSignGreaterThanOrEqualTo(String value) {
			addCriterion("photo_sign >=", value, "photoSign");
			return (Criteria) this;
		}

		public Criteria andPhotoSignLessThan(String value) {
			addCriterion("photo_sign <", value, "photoSign");
			return (Criteria) this;
		}

		public Criteria andPhotoSignLessThanOrEqualTo(String value) {
			addCriterion("photo_sign <=", value, "photoSign");
			return (Criteria) this;
		}

		public Criteria andPhotoSignLike(String value) {
			addCriterion("photo_sign like", value, "photoSign");
			return (Criteria) this;
		}

		public Criteria andPhotoSignNotLike(String value) {
			addCriterion("photo_sign not like", value, "photoSign");
			return (Criteria) this;
		}

		public Criteria andPhotoSignIn(List<String> values) {
			addCriterion("photo_sign in", values, "photoSign");
			return (Criteria) this;
		}

		public Criteria andPhotoSignNotIn(List<String> values) {
			addCriterion("photo_sign not in", values, "photoSign");
			return (Criteria) this;
		}

		public Criteria andPhotoSignBetween(String value1, String value2) {
			addCriterion("photo_sign between", value1, value2, "photoSign");
			return (Criteria) this;
		}

		public Criteria andPhotoSignNotBetween(String value1, String value2) {
			addCriterion("photo_sign not between", value1, value2, "photoSign");
			return (Criteria) this;
		}

		public Criteria andStateIsNull() {
			addCriterion("state is null");
			return (Criteria) this;
		}

		public Criteria andStateIsNotNull() {
			addCriterion("state is not null");
			return (Criteria) this;
		}

		public Criteria andStateEqualTo(String value) {
			addCriterion("state =", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateNotEqualTo(String value) {
			addCriterion("state <>", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateGreaterThan(String value) {
			addCriterion("state >", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateGreaterThanOrEqualTo(String value) {
			addCriterion("state >=", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateLessThan(String value) {
			addCriterion("state <", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateLessThanOrEqualTo(String value) {
			addCriterion("state <=", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateLike(String value) {
			addCriterion("state like", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateNotLike(String value) {
			addCriterion("state not like", value, "state");
			return (Criteria) this;
		}

		public Criteria andStateIn(List<String> values) {
			addCriterion("state in", values, "state");
			return (Criteria) this;
		}

		public Criteria andStateNotIn(List<String> values) {
			addCriterion("state not in", values, "state");
			return (Criteria) this;
		}

		public Criteria andStateBetween(String value1, String value2) {
			addCriterion("state between", value1, value2, "state");
			return (Criteria) this;
		}

		public Criteria andStateNotBetween(String value1, String value2) {
			addCriterion("state not between", value1, value2, "state");
			return (Criteria) this;
		}

		public Criteria andUidIsNull() {
			addCriterion("uid is null");
			return (Criteria) this;
		}

		public Criteria andUidIsNotNull() {
			addCriterion("uid is not null");
			return (Criteria) this;
		}

		public Criteria andUidEqualTo(String value) {
			addCriterion("uid =", value, "uid");
			return (Criteria) this;
		}

		public Criteria andUidNotEqualTo(String value) {
			addCriterion("uid <>", value, "uid");
			return (Criteria) this;
		}

		public Criteria andUidGreaterThan(String value) {
			addCriterion("uid >", value, "uid");
			return (Criteria) this;
		}

		public Criteria andUidGreaterThanOrEqualTo(String value) {
			addCriterion("uid >=", value, "uid");
			return (Criteria) this;
		}

		public Criteria andUidLessThan(String value) {
			addCriterion("uid <", value, "uid");
			return (Criteria) this;
		}

		public Criteria andUidLessThanOrEqualTo(String value) {
			addCriterion("uid <=", value, "uid");
			return (Criteria) this;
		}

		public Criteria andUidLike(String value) {
			addCriterion("uid like", value, "uid");
			return (Criteria) this;
		}

		public Criteria andUidNotLike(String value) {
			addCriterion("uid not like", value, "uid");
			return (Criteria) this;
		}

		public Criteria andUidIn(List<String> values) {
			addCriterion("uid in", values, "uid");
			return (Criteria) this;
		}

		public Criteria andUidNotIn(List<String> values) {
			addCriterion("uid not in", values, "uid");
			return (Criteria) this;
		}

		public Criteria andUidBetween(String value1, String value2) {
			addCriterion("uid between", value1, value2, "uid");
			return (Criteria) this;
		}

		public Criteria andUidNotBetween(String value1, String value2) {
			addCriterion("uid not between", value1, value2, "uid");
			return (Criteria) this;
		}

		public Criteria andPasswordIsNull() {
			addCriterion("password is null");
			return (Criteria) this;
		}

		public Criteria andPasswordIsNotNull() {
			addCriterion("password is not null");
			return (Criteria) this;
		}

		public Criteria andPasswordEqualTo(String value) {
			addCriterion("password =", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordNotEqualTo(String value) {
			addCriterion("password <>", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordGreaterThan(String value) {
			addCriterion("password >", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordGreaterThanOrEqualTo(String value) {
			addCriterion("password >=", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordLessThan(String value) {
			addCriterion("password <", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordLessThanOrEqualTo(String value) {
			addCriterion("password <=", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordLike(String value) {
			addCriterion("password like", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordNotLike(String value) {
			addCriterion("password not like", value, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordIn(List<String> values) {
			addCriterion("password in", values, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordNotIn(List<String> values) {
			addCriterion("password not in", values, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordBetween(String value1, String value2) {
			addCriterion("password between", value1, value2, "password");
			return (Criteria) this;
		}

		public Criteria andPasswordNotBetween(String value1, String value2) {
			addCriterion("password not between", value1, value2, "password");
			return (Criteria) this;
		}

		public Criteria andPermitIpIsNull() {
			addCriterion("permit_ip is null");
			return (Criteria) this;
		}

		public Criteria andPermitIpIsNotNull() {
			addCriterion("permit_ip is not null");
			return (Criteria) this;
		}

		public Criteria andPermitIpEqualTo(String value) {
			addCriterion("permit_ip =", value, "permitIp");
			return (Criteria) this;
		}

		public Criteria andPermitIpNotEqualTo(String value) {
			addCriterion("permit_ip <>", value, "permitIp");
			return (Criteria) this;
		}

		public Criteria andPermitIpGreaterThan(String value) {
			addCriterion("permit_ip >", value, "permitIp");
			return (Criteria) this;
		}

		public Criteria andPermitIpGreaterThanOrEqualTo(String value) {
			addCriterion("permit_ip >=", value, "permitIp");
			return (Criteria) this;
		}

		public Criteria andPermitIpLessThan(String value) {
			addCriterion("permit_ip <", value, "permitIp");
			return (Criteria) this;
		}

		public Criteria andPermitIpLessThanOrEqualTo(String value) {
			addCriterion("permit_ip <=", value, "permitIp");
			return (Criteria) this;
		}

		public Criteria andPermitIpLike(String value) {
			addCriterion("permit_ip like", value, "permitIp");
			return (Criteria) this;
		}

		public Criteria andPermitIpNotLike(String value) {
			addCriterion("permit_ip not like", value, "permitIp");
			return (Criteria) this;
		}

		public Criteria andPermitIpIn(List<String> values) {
			addCriterion("permit_ip in", values, "permitIp");
			return (Criteria) this;
		}

		public Criteria andPermitIpNotIn(List<String> values) {
			addCriterion("permit_ip not in", values, "permitIp");
			return (Criteria) this;
		}

		public Criteria andPermitIpBetween(String value1, String value2) {
			addCriterion("permit_ip between", value1, value2, "permitIp");
			return (Criteria) this;
		}

		public Criteria andPermitIpNotBetween(String value1, String value2) {
			addCriterion("permit_ip not between", value1, value2, "permitIp");
			return (Criteria) this;
		}

		public Criteria andUsableIsNull() {
			addCriterion("usable is null");
			return (Criteria) this;
		}

		public Criteria andUsableIsNotNull() {
			addCriterion("usable is not null");
			return (Criteria) this;
		}

		public Criteria andUsableEqualTo(String value) {
			addCriterion("usable =", value, "usable");
			return (Criteria) this;
		}

		public Criteria andUsableNotEqualTo(String value) {
			addCriterion("usable <>", value, "usable");
			return (Criteria) this;
		}

		public Criteria andUsableGreaterThan(String value) {
			addCriterion("usable >", value, "usable");
			return (Criteria) this;
		}

		public Criteria andUsableGreaterThanOrEqualTo(String value) {
			addCriterion("usable >=", value, "usable");
			return (Criteria) this;
		}

		public Criteria andUsableLessThan(String value) {
			addCriterion("usable <", value, "usable");
			return (Criteria) this;
		}

		public Criteria andUsableLessThanOrEqualTo(String value) {
			addCriterion("usable <=", value, "usable");
			return (Criteria) this;
		}

		public Criteria andUsableLike(String value) {
			addCriterion("usable like", value, "usable");
			return (Criteria) this;
		}

		public Criteria andUsableNotLike(String value) {
			addCriterion("usable not like", value, "usable");
			return (Criteria) this;
		}

		public Criteria andUsableIn(List<String> values) {
			addCriterion("usable in", values, "usable");
			return (Criteria) this;
		}

		public Criteria andUsableNotIn(List<String> values) {
			addCriterion("usable not in", values, "usable");
			return (Criteria) this;
		}

		public Criteria andUsableBetween(String value1, String value2) {
			addCriterion("usable between", value1, value2, "usable");
			return (Criteria) this;
		}

		public Criteria andUsableNotBetween(String value1, String value2) {
			addCriterion("usable not between", value1, value2, "usable");
			return (Criteria) this;
		}

		public Criteria andExpiredTimeIsNull() {
			addCriterion("expired_time is null");
			return (Criteria) this;
		}

		public Criteria andExpiredTimeIsNotNull() {
			addCriterion("expired_time is not null");
			return (Criteria) this;
		}

		public Criteria andExpiredTimeEqualTo(Date value) {
			addCriterionForJDBCDate("expired_time =", value, "expiredTime");
			return (Criteria) this;
		}

		public Criteria andExpiredTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("expired_time <>", value, "expiredTime");
			return (Criteria) this;
		}

		public Criteria andExpiredTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("expired_time >", value, "expiredTime");
			return (Criteria) this;
		}

		public Criteria andExpiredTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("expired_time >=", value, "expiredTime");
			return (Criteria) this;
		}

		public Criteria andExpiredTimeLessThan(Date value) {
			addCriterionForJDBCDate("expired_time <", value, "expiredTime");
			return (Criteria) this;
		}

		public Criteria andExpiredTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("expired_time <=", value, "expiredTime");
			return (Criteria) this;
		}

		public Criteria andExpiredTimeIn(List<Date> values) {
			addCriterionForJDBCDate("expired_time in", values, "expiredTime");
			return (Criteria) this;
		}

		public Criteria andExpiredTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("expired_time not in", values,
					"expiredTime");
			return (Criteria) this;
		}

		public Criteria andExpiredTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("expired_time between", value1, value2,
					"expiredTime");
			return (Criteria) this;
		}

		public Criteria andExpiredTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("expired_time not between", value1, value2,
					"expiredTime");
			return (Criteria) this;
		}

		public Criteria andLastLoginTimeIsNull() {
			addCriterion("last_login_time is null");
			return (Criteria) this;
		}

		public Criteria andLastLoginTimeIsNotNull() {
			addCriterion("last_login_time is not null");
			return (Criteria) this;
		}

		public Criteria andLastLoginTimeEqualTo(Date value) {
			addCriterionForJDBCDate("last_login_time =", value, "lastLoginTime");
			return (Criteria) this;
		}

		public Criteria andLastLoginTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("last_login_time <>", value,
					"lastLoginTime");
			return (Criteria) this;
		}

		public Criteria andLastLoginTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("last_login_time >", value, "lastLoginTime");
			return (Criteria) this;
		}

		public Criteria andLastLoginTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("last_login_time >=", value,
					"lastLoginTime");
			return (Criteria) this;
		}

		public Criteria andLastLoginTimeLessThan(Date value) {
			addCriterionForJDBCDate("last_login_time <", value, "lastLoginTime");
			return (Criteria) this;
		}

		public Criteria andLastLoginTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("last_login_time <=", value,
					"lastLoginTime");
			return (Criteria) this;
		}

		public Criteria andLastLoginTimeIn(List<Date> values) {
			addCriterionForJDBCDate("last_login_time in", values,
					"lastLoginTime");
			return (Criteria) this;
		}

		public Criteria andLastLoginTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("last_login_time not in", values,
					"lastLoginTime");
			return (Criteria) this;
		}

		public Criteria andLastLoginTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("last_login_time between", value1, value2,
					"lastLoginTime");
			return (Criteria) this;
		}

		public Criteria andLastLoginTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("last_login_time not between", value1,
					value2, "lastLoginTime");
			return (Criteria) this;
		}

		public Criteria andPasswdUpdateTimeIsNull() {
			addCriterion("passwd_update_time is null");
			return (Criteria) this;
		}

		public Criteria andPasswdUpdateTimeIsNotNull() {
			addCriterion("passwd_update_time is not null");
			return (Criteria) this;
		}

		public Criteria andPasswdUpdateTimeEqualTo(Date value) {
			addCriterionForJDBCDate("passwd_update_time =", value,
					"passwdUpdateTime");
			return (Criteria) this;
		}

		public Criteria andPasswdUpdateTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("passwd_update_time <>", value,
					"passwdUpdateTime");
			return (Criteria) this;
		}

		public Criteria andPasswdUpdateTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("passwd_update_time >", value,
					"passwdUpdateTime");
			return (Criteria) this;
		}

		public Criteria andPasswdUpdateTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("passwd_update_time >=", value,
					"passwdUpdateTime");
			return (Criteria) this;
		}

		public Criteria andPasswdUpdateTimeLessThan(Date value) {
			addCriterionForJDBCDate("passwd_update_time <", value,
					"passwdUpdateTime");
			return (Criteria) this;
		}

		public Criteria andPasswdUpdateTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("passwd_update_time <=", value,
					"passwdUpdateTime");
			return (Criteria) this;
		}

		public Criteria andPasswdUpdateTimeIn(List<Date> values) {
			addCriterionForJDBCDate("passwd_update_time in", values,
					"passwdUpdateTime");
			return (Criteria) this;
		}

		public Criteria andPasswdUpdateTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("passwd_update_time not in", values,
					"passwdUpdateTime");
			return (Criteria) this;
		}

		public Criteria andPasswdUpdateTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("passwd_update_time between", value1,
					value2, "passwdUpdateTime");
			return (Criteria) this;
		}

		public Criteria andPasswdUpdateTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("passwd_update_time not between", value1,
					value2, "passwdUpdateTime");
			return (Criteria) this;
		}

		public Criteria andUserTypeIsNull() {
			addCriterion("user_type is null");
			return (Criteria) this;
		}

		public Criteria andUserTypeIsNotNull() {
			addCriterion("user_type is not null");
			return (Criteria) this;
		}

		public Criteria andUserTypeEqualTo(String value) {
			addCriterion("user_type =", value, "userType");
			return (Criteria) this;
		}

		public Criteria andUserTypeNotEqualTo(String value) {
			addCriterion("user_type <>", value, "userType");
			return (Criteria) this;
		}

		public Criteria andUserTypeGreaterThan(String value) {
			addCriterion("user_type >", value, "userType");
			return (Criteria) this;
		}

		public Criteria andUserTypeGreaterThanOrEqualTo(String value) {
			addCriterion("user_type >=", value, "userType");
			return (Criteria) this;
		}

		public Criteria andUserTypeLessThan(String value) {
			addCriterion("user_type <", value, "userType");
			return (Criteria) this;
		}

		public Criteria andUserTypeLessThanOrEqualTo(String value) {
			addCriterion("user_type <=", value, "userType");
			return (Criteria) this;
		}

		public Criteria andUserTypeLike(String value) {
			addCriterion("user_type like", value, "userType");
			return (Criteria) this;
		}

		public Criteria andUserTypeNotLike(String value) {
			addCriterion("user_type not like", value, "userType");
			return (Criteria) this;
		}

		public Criteria andUserTypeIn(List<String> values) {
			addCriterion("user_type in", values, "userType");
			return (Criteria) this;
		}

		public Criteria andUserTypeNotIn(List<String> values) {
			addCriterion("user_type not in", values, "userType");
			return (Criteria) this;
		}

		public Criteria andUserTypeBetween(String value1, String value2) {
			addCriterion("user_type between", value1, value2, "userType");
			return (Criteria) this;
		}

		public Criteria andUserTypeNotBetween(String value1, String value2) {
			addCriterion("user_type not between", value1, value2, "userType");
			return (Criteria) this;
		}

		public Criteria andEditableIsNull() {
			addCriterion("editable is null");
			return (Criteria) this;
		}

		public Criteria andEditableIsNotNull() {
			addCriterion("editable is not null");
			return (Criteria) this;
		}

		public Criteria andEditableEqualTo(String value) {
			addCriterion("editable =", value, "editable");
			return (Criteria) this;
		}

		public Criteria andEditableNotEqualTo(String value) {
			addCriterion("editable <>", value, "editable");
			return (Criteria) this;
		}

		public Criteria andEditableGreaterThan(String value) {
			addCriterion("editable >", value, "editable");
			return (Criteria) this;
		}

		public Criteria andEditableGreaterThanOrEqualTo(String value) {
			addCriterion("editable >=", value, "editable");
			return (Criteria) this;
		}

		public Criteria andEditableLessThan(String value) {
			addCriterion("editable <", value, "editable");
			return (Criteria) this;
		}

		public Criteria andEditableLessThanOrEqualTo(String value) {
			addCriterion("editable <=", value, "editable");
			return (Criteria) this;
		}

		public Criteria andEditableLike(String value) {
			addCriterion("editable like", value, "editable");
			return (Criteria) this;
		}

		public Criteria andEditableNotLike(String value) {
			addCriterion("editable not like", value, "editable");
			return (Criteria) this;
		}

		public Criteria andEditableIn(List<String> values) {
			addCriterion("editable in", values, "editable");
			return (Criteria) this;
		}

		public Criteria andEditableNotIn(List<String> values) {
			addCriterion("editable not in", values, "editable");
			return (Criteria) this;
		}

		public Criteria andEditableBetween(String value1, String value2) {
			addCriterion("editable between", value1, value2, "editable");
			return (Criteria) this;
		}

		public Criteria andEditableNotBetween(String value1, String value2) {
			addCriterion("editable not between", value1, value2, "editable");
			return (Criteria) this;
		}

		public Criteria andDeletableIsNull() {
			addCriterion("deletable is null");
			return (Criteria) this;
		}

		public Criteria andDeletableIsNotNull() {
			addCriterion("deletable is not null");
			return (Criteria) this;
		}

		public Criteria andDeletableEqualTo(String value) {
			addCriterion("deletable =", value, "deletable");
			return (Criteria) this;
		}

		public Criteria andDeletableNotEqualTo(String value) {
			addCriterion("deletable <>", value, "deletable");
			return (Criteria) this;
		}

		public Criteria andDeletableGreaterThan(String value) {
			addCriterion("deletable >", value, "deletable");
			return (Criteria) this;
		}

		public Criteria andDeletableGreaterThanOrEqualTo(String value) {
			addCriterion("deletable >=", value, "deletable");
			return (Criteria) this;
		}

		public Criteria andDeletableLessThan(String value) {
			addCriterion("deletable <", value, "deletable");
			return (Criteria) this;
		}

		public Criteria andDeletableLessThanOrEqualTo(String value) {
			addCriterion("deletable <=", value, "deletable");
			return (Criteria) this;
		}

		public Criteria andDeletableLike(String value) {
			addCriterion("deletable like", value, "deletable");
			return (Criteria) this;
		}

		public Criteria andDeletableNotLike(String value) {
			addCriterion("deletable not like", value, "deletable");
			return (Criteria) this;
		}

		public Criteria andDeletableIn(List<String> values) {
			addCriterion("deletable in", values, "deletable");
			return (Criteria) this;
		}

		public Criteria andDeletableNotIn(List<String> values) {
			addCriterion("deletable not in", values, "deletable");
			return (Criteria) this;
		}

		public Criteria andDeletableBetween(String value1, String value2) {
			addCriterion("deletable between", value1, value2, "deletable");
			return (Criteria) this;
		}

		public Criteria andDeletableNotBetween(String value1, String value2) {
			addCriterion("deletable not between", value1, value2, "deletable");
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