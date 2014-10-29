package info.sqlite.model;

public class JudgeScoresDB {
    private int id;
    private int meetId;
    private int diverId;
    private int diveNumber;
    public String diveCategory, diveTypeName, divePosition, failed;
    private double totalScore, score1, score2, score3, score4, score5, score6, score7, multiplier;

    public JudgeScoresDB(){
        this(2, 3, 4, "s1", "s2", "s3", "f", 6.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 0.0);
    }

    public JudgeScoresDB(int meetId, int diverId){
        this.meetId = meetId;
        this.diverId = diverId;
    }

    public JudgeScoresDB(int meetId, int diverId, int diveNumber, String category, String typeName, String divePosition,
                         String failed, double totalScore, double score1, double score2, double score3, double multiplier){
        this.meetId = meetId;
        this.diverId = diverId;
        this.diveNumber = diveNumber;
        this.diveCategory = category;
        this.diveTypeName = typeName;
        this.divePosition = divePosition;
        this.failed = failed;
        this.totalScore = totalScore;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.multiplier = multiplier;
    }

    public JudgeScoresDB(int meetId, int diverId, int diveNumber, String category, String typeName, String divePosition,
                         String failed, double totalScore, double score1, double score2, double score3, double score4, double score5, double multiplier){
        this.meetId = meetId;
        this.diverId = diverId;
        this.diveNumber = diveNumber;
        this.diveCategory = category;
        this.diveTypeName = typeName;
        this.divePosition = divePosition;
        this.failed = failed;
        this.totalScore = totalScore;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.score4 = score4;
        this.score5 = score5;
        this.multiplier = multiplier;
    }

    public JudgeScoresDB(int meetId, int diverId, int diveNumber, String category, String typeName, String divePosition,
                         String failed, double totalScore, double score1, double score2, double score3, double score4,
                         double score5, double score6, double score7, double multiplier){
        this.meetId = meetId;
        this.diverId = diverId;
        this.diveNumber = diveNumber;
        this.diveCategory = category;
        this.diveTypeName = typeName;
        this.divePosition = divePosition;
        this.failed = failed;
        this.totalScore = totalScore;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.score4 = score4;
        this.score5 = score5;
        this.score6 = score6;
        this.score7 = score7;
        this.multiplier = multiplier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeetId() { return meetId; }

    public void setMeetId(int meetId) {
        this.meetId = meetId;
    }

    public int getDiverId() {
        return diverId;
    }

    public void setDiverId(int diverId) {
        this.diverId = diverId;
    }

    public int getDiveNumber() {
        return diveNumber;
    }

    public void setDiveNumber(int diveNumber) {
        this.diveNumber = diveNumber;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getScore1() {
        return score1;
    }

    public void setScore1(double score1) {
        this.score1 = score1;
    }

    public double getScore2() {
        return score2;
    }

    public void setScore2(double score2) {
        this.score2 = score2;
    }

    public double getScore3() {
        return score3;
    }

    public void setScore3(double score3) {
        this.score3 = score3;
    }

    public double getScore4() {
        return score4;
    }

    public void setScore4(double score4) {
        this.score4 = score4;
    }

    public double getScore5() {
        return score5;
    }

    public void setScore5(double score5) {
        this.score5 = score5;
    }

    public double getScore6() {
        return score6;
    }

    public void setScore6(double score6) {
        this.score6 = score6;
    }

    public double getScore7() {
        return score7;
    }

    public void setScore7(double score7) {
        this.score7 = score7;
    }

    public double getMultiplier() { return multiplier; }

    public void setMultiplier(double multiplier) { this.multiplier = multiplier; }

    public String getDiveCategory() { return diveCategory; }

    public String setDiveCategory(String diveCategory) {  return this.diveCategory = diveCategory; }

    public String getDiveTypeName() { return diveTypeName; }

    public String setDiveTypeName(String diveTypeName) { return this.diveTypeName = diveTypeName; }

    public String getFailed() { return failed; }

    public String setFailed(String failed) { return this.failed = failed; }

    public String getDivePosition() { return divePosition; }

    public String setDivePosition(String divePosition) { return this.divePosition = divePosition; }

}
