package com.esliceu.maze.services;

import com.esliceu.maze.dao.RoomDAO;
import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.model.Room;
import com.esliceu.maze.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCoinService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    RoomDAO roomDAO;
    @Autowired
    StartService startService;

    public String addCoinToUser(String username) {
        User user = userDAO.getUserByUsername(username);
        Room actualRoom = roomDAO.getRoomById(user.getRoomId());
        if (actualRoom.getCoins() > 0) {
            int userTotalCoins = user.getCoins() + 1;
            int roomTotalCoins = actualRoom.getCoins() - 1;
            userDAO.updateTotalUserCoins(username, userTotalCoins);
            roomDAO.updateTotalCoins(actualRoom.getId(), roomTotalCoins);
            Room updtaeRoom = roomDAO.getRoomById(user.getRoomId());
            return startService.createJson(username, updtaeRoom, "");
        }
        return startService.createJson(username,actualRoom, "No hay monedas en esta habitación");
    }
}
