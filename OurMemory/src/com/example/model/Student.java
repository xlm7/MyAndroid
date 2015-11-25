package com.example.model;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
public class Student extends BmobUser {
	//用户性别
	 private boolean sex;
	 //用户籍贯
     private String address;
     //用户年龄
     private Integer age;
     //用户班级ID
     private String classId;
      //用户手机号
     private String phone;
     //用户头像
     private BmobFile icon;
     //用户简介
     private String intro;
     //用户ID
     private String StudentId;
     //用户角色
     //1表示管理员 0表示普通用户
     private int role;
     //标记用户是否存在
     //0表示已删除 1表示存在
     private int isexit;     
 	public int getIsexit() {
		return isexit;
	}
	public void setIsexit(int isexit) {
		this.isexit = isexit;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getStudentId() {
		return StudentId;
	}
	public void setStudentId(String studentId) {
		StudentId = studentId;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public BmobFile getIcon() {
		return icon;
	}
	public void setIcon(BmobFile icon) {
		this.icon = icon;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public Integer getAge() {
 		return age;
 	}
 	public void setAge(Integer age) {
 		this.age = age;
 	}	
	
}
