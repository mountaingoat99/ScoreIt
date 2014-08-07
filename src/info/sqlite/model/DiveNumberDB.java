package info.sqlite.model;

public class DiveNumberDB {
    int _id;
    int _meetId;
    int _diverId;
    int _diveNumber;

    public DiveNumberDB(){}

    public DiveNumberDB(int _diveNumber){
        this._diveNumber = _diveNumber;
    }

    public DiveNumberDB(int _meetId, int _diverId, int _diveNumber){
        this._meetId = _meetId;
        this._diverId = _diverId;
        this._diveNumber = _diveNumber;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_meetId() {
        return _meetId;
    }

    public void set_meetId(int _meetId) {
        this._meetId = _meetId;
    }

    public int get_diverId() {
        return _diverId;
    }

    public void set_diverId(int _diverId) {
        this._diverId = _diverId;
    }

    public int get_diveNumber() {
        return _diveNumber;
    }

    public void set_diveNumber(int _diveNumber) {
        this._diveNumber = _diveNumber;
    }
}
