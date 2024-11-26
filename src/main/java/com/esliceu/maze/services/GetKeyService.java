package com.esliceu.maze.services;

import com.esliceu.maze.dao.DoorKeyDAO;
import com.esliceu.maze.dao.RoomDAO;
import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.dao.UserRoomsDAO;
import com.esliceu.maze.model.DoorKey;
import com.esliceu.maze.model.Room;
import com.esliceu.maze.model.User;
import com.esliceu.maze.model.UserRooms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetKeyService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    DoorKeyDAO doorKeyDAO;
    @Autowired
    StartService startService;
    @Autowired
    UserRoomsDAO userRoomsDAO;

    public String ckeckClickInKey(String username) {
        User user = userDAO.getUserByUsername(username);
        UserRooms actualUserRoom = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), user.getRoomId());
        if (actualUserRoom.getDoorKeyId() != null){
            DoorKey doorKey = doorKeyDAO.getKeyById(actualUserRoom.getDoorKeyId());
            if (user.getCoins() >= doorKey.getNeededCoins()) {
                return addKeyToUser(doorKey, user, actualUserRoom);
            }else {
                int coins = doorKey.getNeededCoins() - user.getCoins();
                return startService.createJson(username, actualUserRoom, "Te faltan " + coins + " monedas.");
            }
        }
        return startService.createJson(username, actualUserRoom, "En esta habitación no hay llaves." );
    }

    private String addKeyToUser(DoorKey doorKey, User user, UserRooms actualUserRoom) {
        String doorKeyId = String.valueOf(doorKey.getId());
        String userKeys = user.getIdKeys();
        if (userKeys == null || userKeys.isEmpty()) {
            userKeys = doorKeyId;
        } else {
            userKeys += "," + doorKeyId;
        }
        int actualUserCoins = user.getCoins() - doorKey.getNeededCoins();
        userDAO.updateTotalUserKeys(user.getUsername(), userKeys);
        userDAO.updateTotalUserCoins(user.getUsername(), actualUserCoins);
        return startService.createJson(user.getUsername(), actualUserRoom, "");
    }
}
