package com.esliceu.maze.dao;

import com.esliceu.maze.model.Score;
import com.esliceu.maze.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScoreDAOImplem implements ScoreDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void insertScore(User user, long gameTime, String comentary) {
        jdbcTemplate.update("insert into scores (userName, mapName, gameTime, comentary) values (?,?,?,?)"
                , user.getName(), user.getMapName(), gameTime, comentary);
    }

    @Override
    public List<Score> getAllScores() {
        return jdbcTemplate.query("select * from scores", new BeanPropertyRowMapper<>(Score.class));
    }
}
