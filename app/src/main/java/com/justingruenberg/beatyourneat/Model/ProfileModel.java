package com.justingruenberg.beatyourneat.Model;

public class ProfileModel {

    private String gender, birthdate;
    private int height;
    private double weight;

    private UserModel user;

    public ProfileModel(String gender, int height, String birthdate, double weight, UserModel user) {
        this.gender = gender;
        this.height = height;
        this.birthdate = birthdate;
        this.weight = weight;
        this.user = user;

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
