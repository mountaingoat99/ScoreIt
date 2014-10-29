package info.sqlite.model;

public class DiveListDB {
    private int id;
    private int meetId;
    private int diverId;
    private int listFilled;
    private int noList;

    public DiveListDB(int meetId, int diverId, int listFilled, int noList){
        this.meetId = meetId;
        this.diverId = diverId;
        this.listFilled = listFilled;
        this.noList = noList;

    }

    public int getNoList() {
        return noList;
    }

    public void setNoList(int noList) {
        this.noList = noList;
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

    public int getListFilled() {
        return listFilled;
    }

    public void setListFilled(int listFilled) {
        this.listFilled = listFilled;
    }
}
