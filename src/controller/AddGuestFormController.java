package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Customer;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddGuestFormController {

    public TextField txtAddress;
    public TextField txtName;
    public TextField txtTeleNo;
    public TextField txtEmail;
    public TextField txtId;
    public TextField txtHeadId;
    public Button btnSaveCustomer;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        Pattern idPattern = Pattern.compile("^(C)[0-9]{3,5}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{2,30}$");
        Pattern teleNoPattern = Pattern.compile("^[\\+](94)[0-9]{9}$");
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        map.put(txtId,idPattern);
        map.put(txtName,namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtTeleNo,teleNoPattern);
        map.put(txtEmail,emailPattern);

        btnSaveCustomer.setDisable(true);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        save();

    }

    private void save() {
        if(btnSaveCustomer.getText().equals("Save Customer")){
            Customer c =new Customer(
                    txtId.getText(),txtName.getText(),txtAddress.getText(),txtTeleNo.getText(),txtEmail.getText()
            );
            try {
                if (CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?)",c.getId(),c.getName(),c.getAddress(),c.getTeleNo(),c.getEmail())) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Saved..").show();
                    clearText();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }else{
            Customer c =new Customer(
                    txtId.getText(),txtName.getText(),txtAddress.getText(),txtTeleNo.getText(),txtEmail.getText()
            );
            try {
                if(CrudUtil.execute("UPDATE Customer SET name=? , address=? , telNo=? ,email=?  WHERE id=?",c.getName(),c.getAddress(),c.getTeleNo(),c.getEmail(),c.getId())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated") .show();
                    clearText();
                    btnSaveCustomer.setText("Save Customer");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,e.getMessage()) .show();
            }
        }

    }

    private void clearText() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtTeleNo.clear();
        txtEmail.clear();
        txtId.requestFocus();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        Search();
        btnSaveCustomer.setText("Update Customer");
    }

    private void Search() {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM customer WHERE id=?",txtHeadId.getText());
            if (result.next()) {
                txtId.setText(result.getString(1));
                txtName.setText(result.getString(2));
                txtAddress.setText(result.getString(3));
                txtTeleNo.setText(result.getString(4));
                txtEmail.setText(result.getString(5));
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) {
        btnSaveCustomer.setText("Save Customer");
        clearText();
        txtId.setStyle("-fx-border-color:  #182AE2");
        txtName.setStyle("-fx-border-color:  #182AE2");
        txtAddress.setStyle("-fx-border-color:  #182AE2");
        txtTeleNo.setStyle("-fx-border-color:  #182AE2");
        txtEmail.setStyle("-fx-border-color:  #182AE2");



    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSaveCustomer);
        if (keyEvent.getCode() == KeyCode.ENTER){
            Object response = ValidationUtil.validate(map,btnSaveCustomer);

            if (response instanceof TextField ) {
                TextField txtField =(TextField) response;
                txtField.requestFocus();
            }else if(response instanceof Boolean) {
                System.out.println("Work");
                save();
            }
        }
    }

    public void btnDeleteCustomerOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("DELETE FROM Customer WHERE id=?",txtId.getText())) {
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
                clearText();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage());
        }

    }
}
