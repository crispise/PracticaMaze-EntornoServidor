package com.esliceu.maze.model;

public class DoorKey {
    private int id;
    private int neededCoins;
    private int roomId; //habitacion en la que esta
    private int doorId; //que puerta abre

    public int getId() {
        return id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getDoorId() {
        return doorId;
    }

    public void setDoorId(int doorId) {
        this.doorId = doorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNeededCoins() {
        return neededCoins;
    }

    public void setNeededCoins(int neededCoins) {
        this.neededCoins = neededCoins;
    }

}
