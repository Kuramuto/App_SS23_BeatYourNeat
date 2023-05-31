package com.justingruenberg.beatyourneat.Model;

public class UserModel {
    private String userName;
    private String password;
    private ProfileModel userProfile;

    public UserModel(String userName, String password, ProfileModel userProfile) {
        this.userName = userName;
        this.password = password;
        this.userProfile = userProfile;
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


    public ProfileModel getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(ProfileModel userProfile) {
        this.userProfile = userProfile;
    }
}
