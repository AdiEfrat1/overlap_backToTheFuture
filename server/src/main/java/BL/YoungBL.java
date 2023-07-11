package BL;

import DAL.Young;
import DB.YoungDB;

import java.util.ArrayList;

public class YoungBL {

    private YoungDB youngDB = new YoungDB();

    public ArrayList<Young> getAllYoung() {
        return this.youngDB.youngs;
    }
}
