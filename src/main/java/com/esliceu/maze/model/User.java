package com.esliceu.maze.model;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String mapName;
    private Integer roomId;
    private Integer coins;
    private Long gameTime;
    private String idKeys;
    private String openDoors;

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public Long getGameTime() {
        return gameTime;
    }

    public void setGameTime(Long gameTime) {
        this.gameTime = gameTime;
    }

    public String getIdKeys() {
        return idKeys;
    }

    public void setIdKeys(String idKeys) {
        this.idKeys = idKeys;
    }

    public String getOpenDoors() {
        return openDoors;
    }

    public void setOpenDoors(String openDoors) {
        this.openDoors = openDoors;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mapName='" + mapName + '\'' +
                ", roomId=" + roomId +
                ", coins=" + coins +
                ", gameTime=" + gameTime +
                ", idKeys='" + idKeys + '\'' +
                ", openDoors='" + openDoors + '\'' +
                '}';
    }
}
