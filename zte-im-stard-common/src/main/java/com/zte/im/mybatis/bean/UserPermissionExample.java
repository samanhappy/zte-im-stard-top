package com.zte.im.mybatis.bean;

import java.util.ArrayList;
import java.util.List;

public class UserPermissionExample {
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public UserPermissionExample() {
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

		public Criteria andMemberIdIsNull() {
			addCriterion("member_id is null");
			return (Criteria) this;
		}

		public Criteria andMemberIdIsNotNull() {
			addCriterion("member_id is not null");
			return (Criteria) this;
		}

		public Criteria andMemberIdEqualTo(String value) {
			addCriterion("member_id =", value, "memberId");
			return (Criteria) this;
		}

		public Criteria andMemberIdNotEqualTo(String value) {
			addCriterion("member_id <>", value, "memberId");
			return (Criteria) this;
		}

		public Criteria andMemberIdGreaterThan(String value) {
			addCriterion("member_id >", value, "memberId");
			return (Criteria) this;
		}

		public Criteria andMemberIdGreaterThanOrEqualTo(String value) {
			addCriterion("member_id >=", value, "memberId");
			return (Criteria) this;
		}

		public Criteria andMemberIdLessThan(String value) {
			addCriterion("member_id <", value, "memberId");
			return (Criteria) this;
		}

		public Criteria andMemberIdLessThanOrEqualTo(String value) {
			addCriterion("member_id <=", value, "memberId");
			return (Criteria) this;
		}

		public Criteria andMemberIdLike(String value) {
			addCriterion("member_id like", value, "memberId");
			return (Criteria) this;
		}

		public Criteria andMemberIdNotLike(String value) {
			addCriterion("member_id not like", value, "memberId");
			return (Criteria) this;
		}

		public Criteria andMemberIdIn(List<String> values) {
			addCriterion("member_id in", values, "memberId");
			return (Criteria) this;
		}

		public Criteria andMemberIdNotIn(List<String> values) {
			addCriterion("member_id not in", values, "memberId");
			return (Criteria) this;
		}

		public Criteria andMemberIdBetween(String value1, String value2) {
			addCriterion("member_id between", value1, value2, "memberId");
			return (Criteria) this;
		}

		public Criteria andMemberIdNotBetween(String value1, String value2) {
			addCriterion("member_id not between", value1, value2, "memberId");
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

		public Criteria andPermTypeIsNull() {
			addCriterion("perm_type is null");
			return (Criteria) this;
		}

		public Criteria andPermTypeIsNotNull() {
			addCriterion("perm_type is not null");
			return (Criteria) this;
		}

		public Criteria andPermTypeEqualTo(Integer value) {
			addCriterion("perm_type =", value, "permType");
			return (Criteria) this;
		}

		public Criteria andPermTypeNotEqualTo(Integer value) {
			addCriterion("perm_type <>", value, "permType");
			return (Criteria) this;
		}

		public Criteria andPermTypeGreaterThan(Integer value) {
			addCriterion("perm_type >", value, "permType");
			return (Criteria) this;
		}

		public Criteria andPermTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("perm_type >=", value, "permType");
			return (Criteria) this;
		}

		public Criteria andPermTypeLessThan(Integer value) {
			addCriterion("perm_type <", value, "permType");
			return (Criteria) this;
		}

		public Criteria andPermTypeLessThanOrEqualTo(Integer value) {
			addCriterion("perm_type <=", value, "permType");
			return (Criteria) this;
		}

		public Criteria andPermTypeIn(List<Integer> values) {
			addCriterion("perm_type in", values, "permType");
			return (Criteria) this;
		}

		public Criteria andPermTypeNotIn(List<Integer> values) {
			addCriterion("perm_type not in", values, "permType");
			return (Criteria) this;
		}

		public Criteria andPermTypeBetween(Integer value1, Integer value2) {
			addCriterion("perm_type between", value1, value2, "permType");
			return (Criteria) this;
		}

		public Criteria andPermTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("perm_type not between", value1, value2, "permType");
			return (Criteria) this;
		}

