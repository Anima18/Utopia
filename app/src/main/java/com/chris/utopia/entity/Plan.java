package com.chris.utopia.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

@DatabaseTable(tableName = "PLAN")
public class Plan implements Serializable {
	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField(columnName = "ROLE_ID")
	private Integer roleId;
	@DatabaseField(columnName = "NAME")
	private String name;
	@DatabaseField(columnName = "DESCRIPTION")
	private String description;
	@DatabaseField(columnName = "PROGRESS")
	private String progress;
	@DatabaseField(columnName = "USER_ID")
	private Integer userId;
	@DatabaseField(columnName = "THING_CLASSES_ID")
	private Integer thingClassesId;
	@DatabaseField(columnName = "THING_QUADRANT")
	private String thingQuadrant;
	@DatabaseField(columnName = "BEGIN_DATE")
	private String beginDate;
	@DatabaseField(columnName = "END_DATE")
	private String endDate;
	@DatabaseField(columnName = "CREATE_BY")
	private String createBy;
	@DatabaseField(columnName = "CREATE_AT")
	private String createAt;
	@DatabaseField(columnName = "UPDATE_BY")
	private String updateBy;
	@DatabaseField(columnName = "UPDATE_AT")
	private String updateAt;
	@DatabaseField(columnName = "STATUS")
	private String status;

	private List<Thing> thingList;

	public Plan() {}

	public Plan(int roleId, String name, String description, String progress, int userId,
				int thingClassesId, String thingQuadrant, String status, String userName, String dateTime ) {
		this.roleId = roleId;
		this.name = name;
		this.description = description;
		this.progress = progress;
		this.userId = userId;
		this.thingClassesId = thingClassesId;
		this.thingQuadrant = thingQuadrant;
		this.status = status;
		this.createBy = userName;
		this.createAt = dateTime;
		this.updateAt = dateTime;
		this.updateBy = userName;
	}

	public Plan(String id, String roleId, String name, String description, String progress, String userId,
				String thingClassesId, String thingQuadrant, String beginDate, String endDate, String status ) {
		this.id = Integer.parseInt(id);
		this.roleId = Integer.parseInt(roleId);
		this.name = name;
		this.description = description;
		this.progress = progress;
		this.userId = Integer.parseInt(userId);
		this.thingClassesId = Integer.parseInt(thingClassesId);
		this.thingQuadrant = thingQuadrant;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getThingClassesId() {
		return thingClassesId;
	}

	public void setThingClassesId(Integer thingClassesId) {
		this.thingClassesId = thingClassesId;
	}

	public String getThingQuadrant() {
		return thingQuadrant;
	}

	public void setThingQuadrant(String thingQuadrant) {
		this.thingQuadrant = thingQuadrant;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Thing> getThingList() {
		return thingList;
	}

	public void setThingList(List<Thing> thingList) {
		this.thingList = thingList;
	}
}
