package BL;

import DAL.Young;
import DB.YoungDB;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class YoungBL {

    private YoungDB youngDB = new YoungDB();

    public ArrayList<Young> getAllYoung() throws Exception {
        try {
            return this.youngDB.youngs;
        } catch (Exception e) {
            throw new Exception("Error fetcing all youngs from DB");
        }
    }

    public Young getSpecific(int id) throws Exception {
        try {
            return this.youngDB.youngs.stream()
                    .filter((young) -> young.id() == id).findFirst()
                    .orElseThrow(NoSuchElementException::new);
        } catch (Exception e) {
            throw new Exception("Error fetcing specific young { id: " + id + " } from DB");
        }
    }

    public void removeYoung(int id) throws Exception {
        try {
            this.youngDB.youngs.removeIf((young) -> young.id() == id);
        } catch (Exception e) {
            throw new Exception("Error removing specific young { id: " + id + " } from DB");
        }
    }

    public void addYoung(Young young) throws Exception {
        try {
            this.youngDB.youngs.add(young);
        } catch (Exception e) {
            throw new Exception("Error add new young { id: " + young.id() + " } to DB");
        }
    }
}
