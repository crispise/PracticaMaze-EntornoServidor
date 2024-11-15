package com.esliceu.maze.model;

public class Door {
    private int id;
    private int room1Id;
    private int room2Id;
    private boolean state; //true = open, false = close

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoom1Id() {
        return room1Id;
    }

    public void setRoom1Id(int room1Id) {
        this.room1Id = room1Id;
    }

    public int getRoom2Id() {
        return room2Id;
    }

    public void setRoom2Id(int room2Id) {
        this.room2Id = room2Id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
