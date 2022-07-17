package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.Room;
import util.CrudUtil;
import util.NotificationUtil;
import view.tm.GuestTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ViewGuestFormController extends ReservationViewFormController{
    public TableColumn colCustomerId;
    public TableColumn colRoomId;
    public TableColumn colReservationId;
    public TableColumn colCheckInDate;
    public TableColumn colCheckOutDate;
    public TableColumn colOption;
    public TableView tblViewGuest;
    public TextField txtSearch;
    public AnchorPane checkoutContext;
    public AnchorPane GuestViewContext;
    ObservableList<GuestTM> tmList = FXCollections.observableArrayList();
    FilteredList<GuestTM>filteredData = new FilteredList<>(tmList, b -> true);


    public void initialize() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory("customerId"));
        colRoomId.setCellValueFactory(new PropertyValueFactory("roomId"));
        colReservationId.setCellValueFactory(new PropertyValueFactory("reservationId"));
        colCheckInDate.setCellValueFactory(new PropertyValueFactory("checkinDate"));
        colCheckOutDate.setCellValueFactory(new PropertyValueFactory("checkoutDate"));
        colOption.setCellValueFactory(new PropertyValueFactory("btn"));
        loadData();
        SelectData();

    }

    private void SelectData() {
        String id ="";
        String cId= "";
        for (GuestTM tm:tmList) {
            id =tm.getRoomId();
            System.out.println(tm.getRoomId());
            cId = tm.getCustomerId();
        }

        String finalCId = cId;
        String finalId = id;
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(GuestTM -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (GuestTM.getCustomerId().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                }else if(GuestTM.getReservationId().toLowerCase().indexOf(lowerCaseFilter) != -1 ){
                    return true;
                }else if(GuestTM.getRoomId().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                }
                else
                     return false; // Does not match.
            });

        });

        SortedList<GuestTM> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tblViewGuest.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tblViewGuest.setItems(sortedData);
    }


    private void loadData() {
        String cId = "C001";
        String roomId = "R001";
        String resId = "RE001";
        String checkinDate = "03.03.2021";
        String checkoutDate = "03.03.2021";
        try {
            ResultSet result2 = CrudUtil.execute("SELECT *  FROM reservation");
            while (result2.next()) {
                System.out.println(1);
                resId = (result2.getString(1));
                checkinDate = (result2.getString(2));
                checkoutDate = (result2.getString(3));
                cId = (result2.getString(4));


                ResultSet result = CrudUtil.execute("SELECT roomId FROM Reservationdetail WHERE reservationId =?", result2.getString(1));
                while (result.next()) {
                    System.out.println(2);
                    roomId = (result.getString(1));
                }


                Button btn = new Button("Checkout");

                GuestTM tm = new GuestTM(
                        cId,
                        roomId,
                        resId,
                        checkinDate,
                        checkoutDate,
                        btn
                );
                tmList.add(tm);
                String finalResId = resId;
                String finalRoomId = roomId;
                btn.setOnAction((e) -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are You Sure?",
                            ButtonType.YES, ButtonType.NO);

                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {

                        tmList.remove(tm);
                        try {
                            CrudUtil.execute("DELETE FROM reservation WHERE id=?", finalResId);
                            CrudUtil.execute("DELETE FROM reservationdetail WHERE reservationId=?", finalResId);
                            CrudUtil.execute("UPDATE room SET isAvailable=? WHERE id=?","Available", finalRoomId);
                            //new Alert(Alert.AlertType.CONFIRMATION,"CheckOut done!").show();
                            Image imageView = new Image(".\\asserts\\Correct1.jpg");
                            new NotificationUtil().notification("Checkout successful","back to menu",imageView,"DashBoardForm");

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException classNotFoundException) {
                            classNotFoundException.printStackTrace();
                            new Alert(Alert.AlertType.ERROR).show();
                        }
                        // calculateTotal();

                    }


                });
            }


            tblViewGuest.setItems(tmList);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnAddReservationOnAction(ActionEvent actionEvent) throws IOException {
        GuestViewContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ReservationViewForm.fxml"));
       GuestViewContext.getChildren().add(parent);
    }
}


