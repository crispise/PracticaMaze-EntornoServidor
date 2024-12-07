package com.esliceu.maze.services;


import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.model.User;
import com.esliceu.maze.utils.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class LoginService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    Encryptor encryptor;

    public User checkUser(String username, String password) throws NoSuchAlgorithmException {
        User u = userDAO.getUserByUsername(username);
        String encryptPassw = encryptor.encryptString(password);
        if (u != null) {
            if (u.getPassword().equals(encryptPassw)) {
                userDAO.updateState(u.getId(), "connect");
                return u;
            }
        }
        return null;
    }
}
