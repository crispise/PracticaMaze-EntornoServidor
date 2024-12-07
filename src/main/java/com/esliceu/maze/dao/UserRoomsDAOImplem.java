package com.esliceu.maze.dao;

import com.esliceu.maze.model.Room;
import com.esliceu.maze.model.UserRooms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoomsDAOImplem implements UserRoomsDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertUserRoom(int userId, int roomId, int mapId, Integer north, Integer south, Integer east, Integer west, Integer coins, Integer doorKeyId) {
        jdbcTemplate.update("insert into userRooms (userId, roomId, mapId, north, south," +
                        " east, west, coins, doorkeyId) values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                userId, roomId, mapId, north, south, east, west, coins, doorKeyId);
    }

    @Override
    public UserRooms getUserRoomByRoomIdAndUserId(int userId, int roomId) {
        return jdbcTemplate.queryForObject("select * from userRooms where userId = ? AND roomId = ?",
                new BeanPropertyRowMapper<>(UserRooms.class), userId, roomId);
    }

    @Override
    public void updateTotalCoins(int userId, int roomId, int roomTotalCoins) {
        jdbcTemplate.update("UPDATE userRooms SET coins = ? WHERE roomId = ? AND userId = ?",
                roomTotalCoins, roomId, userId);
    }

    @Override
    public void updateTotalKeys(int userId, int roomId, Integer roomTotalKeys) {
        jdbcTemplate.update("UPDATE userRooms SET doorKeyId = ? WHERE roomId = ? AND userId = ?",
                null, roomId, userId);
    }

    @Override
    public void deleteUserRoomsByUserIdAndMapId(int userId, int mapId) {
        jdbcTemplate.update("DELETE FROM userRooms WHERE userId = ? AND mapId = ?", userId, mapId);
    }
}
