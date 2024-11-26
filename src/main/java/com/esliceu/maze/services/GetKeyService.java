package com.esliceu.maze.services;

import com.esliceu.maze.dao.DoorKeyDAO;
import com.esliceu.maze.dao.RoomDAO;
import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.model.DoorKey;
import com.esliceu.maze.model.Room;
import com.esliceu.maze.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetKeyService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    RoomDAO roomDAO;
    @Autowired
    DoorKeyDAO doorKeyDAO;
    @Autowired
    StartService startService;

    public String addKeyToUser(String username) {
        User user = userDAO.getUserByUsername(username);
        Room room = roomDAO.getRoomById(user.getRoomId());
        if (room.getDoorKeyId() != null){
            DoorKey doorKey = doorKeyDAO.getKeyById(room.getDoorKeyId());
            if (user.getCoins() >= doorKey.getNeededCoins()) {
                String doorKeyId = String.valueOf(doorKey.getId());
                String userKeys = user.getIdKeys();
                if (userKeys == null || userKeys.isEmpty()) {
                    userKeys = doorKeyId; //
                } else {
                    userKeys += "," + doorKeyId; //
                }
                userDAO.updateTotalUserKeys(username, userKeys);
                int userCoins = user.getCoins() - doorKey.getNeededCoins();
                userDAO.updateTotalUserCoins(username, userCoins);
                roomDAO.updateTotalKeys(room.getId(), 0);
                Room updatedRoom = roomDAO.getRoomById(room.getId());
                return startService.createJson(username, updatedRoom, "");
            }else {
                int coins = doorKey.getNeededCoins() - user.getCoins();
                return startService.createJson(username, room, "Te faltan " + coins + " monedas.");
            }

        }
        return startService.createJson(username, room, "En esta habitación no hay llaves." );
    }
}
