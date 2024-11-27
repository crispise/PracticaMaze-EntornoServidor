package com.esliceu.maze.dao;

import com.esliceu.maze.model.Door;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DoorDAOImplem implements DoorDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Door> findAllDoorsByRoomId(int roomId) {
        return jdbcTemplate.query("select * from door where room1Id = ? OR room2Id = ?",
                new BeanPropertyRowMapper<>(Door.class), roomId, roomId);
    }

    @Override
    public Door getDoorById(int doorId) {
        return jdbcTemplate.queryForObject("select * from door where id = ?",
                new BeanPropertyRowMapper<>(Door.class), doorId);
    }
}
