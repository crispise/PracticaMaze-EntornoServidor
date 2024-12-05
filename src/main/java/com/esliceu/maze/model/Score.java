package com.esliceu.maze.model;

public class Score {
    private int id;
    private String userName;
    private String mapName;
    private Long gameTime;
    private String comentary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Long getGameTime() {
        return gameTime;
    }

    public void setGameTime(Long gameTime) {
        this.gameTime = gameTime;
    }

    public String getComentary() {
        return comentary;
    }

    public void setComentary(String comentary) {
        this.comentary = comentary;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", mapName='" + mapName + '\'' +
                ", gameTime=" + gameTime +
                ", comentary='" + comentary + '\'' +
                '}';
    }
}
