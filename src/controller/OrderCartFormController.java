package controller;

import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Customer;
import model.Meal;
import model.Order;
import model.OrderDetails;
import util.CrudUtil;
import view.tm.CartTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class OrderCartFormController {

    public ComboBox <String>cmbMealId;
    public TextField txtCustomerId;
    public TextField txtCustomerName;
    public TextField txtCustomerTele;
    public TextField txtMealName;
    public TextField txtType;
    public TextField txtQty;
    public ComboBox <String>cmbCustomerId;
    public TextField txtCustomerAddress;
    public TableView tblCart;
    public Label lblDate;
    public Label lblTime;
    public TableColumn colId;
    public TableColumn ColName;
    public TableColumn colPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public TableColumn colOption;
    public TextField txtPrice;
    public Label lblTotalCost;
    ObservableList<CartTM> tmList = FXCollections.observableArrayList();
    ObservableList<CartTM> tmList2 = FXCollections.observableArrayList();

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        ColName.setCellValueFactory(new PropertyValueFactory("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory("mealPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory("Qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory("TotalCost"));
        colOption.setCellValueFactory(new PropertyValueFactory("btn"));
        setClock();
        SetCustomerIds();
        SetMealIds();
        ObservableList mId = new OrderFormController().getMealId();
        System.out.println(mId);
        set(mId);
        //calculateTotal();

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerDetails(newValue);
        });
        cmbMealId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setMealDetails(newValue);
        });
    }

    private void setClock() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + "-" +
                    currentTime.getMinute() + "-" +
                    currentTime.getSecond());

        }), new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void setMealDetails(String selectedMealId) {
        try {
            Meal m = MealCrudController.getMeal(selectedMealId);
            if (m != null) {
                txtMealName.setText(m.getName());
                txtPrice.setText(String.valueOf(m.getPrice()));

            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setCustomerDetails(String selectedCustomerId) {
        try {
            Customer c = CustomerCrudController.getCustomer(selectedCustomerId);
            if (c != null) {
                txtCustomerName.setText(c.getName());
                txtCustomerAddress.setText(c.getAddress());
                txtCustomerTele.setText(c.getTeleNo());
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }




    private void SetMealIds() {
        try {
            cmbMealId.setItems(FXCollections.observableArrayList(MealCrudController.getMealCodes()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void SetCustomerIds() {
        try {
            cmbCustomerId.setItems(FXCollections.observableArrayList(CustomerCrudController.getCustomerIds()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private CartTM isExists(String itemCode) {
        for (CartTM tm : tmList
        ) {
            if (tm.getId().equals(itemCode)) {
                return tm;
            }
        }
        return null;
    }
    public void set(ObservableList<CartTM> id) {
       // tblCart.setItems(id);

        //Want to add id tm list to tm list
        double total = 0;
        for (CartTM tm : id
        ) {
            tmList.add(tm);
            tblCart.setItems(tmList);
            total += tm.getTotalCost();
        }
        lblTotalCost.setText(String.valueOf(total));
    }
    public void getDetails(String id) {

    }





    public void btnAddToCart(ActionEvent actionEvent) {
        double unitPrice = Double.parseDouble(txtPrice.getText());
        System.out.println(unitPrice);
        int qty = Integer.parseInt(txtQty.getText());
        double totalCost = unitPrice * qty;

        CartTM isExists = isExists(cmbMealId.getValue());
        if (isExists != null) {
            for (CartTM temp : tmList
            ) {
                if (temp.equals(isExists)) {
                    isExists.setQty((temp.getQty() + qty));
                    temp.setTotalCost(temp.getTotalCost() + totalCost);
                }
            }

        } else {
            Button btn = new Button("Delete");

            CartTM tm = new CartTM(
                    cmbMealId.getValue(),
                    txtMealName.getText(),
                    unitPrice,
                    qty,
                    totalCost,
                    btn
            );
            tmList.add(tm);

            calculateTotal();


            btn.setOnAction((e) -> {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are You Sure?",
                        ButtonType.YES, ButtonType.NO);

                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {

                    tmList.remove(tm);
                   // calculateTotal();

                }


            });
             tblCart.setItems(tmList);
        }
        tblCart.refresh();

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String getId =new OrderCrudController().getOrderId();
        System.out.println(getId);

        Order order = new Order(
                getId,
                lblDate.getText(),
                cmbCustomerId.getValue()
        );
        ArrayList<OrderDetails> details = new ArrayList<>();
        for (CartTM tm : tmList
        ) {
            details.add(
                    new OrderDetails(
                            getId,
                            tm.getId(),
                            tm.getQty(),
                            tm.getMealPrice()
                    )
            );

        }
        Connection connection= null;

        try {
            connection= DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved = new OrderCrudController().saveOrder(order);
            if (isOrderSaved) {
                boolean isDetailsSaved=new OrderCrudController().saveOrderDetails(details);
                if (isDetailsSaved){
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION,"Saved!").show();
                }else{
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR,"Error!").show();
                }
            }else{
                connection.rollback();
                new Alert(Alert.AlertType.ERROR,"Error!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }finally {
            connection.setAutoCommit(true);
        }
        tblCart.getItems().clear();
        calculateTotal(0);

    }
    private void calculateTotal() {
        double total = 0;
        for (CartTM tm : tmList
        ) {
            total += tm.getTotalCost();
        }
        lblTotalCost.setText(String.valueOf(total));
    }
    private void calculateTotal(int x){
        lblTotalCost.setText(String.valueOf(x));
    }


}
