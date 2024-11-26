package com.esliceu.maze.services;

import com.esliceu.maze.dao.*;
import com.esliceu.maze.model.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@Service
public class StartService {
    @Autowired
    MapDAO mapDAO;
    @Autowired
    RoomDAO roomDAO;
    @Autowired
    DoorDAO doorDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserRoomsDAO userRoomsDAO;


    public List<Map> getAllMaps() {
        return mapDAO.getAllMaps();
    }

    public String getFirstJson(String mapId, String username) {
        Map map = mapDAO.getMapById(Integer.parseInt(mapId));
        User user = userDAO.getUserByUsername(username);
        List<Room> mapRooms = roomDAO.getAllRoomsByMapId(map.getId());
        for (Room room : mapRooms) {
            userRoomsDAO.insertUserRoom(user.getId(), room.getId(), room.getMapId(),
                    room.getNorth(), room.getSouth(), room.getEast(), room.getWest(), room.getCoins(), room.getDoorKeyId());
        }
        UserRooms initialRoom = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), map.getStartRoomId());
        return createJson(username, initialRoom, "");
    }

    public String createJson(String username, UserRooms userRooms, String errorMessage) {
        userDAO.updateUserRoomStatus(username, userRooms.getRoomId());
        User user = userDAO.getUserByUsername(username);
        List<Door> doors = doorDAO.findAllDoorsByRoomId(userRooms.getRoomId());

        HashMap<String, Object> mapa = new HashMap<>();
        if (checkIfUserHasKey(user, userRooms)) {
            mapa.put("keys", userRooms.getDoorKeyId());
        }
        mapa.put("north", userRooms.getNorth());
        mapa.put("south", userRooms.getSouth());
        mapa.put("east", userRooms.getEast());
        mapa.put("west", userRooms.getWest());
        mapa.put("coins", userRooms.getCoins());
        mapa.put("userRoom", user.getRoomId());
        mapa.put("userCoins", user.getCoins());
        mapa.put("userKeys", getKeysInfo(user));
        mapa.put("doors", getDoorsInfo(doors));
        mapa.put("errorMessage", errorMessage);
        Gson gson = new Gson();
        return gson.toJson(mapa);
    }

    private boolean checkIfUserHasKey(User user, UserRooms userRooms) {
        if (user.getIdKeys() == null || user.getIdKeys().isEmpty()) {
            return true;
        }
        if (userRooms.getDoorKeyId() != null) {
            List<String> userKeys = Arrays.asList(user.getIdKeys().split(","));
            String roomKey = String.valueOf(userRooms.getDoorKeyId());
            if (!userKeys.contains(roomKey)) return true;
        }
        return false;
    }

    private String getKeysInfo(User user) {
        String idKeys = user.getIdKeys();
        return "";
    }


    private static List<HashMap<String, Object>> getDoorsInfo(List<Door> doors) {
        List<HashMap<String, Object>> doorsInfo = new ArrayList<>();

        if (doors != null && !doors.isEmpty()) {
            for (Door door : doors) {
                HashMap<String, Object> doorInfo = new HashMap<>();
                doorInfo.put("doorId", door.getId());
                doorInfo.put("state", door.getState());
                doorsInfo.add(doorInfo);
            }
        }
        return doorsInfo;
    }
}
