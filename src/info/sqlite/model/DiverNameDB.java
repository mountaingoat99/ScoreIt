package info.sqlite.model;

public class DiverNameDB {
	int _id;
	String _name;
	String _age;
	String _grade;
	String _school;
	
	public DiverNameDB(){}
	
	public DiverNameDB(String name, String age,
			String grade, String school){
		//this.id = id;
		this._name = name;
		this._age = age;
		this._grade = grade;
		this._school = school;
	}

	public int getId() {
		return this._id;
	}

	public void setId(int id) {
		this._id = id;
	}

	public String getName() {
		return this._name;
	}

	public String setName(String name) {
		return this._name = name;
	}

	public String getAge() {
		return this._age;
	}

	public void setAge(String age) {
		this._age = age;
	}

	public String getGrade() {
		return this._grade;
	}

	public void setGrade(String grade) {
		this._grade = grade;
	}

	public String getSchool() {
		return this._school;
	}

	public void setSchool(String school) {
		this._school = school;
	}
}
