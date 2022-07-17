package model;

public class Employee {
    private String id;
    private String name;
    private String address;
    private String teleNo;
    private String position;

    public Employee() {
    }

    public Employee(String id, String name, String address, String teleNo, String position) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.teleNo = teleNo;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTeleNo() {
        return teleNo;
    }

    public void setTeleNo(String teleNo) {
        this.teleNo = teleNo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", teleNo='" + teleNo + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
