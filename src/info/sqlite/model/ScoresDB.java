package info.sqlite.model;

public class ScoresDB {
    private int id, score10;
    private Double score0, score05, score1, score15, score2, score25,
                    score3, score35, score4, score45, score5, score55,
                    score6, score65, score7, score75, score8, score85,
                    score9, score95;

    public ScoresDB(Double score0, Double score05, Double score1, Double score15,
                    Double score2, Double score25, Double score3, Double score35, Double score4,
                    Double score45, Double score5, Double score55, Double score6, Double score65,
                    Double score7, Double score75, Double score8, Double score85, Double score9,
                    Double score95, int score10){

        this.score0 = score0;
        this.score05 = score05;
        this.score1 = score1;
        this.score15 = score15;
        this.score2 = score2;
        this.score25 = score25;
        this.score3 = score3;
        this.score35 = score35;
        this.score4 = score4;
        this.score45 = score45;
        this.score5 = score5;
        this.score55 = score55;
        this.score6 = score6;

        this.score65 = score65;
        this.score7 = score7;
        this.score75 = score75;
        this.score8 = score8;
        this.score85 = score85;
        this.score9 = score9;
        this.score95 = score95;
        this.score10 = score10;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getScore0() {
        return score0;
    }

    public void setScore0(Double score0) {
        this.score0 = score0;
    }

    public Double getScore05() {
        return score05;
    }

    public void setScore05(Double score05) {
        this.score05 = score05;
    }

    public Double getScore1() {
        return score1;
    }

    public void setScore1(Double score1) {
        this.score1 = score1;
    }

    public Double getScore15() {
        return score15;
    }

    public void setScore15(Double score15) {
        this.score15 = score15;
    }

    public Double getScore2() {
        return score2;
    }

    public void setScore2(Double score2) {
        this.score2 = score2;
    }

    public Double getScore25() {
        return score25;
    }

    public void setScore25(Double score25) {
        this.score25 = score25;
    }

    public Double getScore3() {
        return score3;
    }

    public void setScore3(Double score3) {
        this.score3 = score3;
    }

    public Double getScore35() {
        return score35;
    }

    public void setScore35(Double score35) {
        this.score35 = score35;
    }

    public Double getScore4() {
        return score4;
    }

    public void setScore4(Double score4) {
        this.score4 = score4;
    }

    public Double getScore45() {
        return score45;
    }

    public void setScore45(Double score45) {
        this.score45 = score45;
    }

    public Double getScore5() {
        return score5;
    }

    public void setScore5(Double score5) {
        this.score5 = score5;
    }

    public Double getScore55() {
        return score55;
    }

    public void setScore55(Double score55) {
        this.score55 = score55;
    }

    public Double getScore6() {
        return score6;
    }

    public void setScore6(Double score6) {
        this.score6 = score6;
    }

    public Double getScore65() {
        return score65;
    }

    public void setScore65(Double score65) {
        this.score65 = score65;
    }

    public Double getScore7() {
        return score7;
    }

    public void setScore7(Double score7) {
        this.score7 = score7;
    }

    public Double getScore75() {
        return score75;
    }

    public void setScore75(Double score75) {
        this.score75 = score75;
    }

    public Double getScore8() {
        return score8;
    }

    public void setScore8(Double score8) {
        this.score8 = score8;
    }

    public Double getScore85() {
        return score85;
    }

    public void setScore85(Double score85) {
        this.score85 = score85;
    }

    public Double getScore9() {
        return score9;
    }

    public void setScore9(Double score9) {
        this.score9 = score9;
    }

    public Double getScore95() {
        return score95;
    }

    public void setScore95(Double score95) {
        this.score95 = score95;
    }

    public int getScore10() {
        return score10;
    }

    public void setScore10(int score10) {
        this.score10 = score10;
    }
}
