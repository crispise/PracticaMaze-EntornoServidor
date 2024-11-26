package com.esliceu.maze.model;

public class UserRooms {
    private int id;
    private int userId;
    private int mapId;
    private int roomId;
    private Integer north;
    private Integer south;
    private Integer east;
    private Integer west;
    private Integer coins;
    private Integer doorKeyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Integer getNorth() {
        return north;
    }

    public void setNorth(Integer north) {
        this.north = north;
    }

    public Integer getSouth() {
        return south;
    }

    public void setSouth(Integer south) {
        this.south = south;
    }

    public Integer getEast() {
        return east;
    }

    public void setEast(Integer east) {
        this.east = east;
    }

    public Integer getWest() {
        return west;
    }

    public void setWest(Integer west) {
        this.west = west;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public Integer getDoorKeyId() {
        return doorKeyId;
    }

    public void setDoorKeyId(Integer doorKeyId) {
        this.doorKeyId = doorKeyId;
    }

    @Override
    public String toString() {
        return "UserRooms{" +
                "id=" + id +
                ", userId=" + userId +
                ", mapId=" + mapId +
                ", roomId=" + roomId +
                ", north=" + north +
                ", south=" + south +
                ", east=" + east +
                ", west=" + west +
                ", coins=" + coins +
                ", doorKeyId=" + doorKeyId +
                '}';
    }
}