		public Criteria andPermIdIsNull() {
			addCriterion("perm_id is null");
			return (Criteria) this;
		}

		public Criteria andPermIdIsNotNull() {
			addCriterion("perm_id is not null");
			return (Criteria) this;
		}

		public Criteria andPermIdEqualTo(String value) {
			addCriterion("perm_id =", value, "permId");
			return (Criteria) this;
		}

		public Criteria andPermIdNotEqualTo(String value) {
			addCriterion("perm_id <>", value, "permId");
			return (Criteria) this;
		}

		public Criteria andPermIdGreaterThan(String value) {
			addCriterion("perm_id >", value, "permId");
			return (Criteria) this;
		}

		public Criteria andPermIdGreaterThanOrEqualTo(String value) {
			addCriterion("perm_id >=", value, "permId");
			return (Criteria) this;
		}

		public Criteria andPermIdLessThan(String value) {
			addCriterion("perm_id <", value, "permId");
			return (Criteria) this;
		}

		public Criteria andPermIdLessThanOrEqualTo(String value) {
			addCriterion("perm_id <=", value, "permId");
			return (Criteria) this;
		}

		public Criteria andPermIdLike(String value) {
			addCriterion("perm_id like", value, "permId");
			return (Criteria) this;
		}

		public Criteria andPermIdNotLike(String value) {
			addCriterion("perm_id not like", value, "permId");
			return (Criteria) this;
		}

		public Criteria andPermIdIn(List<String> values) {
			addCriterion("perm_id in", values, "permId");
			return (Criteria) this;
		}

		public Criteria andPermIdNotIn(List<String> values) {
			addCriterion("perm_id not in", values, "permId");
			return (Criteria) this;
		}

		public Criteria andPermIdBetween(String value1, String value2) {
			addCriterion("perm_id between", value1, value2, "permId");
			return (Criteria) this;
		}

		public Criteria andPermIdNotBetween(String value1, String value2) {
			addCriterion("perm_id not between", value1, value2, "permId");
			return (Criteria) this;
		}

		public Criteria andPermNameIsNull() {
			addCriterion("perm_name is null");
			return (Criteria) this;
		}

		public Criteria andPermNameIsNotNull() {
			addCriterion("perm_name is not null");
			return (Criteria) this;
		}

		public Criteria andPermNameEqualTo(String value) {
			addCriterion("perm_name =", value, "permName");
			return (Criteria) this;
		}

		public Criteria andPermNameNotEqualTo(String value) {
			addCriterion("perm_name <>", value, "permName");
			return (Criteria) this;
		}

		public Criteria andPermNameGreaterThan(String value) {
			addCriterion("perm_name >", value, "permName");
			return (Criteria) this;
		}

		public Criteria andPermNameGreaterThanOrEqualTo(String value) {
			addCriterion("perm_name >=", value, "permName");
			return (Criteria) this;
		}

		public Criteria andPermNameLessThan(String value) {
			addCriterion("perm_name <", value, "permName");
			return (Criteria) this;
		}

		public Criteria andPermNameLessThanOrEqualTo(String value) {
			addCriterion("perm_name <=", value, "permName");
			return (Criteria) this;
		}

		public Criteria andPermNameLike(String value) {
			addCriterion("perm_name like", value, "permName");
			return (Criteria) this;
		}

		public Criteria andPermNameNotLike(String value) {
			addCriterion("perm_name not like", value, "permName");
			return (Criteria) this;
		}

		public Criteria andPermNameIn(List<String> values) {
			addCriterion("perm_name in", values, "permName");
			return (Criteria) this;
		}

		public Criteria andPermNameNotIn(List<String> values) {
			addCriterion("perm_name not in", values, "permName");
			return (Criteria) this;
		}

		public Criteria andPermNameBetween(String value1, String value2) {
			addCriterion("perm_name between", value1, value2, "permName");
			return (Criteria) this;
		}

		public Criteria andPermNameNotBetween(String value1, String value2) {
			addCriterion("perm_name not between", value1, value2, "permName");
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