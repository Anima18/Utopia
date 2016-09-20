package com.chris.utopia.entity;

public class MenuEntity {

	private int imageResourceId;
	
	private int imageSelectResourceId;
	
	private String name;
	
	private String code;
	
	private boolean isSelect;
	
	public MenuEntity(int id, int imageSelectResourceId, String name, String code) {
		this.imageResourceId = id;
		this.imageSelectResourceId = imageSelectResourceId;
		this.name = name;
		this.code = code;
	}
	
	public int getImageResourceId() {
		return imageResourceId;
	}
	public void setImageResourceId(int imageResourceId) {
		this.imageResourceId = imageResourceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public int getImageSelectResourceId() {
		return imageSelectResourceId;
	}

	public void setImageSelectResourceId(int imageSelectResourceId) {
		this.imageSelectResourceId = imageSelectResourceId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}	
}
