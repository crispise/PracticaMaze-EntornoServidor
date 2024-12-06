package com.esliceu.maze.services;

import com.esliceu.maze.dao.ScoreDAO;
import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.model.Score;
import com.esliceu.maze.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoresService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    ScoreDAO scoreDAO;
    @Autowired
    ResetService resetService;


    public void updateScores(String username, String gameComent) {
        User user = userDAO.getUserByUsername(username);
        long gameTime = calculateFinalTime(user);
        scoreDAO.insertScore(user, gameTime, gameComent);
        resetService.resetGame(username);
    }
    private long calculateFinalTime(User user) {
        long currentTime = System.currentTimeMillis(); // Tiempo actual en milisegundos
        long elapsedTime = currentTime - user.getGameTime();
        return elapsedTime / 1000;
    }

    public List<Score> obtainScores(String username) {
        List<Score> usersScore = scoreDAO.getAllScores();
        return usersScore;
    }



}
