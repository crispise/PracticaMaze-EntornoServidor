package com.esliceu.maze.dao;

import com.esliceu.maze.model.User;

import java.util.List;

public interface UserDAO {
    User findUserByUsername(String username);
    void saveUser(User user);
}
