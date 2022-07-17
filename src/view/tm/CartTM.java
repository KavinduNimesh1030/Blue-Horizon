package view.tm;

import javafx.scene.control.Button;

public class CartTM {
    private String id;
    private String name;
    private double mealPrice;
    private int qty;
    private double totalCost;
    private Button btn;

    public CartTM() {
    }

    public CartTM(String id, String name, double mealPrice, int qty, double totalCost, Button btn) {
        this.id = id;
        this.name = name;
        this.mealPrice = mealPrice;
        this.qty = qty;
        this.totalCost = totalCost;
        this.btn = btn;
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

    public double getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(double mealPrice) {
        this.mealPrice = mealPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mealPrice=" + mealPrice +
                ", qty=" + qty +
                ", totalCost=" + totalCost +
                ", btn=" + btn +
                '}';
    }
}
