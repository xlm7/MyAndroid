package com.example.model;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
public class Student extends BmobUser {
	//�û��Ա�
	 private boolean sex;
	 //�û�����
     private String address;
     //�û�����
     private Integer age;
     //�û��༶ID
     private String classId;
      //�û��ֻ���
     private String phone;
     //�û�ͷ��
     private BmobFile icon;
     //�û����
     private String intro;
     //�û�ID
     private String StudentId;
     //�û���ɫ
     //1��ʾ����Ա 0��ʾ��ͨ�û�
     private int role;
     //����û��Ƿ����
     //0��ʾ��ɾ�� 1��ʾ����
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
