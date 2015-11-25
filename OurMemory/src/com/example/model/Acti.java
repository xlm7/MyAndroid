package com.example.model;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
public class Acti extends BmobObject{
//班级ＩＤ号
private String classId;
//事件名称
private String name;
//事件图片
private BmobFile pic;
//事件简介
private String intro;
//事件发生事件
private String occurTime;
//事件记录者姓名
private String writer;
//事件是否删除的标记
//1表示存在 0表示删除
private int isexit;

public int getIsexit() {
	return isexit;
}
public void setIsexit(int isexit) {
	this.isexit = isexit;
}
public String getWriter() {
	return writer;
}
public void setWriter(String writer) {
	this.writer = writer;
}
public String getOccurTime() {
	return occurTime;
}
public void setOccurTime(String occurTime) {
	this.occurTime = occurTime;
}
public String getClassId() {
	return classId;
}
public void setClassId(String classId) {
	this.classId = classId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public BmobFile getPic() {
	return pic;
}
public void setPic(BmobFile pic) {
	this.pic = pic;
}
public String getIntro() {
	return intro;
}
public void setIntro(String intro) {
	this.intro = intro;
}
}
