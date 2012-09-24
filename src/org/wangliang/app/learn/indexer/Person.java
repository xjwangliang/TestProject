package org.wangliang.app.learn.indexer;

public class Person {

	//联系人ID
	private int id;
	//联系人姓名
	private String name;
	//联系人电话号码
	private String phoneNum;
	//联系人是否被选择
	private boolean isSelected;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public boolean isSelected() {
		return isSelected;
	}
}
