package com.imooc.page.model;

import java.io.Serializable;
import java.util.Map;

public class Student implements Serializable {

	private static final long serialVersionUID = -4872794659303115708L;
	
	/** 学生id */
	private int id;
	
	/** 学生姓名 */
	private String stuName;
	
	/** 学生年龄*/
	private int age;
	
	/**	 学生性别  1表示男 2表示女*/
	private int gender;
	
	/** 学生住址 */
	private String address;
	
	public Student() {
		super();
	}

	public Student(int id, String stuName, int age, int gender, String address) {
		super();
		this.id = id;
		this.stuName = stuName;
		this.age = age;
		this.gender = gender;
		this.address = address;
	}

	public Student(Map<String, Object> map){
		this.id = ((Integer) map.get("id")).intValue();
		this.stuName = (String)map.get("stu_name");
		this.age = ((Integer) map.get("age")).intValue();
		this.gender = ((Integer) map.get("gender")).intValue();
		this.address = (String)map.get("address");
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + stuName + ", age=" + age + ", gender=" + gender + ", address=" + address
				+ "]";
	}
}
