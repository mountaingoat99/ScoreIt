package info.sqlite.model;

public class ResultsDB {
	private int id;
	private int meetId;
	private int diverId;
	private Double dive1, dive2, dive3, dive4, dive5, dive6, dive7,
			dive8, dive9, dive10, dive11, totalScore;

    public ResultsDB(){
		this(1, 2, 3, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
	}
	
	public ResultsDB(int id, int meetId, int diverId){
		this.id = id;
		this.meetId = meetId;
		this.diverId = diverId;
	}
	
	public ResultsDB(int id, int meetId, int diverId, Double dive1, Double dive2,
                     Double dive3, Double dive4, Double dive5, Double dive6, Double totalScore){
		this.id = id;
		this.meetId = meetId;
		this.diverId = diverId;
		this.dive1 = dive1;
		this.dive2 = dive2;
		this.dive3 = dive3;
		this.dive4 = dive4;
		this.dive5 = dive5;
		this.dive6 = dive6;
		this.totalScore = totalScore;
	}
	
	public ResultsDB(int id, int meetId, int diverId, Double dive1, Double dive2,
                     Double dive3, Double dive4, Double dive5, Double dive6, Double dive7, Double dive8,
                     Double dive9, Double dive10, Double dive11, Double totalScore){
		this.id = id;
		this.meetId = meetId;
		this.diverId = diverId;
		this.dive1 = dive1;
		this.dive2 = dive2;
		this.dive3 = dive3;
		this.dive4 = dive4;
		this.dive5 = dive5;
		this.dive6 = dive6;
		this.dive7 = dive7;
		this.dive8 = dive8;
		this.dive9 = dive9;
		this.dive10 = dive10;
		this.dive11 = dive11;
		this.totalScore = totalScore;
	}

	public ResultsDB(int meetid, int diverid, Double dive1, Double dive2,
                     Double dive3, Double dive4, Double dive5, Double dive6, Double dive7, Double dive8,
                     Double dive9, Double dive10, Double dive11, Double totalScore) {
		
		this.meetId = meetid;
		this.diverId = diverid;
		this.dive1 = dive1;
		this.dive2 = dive2;
		this.dive3 = dive3;
		this.dive4 = dive4;
		this.dive5 = dive5;
		this.dive6 = dive6;
		this.dive7 = dive7;
		this.dive8 = dive8;
		this.dive9 = dive9;
		this.dive10 = dive10;
		this.dive11 = dive11;
		this.totalScore = totalScore;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMeetId() {
		return meetId;
	}

	public void setMeetId(int meetId) {
		this.meetId = meetId;
	}

	public int getDiverId() {
		return diverId;
	}

	public void setDiverId(int diverId) {
		this.diverId = diverId;
	}

	public Double getDive1() {
		return dive1;
	}

	public void setDive1(Double dive1) {
		this.dive1 = dive1;
	}

	public Double getDive2() {
		return dive2;
	}

	public void setDive2(Double dive2) {
		this.dive2 = dive2;
	}

	public Double getDive3() {
		return dive3;
	}

	public void setDive3(Double dive3) {
		this.dive3 = dive3;
	}

	public Double getDive4() {
		return dive4;
	}

	public void setDive4(Double dive4) {
		this.dive4 = dive4;
	}

	public Double getDive5() {
		return dive5;
	}

	public void setDive5(Double dive5) {
		this.dive5 = dive5;
	}

	public Double getDive6() {
		return dive6;
	}

	public void setDive6(Double dive6) {
		this.dive6 = dive6;
	}

	public Double getDive7() {
		return dive7;
	}

	public void setDive7(Double dive7) {
		this.dive7 = dive7;
	}

	public Double getDive8() {
		return dive8;
	}

	public void setDive8(Double dive8) {
		this.dive8 = dive8;
	}

	public Double getDive9() {
		return dive9;
	}

	public void setDive9(Double dive9) {
		this.dive9 = dive9;
	}

	public Double getDive10() {
		return dive10;
	}

	public void setDive10(Double dive10) {
		this.dive10 = dive10;
	}

	public Double getDive11() {
		return dive11;
	}

	public void setDive11(Double dive11) {
		this.dive11 = dive11;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}
	
	public Double calcScoreTotal(Double s1, Double s2, Double s3, Double s4,
                                 Double s5, Double s6, Double s7, Double s8, Double s9,
                                 Double s10, Double s11){

        return s1 + s2 + s3 + s4 + s5 + s6 + s7 + s8 + s9 + s10 + s11;
	}
}

	
	
	
