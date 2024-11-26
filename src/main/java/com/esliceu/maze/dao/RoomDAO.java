package com.esliceu.maze.dao;

import com.esliceu.maze.model.Room;

import java.util.List;

public interface RoomDAO {
   Room getRoomById(int roomId);
   List<Room> getAllRoomsByMapId(int mapId);
}
