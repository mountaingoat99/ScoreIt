package info.sqlite.model;

public class MeetDB {
	int id;
	String meetName;
	String school;
	String city;
	String state;
	String meetDate;
	int judges;
	
	public MeetDB(){}

	public MeetDB(String meetName, String school, String city,
			String state, String meetDate, int judges) {
		this.meetName = meetName;
		this.school = school;
		this.city = city;
		this.state = state;
		this.meetDate = meetDate;
		this.judges = judges;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMeetName() {
		return meetName;
	}

	public String setMeetName(String meetName) {
		return this.meetName = meetName;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMeetDate() {
		return meetDate;
	}

	public void setMeetDate(String meetDate) {
		this.meetDate = meetDate;
	}

	public int getJudges() {
		return judges;
	}

	public void setJudges(int judges) {
		this.judges = judges;
	}
}
