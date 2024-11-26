package com.esliceu.maze.services;

import com.esliceu.maze.dao.DoorDAO;
import com.esliceu.maze.dao.MapDAO;
import com.esliceu.maze.dao.RoomDAO;
import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.model.Door;
import com.esliceu.maze.model.Map;
import com.esliceu.maze.model.Room;
import com.esliceu.maze.model.User;
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

    public List<Map> getAllMaps() {
        return mapDAO.getAllMaps();
    }

    public String getFirstJson(String mapId, String username) {
        Map map = mapDAO.getMapById(Integer.parseInt(mapId));
        Room room = roomDAO.getRoomById(map.getStartRoomId());
        return createJson(username, room, "");
    }

    public String createJson(String username, Room room, String errorMessage) {
        HashMap<String, Object> mapa = new HashMap<>();
        List<Door> doors = doorDAO.findAllDoorsByRoomId(room.getId());
        updateUserStatus(username, room.getId());
        User user = userDAO.getUserByUsername(username);
        if (checkIfUserHasKey(user, room)){
            mapa.put("keys", room.getDoorKeyId());
        }
        mapa.put("userRoom", user.getRoomId());
        mapa.put("userCoins", user.getCoins());
        mapa.put("userKeys", getKeysInfo(user));
        mapa.put("north", room.getNorth());
        mapa.put("south", room.getSouth());
        mapa.put("east", room.getEast());
        mapa.put("west", room.getWest());
        mapa.put("coins", room.getCoins());
        mapa.put("doors", getDoorsInfo(doors));
        mapa.put("errorMessage", errorMessage);
        Gson gson = new Gson();
        return gson.toJson(mapa);
    }

    private boolean checkIfUserHasKey(User user, Room room) {
        if (user.getIdKeys() == null || user.getIdKeys().isEmpty()) {
            return true;
        }
        if (room.getDoorKeyId() != null){
            List<String> userKeys = Arrays.asList(user.getIdKeys().split(","));
            String roomKey = String.valueOf(room.getDoorKeyId());
            if (!userKeys.contains(roomKey)) return true;
        }
        return false;
    }



    private String getKeysInfo(User user) {
        String idKeys = user.getIdKeys();
        return "";
    }

    private void updateUserStatus(String username, int roomId) {
        userDAO.updateUserRoomStatus(username, roomId);
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
