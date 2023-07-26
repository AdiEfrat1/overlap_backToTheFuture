package Models;

import com.google.gson.annotations.SerializedName;

public class Young {

    @SerializedName("_id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("city")
    private String city;
    @SerializedName("phone")
    private String phone;
    @SerializedName("hobby")
    private String hobby;

    @SerializedName("book")
    private String book;

    public Young(int id, String name, String city, String phone, String hobby, String book) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.phone = phone;
        this.hobby = hobby;
        this.book = book;
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

    public String hobby() {
        return this.hobby;
    }

    public String book() {
        return this.book;
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

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setBook(String book) {
        this.book = book;
    }
}
