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
                        40,
                        "עדי שטיינר",
                        "להבים",
                        "051-1234567",
                        "בילויים",
                        "חדווא 2"));
        this.youngs.add(
                new Young(
                        9,
                        "עדי אפרת",
                        "זכרון יעקב",
                        "057-5445545",
                        "שינה",
                        "משחקי הרעב 1"));
        this.youngs.add(
                new Young(
                        32,
                        "ירדן כפטל",
                        "אנדפיינד",
                        "057-5445545",
                        "לבדוק",
                        "מדריך טיולים"));
        this.youngs.add(
                new Young(
                        8,
                        "רן בנימיני",
                        "מעלה אדומים",
                        "057-5445545",
                        "אנימה",
                        "אין ספר"));
        this.youngs.add(
                new Young(
                        2000006,
                        "מטר שרמן",
                        "רעות",
                        "057-5445545",
                        "מונו-פול",
                        "מילון כרך ב"));
    }
}
