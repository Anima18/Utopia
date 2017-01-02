package com.chris.utopia.entity;

import java.util.List;

/**
 * Created by Admin on 2016/12/21.
 */

public class UserData {
    private User user;
    private List<Role> roleList;
    private List<ThingClasses> classesList;
    private List<Idea> ideaList;
    private List<Plan> planList;
    private List<Thing> thingList;
    private UserSyncRecord record;

    public UserData(User user, List<Role> roleList, List<ThingClasses> classesList, List<Idea> ideaList, List<Plan> planList, List<Thing> thingList) {
        this.user = user;
        this.roleList = roleList;
        this.classesList = classesList;
        this.ideaList = ideaList;
        this.planList = planList;
        this.thingList = thingList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<ThingClasses> getClassesList() {
        return classesList;
    }

    public void setClassesList(List<ThingClasses> classesList) {
        this.classesList = classesList;
    }

    public List<Idea> getIdeaList() {
        return ideaList;
    }

    public void setIdeaList(List<Idea> ideaList) {
        this.ideaList = ideaList;
    }

    public List<Plan> getPlanList() {
        return planList;
    }

    public void setPlanList(List<Plan> planList) {
        this.planList = planList;
    }

    public List<Thing> getThingList() {
        return thingList;
    }

    public void setThingList(List<Thing> thingList) {
        this.thingList = thingList;
    }

    public UserSyncRecord getRecord() {
        return record;
    }

    public void setRecord(UserSyncRecord record) {
        this.record = record;
    }
}
