package model;

public class OrderDetails {
    private String orderId;
    private String foodId;
    private int qty;
    private double unitPrice;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String foodId, int qty, double unitPrice) {
        this.orderId = orderId;
        this.foodId = foodId;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderIid) {
        this.orderId = orderIid;
    }

    public String getItemCode() {
        return foodId;
    }

    public void setItemCode(String itemCode) {
        this.foodId = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + foodId + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
