package com.chris.utopia.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "ROLE")
public class Role implements Serializable {
	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField(columnName = "NAME")
	private String name;
	@DatabaseField(columnName = "DESCRIPTION")
	private String description;
	@DatabaseField(columnName = "USER_ID")
	private Integer userId;
	@DatabaseField(columnName = "CREATE_BY")
	private String createBy;
	@DatabaseField(columnName = "CREATE_AT")
	private String createAt;
	@DatabaseField(columnName = "UPDATE_BY")
	private String updateBy;
	@DatabaseField(columnName = "UPDATE_AT")
	private String updateAt;

	public Role() {}

	public Role(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Role(String name, String description, Integer userId, String userName, String dateTime) {
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.createBy = userName;
		this.createAt = dateTime;
		this.updateAt = dateTime;
		this.updateBy = userName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
}
