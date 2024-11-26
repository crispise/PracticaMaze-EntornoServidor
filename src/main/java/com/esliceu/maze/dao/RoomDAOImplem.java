package com.esliceu.maze.dao;

import com.esliceu.maze.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Room> getAllRoomsByMapId(int mapId) {
        return jdbcTemplate.query("select * from room where mapId = ?",new BeanPropertyRowMapper<>(Room.class), mapId);
    }
}
