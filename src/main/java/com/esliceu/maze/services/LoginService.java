package com.esliceu.maze.services;


import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    @Autowired
    UserDAO userDAO;

    public User checkUser(String username, String password) {
        User u = userDAO.findUserByUsername(username);
        if (u != null) {
            if (u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
}
