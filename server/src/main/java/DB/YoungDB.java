package DB;

import DAL.Young;

import java.util.ArrayList;

public class YoungDB {

    public ArrayList<Young> youngs;

    public YoungDB() {
        this.youngs = new ArrayList<>();

        youngs.add(
                new Young(
                        1,
                        "חבר פרבר",
                        "פני חבר",
                        "058-5675444",
                        "משחקי מחשב",
                        "הארי פוטר"));
        youngs.add(
                new Young(
                        46,
                        "עדי שטיינר",
                        "להבים",
                        "051-1234567",
                        "בילויים",
                        "חדווא 2"));
        youngs.add(
                new Young(
                        99,
                        "סאני סימן-טוב",
                        "חולון",
                        "012-1234567",
                        "טניס",
                        "תלמוד בבלי"));
        youngs.add(
                new Young(
                        9,
                        "עדי אפרת",
                        "זכרון יעקב",
                        "057-5445545",
                        "שינה",
                        "משחקי הרעב 1"));
    }
}
