package com.esliceu.maze.dao;

import com.esliceu.maze.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoomDAOImplem implements RoomDAO{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Room getRoomById(int roomId) {
        return jdbcTemplate.queryForObject("select * from room where id = ?",
                new BeanPropertyRowMapper<>(Room.class), roomId);
    }

    @Override
    public void updateTotalCoins(int id, int roomTotalCoins) {
        jdbcTemplate.update("UPDATE room SET coins = ? WHERE id = ?", roomTotalCoins, id);
    }

    @Override
    public void updateTotalKeys(int roomId, int roomTotalKeys) {
        jdbcTemplate.update("UPDATE room SET doorKeyId = ? WHERE id = ?", 0, roomId);
    }
}
