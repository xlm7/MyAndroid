package com.example.model;
import cn.bmob.v3.BmobObject;
public class OurClass extends BmobObject {
	//学校名称
	private String Schoolname;
	//班级名称
	private  String classname;
	//班级简介
	private String classintro;
	public String getSchoolname() {
		return Schoolname;
	}
	public void setSchoolname(String schoolname) {
		Schoolname = schoolname;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getClassintro() {
		return classintro;
	}
	public void setClassintro(String classintro) {
		this.classintro = classintro;
	}
}
