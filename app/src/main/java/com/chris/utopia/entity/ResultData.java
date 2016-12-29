package com.chris.utopia.entity;

public class ResultData {
	
	private int code;
	private String info;
	private UserData userData;
	
	public ResultData() {}
	
	public ResultData(int code, String info, UserData userData) {
		this.code = code;
		this.info = info;
		this.userData = userData;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public UserData getUserData() {
		return userData;
	}
	public void setUserData(UserData userData) {
		this.userData = userData;
	}
	
}
