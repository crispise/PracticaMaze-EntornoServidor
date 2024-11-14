package com.esliceu.maze.services;


import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.model.User;
import com.esliceu.maze.utils.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class LoginService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    Encryptor encryptor;

    public User checkUser(String username, String password) throws NoSuchAlgorithmException {
        User u = userDAO.findUserByUsername(username);
        String encryptPassw = encryptor.encryptString(password);

        if (u != null) {
            System.out.println("hay user");
            if (u.getPassword().equals(encryptPassw)) {
                System.out.println("el paswword concuerda");
                return u;
            }
        }
        return null;
    }
}
