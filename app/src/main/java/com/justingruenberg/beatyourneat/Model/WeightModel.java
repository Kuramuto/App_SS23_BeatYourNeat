package com.justingruenberg.beatyourneat.Model;

public class WeightModel {

    private String date;
    private double weight;
    private UserModel userModel;

    public WeightModel(String date, double weight, UserModel userModel) {
        this.date = date;
        this.weight = weight;
        this.userModel = userModel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
