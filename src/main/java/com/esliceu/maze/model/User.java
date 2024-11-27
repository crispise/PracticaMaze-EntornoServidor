package com.esliceu.maze.model;

import java.sql.Time;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private Integer roomId;
    private Integer coins;
    private Integer gameTime;
    private String idKeys;
    private String openDoors;


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

    public Integer getGameTime() {
        return gameTime;
    }

    public void setGameTime(Integer gameTime) {
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
                ", roomId=" + roomId +
                ", coins=" + coins +
                ", gameTime=" + gameTime +
                ", idKeys='" + idKeys + '\'' +
                ", openDoors='" + openDoors + '\'' +
                '}';
    }
}
