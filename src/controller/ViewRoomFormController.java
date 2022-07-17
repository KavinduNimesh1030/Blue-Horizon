package controller;

import db.Database;
import db.PasswordDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import model.Room;
import util.CrudUtil;
import util.Loader;
import util.NotificationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewRoomFormController {
    public TableView <Room>tblRoomView;
    public TableColumn colRoomId;
    public TableColumn ColCapacity;
    public TableColumn colType;
    public TableColumn ColCost;
    public TableColumn colIsAvailable;
    public TextField maintenanceContext;
    public TextField txtMaintained;
    public AnchorPane viewRoomContext;

    public void initialize(){

        colRoomId.setCellValueFactory(new PropertyValueFactory("id"));
        ColCapacity.setCellValueFactory(new PropertyValueFactory("capacity"));
        colType.setCellValueFactory(new PropertyValueFactory("type"));
        ColCost.setCellValueFactory(new PropertyValueFactory("costPerNight"));
        colIsAvailable.setCellValueFactory(new PropertyValueFactory("isAvailable"));

        try {
            loadAllRoom();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllRoom() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Room");
        ObservableList<Room> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Room(
                           result.getString("id"),
                            result.getString("capacity"),
                            result.getString("type"),
                            result.getDouble("costPerNight"),
                            result.getString("isAvailable")
                )
            );

        }
        tblRoomView.setItems(obList);

    }


    public void btnOpenNewRoom(ActionEvent actionEvent) throws IOException {
        Image imageView = new Image(".\\asserts\\Warning.png");
        if(new PasswordDb().getPassword().equals("1234")){
            viewRoomContext.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("../view/AddRoomForm.fxml"));
           viewRoomContext.getChildren().add(parent);
        }else{
            new NotificationUtil().notification("Accesses Only Admin","Click Hear to login Admin quickly",imageView,"AdminLoginForm");
        }

    }

    public void btnAddMaintenance(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        System.out.println("work");
        ResultSet result = CrudUtil.execute("SELECT id FROM Room");
        while (result.next()) {
            if (result.getString("id").equals(txtMaintained.getText())) {
                System.out.println("Has");
                if(CrudUtil.execute("UPDATE Room SET isAvailable=?  WHERE id=?","NotAvailable",result.getString("id"))) {
                    tblRoomView.refresh();
                    initialize();
                }
            }
        }
    }
}
