package com.chris.utopia.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Chris on 2016/1/17.
 */
@DatabaseTable(tableName = "IDEA")
public class Idea implements Serializable {
    @DatabaseField(columnName = "ID", id = true, unique = true)
    private String id;
    @DatabaseField(columnName = "NAME")
    private String name;
    @DatabaseField(columnName = "DESCRIPTION")
    private String description;
    @DatabaseField(columnName = "USER_ID")
    private String userId;
    @DatabaseField(columnName = "THING_CLASSES_ID")
    private String thingClassesId;
    @DatabaseField(columnName = "REMIND_TIME")
    private String remindTime;
    @DatabaseField(columnName = "BACKGROUND")
    private String background;
    @DatabaseField(columnName = "CREATE_BY")
    private String createBy;
    @DatabaseField(columnName = "CREATE_AT")
    private String createAt;
    @DatabaseField(columnName = "UPDATE_BY")
    private String updateBy;
    @DatabaseField(columnName = "UPDATE_AT")
    private String updateAt;

    public Idea() {}

    public Idea(String name, String description, String userId, String thingClassesId, String remindTime, String background, String userName, String dateTime) {
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.thingClassesId = thingClassesId;
        this.remindTime = remindTime;
        this.background = background;
        this.createBy = userName;
        this.createAt = dateTime;
        this.updateAt = dateTime;
        this.updateBy = userName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getThingClassesId() {
        return thingClassesId;
    }

    public void setThingClassesId(String thingClassesId) {
        this.thingClassesId = thingClassesId;
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

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
