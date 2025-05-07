package com.example.lostandfound;

import java.io.Serializable;

public class AdvertModel implements Serializable {
    private int id;
    private String type;
    private String name;
    private String phone;
    private String description;
    private String date;
    private String location;

    public AdvertModel(int id, String type, String name, String phone, String description, String date, String location) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
    }

    // Getters
    public int getId() { return id; }
    public String getType() { return type; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
}
