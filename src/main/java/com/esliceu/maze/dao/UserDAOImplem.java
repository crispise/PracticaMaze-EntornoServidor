package com.esliceu.maze.dao;

import com.esliceu.maze.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImplem implements UserDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User findUserByUsername(String username) {
        String sql = "select * from user where username = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
    }

    @Override
    public void saveUser(User user) {
       jdbcTemplate.update("insert into user (name, username, password) values (?,?,?)",
        user.getName(), user.getUsername(), user.getPassword());
    }
}
