package com.esliceu.maze.dao;

import com.esliceu.maze.model.Door;

import java.util.List;

public interface DoorDAO {
    List<Door> findAllDoorsByRoomId(int roomId);
    Door getDoorById (int doorId);
}
