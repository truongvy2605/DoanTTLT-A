package com.com.flag.entity;

public class Users {
    String name, score;

    public Users() {
    }

    public Users(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public String name() {
        return name;
    }
    public String score() {
        return score;
    }

    public void highscore(String hs) {
        this.score = hs;
    }
}
