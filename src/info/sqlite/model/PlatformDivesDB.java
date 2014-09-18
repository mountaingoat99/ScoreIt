package info.sqlite.model;


public class PlatformDivesDB {

    int id;
    String diveName;

    public PlatformDivesDB(){}

    public PlatformDivesDB(int id, String diveName){
        this.id = id;
        this.diveName = diveName;
    }

    public int getId() {
        return id;
    }

    public String getDiveName() {
        return diveName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String setDiveName(String diveName) {
        return this.diveName = diveName;
    }

}

