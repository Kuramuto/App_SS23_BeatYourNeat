package com.justingruenberg.beatyourneat.Model.DAO.DAOInterfaces;

import com.justingruenberg.beatyourneat.Model.DAO.DAOInterfaces.DAO;
import com.justingruenberg.beatyourneat.Model.UserModel;

public interface UserModelDAO extends DAO<UserModel> {

    boolean userExists(String username);

}
