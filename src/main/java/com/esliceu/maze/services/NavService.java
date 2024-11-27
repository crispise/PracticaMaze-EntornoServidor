package com.esliceu.maze.services;

import com.esliceu.maze.dao.DoorDAO;
import com.esliceu.maze.dao.RoomDAO;
import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.dao.UserRoomsDAO;
import com.esliceu.maze.model.Door;
import com.esliceu.maze.model.Room;
import com.esliceu.maze.model.User;
import com.esliceu.maze.model.UserRooms;
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
    @Autowired
    UserRoomsDAO userRoomsDAO;

    public String trySelectedDirection(String direction, String username) {
        User user = userDAO.getUserByUsername(username);
        UserRooms actualUserRoom = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), user.getRoomId());
        Door roomDoor = null;
        UserRooms newUserRooms = null;
        if (actualUserRoom != null) {
            roomDoor = findDoor(direction, actualUserRoom);
        }
        if (roomDoor != null) {
            if (roomDoor.getState() == 1) {
                if (roomDoor.getRoom1Id() == actualUserRoom.getRoomId()) {
                    newUserRooms = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), roomDoor.getRoom2Id());
                } else if (roomDoor.getRoom2Id() == actualUserRoom.getRoomId()) {
                    newUserRooms = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), roomDoor.getRoom1Id());
                }
                return startService.createJson(username, newUserRooms, "");
            }
            return startService.createJson(username, actualUserRoom, "La puerta esta cerrada");
        }
        return startService.createJson(username, actualUserRoom, "No hay puertas en esa dirección");
    }

    private Door findDoor(String direction, UserRooms actualUserRoom) {
        switch (direction) {
            case "north":
                if (actualUserRoom.getNorth() != null) {
                    return doorDAO.getDoorById(actualUserRoom.getNorth());
                }
            case "south":
                if (actualUserRoom.getSouth() != null) {
                    return doorDAO.getDoorById(actualUserRoom.getSouth());
                }
            case "east":
                if (actualUserRoom.getEast() != null) {
                    return doorDAO.getDoorById(actualUserRoom.getEast());
                }
            case "west":
                if (actualUserRoom.getWest() != null) {
                    return doorDAO.getDoorById(actualUserRoom.getWest());
                }
            default:
                return null;
        }
    }
}
