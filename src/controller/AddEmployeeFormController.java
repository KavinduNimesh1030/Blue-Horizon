package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Employee;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddEmployeeFormController {
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtTeleNo;
    public TextField txtPosition;
    public TextField txtId;
    public TextField txtHeadId;
    public Button btnSaveEmployee;
    public AnchorPane employeeContext;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        Pattern idPattern = Pattern.compile("^(E)[0-9]{3,5}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{2,30}$");
        Pattern teleNoPattern = Pattern.compile("^[\\+](94)[0-9]{9}$");
        Pattern positionPattern = Pattern.compile("^[A-z ]{3,20}$");

        map.put(txtId,idPattern);
        map.put(txtName,namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtTeleNo,teleNoPattern);
        map.put(txtPosition,positionPattern);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSaveEmployee);
        if (keyEvent.getCode() == KeyCode.ENTER){
            Object response = ValidationUtil.validate(map,btnSaveEmployee);

            if (response instanceof TextField ) {
                TextField txtField =(TextField) response;
                txtField.requestFocus();
            }else if(response instanceof Boolean) {
                System.out.println("Work");
                save();
            }
        }

    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
      search();
      btnSaveEmployee.setText("Update Employee");

    }

    private void search() {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM employee WHERE id=?",txtHeadId.getText());
            if (result.next()) {
                txtId.setText(result.getString(1));
                txtName.setText(result.getString(2));
                txtAddress.setText(result.getString(3));
                txtTeleNo.setText(result.getString(4));
                txtPosition.setText(result.getString(5));
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        save();
    }
    private void save() {
        if(btnSaveEmployee.getText().equals("Save Employee")){
            Employee e =new Employee(
                    txtId.getText(),txtName.getText(),txtAddress.getText(),txtTeleNo.getText(),txtPosition.getText()
            );
            try {
                if (CrudUtil.execute("INSERT INTO employee VALUES (?,?,?,?,?)",e.getId(),e.getName(),e.getAddress(),e.getTeleNo(),e.getPosition())) {
                    new Alert(Alert.AlertType.CONFIRMATION,"Saved..").show();
                    clearText();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException a) {
                a.printStackTrace();
                new Alert(Alert.AlertType.ERROR,a.getMessage()).show();
            }
        }else{
            Employee e =new Employee(
                    txtId.getText(),txtName.getText(),txtAddress.getText(),txtTeleNo.getText(),txtPosition.getText()
            );
            try {
                if(CrudUtil.execute("UPDATE Employee SET name=? , address=? , telNo=? ,position=?  WHERE id=?",e.getName(),e.getAddress(),e.getTeleNo(),e.getPosition(),e.getId())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated") .show();
                    clearText();
                    btnSaveEmployee.setText("Save Employee");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException a) {
                a.printStackTrace();
                new Alert(Alert.AlertType.ERROR,a.getMessage()) .show();
            }
        }

    }

    private void clearText() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtTeleNo.clear();
        txtPosition.clear();
        txtId.requestFocus();
    }

    public void btnNewEmployeeOnAction(ActionEvent actionEvent) {
        btnSaveEmployee.setText("Save Employee");
        clearText();
        txtId.setStyle("-fx-border-color:  #182AE2");
        txtName.setStyle("-fx-border-color:  #182AE2");
        txtAddress.setStyle("-fx-border-color:  #182AE2");
        txtTeleNo.setStyle("-fx-border-color:  #182AE2");
        txtPosition.setStyle("-fx-border-color:  #182AE2");
    }

    public void btnDeleteEmployeeOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("DELETE FROM employee WHERE id=?",txtId.getText())) {
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

    public void btnViewEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        employeeContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ViewEmployeeForm.fxml"));
        employeeContext.getChildren().add(parent);
    }
}
