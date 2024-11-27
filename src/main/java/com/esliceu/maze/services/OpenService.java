package com.esliceu.maze.services;

import com.esliceu.maze.dao.DoorDAO;
import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.dao.UserRoomsDAO;
import com.esliceu.maze.model.Door;
import com.esliceu.maze.model.User;
import com.esliceu.maze.model.UserRooms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OpenService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserRoomsDAO userRoomsDAO;
    @Autowired
    NavService navService;
    @Autowired
    StartService startService;

    public String tryOpenDoor(String direction, String username) {
        System.out.println("entra en el tryOPen");
        User user = userDAO.getUserByUsername(username);
        System.out.println(user);
        UserRooms actualUserRoom = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), user.getRoomId());
        System.out.println(actualUserRoom);
        Door door = null;
        if (actualUserRoom != null) {
            System.out.println("dentro de actualUserRoom no nulo");
            door = navService.findDoor(direction, actualUserRoom);
        }
        if (door != null) {
            System.out.println("puerta no nula");
            System.out.println(door);
            if (door.getState() == 0) {
                System.out.println("estado de la puerta cerrada");
                if (user.getIdKeys() == null || user.getIdKeys().isEmpty()){
                return startService.createJson(username, actualUserRoom, "El usuario no tiene llaves");
                }
                String doorKeyId = String.valueOf(door.getDoorKeyId());
                List<String> userIdKeys = Arrays.asList(user.getIdKeys().split(","));
                boolean hasKey = false;
                for (String idKey : userIdKeys){
                    if (idKey.equals(doorKeyId)) hasKey = true;
                }
                if (hasKey){
                    System.out.println("el usuario tiene la llave");
                    userDAO.updateOpenDoors(username, String.valueOf(door.getId()));
                    return startService.createJson(username, actualUserRoom, "");
                }else {
                    return startService.createJson(username, actualUserRoom, "La llave correcta es la LLave " + doorKeyId);
                }
            }
        }
        return startService.createJson(username, actualUserRoom, "No hay puerta");
    }


}
