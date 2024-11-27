package com.esliceu.maze.services;

import com.esliceu.maze.dao.*;
import com.esliceu.maze.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class NavService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    RoomDAO roomDAO;
    @Autowired
    DoorDAO doorDAO;
    @Autowired
    MapDAO mapDAO;
    @Autowired
    StartService startService;
    @Autowired
    UserRoomsDAO userRoomsDAO;

    public String trySelectedDirection(String direction, String username) {
        User user = userDAO.getUserByUsername(username);
        UserRooms actualUserRoom = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), user.getRoomId());
        Map map = mapDAO.getMapById(actualUserRoom.getMapId());
        if (user.getRoomId() == map.getFinishRoomId()){
            return startService.createJson(username, actualUserRoom, "El juego ha acabado");
        }
        Door door = findDoor(direction, actualUserRoom);

        if (door != null) {
            if (user.getOpenDoors() != null){
                Set<String> openDoorIds = new HashSet<>(Arrays.asList(user.getOpenDoors().split(",")));
                if (openDoorIds.contains(String.valueOf(door.getId()))) {
                     door.setState(1);
                }
            }
            if (door.getState() == 1) {
                UserRooms newUserRooms = null;
                if (door.getRoom1Id() == actualUserRoom.getRoomId()) {
                    newUserRooms = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), door.getRoom2Id());
                } else if (door.getRoom2Id() == actualUserRoom.getRoomId()) {
                    newUserRooms = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), door.getRoom1Id());
                }
                return startService.createJson(username, newUserRooms, "");
            }
            return startService.createJson(username, actualUserRoom, "La puerta esta cerrada");
        }
        return startService.createJson(username, actualUserRoom, "No hay puertas en esa dirección");
    }

    public Door findDoor(String direction, UserRooms actualUserRoom) {
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
