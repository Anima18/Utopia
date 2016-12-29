package com.chris.utopia.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "THING")
public class Thing implements Serializable {
	@DatabaseField(columnName = "ID", id = true, unique = true)
	private String id;
	@DatabaseField(columnName = "TITLE")
	private String title;
	@DatabaseField(columnName = "DESCRIPTION")
	private String description;
	@DatabaseField(columnName = "SAID_SOMETHING")
	private String saidSomething;
	@DatabaseField(columnName = "STATUS")
	private String status;
	@DatabaseField(columnName = "BEGIN_DATE")
	private String beginDate;
	@DatabaseField(columnName = "BEGIN_TIME")
	private String beginTime;
	@DatabaseField(columnName = "END_TIME")
	private String endTime;
	@DatabaseField(columnName = "IS_PROMPTING")
	private boolean isPrompting;
	@DatabaseField(columnName = "CLASSESS_ID")
	private String classessId;
	@DatabaseField(columnName = "THING_QUADRANT")
	private String thingQuadrant;
	@DatabaseField(columnName = "PLAN_ID")
	private String planId;
	@DatabaseField(columnName = "ROLE_ID")
	private String roleId;
	@DatabaseField(columnName = "USER_ID")
	private String userId;
	@DatabaseField(columnName = "PROGRESS")
	private String progress;
	@DatabaseField(columnName = "CREATE_BY")
	private String createBy;
	@DatabaseField(columnName = "CREATE_AT")
	private String createAt;
	@DatabaseField(columnName = "UPDATE_BY")
	private String updateBy;
	@DatabaseField(columnName = "UPDATE_AT")
	private String updateAt;
	@DatabaseField(columnName = "TYPE")
	private String type;
	@DatabaseField(columnName = "PERIOD")
	private String period;
	@DatabaseField(columnName = "WHAT_DAY")
	private String whatDay;
	@DatabaseField(columnName = "HOLD_ON_DAY")
	private Integer holdOnDay;
	@DatabaseField(columnName = "HABIT_STATUS")
	private String habitStatus;

	public Thing() {}

	public Thing(String title, String description, String status, String beginDate, String beginTime,
				 String classessId, String thingQuadrant, String planId, String roleId, String userId,
				 String progress, String type, String userName, String dateTime) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.beginDate = beginDate;
		this.beginTime = beginTime;
		this.classessId = classessId;
		this.thingQuadrant = thingQuadrant;
		this.planId = planId;
		this.roleId = roleId;
		this.userId = userId;
		this.progress = progress;
		this.type = type;
		this.createBy = userName;
		this.createAt = dateTime;
		this.updateAt = dateTime;
		this.updateBy = userName;
	}

	public Thing(String id, String title, String description, String saidSomething, String status, String beginDate, String beginTime,
					  String endTime, String isPrompting, String classessId, String thingQuadrant, String planId, String roleId, String userId,
					  String progress, String type, String period, String whatDay, String holdOnDay, String habitStatus, String updateAt) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.saidSomething = saidSomething;
		this.status = status;
		this.beginDate = beginDate;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.isPrompting = "1".equals(isPrompting) ? true : false;
		this.classessId = classessId;
		this.thingQuadrant = thingQuadrant;
		this.planId = planId;
		this.roleId = roleId;
		this.userId = userId;
		this.progress = progress;
		this.type = type;
		this.period = period;
		this.whatDay = whatDay;
		this.holdOnDay = holdOnDay != null ? Integer.parseInt(holdOnDay) : 0;
		this.habitStatus = habitStatus;
		this.updateAt = updateAt;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSaidSomething() {
		return saidSomething;
	}

	public void setSaidSomething(String saidSomething) {
		this.saidSomething = saidSomething;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public boolean isPrompting() {
		return isPrompting;
	}

	public void setIsPrompting(boolean isPrompting) {
		this.isPrompting = isPrompting;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public void setPrompting(boolean prompting) {
		isPrompting = prompting;
	}

	public String getClassessId() {
		return classessId;
	}

	public void setClassessId(String classessId) {
		this.classessId = classessId;
	}

	public String getThingQuadrant() {
		return thingQuadrant;
	}

	public void setThingQuadrant(String thingQuadrant) {
		this.thingQuadrant = thingQuadrant;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getWhatDay() {
		return whatDay;
	}

	public void setWhatDay(String whatDay) {
		this.whatDay = whatDay;
	}

	public Integer getHoldOnDay() {
		return holdOnDay;
	}

	public void setHoldOnDay(Integer holdOnDay) {
		this.holdOnDay = holdOnDay;
	}

	public String getHabitStatus() {
		return habitStatus;
	}

	public void setHabitStatus(String habitStatus) {
		this.habitStatus = habitStatus;
	}
}
