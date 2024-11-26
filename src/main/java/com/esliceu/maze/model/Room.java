package com.esliceu.maze.model;

public class Room {
    private int id;
    private int mapId;
    private Integer north;
    private Integer south;
    private Integer east;
    private Integer west;
    private Integer coins;

    public Integer getInitialCoins() {
        return initialCoins;
    }

    public void setInitialCoins(Integer initialCoins) {
        this.initialCoins = initialCoins;
    }

    private Integer initialCoins;
    private Integer doorKeyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
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
        return "Room{" +
                "id=" + id +
                ", mapId=" + mapId +
                ", north=" + north +
                ", south=" + south +
                ", east=" + east +
                ", west=" + west +
                ", coins=" + coins +
                ", initialCoins=" + initialCoins +
                ", doorKeyId=" + doorKeyId +
                '}';
    }
}
