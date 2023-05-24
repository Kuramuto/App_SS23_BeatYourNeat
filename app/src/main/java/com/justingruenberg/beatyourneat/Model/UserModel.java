package com.justingruenberg.beatyourneat.Model;

public class UserModel {
    private String userName;
    private String password;
    private boolean isInitialized;

    public UserModel(String userName, String password, boolean isInitialized) {
        this.userName = userName;
        this.password = password;
        this.isInitialized = isInitialized;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }
}
