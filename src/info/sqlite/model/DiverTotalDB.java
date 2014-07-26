package info.sqlite.model;

public class DiverTotalDB {
    int _id;
    int _diveTotal;

    public DiverTotalDB(){}

    public DiverTotalDB(int diveTotal){
        this._diveTotal = diveTotal;
    }

    public int get_diveTotal() {
        return _diveTotal;
    }

    public void set_diveTotal(int _diveTotal) {
        this._diveTotal = _diveTotal;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
