package com.example.model;
import cn.bmob.v3.BmobObject;
public class OurClass extends BmobObject {
	//ѧУ����
	private String Schoolname;
	//�༶����
	private  String classname;
	//�༶���
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
