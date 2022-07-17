package model;

public class ReservationDetail {
    private String reservationId;
    private String roomId;
    private double totalCost;
    private int Duration;

    public ReservationDetail() {

    }

    public ReservationDetail(String reservationId, String roomId, double totalCost, int duration) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.totalCost = totalCost;
        this.Duration = duration;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    @Override
    public String toString() {
        return "ReservationDetail{" +
                "reservationId='" + reservationId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", totalCost=" + totalCost +
                ", Duration=" + Duration +
                '}';
    }
}
