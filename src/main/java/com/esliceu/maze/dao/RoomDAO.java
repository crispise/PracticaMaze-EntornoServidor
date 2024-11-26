package com.esliceu.maze.dao;

import com.esliceu.maze.model.Room;

public interface RoomDAO {
   Room getRoomById(int roomId);
   void updateTotalCoins(int id, int roomTotalCoins);
   void updateTotalKeys(int roomId, int roomTotalKeys);
}
