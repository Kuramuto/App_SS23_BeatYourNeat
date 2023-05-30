package com.justingruenberg.beatyourneat.Model;

public class UserManager {

    private static UserManager instance;
    private UserModel currentUser;

    private UserManager(){

    }

    public static UserManager getInstance(){

        if(instance == null){
            return instance = new UserManager();
        }else{
            return instance;
        }
    }

    public void setUser(UserModel user){
        currentUser = user;
    }
    public UserModel getCurrentUser(){
        return currentUser;
    }
}
