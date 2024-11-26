package com.esliceu.maze.model;

public class Map {
    private int id;
    private String mapName;
    private int startRoomId;
    private int finishRoomId;

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getFinishRoomId() {
        return finishRoomId;
    }

    public void setFinishRoomId(int finishRoomId) {
        this.finishRoomId = finishRoomId;
    }

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

    @Override
    public String toString() {
        return "Map{" +
                "id=" + id +
                ", mapName='" + mapName + '\'' +
                ", startRoomId=" + startRoomId +
                ", finishRoomId=" + finishRoomId +
                '}';
    }
}
