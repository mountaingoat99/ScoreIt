package info.sqlite.model;

public class TypeDB {

    private int id;
    private int meetId;
    private int diverId;
    private int type;

    public TypeDB(){}

    public TypeDB(int id, int meetId, int diverId, int type){
        this.id = id;
        this.meetId = meetId;
        this.diverId = diverId;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
