package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Meal;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddMealFormController {

    public TextField txtHeadId;
    public TextField txtMealName;
    public TextField txtMealId;
    public TextField txtMealType;
    public TextField txtMealPrice;
    public Button btnSaveMeal;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        Pattern idPattern = Pattern.compile("^(M)[0-9]{3,5}$");
        Pattern namePattern = Pattern.compile("^[A-z 0-9 ]{3,20}$");
        Pattern typePattern = Pattern.compile("^[A-z]{3,20}$");
        Pattern pricePattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$");

        map.put(txtMealId,idPattern);
        map.put(txtMealName,namePattern);
        map.put(txtMealType,typePattern);
        map.put(txtMealPrice,pricePattern);

    }

    public void btnMealSaveOnAction(ActionEvent actionEvent) {
        save();
    }

    private void save() {
        if (btnSaveMeal.getText().equals("Save")) {
            Meal m1 = new Meal(
                    txtMealId.getText(),txtMealName.getText(),txtMealType.getText(),Double.parseDouble(txtMealPrice.getText())
            );
            try {
                if (CrudUtil.execute("INSERT INTO food VALUES (?,?,?,?)",m1.getId(),m1.getName(),m1.getType(),m1.getPrice())) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Saved!!").show();
                    clearTxt();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }else{
            Meal m1 = new Meal(
                    txtMealId.getText(),txtMealName.getText(),txtMealType.getText(),Double.parseDouble(txtMealPrice.getText())
            );
            try {
                if (CrudUtil.execute("UPDATE Food SET mealName=? ,type=? ,unitPrice=? WHERE id=?",m1.getName(),m1.getType(),m1.getPrice(),m1.getId())) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated!!").show();
                    clearTxt();
                    btnSaveMeal.setText("Save");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new  Alert(Alert.AlertType.ERROR,"Some thing wrong").show();
            }
        }

    }

    public void btnNewMealOnAction(ActionEvent actionEvent) {
        btnSaveMeal.setText("Save");
        clearTxt();
        txtMealId.setStyle("-fx-border-color:  #182AE2");
        txtMealType.setStyle("-fx-border-color:  #182AE2");
        txtMealName.setStyle("-fx-border-color:  #182AE2");
        txtMealPrice.setStyle("-fx-border-color:  #182AE2");
    }

    private void clearTxt() {
        txtMealId.clear();
        txtMealName.clear();
        txtMealType.clear();
        txtMealPrice.clear();

    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        search();
        btnSaveMeal.setText("Update");

    }

    private void search() {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Food WHERE id=?",txtHeadId.getText());
            if (result.next()){
                txtMealId.setText(result.getString(1));
                txtMealName.setText(result.getString(2));
                txtMealType.setText(result.getString(3));
                txtMealPrice.setText(String.valueOf(result.getDouble(4)));

            }else {
                new Alert(Alert.AlertType.ERROR,"empty Result").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSaveMeal);
        if (keyEvent.getCode() == KeyCode.ENTER){
            Object response = ValidationUtil.validate(map,btnSaveMeal);

            if (response instanceof TextField ) {
                TextField txtField =(TextField) response;
                txtField.requestFocus();
            }else if(response instanceof Boolean) {
                System.out.println("Work");
                save();
            }
        }
    }
}
