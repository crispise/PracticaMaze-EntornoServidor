package com.esliceu.maze.dao;

import com.esliceu.maze.model.Map;

import java.util.List;


public interface MapDAO {
    List<Map> getAllMaps();

    Map getMapById(int id);
}
