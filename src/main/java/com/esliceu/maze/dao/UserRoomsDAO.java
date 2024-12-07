package com.esliceu.maze.dao;

import com.esliceu.maze.model.UserRooms;

public interface UserRoomsDAO {
    void insertUserRoom(int id, int id1, int mapId, Integer north, Integer south, Integer east, Integer west, Integer coins, Integer doorKeyId);

    UserRooms getUserRoomByRoomIdAndUserId(int userId, int roomId);

    void updateTotalCoins(int userId, int roomId, int roomTotalCoins);

    void updateTotalKeys(int userId, int roomId, Integer roomTotalKeys);

    void deleteUserRoomsByUserIdAndMapId(int userId, int mapId);

    void deleteUserRoomsByUserIdExcludingMapID(int userId, int mapId);
}
