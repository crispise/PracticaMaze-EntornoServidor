package com.esliceu.maze.services;

import com.esliceu.maze.dao.MapDAO;
import com.esliceu.maze.dao.RoomDAO;
import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.model.Map;
import com.esliceu.maze.model.Room;
import com.esliceu.maze.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResetService {
    @Autowired
    MapDAO mapDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    RoomDAO roomDAO;
    @Autowired
    StartService startService;
    public String resetGame(String username) {
        User user = userDAO.getUserByUsername(username);
        Room actualRoom = roomDAO.getRoomById(user.getRoomId());
        Map map = mapDAO.getMapById(actualRoom.getMapId());
        userDAO.resetUser(map.getStartRoomId(), 0, null, null, username);
        List<Room> roomsWithInitialCoins = roomDAO.getRoomsWithCoins();
        for (Room room : roomsWithInitialCoins) {
            roomDAO.resetRoomCoins(room);
        }
       Room initialRoom = roomDAO.getRoomById(map.getStartRoomId());
       return startService.createJson(username, initialRoom, "");
    }
}
