package com.example.model;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
public class Acti extends BmobObject{
//�༶�ɣĺ�
private String classId;
//�¼�����
private String name;
//�¼�ͼƬ
private BmobFile pic;
//�¼����
private String intro;
//�¼������¼�
private String occurTime;
//�¼���¼������
private String writer;
//�¼��Ƿ�ɾ���ı��
//1��ʾ���� 0��ʾɾ��
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
