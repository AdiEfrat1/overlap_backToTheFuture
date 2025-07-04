package BL;

import DAL.YoungDAL;
import Models.Young;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class YoungBL {

    private YoungDAL youngDAL = new YoungDAL();

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Young[] getAllYoung() throws Exception {
        try {
            return this.youngDAL.getAll();
        } catch (Exception e) {
            throw new Exception("Error fetcing all youngs from DB");
        }
    }

    public Young getYoungById(int id) throws Exception {
        try {
            return this.youngDAL.getById(id);
        } catch (Exception e) {
            throw new Exception("Error fetcing specific young { id: " + id + " } from DB");
        }
    }

    public void removeYoungById(int id) throws Exception {
        try {
            this.youngDAL.removeById(id);
        } catch (Exception e) {
            throw new Exception("Error removing specific young { id: " + id + " } from DB");
        }
    }

    public void addYoung(Young young) throws Exception {
        try {
            this.youngDAL.add(young);
        } catch (Exception e) {
            throw new Exception("Error add new young { id: " + young.id() + " } to DB");
        }
    }
}
