package com.esliceu.maze.services;

import com.esliceu.maze.dao.DoorDAO;
import com.esliceu.maze.dao.RoomDAO;
import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.model.Door;
import com.esliceu.maze.model.Room;
import com.esliceu.maze.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NavService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    RoomDAO roomDAO;
    @Autowired
    DoorDAO doorDAO;
    @Autowired
    StartService startService;

    public String trySelectedDirection(String direction, String username) {
        User user = userDAO.getUserByUsername(username);
        Room actualRoom = roomDAO.getRoomById(user.getRoomId());
        Door roomDoor = null;
        Room newRoom = null;
        if (actualRoom != null) {
            roomDoor = findDoor(direction, actualRoom);
        }
        if (roomDoor != null) {
            if (roomDoor.getState() == 1) {
                if (roomDoor.getRoom1Id() == actualRoom.getId()) {
                    newRoom = roomDAO.getRoomById(roomDoor.getRoom2Id());
                } else if (roomDoor.getRoom2Id() == actualRoom.getId()) {
                    newRoom = roomDAO.getRoomById(roomDoor.getRoom1Id());
                }
                return startService.createJson(username, newRoom, "");
            }
            return startService.createJson(username, actualRoom, "La puerta esta cerrada");
        }
        return startService.createJson(username, actualRoom, "No hay puertas en esa dirección");
    }

    private Door findDoor(String direction, Room actualRoom) {
        switch (direction) {
            case "north":
                if (actualRoom.getNorth() != null) {
                    return doorDAO.getDoorById(actualRoom.getNorth());
                }
            case "south":
                if (actualRoom.getSouth() != null) {
                    return doorDAO.getDoorById(actualRoom.getSouth());
                }
            case "east":
                if (actualRoom.getEast() != null) {
                    return doorDAO.getDoorById(actualRoom.getEast());
                }
            case "west":
                if (actualRoom.getWest() != null) {
                    return doorDAO.getDoorById(actualRoom.getWest());
                }
            default:
                return null;
        }
    }

}
