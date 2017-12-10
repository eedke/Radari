package mnmnm.radari;

import com.orm.SugarRecord;

public class SugarRadars extends SugarRecord {
    String date;
    String PU;
    String time1;
    String time2;
    String place;

    public SugarRadars(){

    }

    public SugarRadars(String date, String PU, String time1, String time2, String place){
        this.date = date;
        this.PU = PU;
        this.time1 = time1;
        this.time2 = time2;
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPU() {
        return PU;
    }

    public void setPU(String PU) {
        this.PU = PU;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
