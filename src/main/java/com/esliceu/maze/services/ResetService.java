package com.esliceu.maze.services;

import com.esliceu.maze.dao.MapDAO;
import com.esliceu.maze.dao.RoomDAO;
import com.esliceu.maze.dao.UserDAO;
import com.esliceu.maze.dao.UserRoomsDAO;
import com.esliceu.maze.model.Map;
import com.esliceu.maze.model.Room;
import com.esliceu.maze.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    UserRoomsDAO userRoomsDAO;

    public void resetGame(String username) {
        User user = userDAO.getUserByUsername(username);
        Room actualRoom = roomDAO.getRoomById(user.getRoomId());
        Map map = mapDAO.getMapById(actualRoom.getMapId());
        userDAO.resetUser(null, 0, null, null, null, username, null, "disconect");
        userRoomsDAO.deleteUserRoomsByUserIdAndMapId(user.getId(), map.getId());
    }
}
