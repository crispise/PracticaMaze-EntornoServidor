package com.esliceu.maze.services;

import com.esliceu.maze.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoresService {
    @Autowired
    UserDAO userDAO;

    public String obtainScores(String username) {
        return "";
    }
}
