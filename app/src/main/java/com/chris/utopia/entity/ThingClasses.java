package com.chris.utopia.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "THING_CLASSES")
public class ThingClasses implements Serializable{
	@DatabaseField(columnName = "ID", id = true, unique = true)
	private String id;
	@DatabaseField(columnName = "NAME")
	private String name;
	@DatabaseField(columnName = "DESCRIPTION")
	private String description;
	@DatabaseField(columnName = "USER_ID")
	private String userId;
	@DatabaseField(columnName = "CREATE_BY")
	private String createBy;
	@DatabaseField(columnName = "CREATE_AT")
	private String createAt;
	@DatabaseField(columnName = "UPDATE_BY")
	private String updateBy;
	@DatabaseField(columnName = "UPDATE_AT")
	private String updateAt;

	public ThingClasses() {}

	public ThingClasses(String title, String description) {
		this.name = title;
		this.description = description;
	}

	public ThingClasses(String title, String description, String userId) {
		this.name = title;
		this.description = description;
		this.userId = userId;
	}

	public ThingClasses(String title, String description, String userId, String userName, String dateTime) {
		this.name = title;
		this.description = description;
		this.userId = userId;
		this.createBy = userName;
		this.updateBy = userName;
		this.createAt = dateTime;
		this.updateAt = dateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
