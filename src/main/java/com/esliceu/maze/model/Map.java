package com.esliceu.maze.model;

import java.util.List;

public class Map {
    private int id;
    private int startRoomId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartRoomId() {
        return startRoomId;
    }

    public void setStartRoomId(int startRoomId) {
        this.startRoomId = startRoomId;
    }
}
