package com.esliceu.maze.dao;

import com.esliceu.maze.model.Room;

import java.util.List;

public interface RoomDAO {
   Room getRoomById(int roomId);
   void updateTotalCoins(int id, int roomTotalCoins);
   void updateTotalKeys(int roomId, int roomTotalKeys);
    List<Room> getRoomsWithCoins();
    void resetRoomCoins(Room room);
}
