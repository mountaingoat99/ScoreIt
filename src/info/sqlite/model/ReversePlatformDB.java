package info.sqlite.model;


public class ReversePlatformDB {

    int id;
    int tenMeter;
    int sevenFiveMeter;
    int fiveMeter;
    String diveName;
    double tenA;
    double tenB;
    double tenC;
    double tenD;
    double sevenFiveA;
    double sevenFiveB;
    double sevenFiveC;
    double sevenFiveD;
    double fiveA;
    double fiveB;
    double fiveC;
    double fiveD;

    public ReversePlatformDB(){}

    public ReversePlatformDB(int id, int tenMeter, int sevenFiveMeter, int fiveMeter, String diveName,
                           double tenA, double tenB, double tenC, double tenD,
                           double sevenFiveA, double sevenFiveB, double sevenFiveC, double sevenFiveD,
                           double fiveA, double fiveB, double fiveC, double fiveD){
        this.id = id;
        this.tenMeter = tenMeter;
        this.sevenFiveMeter = sevenFiveMeter;
        this.fiveMeter = fiveMeter;
        this.diveName = diveName;
        this.tenA = tenA;
        this.tenB = tenB;
        this.tenC = tenC;
        this.tenD = tenD;
        this.sevenFiveA = sevenFiveA;
        this.sevenFiveB = sevenFiveB;
        this.sevenFiveC = sevenFiveC;
        this.sevenFiveD = sevenFiveD;
        this.fiveA = fiveA;
        this.fiveB = fiveB;
        this.fiveC = fiveC;
        this.fiveD = fiveD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTenMeter() {
        return tenMeter;
    }

    public void setTenMeter(int tenMeter) {
        this.tenMeter = tenMeter;
    }

    public int getSevenFiveMeter() {
        return sevenFiveMeter;
    }

    public void setSevenFiveMeter(int sevenFiveMeter) {
        this.sevenFiveMeter = sevenFiveMeter;
    }

    public int getFiveMeter() {
        return fiveMeter;
    }

    public void setFiveMeter(int fiveMeter) {
        this.fiveMeter = fiveMeter;
    }

    public String getDiveName() {
        return diveName;
    }

    public void setDiveName(String diveName) {
        this.diveName = diveName;
    }

    public double getTenA() {
        return tenA;
    }

    public void setTenA(double tenA) {
        this.tenA = tenA;
    }

    public double getTenB() {
        return tenB;
    }

    public void setTenB(double tenB) {
        this.tenB = tenB;
    }

    public double getTenC() {
        return tenC;
    }

    public void setTenC(double tenC) {
        this.tenC = tenC;
    }

    public double getTenD() {
        return tenD;
    }

    public void setTenD(double tenD) {
        this.tenD = tenD;
    }

    public double getSevenFiveA() {
        return sevenFiveA;
    }

    public void setSevenFiveA(double sevenFiveA) {
        this.sevenFiveA = sevenFiveA;
    }

    public double getSevenFiveB() {
        return sevenFiveB;
    }

    public void setSevenFiveB(double sevenFiveB) {
        this.sevenFiveB = sevenFiveB;
    }

    public double getSevenFiveC() {
        return sevenFiveC;
    }

    public void setSevenFiveC(double sevenFiveC) {
        this.sevenFiveC = sevenFiveC;
    }

    public double getSevenFiveD() {
        return sevenFiveD;
    }

    public void setSevenFiveD(double sevenFiveD) {
        this.sevenFiveD = sevenFiveD;
    }

    public double getFiveA() {
        return fiveA;
    }

    public void setFiveA(double fiveA) {
        this.fiveA = fiveA;
    }

    public double getFiveB() {
        return fiveB;
    }

    public void setFiveB(double fiveB) {
        this.fiveB = fiveB;
    }

    public double getFiveC() {
        return fiveC;
    }

    public void setFiveC(double fiveC) {
        this.fiveC = fiveC;
    }

    public double getFiveD() {
        return fiveD;
    }

    public void setFiveD(double fiveD) {
        this.fiveD = fiveD;
    }
}
