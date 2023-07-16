package BL;

import DAL.Young;
import DAL.YoungDTO;
import DB.YoungDB;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class YoungBL {

    private YoungDB youngDB = new YoungDB();

    public ArrayList<Young> getAllYoung() {
        return this.youngDB.youngs;
    }

    public Young getSpecific(int id) {
        return this.youngDB.youngs.stream()
                .filter((young) -> young.id() == id).findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public void removeYoung(int id) {
        this.youngDB.youngs.removeIf((young) -> young.id() == id);
    }

    public void addYoung(Young young) {
        this.youngDB.youngs.add(young);
    }
}
