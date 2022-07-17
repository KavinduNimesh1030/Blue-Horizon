package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Room;
import util.CrudUtil;
import util.Loader;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class ReservationViewFormController extends  ReservationFormController implements Loader{
    public TextField txtAvailability;
    public DatePicker datCheckInDate;
    public ComboBox cmbRoomType;
    public ComboBox cmbCapacity;
    public DatePicker datCheckOutDate;
    public String roomId;
    public String roomType;
    public String roomCapacity;
    public  double roomCost;
    public static String id;
    private static LocalDate checkInDate;
    private static LocalDate checkOutDate;
    private static String availableRoomId;
    public AnchorPane DashBoardContext;

    public void initialize(){

        ObservableList<String> obList1= FXCollections.observableArrayList();
        obList1.add("Single");
        obList1.add("Double");
        obList1.add("Triple");
        obList1.add("Quad");

        ObservableList<String>obList2= FXCollections.observableArrayList();
        obList2.add("Vip");
        obList2.add("Business");
        obList2.add("Economy");

        cmbRoomType.setItems(obList2);
        cmbCapacity.setItems(obList1);

        datCheckOutDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = datCheckInDate.getValue().plusDays(1);


                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        datCheckInDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();


                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }


    public void btnCheckingOnAction(ActionEvent actionEvent) throws IOException {
        System.out.println(123);

            DashBoardContext.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ReservationConfirmForm.fxml"));
            DashBoardContext.getChildren().add(parent);

//        URL resource = getClass().getResource("../view/ReservationConfirmForm.fxml");
//        Parent load = FXMLLoader.load(resource);
//        Scene scene = new Scene(load);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.show();
        checkInDate = datCheckInDate.getValue();
        checkOutDate = datCheckOutDate.getValue();
        availableRoomId= txtAvailability.getText();

    }
    public void loadUi(String Location) throws IOException {
        ReservationContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/"+Location+".fxml"));
        ReservationContext.getChildren().add(parent);
    }
    public static LocalDate getCheckInDate(){

        return checkInDate;
    }
    public  LocalDate getCheckOutDate(){
        return checkOutDate;
    }
    public String getAvailableRoomId(){
        return availableRoomId;
    }

    public void btnRoomSearchOnAction(ActionEvent actionEvent) throws IOException {
        try {
            ResultSet result = CrudUtil.execute("SELECT  * FROM room WHERE capacity=? and type=? and isAvailable ='Available'",cmbCapacity.getValue(),cmbRoomType.getValue());
            if (result.next()) {
                txtAvailability.setText(result.getString(1));
                roomId = txtAvailability.getText();
                id =roomId;
                passData();
                URL resource = getClass().getResource("../view/RoomShortForm.fxml");
                Parent load = FXMLLoader.load(resource);
                Scene scene = new Scene(load);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();



            }else {
                new Alert(Alert.AlertType.ERROR,"Not Available For Now").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Room passData() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT  * FROM room WHERE id=?",id);
        System.out.println("Come");
        if(result.next()) {
            return new Room(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getString(5)

            );
        }
        return null;

    }

    public void btnCheckoutOnAction(ActionEvent actionEvent) throws IOException {
        DashBoardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ViewGuestForm.fxml"));
        DashBoardContext.getChildren().add(parent);
    }
}
