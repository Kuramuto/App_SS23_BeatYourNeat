package com.justingruenberg.beatyourneat.Model;

public class ProfileModel {

    private String gender;
    private int height;
    private String birthdate;
    private double weight;
    private String userName;

    public ProfileModel(String gender, int height, String birthdate, double weight, String userName) {
        this.gender = gender;
        this.height = height;
        this.birthdate = birthdate;
        this.weight = weight;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
