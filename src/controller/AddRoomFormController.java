package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Room;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddRoomFormController {
    public TextField txtHeadId;
    public TextField txtId;
    public ComboBox cmbCapacity;
    public TextField txtPrice;
    public ComboBox cmbType;
    public ComboBox cmbIsAvailable;
    public Button btnSaveRoom;
    public AnchorPane AddRoomContext;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        ObservableList<String>obList1= FXCollections.observableArrayList();
        obList1.add("Single");
        obList1.add("Double");
        obList1.add("Triple");
        obList1.add("Quad");

        ObservableList<String>obList2= FXCollections.observableArrayList();
        obList2.add("Vip");
        obList2.add("Business");
        obList2.add("Economy");

        ObservableList<String>obList3= FXCollections.observableArrayList();
        obList3.add("Available");
        obList3.add("Not Available");

        cmbType.setItems(obList2);
        cmbCapacity.setItems(obList1);
        cmbIsAvailable.setItems(obList3);

        Pattern idPattern = Pattern.compile("^(R)[0-9]{3,5}$");
        Pattern pricePattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$");

        map.put(txtId,idPattern);
        map.put(txtPrice,pricePattern);

    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        Search();
        btnSaveRoom.setText("Update Room");
    }

    public void btnNewRoomOnAction(ActionEvent actionEvent) {
        btnSaveRoom.setText("Save Room");
        clearText();
        txtId.setStyle("-fx-border-color:  #182AE2");
        txtPrice.setStyle("-fx-border-color:  #182AE2");
    }

    private void clearText() {
        txtId.clear();
        cmbCapacity.setValue(null);
        cmbType.setValue(null);
        txtPrice.clear();
        cmbIsAvailable.setValue(null);
        txtId.requestFocus();
    }

    private void Search() {
            try {
                ResultSet result = CrudUtil.execute("SELECT * FROM Room WHERE id=?", txtHeadId.getText());
                if (result.next()) {
                    txtId.setText(result.getString(1));
                    cmbCapacity.setValue(result.getString(2));
                    cmbType.setValue(result.getString(3));
                    txtPrice.setText(String.valueOf(result.getDouble(4)));
                    cmbIsAvailable.setValue(result.getString(5));
                } else {
                    new Alert(Alert.AlertType.WARNING, "Empty Result").show();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

    }

    public void btnRoomOnAction(ActionEvent actionEvent) {
        Save();

    }

    private void Save() {
        if (btnSaveRoom.getText().equals("Save Room")) {
            Room r = new Room(
                    txtId.getText(),  ((String) cmbCapacity.getValue()),((String) cmbType.getValue()), Double.parseDouble(txtPrice.getText()), (String) cmbIsAvailable.getValue()
            );

            try {
                if (CrudUtil.execute("INSERT INTO Room VALUES (?,?,?,?,?)", r.getId(), r.getCapacity(), r.getType(), r.getCostPerNight(), r.getIsAvailable())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!!").show();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                e.printStackTrace();
            }
        }else {
            Room r = new Room(
                    txtId.getText(), ((String) cmbCapacity.getValue()),((String) cmbType.getValue()), Double.parseDouble(txtPrice.getText()), (String) cmbIsAvailable.getValue()
            );
            try {
                if(CrudUtil.execute("UPDATE Room SET capacity=? , type=? , costPerNight=? ,isAvailable=?  WHERE id=?", r.getCapacity(), r.getType(), r.getCostPerNight(), r.getIsAvailable(),r.getId())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated").show();
                    clearText();
                    btnSaveRoom.setText("Save Room");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSaveRoom);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map, btnSaveRoom);

            if (response instanceof TextField) {
                TextField txtField = (TextField) response;
                txtField.requestFocus();
            } else if (response instanceof Boolean) {
                System.out.println("Work");
                Save();

            }
        }
    }

    public void btnViewRoomOnAction(ActionEvent actionEvent) throws IOException {

       AddRoomContext.getChildren().clear();
       Parent parent = FXMLLoader.load(getClass().getResource("../view/ViewRoomForm.fxml"));
       AddRoomContext.getChildren().add(parent);
    }
}
