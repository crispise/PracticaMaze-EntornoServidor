package com.esliceu.maze.dao;

import com.esliceu.maze.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImplem implements UserDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User getUserByUsername(String username) {
        String sql = "select * from users where username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        jdbcTemplate.update("insert into users (name, username, password) values (?,?,?)",
                user.getName(), user.getUsername(), user.getPassword());
    }

    @Override
    public void updateUserRoomStatus(String username, int roomId) {
        jdbcTemplate.update("UPDATE users SET roomId = ? WHERE username = ?", roomId, username);
    }

    @Override
    public void updateTotalUserCoins(String username, int totalCoins) {
        jdbcTemplate.update("UPDATE users SET coins = ? WHERE username = ?", totalCoins, username);
    }

    @Override
    public void updateTotalUserKeys(String username, String userKeys) {
        jdbcTemplate.update("UPDATE users SET idKeys = ? WHERE username = ?", userKeys, username);
    }

    @Override
    public void updateOpenDoors(String username, String doorKeyId) {
        jdbcTemplate.update("update users set openDoors = ? where username = ?", doorKeyId, username);
    }

    @Override
    public void resetUser(Object roomId, int initialCoins, Object gameTime, Object idKeys, Object openDoors, String username) {
        jdbcTemplate.update(
                "UPDATE users SET roomId = ?, coins = ?, gameTime = ?, idKeys = ?, openDoors = ? WHERE username = ?",
                roomId, initialCoins, gameTime, idKeys, openDoors, username
        );
    }
}
