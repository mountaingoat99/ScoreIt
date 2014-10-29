package info.sqlite.model;


public class QuickScoreDB {
    private int id;
    private String nameMeet;
    private Double dive1, dive2, dive3, dive4, dive5, dive6, dive7,
            dive8, dive9, dive10, dive11, totalScore;

    public QuickScoreDB(){}

    public QuickScoreDB(String nameMeet, Double dive1, Double dive2,
                        Double dive3, Double dive4, Double dive5, Double dive6, Double dive7, Double dive8,
                        Double dive9, Double dive10, Double dive11, Double totalScore){
        this.nameMeet = nameMeet;
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

    public String getNameMeet() {
        return nameMeet;
    }

    public String setNameMeet(String nameMeet) {
        return this.nameMeet = nameMeet;
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
}
