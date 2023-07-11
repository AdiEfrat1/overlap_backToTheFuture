package DB;

import DAL.Young;

import java.util.ArrayList;

public class YoungDB {

    public ArrayList<Young> youngs;

    public YoungDB() {
        this.youngs = new ArrayList<>();

        this.youngs.add(
                new Young(
                        1,
                        "חבר פרבר",
                        "פני חבר",
                        "058-5675444",
                        "משחקי מחשב",
                        "הארי פוטר"));
        this.youngs.add(
                new Young(
                        46,
                        "עדי שטיינר",
                        "להבים",
                        "051-1234567",
                        "בילויים",
                        "חדווא 2"));
        this.youngs.add(
                new Young(
                        99,
                        "סאני סימן-טוב",
                        "חולון",
                        "012-1234567",
                        "טניס",
                        "תלמוד בבלי"));
        this.youngs.add(
                new Young(
                        9,
                        "עדי אפרת",
                        "זכרון יעקב",
                        "057-5445545",
                        "שינה",
                        "משחקי הרעב 1"));
    }
}
