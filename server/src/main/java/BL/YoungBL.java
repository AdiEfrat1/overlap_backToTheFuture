package BL;

import DAL.YoungDAL;
import Models.Young;
import DB.YoungDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class YoungBL {

    private YoungDAL youngDAL = new YoungDAL();

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ArrayList<Young> getAllYoung() throws Exception {
        try {
            return this.youngDAL.getAllYoungs();
        } catch (Exception e) {
            throw new Exception("Error fetcing all youngs from DB");
        }
    }

    public Young getSpecific(int id) throws Exception {
        try {
            String documentString = this.youngDAL.getSpecific(id);

            return gson.fromJson(documentString, Young.class);
        } catch (Exception e) {
            throw new Exception("Error fetcing specific young { id: " + id + " } from DB");
        }
    }

    public void removeYoung(int id) throws Exception {
        try {
            this.youngDAL.removeYoung(id);
        } catch (Exception e) {
            throw new Exception("Error removing specific young { id: " + id + " } from DB");
        }
    }

    public void addYoung(Young young) throws Exception {
        try {
            this.youngDAL.addYoung(young);
        } catch (Exception e) {
            throw new Exception("Error add new young { id: " + young.id() + " } to DB");
        }
    }
}
