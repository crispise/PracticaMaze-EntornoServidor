package com.esliceu.maze.dao;

import com.esliceu.maze.model.User;

public interface UserDAO {
    User getUserByUsername(String username);
    void saveUser(User user);

    void updateUserRoomStatus(String username, int roomId);

    void updateTotalUserCoins(String username, int totalCoins);

    void updateTotalUserKeys(String username, String userKeys);

    void resetUser(Object roomId, int i, Object o, Object o1, String username);
}
