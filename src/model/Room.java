package model;

public class Room {
    private String id;
    private String Capacity;
    private String type;
    private Double costPerNight;
    private String isAvailable;

    public Room() {
    }

    public Room(String id, String capacity, String type, Double costPerNight, String isAvailable) {
        this.id = id;
        Capacity = capacity;
        this.type = type;
        this.costPerNight = costPerNight;
        this.isAvailable = isAvailable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCapacity() {
        return Capacity;
    }

    public void setCapacity(String capacity) {
        Capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCostPerNight() {
        return costPerNight;
    }

    public void setCostPerNight(Double costPerNight) {
        this.costPerNight = costPerNight;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", Capacity='" + Capacity + '\'' +
                ", type='" + type + '\'' +
                ", costPerNight='" + costPerNight + '\'' +
                ", isAvailable='" + isAvailable + '\'' +
                '}';
    }
}
