package com.com.flag;

public class Flag {
    int id;
    String name;
    String image;

    public Flag() {
        
    }

    public Flag (int id, String name, String image) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return name;
    }

    public void setImage(String image) {
        this.name = image;
    }
}
