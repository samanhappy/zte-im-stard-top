package com.zte.im.mybatis.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UcTenantExample {
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public UcTenantExample() {
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

		public Criteria andPlatformIdIsNull() {
			addCriterion("platform_id is null");
			return (Criteria) this;
		}

		public Criteria andPlatformIdIsNotNull() {
			addCriterion("platform_id is not null");
			return (Criteria) this;
		}

		public Criteria andPlatformIdEqualTo(String value) {
			addCriterion("platform_id =", value, "platformId");
			return (Criteria) this;
		}

		public Criteria andPlatformIdNotEqualTo(String value) {
			addCriterion("platform_id <>", value, "platformId");
			return (Criteria) this;
		}

		public Criteria andPlatformIdGreaterThan(String value) {
			addCriterion("platform_id >", value, "platformId");
			return (Criteria) this;
		}

		public Criteria andPlatformIdGreaterThanOrEqualTo(String value) {
			addCriterion("platform_id >=", value, "platformId");
			return (Criteria) this;
		}

		public Criteria andPlatformIdLessThan(String value) {
			addCriterion("platform_id <", value, "platformId");
			return (Criteria) this;
		}

		public Criteria andPlatformIdLessThanOrEqualTo(String value) {
			addCriterion("platform_id <=", value, "platformId");
			return (Criteria) this;
		}

		public Criteria andPlatformIdLike(String value) {
			addCriterion("platform_id like", value, "platformId");
			return (Criteria) this;
		}

		public Criteria andPlatformIdNotLike(String value) {
			addCriterion("platform_id not like", value, "platformId");
			return (Criteria) this;
		}

		public Criteria andPlatformIdIn(List<String> values) {
			addCriterion("platform_id in", values, "platformId");
			return (Criteria) this;
		}

		public Criteria andPlatformIdNotIn(List<String> values) {
			addCriterion("platform_id not in", values, "platformId");
			return (Criteria) this;
		}

		public Criteria andPlatformIdBetween(String value1, String value2) {
			addCriterion("platform_id between", value1, value2, "platformId");
			return (Criteria) this;
		}

		public Criteria andPlatformIdNotBetween(String value1, String value2) {
			addCriterion("platform_id not between", value1, value2,
					"platformId");
			return (Criteria) this;
		}

		public Criteria andEcidIsNull() {
			addCriterion("ecid is null");
			return (Criteria) this;
		}

		public Criteria andEcidIsNotNull() {
			addCriterion("ecid is not null");
			return (Criteria) this;
		}

		public Criteria andEcidEqualTo(String value) {
			addCriterion("ecid =", value, "ecid");
			return (Criteria) this;
		}

		public Criteria andEcidNotEqualTo(String value) {
			addCriterion("ecid <>", value, "ecid");
			return (Criteria) this;
		}

		public Criteria andEcidGreaterThan(String value) {
			addCriterion("ecid >", value, "ecid");
			return (Criteria) this;
		}

		public Criteria andEcidGreaterThanOrEqualTo(String value) {
			addCriterion("ecid >=", value, "ecid");
			return (Criteria) this;
		}

		public Criteria andEcidLessThan(String value) {
			addCriterion("ecid <", value, "ecid");
			return (Criteria) this;
		}

		public Criteria andEcidLessThanOrEqualTo(String value) {
			addCriterion("ecid <=", value, "ecid");
			return (Criteria) this;
		}

		public Criteria andEcidLike(String value) {
			addCriterion("ecid like", value, "ecid");
			return (Criteria) this;
		}

		public Criteria andEcidNotLike(String value) {
			addCriterion("ecid not like", value, "ecid");
			return (Criteria) this;
		}

		public Criteria andEcidIn(List<String> values) {
			addCriterion("ecid in", values, "ecid");
			return (Criteria) this;
		}

		public Criteria andEcidNotIn(List<String> values) {
			addCriterion("ecid not in", values, "ecid");
			return (Criteria) this;
		}

		public Criteria andEcidBetween(String value1, String value2) {
			addCriterion("ecid between", value1, value2, "ecid");
			return (Criteria) this;
		}

		public Criteria andEcidNotBetween(String value1, String value2) {
			addCriterion("ecid not between", value1, value2, "ecid");
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

		public Criteria andCatalogIdIsNull() {
			addCriterion("catalog_id is null");
			return (Criteria) this;
		}

		public Criteria andCatalogIdIsNotNull() {
			addCriterion("catalog_id is not null");
			return (Criteria) this;
		}

		public Criteria andCatalogIdEqualTo(String value) {
			addCriterion("catalog_id =", value, "catalogId");
			return (Criteria) this;
		}

		public Criteria andCatalogIdNotEqualTo(String value) {
			addCriterion("catalog_id <>", value, "catalogId");
			return (Criteria) this;
		}

		public Criteria andCatalogIdGreaterThan(String value) {
			addCriterion("catalog_id >", value, "catalogId");
			return (Criteria) this;
		}

		public Criteria andCatalogIdGreaterThanOrEqualTo(String value) {
			addCriterion("catalog_id >=", value, "catalogId");
			return (Criteria) this;
		}

		public Criteria andCatalogIdLessThan(String value) {
			addCriterion("catalog_id <", value, "catalogId");
			return (Criteria) this;
		}

		public Criteria andCatalogIdLessThanOrEqualTo(String value) {
			addCriterion("catalog_id <=", value, "catalogId");
			return (Criteria) this;
		}

		public Criteria andCatalogIdLike(String value) {
			addCriterion("catalog_id like", value, "catalogId");
			return (Criteria) this;
		}

		public Criteria andCatalogIdNotLike(String value) {
			addCriterion("catalog_id not like", value, "catalogId");
			return (Criteria) this;
		}

		public Criteria andCatalogIdIn(List<String> values) {
			addCriterion("catalog_id in", values, "catalogId");
			return (Criteria) this;
		}

		public Criteria andCatalogIdNotIn(List<String> values) {
			addCriterion("catalog_id not in", values, "catalogId");
			return (Criteria) this;
		}

		public Criteria andCatalogIdBetween(String value1, String value2) {
			addCriterion("catalog_id between", value1, value2, "catalogId");
			return (Criteria) this;
		}

		public Criteria andCatalogIdNotBetween(String value1, String value2) {
			addCriterion("catalog_id not between", value1, value2, "catalogId");
			return (Criteria) this;
		}

		public Criteria andMaxUsersIsNull() {
			addCriterion("max_users is null");
			return (Criteria) this;
		}

		public Criteria andMaxUsersIsNotNull() {
			addCriterion("max_users is not null");
			return (Criteria) this;
		}

		public Criteria andMaxUsersEqualTo(Long value) {
			addCriterion("max_users =", value, "maxUsers");
			return (Criteria) this;
		}

		public Criteria andMaxUsersNotEqualTo(Long value) {
			addCriterion("max_users <>", value, "maxUsers");
			return (Criteria) this;
		}

		public Criteria andMaxUsersGreaterThan(Long value) {
			addCriterion("max_users >", value, "maxUsers");
			return (Criteria) this;
		}

		public Criteria andMaxUsersGreaterThanOrEqualTo(Long value) {
			addCriterion("max_users >=", value, "maxUsers");
			return (Criteria) this;
		}

		public Criteria andMaxUsersLessThan(Long value) {
			addCriterion("max_users <", value, "maxUsers");
			return (Criteria) this;
		}

		public Criteria andMaxUsersLessThanOrEqualTo(Long value) {
			addCriterion("max_users <=", value, "maxUsers");
			return (Criteria) this;
		}

		public Criteria andMaxUsersIn(List<Long> values) {
			addCriterion("max_users in", values, "maxUsers");
			return (Criteria) this;
		}

		public Criteria andMaxUsersNotIn(List<Long> values) {
			addCriterion("max_users not in", values, "maxUsers");
			return (Criteria) this;
		}

		public Criteria andMaxUsersBetween(Long value1, Long value2) {
			addCriterion("max_users between", value1, value2, "maxUsers");
			return (Criteria) this;
		}

		public Criteria andMaxUsersNotBetween(Long value1, Long value2) {
			addCriterion("max_users not between", value1, value2, "maxUsers");
			return (Criteria) this;
		}

		public Criteria andAddressIsNull() {
			addCriterion("address is null");
			return (Criteria) this;
		}

		public Criteria andAddressIsNotNull() {
			addCriterion("address is not null");
			return (Criteria) this;
		}

		public Criteria andAddressEqualTo(String value) {
			addCriterion("address =", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressNotEqualTo(String value) {
			addCriterion("address <>", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressGreaterThan(String value) {
			addCriterion("address >", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressGreaterThanOrEqualTo(String value) {
			addCriterion("address >=", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressLessThan(String value) {
			addCriterion("address <", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressLessThanOrEqualTo(String value) {
			addCriterion("address <=", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressLike(String value) {
			addCriterion("address like", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressNotLike(String value) {
			addCriterion("address not like", value, "address");
			return (Criteria) this;
		}

		public Criteria andAddressIn(List<String> values) {
			addCriterion("address in", values, "address");
			return (Criteria) this;
		}

		public Criteria andAddressNotIn(List<String> values) {
			addCriterion("address not in", values, "address");
			return (Criteria) this;
		}

		public Criteria andAddressBetween(String value1, String value2) {
			addCriterion("address between", value1, value2, "address");
			return (Criteria) this;
		}

		public Criteria andAddressNotBetween(String value1, String value2) {
			addCriterion("address not between", value1, value2, "address");
			return (Criteria) this;
		}

		public Criteria andLinkmanIsNull() {
			addCriterion("linkman is null");
			return (Criteria) this;
		}

		public Criteria andLinkmanIsNotNull() {
			addCriterion("linkman is not null");
			return (Criteria) this;
		}

		public Criteria andLinkmanEqualTo(String value) {
			addCriterion("linkman =", value, "linkman");
			return (Criteria) this;
		}

		public Criteria andLinkmanNotEqualTo(String value) {
			addCriterion("linkman <>", value, "linkman");
			return (Criteria) this;
		}

		public Criteria andLinkmanGreaterThan(String value) {
			addCriterion("linkman >", value, "linkman");
			return (Criteria) this;
		}

		public Criteria andLinkmanGreaterThanOrEqualTo(String value) {
			addCriterion("linkman >=", value, "linkman");
			return (Criteria) this;
		}

		public Criteria andLinkmanLessThan(String value) {
			addCriterion("linkman <", value, "linkman");
			return (Criteria) this;
		}

		public Criteria andLinkmanLessThanOrEqualTo(String value) {
			addCriterion("linkman <=", value, "linkman");
			return (Criteria) this;
		}

		public Criteria andLinkmanLike(String value) {
			addCriterion("linkman like", value, "linkman");
			return (Criteria) this;
		}

		public Criteria andLinkmanNotLike(String value) {
			addCriterion("linkman not like", value, "linkman");
			return (Criteria) this;
		}

		public Criteria andLinkmanIn(List<String> values) {
			addCriterion("linkman in", values, "linkman");
			return (Criteria) this;
		}

		public Criteria andLinkmanNotIn(List<String> values) {
			addCriterion("linkman not in", values, "linkman");
			return (Criteria) this;
		}

		public Criteria andLinkmanBetween(String value1, String value2) {
			addCriterion("linkman between", value1, value2, "linkman");
			return (Criteria) this;
		}

		public Criteria andLinkmanNotBetween(String value1, String value2) {
			addCriterion("linkman not between", value1, value2, "linkman");
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

		public Criteria andTelIsNull() {
			addCriterion("tel is null");
			return (Criteria) this;
		}

		public Criteria andTelIsNotNull() {
			addCriterion("tel is not null");
			return (Criteria) this;
		}

		public Criteria andTelEqualTo(String value) {
			addCriterion("tel =", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelNotEqualTo(String value) {
			addCriterion("tel <>", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelGreaterThan(String value) {
			addCriterion("tel >", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelGreaterThanOrEqualTo(String value) {
			addCriterion("tel >=", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelLessThan(String value) {
			addCriterion("tel <", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelLessThanOrEqualTo(String value) {
			addCriterion("tel <=", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelLike(String value) {
			addCriterion("tel like", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelNotLike(String value) {
			addCriterion("tel not like", value, "tel");
			return (Criteria) this;
		}

		public Criteria andTelIn(List<String> values) {
			addCriterion("tel in", values, "tel");
			return (Criteria) this;
		}

		public Criteria andTelNotIn(List<String> values) {
			addCriterion("tel not in", values, "tel");
			return (Criteria) this;
		}

		public Criteria andTelBetween(String value1, String value2) {
			addCriterion("tel between", value1, value2, "tel");
			return (Criteria) this;
		}

		public Criteria andTelNotBetween(String value1, String value2) {
			addCriterion("tel not between", value1, value2, "tel");
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

		public Criteria andOrgCodeIsNull() {
			addCriterion("org_code is null");
			return (Criteria) this;
		}

		public Criteria andOrgCodeIsNotNull() {
			addCriterion("org_code is not null");
			return (Criteria) this;
		}

		public Criteria andOrgCodeEqualTo(String value) {
			addCriterion("org_code =", value, "orgCode");
			return (Criteria) this;
		}

		public Criteria andOrgCodeNotEqualTo(String value) {
			addCriterion("org_code <>", value, "orgCode");
			return (Criteria) this;
		}

		public Criteria andOrgCodeGreaterThan(String value) {
			addCriterion("org_code >", value, "orgCode");
			return (Criteria) this;
		}

		public Criteria andOrgCodeGreaterThanOrEqualTo(String value) {
			addCriterion("org_code >=", value, "orgCode");
			return (Criteria) this;
		}

		public Criteria andOrgCodeLessThan(String value) {
			addCriterion("org_code <", value, "orgCode");
			return (Criteria) this;
		}

		public Criteria andOrgCodeLessThanOrEqualTo(String value) {
			addCriterion("org_code <=", value, "orgCode");
			return (Criteria) this;
		}

		public Criteria andOrgCodeLike(String value) {
			addCriterion("org_code like", value, "orgCode");
			return (Criteria) this;
		}

		public Criteria andOrgCodeNotLike(String value) {
			addCriterion("org_code not like", value, "orgCode");
			return (Criteria) this;
		}

		public Criteria andOrgCodeIn(List<String> values) {
			addCriterion("org_code in", values, "orgCode");
			return (Criteria) this;
		}

		public Criteria andOrgCodeNotIn(List<String> values) {
			addCriterion("org_code not in", values, "orgCode");
			return (Criteria) this;
		}

		public Criteria andOrgCodeBetween(String value1, String value2) {
			addCriterion("org_code between", value1, value2, "orgCode");
			return (Criteria) this;
		}

		public Criteria andOrgCodeNotBetween(String value1, String value2) {
			addCriterion("org_code not between", value1, value2, "orgCode");
			return (Criteria) this;
		}

		public Criteria andLicenseFileIsNull() {
			addCriterion("license_file is null");
			return (Criteria) this;
		}

		public Criteria andLicenseFileIsNotNull() {
			addCriterion("license_file is not null");
			return (Criteria) this;
		}

		public Criteria andLicenseFileEqualTo(String value) {
			addCriterion("license_file =", value, "licenseFile");
			return (Criteria) this;
		}

		public Criteria andLicenseFileNotEqualTo(String value) {
			addCriterion("license_file <>", value, "licenseFile");
			return (Criteria) this;
		}

		public Criteria andLicenseFileGreaterThan(String value) {
			addCriterion("license_file >", value, "licenseFile");
			return (Criteria) this;
		}

		public Criteria andLicenseFileGreaterThanOrEqualTo(String value) {
			addCriterion("license_file >=", value, "licenseFile");
			return (Criteria) this;
		}

		public Criteria andLicenseFileLessThan(String value) {
			addCriterion("license_file <", value, "licenseFile");
			return (Criteria) this;
		}

		public Criteria andLicenseFileLessThanOrEqualTo(String value) {
			addCriterion("license_file <=", value, "licenseFile");
			return (Criteria) this;
		}

		public Criteria andLicenseFileLike(String value) {
			addCriterion("license_file like", value, "licenseFile");
			return (Criteria) this;
		}

		public Criteria andLicenseFileNotLike(String value) {
			addCriterion("license_file not like", value, "licenseFile");
			return (Criteria) this;
		}

		public Criteria andLicenseFileIn(List<String> values) {
			addCriterion("license_file in", values, "licenseFile");
			return (Criteria) this;
		}

		public Criteria andLicenseFileNotIn(List<String> values) {
			addCriterion("license_file not in", values, "licenseFile");
			return (Criteria) this;
		}

		public Criteria andLicenseFileBetween(String value1, String value2) {
			addCriterion("license_file between", value1, value2, "licenseFile");
			return (Criteria) this;
		}

		public Criteria andLicenseFileNotBetween(String value1, String value2) {
			addCriterion("license_file not between", value1, value2,
					"licenseFile");
			return (Criteria) this;
		}

		public Criteria andUserCountIsNull() {
			addCriterion("user_count is null");
			return (Criteria) this;
		}

		public Criteria andUserCountIsNotNull() {
			addCriterion("user_count is not null");
			return (Criteria) this;
		}

		public Criteria andUserCountEqualTo(Long value) {
			addCriterion("user_count =", value, "userCount");
			return (Criteria) this;
		}

		public Criteria andUserCountNotEqualTo(Long value) {
			addCriterion("user_count <>", value, "userCount");
			return (Criteria) this;
		}

		public Criteria andUserCountGreaterThan(Long value) {
			addCriterion("user_count >", value, "userCount");
			return (Criteria) this;
		}

		public Criteria andUserCountGreaterThanOrEqualTo(Long value) {
			addCriterion("user_count >=", value, "userCount");
			return (Criteria) this;
		}

		public Criteria andUserCountLessThan(Long value) {
			addCriterion("user_count <", value, "userCount");
			return (Criteria) this;
		}

		public Criteria andUserCountLessThanOrEqualTo(Long value) {
			addCriterion("user_count <=", value, "userCount");
			return (Criteria) this;
		}

		public Criteria andUserCountIn(List<Long> values) {
			addCriterion("user_count in", values, "userCount");
			return (Criteria) this;
		}

		public Criteria andUserCountNotIn(List<Long> values) {
			addCriterion("user_count not in", values, "userCount");
			return (Criteria) this;
		}

		public Criteria andUserCountBetween(Long value1, Long value2) {
			addCriterion("user_count between", value1, value2, "userCount");
			return (Criteria) this;
		}

		public Criteria andUserCountNotBetween(Long value1, Long value2) {
			addCriterion("user_count not between", value1, value2, "userCount");
			return (Criteria) this;
		}

		public Criteria andAuditedIsNull() {
			addCriterion("audited is null");
			return (Criteria) this;
		}

		public Criteria andAuditedIsNotNull() {
			addCriterion("audited is not null");
			return (Criteria) this;
		}

		public Criteria andAuditedEqualTo(String value) {
			addCriterion("audited =", value, "audited");
			return (Criteria) this;
		}

		public Criteria andAuditedNotEqualTo(String value) {
			addCriterion("audited <>", value, "audited");
			return (Criteria) this;
		}

		public Criteria andAuditedGreaterThan(String value) {
			addCriterion("audited >", value, "audited");
			return (Criteria) this;
		}

		public Criteria andAuditedGreaterThanOrEqualTo(String value) {
			addCriterion("audited >=", value, "audited");
			return (Criteria) this;
		}

		public Criteria andAuditedLessThan(String value) {
			addCriterion("audited <", value, "audited");
			return (Criteria) this;
		}

		public Criteria andAuditedLessThanOrEqualTo(String value) {
			addCriterion("audited <=", value, "audited");
			return (Criteria) this;
		}

		public Criteria andAuditedLike(String value) {
			addCriterion("audited like", value, "audited");
			return (Criteria) this;
		}

		public Criteria andAuditedNotLike(String value) {
			addCriterion("audited not like", value, "audited");
			return (Criteria) this;
		}

		public Criteria andAuditedIn(List<String> values) {
			addCriterion("audited in", values, "audited");
			return (Criteria) this;
		}

		public Criteria andAuditedNotIn(List<String> values) {
			addCriterion("audited not in", values, "audited");
			return (Criteria) this;
		}

		public Criteria andAuditedBetween(String value1, String value2) {
			addCriterion("audited between", value1, value2, "audited");
			return (Criteria) this;
		}

		public Criteria andAuditedNotBetween(String value1, String value2) {
			addCriterion("audited not between", value1, value2, "audited");
			return (Criteria) this;
		}

		public Criteria andInitializedIsNull() {
			addCriterion("initialized is null");
			return (Criteria) this;
		}

		public Criteria andInitializedIsNotNull() {
			addCriterion("initialized is not null");
			return (Criteria) this;
		}

		public Criteria andInitializedEqualTo(String value) {
			addCriterion("initialized =", value, "initialized");
			return (Criteria) this;
		}

		public Criteria andInitializedNotEqualTo(String value) {
			addCriterion("initialized <>", value, "initialized");
			return (Criteria) this;
		}

		public Criteria andInitializedGreaterThan(String value) {
			addCriterion("initialized >", value, "initialized");
			return (Criteria) this;
		}

		public Criteria andInitializedGreaterThanOrEqualTo(String value) {
			addCriterion("initialized >=", value, "initialized");
			return (Criteria) this;
		}

		public Criteria andInitializedLessThan(String value) {
			addCriterion("initialized <", value, "initialized");
			return (Criteria) this;
		}

		public Criteria andInitializedLessThanOrEqualTo(String value) {
			addCriterion("initialized <=", value, "initialized");
			return (Criteria) this;
		}

		public Criteria andInitializedLike(String value) {
			addCriterion("initialized like", value, "initialized");
			return (Criteria) this;
		}

		public Criteria andInitializedNotLike(String value) {
			addCriterion("initialized not like", value, "initialized");
			return (Criteria) this;
		}

		public Criteria andInitializedIn(List<String> values) {
			addCriterion("initialized in", values, "initialized");
			return (Criteria) this;
		}

		public Criteria andInitializedNotIn(List<String> values) {
			addCriterion("initialized not in", values, "initialized");
			return (Criteria) this;
		}

		public Criteria andInitializedBetween(String value1, String value2) {
			addCriterion("initialized between", value1, value2, "initialized");
			return (Criteria) this;
		}

		public Criteria andInitializedNotBetween(String value1, String value2) {
			addCriterion("initialized not between", value1, value2,
					"initialized");
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

		public Criteria andPauseTimeIsNull() {
			addCriterion("pause_time is null");
			return (Criteria) this;
		}

		public Criteria andPauseTimeIsNotNull() {
			addCriterion("pause_time is not null");
			return (Criteria) this;
		}

		public Criteria andPauseTimeEqualTo(Date value) {
			addCriterionForJDBCDate("pause_time =", value, "pauseTime");
			return (Criteria) this;
		}

		public Criteria andPauseTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("pause_time <>", value, "pauseTime");
			return (Criteria) this;
		}

		public Criteria andPauseTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("pause_time >", value, "pauseTime");
			return (Criteria) this;
		}

		public Criteria andPauseTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("pause_time >=", value, "pauseTime");
			return (Criteria) this;
		}

		public Criteria andPauseTimeLessThan(Date value) {
			addCriterionForJDBCDate("pause_time <", value, "pauseTime");
			return (Criteria) this;
		}

		public Criteria andPauseTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("pause_time <=", value, "pauseTime");
			return (Criteria) this;
		}

		public Criteria andPauseTimeIn(List<Date> values) {
			addCriterionForJDBCDate("pause_time in", values, "pauseTime");
			return (Criteria) this;
		}

		public Criteria andPauseTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("pause_time not in", values, "pauseTime");
			return (Criteria) this;
		}

		public Criteria andPauseTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("pause_time between", value1, value2,
					"pauseTime");
			return (Criteria) this;
		}

		public Criteria andPauseTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("pause_time not between", value1, value2,
					"pauseTime");
			return (Criteria) this;
		}

		public Criteria andIsFixedIsNull() {
			addCriterion("is_fixed is null");
			return (Criteria) this;
		}

		public Criteria andIsFixedIsNotNull() {
			addCriterion("is_fixed is not null");
			return (Criteria) this;
		}

		public Criteria andIsFixedEqualTo(String value) {
			addCriterion("is_fixed =", value, "isFixed");
			return (Criteria) this;
		}

		public Criteria andIsFixedNotEqualTo(String value) {
			addCriterion("is_fixed <>", value, "isFixed");
			return (Criteria) this;
		}

		public Criteria andIsFixedGreaterThan(String value) {
			addCriterion("is_fixed >", value, "isFixed");
			return (Criteria) this;
		}

		public Criteria andIsFixedGreaterThanOrEqualTo(String value) {
			addCriterion("is_fixed >=", value, "isFixed");
			return (Criteria) this;
		}

		public Criteria andIsFixedLessThan(String value) {
			addCriterion("is_fixed <", value, "isFixed");
			return (Criteria) this;
		}

		public Criteria andIsFixedLessThanOrEqualTo(String value) {
			addCriterion("is_fixed <=", value, "isFixed");
			return (Criteria) this;
		}

		public Criteria andIsFixedLike(String value) {
			addCriterion("is_fixed like", value, "isFixed");
			return (Criteria) this;
		}

		public Criteria andIsFixedNotLike(String value) {
			addCriterion("is_fixed not like", value, "isFixed");
			return (Criteria) this;
		}

		public Criteria andIsFixedIn(List<String> values) {
			addCriterion("is_fixed in", values, "isFixed");
			return (Criteria) this;
		}

		public Criteria andIsFixedNotIn(List<String> values) {
			addCriterion("is_fixed not in", values, "isFixed");
			return (Criteria) this;
		}

		public Criteria andIsFixedBetween(String value1, String value2) {
			addCriterion("is_fixed between", value1, value2, "isFixed");
			return (Criteria) this;
		}

		public Criteria andIsFixedNotBetween(String value1, String value2) {
			addCriterion("is_fixed not between", value1, value2, "isFixed");
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