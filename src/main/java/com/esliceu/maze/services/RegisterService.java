package com.esliceu.maze.services;

import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
 @Autowired
    UserDAO userDAO;
 public void registerUser (String name, String username, String password){
     User user = new User();
     user.setName(name);
     user.setUsername(username);
     user.setPassword(password);
     userDAO.saveUser(user);
 }
}
