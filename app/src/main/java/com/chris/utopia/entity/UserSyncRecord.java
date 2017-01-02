package com.chris.utopia.entity;

public class UserSyncRecord {

	private Integer id;
	private String userId;
	private String deviceId;
	private String syncDate;
	
	public UserSyncRecord() {}

	public UserSyncRecord(String userId, String deviceId, String syncDate) {
		this.userId = userId;
		this.deviceId = deviceId;
		this.syncDate = syncDate;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getSyncDate() {
		return syncDate;
	}
	public void setSyncDate(String syncDate) {
		this.syncDate = syncDate;
	}
}
