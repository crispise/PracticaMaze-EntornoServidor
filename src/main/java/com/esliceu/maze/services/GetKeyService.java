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

    public String ckeckClickInKey(String username) {
        User user = userDAO.getUserByUsername(username);
        Room room = roomDAO.getRoomById(user.getRoomId());
        if (room.getDoorKeyId() != null){
            DoorKey doorKey = doorKeyDAO.getKeyById(room.getDoorKeyId());
            if (user.getCoins() >= doorKey.getNeededCoins()) {
                return addKeyToUser(username, doorKey, user, room);
            }else {
                int coins = doorKey.getNeededCoins() - user.getCoins();
                return startService.createJson(username, room, "Te faltan " + coins + " monedas.");
            }
        }
        return startService.createJson(username, room, "En esta habitación no hay llaves." );
    }

    private String addKeyToUser(String username, DoorKey doorKey, User user, Room room) {
        String doorKeyId = String.valueOf(doorKey.getId());
        String userKeys = user.getIdKeys();
        if (userKeys == null || userKeys.isEmpty()) {
            userKeys = doorKeyId;
        } else {
            userKeys += "," + doorKeyId;
        }
        int actualUserCoins = user.getCoins() - doorKey.getNeededCoins();
        userDAO.updateTotalUserKeys(username, userKeys);
        userDAO.updateTotalUserCoins(username, actualUserCoins);
        return startService.createJson(username, room, "");
    }
}
