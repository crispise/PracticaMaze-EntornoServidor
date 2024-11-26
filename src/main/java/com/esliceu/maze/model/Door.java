package com.esliceu.maze.model;

public class Door {
    private int id;
    private int room1Id;
    private int room2Id;
    private int state; //1 = open, 0 = close
    private Integer doorKeyId;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Integer getDoorKeyId() {
        return doorKeyId;
    }

    public void setDoorKeyId(Integer doorKeyId) {
        this.doorKeyId = doorKeyId;
    }

    @Override
    public String toString() {
        return "Door{" +
                "id=" + id +
                ", room1Id=" + room1Id +
                ", room2Id=" + room2Id +
                ", state=" + state +
                ", doorKeyId=" + doorKeyId +
                '}';
    }
}
