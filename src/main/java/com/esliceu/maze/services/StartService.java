package com.esliceu.maze.services;

import com.esliceu.maze.dao.*;
import com.esliceu.maze.model.*;
import com.esliceu.maze.model.Map;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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
        List<Room> mapRooms = roomDAO.getAllRoomsByMapId(map.getId());
        User user = userDAO.getUserByUsername(username);
        updateGameTime(user);
        updateMapNameInUser(user, map);
        UserRooms actualRoom;
        if (user.getRoomId() == null) {
            updateUserRoomsWithRoomMaps(mapRooms, user);
            actualRoom = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), map.getStartRoomId());
        } else {
            Room room = roomDAO.getRoomById(user.getRoomId());
            if (room.getMapId() == map.getId()){
                actualRoom = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), user.getRoomId());
            }else {//////casos en los que al cerrar sesion y volver a jugar en diferentes navegadores escojas otro mapa
                userRoomsDAO.deleteUserRoomsByUserIdExcludingMapID(user.getId(), map.getId());
                updateUserRoomsWithRoomMaps(mapRooms, user);
                actualRoom = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), map.getStartRoomId());
            }
        }
        return createJson(username, actualRoom, "");
    }

    private void updateGameTime(User user) {
        long currentTime = System.currentTimeMillis(); // Tiempo actual en milisegundos
        userDAO.updateGameTime(user.getUsername(), currentTime);
    }

    private void updateMapNameInUser(User user, Map map) {
        userDAO.updateMapName(user.getUsername(), map.getMapName());
    }

    private void updateUserRoomsWithRoomMaps(List<Room> mapRooms, User user) {
        for (Room room : mapRooms) {
            userRoomsDAO.insertUserRoom(user.getId(), room.getId(), room.getMapId(),
                    room.getNorth(), room.getSouth(), room.getEast(), room.getWest(), room.getCoins(), room.getDoorKeyId());
        }
    }

    public String createJson(String username, UserRooms userRooms, String errorMessage) {
        userDAO.updateUserRoomStatus(username, userRooms.getRoomId());
        User user = userDAO.getUserByUsername(username);
        List<Door> doors = doorDAO.findAllDoorsByRoomId(userRooms.getRoomId());
        HashMap<String, Object> mapa = new HashMap<>();
        updateHashmap(userRooms, errorMessage, user, mapa, doors);
        Gson gson = new Gson();
        return gson.toJson(mapa);
    }

    private void updateHashmap(UserRooms userRooms, String errorMessage, User user, HashMap<String, Object> mapa, List<Door> doors) {
        if (checkIfUserHasKey(user, userRooms)) mapa.put("keys", userRooms.getDoorKeyId());
        if (user.getOpenDoors() != null) updateDoorState(user, doors);
        if (checkFinalRoom(user)) mapa.put("finalRoom", user.getRoomId());
        System.out.println(user.getMapName());
        mapa.put("mapName", user.getMapName());
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

    private static void updateDoorState(User user, List<Door> doors) {
        Set<String> openDoorIds = new HashSet<>(Arrays.asList(user.getOpenDoors().split(",")));
        for (Door door : doors) {
            if (openDoorIds.contains(String.valueOf(door.getId()))) {
                door.setState(1);
            }
        }
    }

    private boolean checkFinalRoom(User user) {
        UserRooms userRooms = userRoomsDAO.getUserRoomByRoomIdAndUserId(user.getId(), user.getRoomId());
        Map map = mapDAO.getMapById(userRooms.getMapId());
        if (user.getRoomId() == map.getFinishRoomId()) {
            return true;
        }
        return false;
    }

    private String getKeysInfo(User user) {
        if (user.getIdKeys() != null) {
            List<String> idKeys = Arrays.asList(user.getIdKeys().split(","));
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < idKeys.size(); i++) {
                stringBuilder.append("Llave ").append(idKeys.get(i));
                if (i != idKeys.size() - 1) {
                    stringBuilder.append(",");
                }
            }
            return stringBuilder.toString();
        }
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
