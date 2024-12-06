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
        System.out.println("la direccion");
        System.out.println(direction);
        System.out.println("la habitacion actual");
        System.out.println(actualUserRoom);
        Map map = mapDAO.getMapById(actualUserRoom.getMapId());
        if (user.getRoomId() == map.getFinishRoomId()){
            return startService.createJson(username, actualUserRoom, "El juego ha acabado");
        }

        Door door = findDoor(direction, actualUserRoom);
        System.out.println("la puerta que encuentra");
        System.out.println(door);
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
        System.out.println("dirección dentro de findDoor");
        System.out.println(direction);
        switch (direction) {
            case "north":
                if (actualUserRoom.getNorth() != null) {
                    System.out.println("el norte no es nulo");
                    return doorDAO.getDoorById(actualUserRoom.getNorth());
                }
                break;  // Añadido break para evitar el "fall-through"
            case "south":
                if (actualUserRoom.getSouth() != null) {
                    System.out.println("el sur no es nulo");
                    return doorDAO.getDoorById(actualUserRoom.getSouth());
                }
                break;  // Añadido break
            case "east":
                if (actualUserRoom.getEast() != null) {
                    System.out.println("el este no es nulo");
                    return doorDAO.getDoorById(actualUserRoom.getEast());
                }
                break;  // Añadido break
            case "west":
                if (actualUserRoom.getWest() != null) {
                    System.out.println("el oeste no es nulo");
                    return doorDAO.getDoorById(actualUserRoom.getWest());
                }
                break;  // Añadido break
        }
        return null;  // Si no es ninguna de las direcciones, devolvemos null
    }
}
