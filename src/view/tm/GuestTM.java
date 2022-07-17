package view.tm;

import javafx.scene.control.Button;

public class GuestTM {
    private String customerId;
    private String roomId;
    private String reservationId;
    private String checkinDate;
    private String checkoutDate;
    private Button btn;
    private  static  String rId;

    public GuestTM() {
    }

    public GuestTM(String customerId, String roomId, String reservationId, String checkinDate, String checkoutDate, Button btn) {
        this.customerId = customerId;
        this.roomId = roomId;
        this.reservationId = reservationId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.btn = btn;
    }

    public  String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public  String getRoomId() {
        rId=roomId;
        return roomId;

    }
    public static String getRId(){

        return rId;
    }

    public void setRoomId(String roomId) {
        rId=roomId;
        this.roomId = roomId

        ;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "GuestTM{" +
                "customerId='" + customerId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", reservationId='" + reservationId + '\'' +
                ", checkinDate='" + checkinDate + '\'' +
                ", checkoutDate='" + checkoutDate + '\'' +
                ", btn=" + btn +
                '}';
    }
}
