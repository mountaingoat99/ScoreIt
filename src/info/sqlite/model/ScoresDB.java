package info.sqlite.model;

public class ScoresDB {
    private int id;
    private String digits;

    public ScoresDB(){}

    public ScoresDB(int id, String digits){
        this.id = id;
        this.digits = digits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDigits() {
        return digits;
    }

    public String setDigits(String digits) {
        this.digits = digits;
        return digits;
    }
}
