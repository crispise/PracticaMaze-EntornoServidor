package com.esliceu.maze.dao;

import com.esliceu.maze.model.DoorKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DoorKeyDAOImplem implements DoorKeyDAO{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public DoorKey getKeyById(Integer doorKeyId) {
        return jdbcTemplate.queryForObject("select * from doorkey where id = ?", new BeanPropertyRowMapper<>(DoorKey.class), doorKeyId);
    }
}
