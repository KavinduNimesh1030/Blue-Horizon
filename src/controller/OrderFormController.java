package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import model.Meal;
import view.tm.CartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class OrderFormController {


    public AnchorPane OrderContext;
    private static String id;
    private static ObservableList<CartTM> tmList = FXCollections.observableArrayList();

    public void btnCheckCartOnAction(ActionEvent actionEvent) throws IOException {
        OrderContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/OrderCartForm.fxml"));
        OrderContext.getChildren().add(parent);

    }
    public void btnOrderMoreOnAction(ActionEvent actionEvent) throws IOException {
        OrderContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/OrderCartForm.fxml"));
        OrderContext.getChildren().add(parent);
    }
    public  void SetMealId(String id){
        this.id=id;
        try {
           Meal m = MealCrudController.getMeal(id);
            if (m != null) {
                String mealId = m.getId();
                String mealName = m.getName();
                double unitPrice = m.getPrice();
                int qty = 1;
                double totalCost = m.getPrice();

                Button btn = new Button("Delete");

                CartTM tm1 = new CartTM(
                        mealId,
                        mealName,
                        unitPrice,
                        qty,
                        totalCost,
                        btn
                );
                System.out.println(tm1.getId());
                tmList.add(tm1);

                btn.setOnAction((e) -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are You Sure?",
                            ButtonType.YES, ButtonType.NO);

                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {

                        tmList.remove(tm1);
                        // calculateTotal();

                    }


                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
    public  ObservableList  getMealId(){
        return  tmList;
    }

    public void AAddTOCartOnAction(ActionEvent actionEvent) {
       SetMealId("M003");
    }


    public void BAddTOCartOnAction(ActionEvent actionEvent) {
        SetMealId("M002");
    }

    public void CAddTOCartOnAction(ActionEvent actionEvent) {
        SetMealId("M010");
    }

    public void DAddTOCartOnAction(ActionEvent mouseEvent) {
        SetMealId("M005");

    }
}
