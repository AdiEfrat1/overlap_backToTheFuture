package DAL;

public class YoungDTO {

    private int id;
    private String name;
    private String city;
    private String phone;

    public YoungDTO(int id, String name, String city, String phone) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.phone = phone;
    }

    public int id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }


    public String city() {
        return this.city;
    }

    public String phone() {
        return this.phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
