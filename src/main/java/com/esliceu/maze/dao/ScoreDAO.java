package com.esliceu.maze.dao;

import com.esliceu.maze.model.Score;
import com.esliceu.maze.model.User;

import java.util.List;

public interface ScoreDAO {
    void insertScore(User user, long gameTime, String comentary);

    List<Score> getAllScores();
}
