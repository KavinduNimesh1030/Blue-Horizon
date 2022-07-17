package model;

public class Payment {
    private String id;
    private String reservationId;
    private String paymentType;
    private double totalPayment;

    public Payment() {
    }

    public Payment(String id, String reservationId, String paymentType, double totalPayment) {
        this.id = id;
        this.reservationId = reservationId;
        this.paymentType = paymentType;
        this.totalPayment = totalPayment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", reservationId='" + reservationId + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", totalPayment=" + totalPayment +
                '}';
    }
}
