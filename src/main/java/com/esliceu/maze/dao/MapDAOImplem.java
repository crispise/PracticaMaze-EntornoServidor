package com.esliceu.maze.dao;

import com.esliceu.maze.model.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class MapDAOImplem implements MapDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Map> getAllMaps() {
        return jdbcTemplate.query("select * from map", new BeanPropertyRowMapper<>(Map.class));
    }

    @Override
    public Map getMapById(int id) {
        return jdbcTemplate.queryForObject("select * from map where id = ?",
                new BeanPropertyRowMapper<>(Map.class), id);
    }
}
