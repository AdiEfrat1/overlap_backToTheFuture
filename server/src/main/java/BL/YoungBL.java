package BL;

import DAL.Young;
import DB.YoungDB;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class YoungBL {

    private YoungDB youngDB = new YoungDB();

    public ArrayList<Young> getAllYoung() {
        return this.youngDB.youngs;
    }

    public Young getSpecificFullDetails(int id) {
        return this.youngDB.youngs.stream()
                .filter((young) -> young.id() == id).findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
