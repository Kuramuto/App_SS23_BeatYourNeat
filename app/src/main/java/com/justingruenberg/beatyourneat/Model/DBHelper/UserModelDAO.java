package com.justingruenberg.beatyourneat.Model.DBHelper;

import com.justingruenberg.beatyourneat.Model.UserModel;

public interface UserModelDAO extends DAO<UserModel> {

    boolean userExists(String username);

}
