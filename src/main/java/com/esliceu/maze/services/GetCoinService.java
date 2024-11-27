package com.esliceu.maze.services;

import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.dao.UserRoomsDAO;
import com.esliceu.maze.model.User;
import com.esliceu.maze.model.UserRooms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCoinService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    StartService startService;
    @Autowired
    UserRoomsDAO userRoomsDAO;


    public String addCoinToUser(String username) {
        User user = userDAO.getUserByUsername(username);
        UserRooms actualUserRoom = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), user.getRoomId());
        if (actualUserRoom.getCoins() > 0) {
            int userTotalCoins;
            if (user.getCoins() == null) {
                userTotalCoins = 1;
            } else {
                userTotalCoins = user.getCoins() + 1;
            }
            int roomTotalCoins = actualUserRoom.getCoins() - 1;
            userDAO.updateTotalUserCoins(username, userTotalCoins);
            userRoomsDAO.updateTotalCoins(user.getId(), actualUserRoom.getRoomId(), roomTotalCoins);
            UserRooms updateUserRoom = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), actualUserRoom.getRoomId());
            return startService.createJson(username, updateUserRoom, "");
        }
        return startService.createJson(username, actualUserRoom, "No hay monedas en esta habitación");
    }
}
