package info.sqlite.model;

public class DiveNumberDB {
    int _id;
    int _meetId;
    int _diverId;
    int _diveNumber;
    double _dive_type;

    public DiveNumberDB(){}

    public DiveNumberDB(int _diveNumber){
        this._diveNumber = _diveNumber;
    }

    public DiveNumberDB(int _meetId, int _diverId, int _diveNumber, double _dive_type){
        this._meetId = _meetId;
        this._diverId = _diverId;
        this._diveNumber = _diveNumber;
        this._dive_type = _dive_type;
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

    public double get_dive_type() {
        return _dive_type;
    }

    public void set_dive_type(double _dive_type) {
        this._dive_type = _dive_type;
    }
}
