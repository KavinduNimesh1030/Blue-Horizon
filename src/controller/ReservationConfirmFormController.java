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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Customer;
import model.Payment;
import model.Reservation;
import model.ReservationDetail;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReservationConfirmFormController {
    public TextField txtPaymentId;
    public ComboBox cmbCustomerId;
    public TextField txtCustomerName;
    public TextField txtReservationId;
    public TextField txtRoomId;
    public Text textCost;
    static  String roomId;
    static LocalDate day;
    public Button btnSaveDetail;
    public AnchorPane ReservationContext;

    public void initialize(){
        setCustomerIds();
        setRoomIds();
        reports();
        btnSaveDetail.setDisable(true);
        String roomId = new ReservationViewFormController().getAvailableRoomId();
        setReservationId();
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                setCustomerName((String) newValue);
            }
        });

    }

    private void reports() {
        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/ReservationReceipt.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JREmptyDataSource(1));
            JasperViewer.viewReport(jasperPrint, false);


        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private void setReservationId() {
        try {
            System.out.println(new ReservationCrudController().getReservationId());
            txtReservationId.setText(new ReservationCrudController().getReservationId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setRoomIds() {
        txtRoomId.setText(new ReservationViewFormController().getAvailableRoomId());
        String id =new ReservationViewFormController().getAvailableRoomId();
        roomId=new ReservationViewFormController().getAvailableRoomId();
        System.out.println("static"+roomId);
        System.out.println(id);
    }

    private void setCustomerIds() {
        try {
            ObservableList<String> cIdObList = FXCollections.observableList(CustomerCrudController.getCustomerIds());
            cmbCustomerId.setItems(cIdObList);
            //setCustomerName();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void btnSaveReservationOnAction(ActionEvent actionEvent) {

        System.out.println(new ReservationViewFormController().getCheckInDate());
        System.out.println(new ReservationViewFormController().getCheckOutDate());
        System.out.println(new ReservationViewFormController().getAvailableRoomId());
        LocalDate startDate = new ReservationViewFormController().getCheckInDate();
        LocalDate endDate   = new ReservationViewFormController().getCheckOutDate();
        String roomId = new ReservationViewFormController().getAvailableRoomId();
        System.out.println(new ReservationViewFormController().getAvailableRoomId());
         Reservation r = new Reservation(
             txtReservationId.getText(),startDate,endDate, (String) cmbCustomerId.getValue()
         );
        try {
            CrudUtil.execute("SET FOREIGN_KEY_CHECKS=0;");
            if(CrudUtil.execute("INSERT INTO Reservation VALUES (?,?,?,?)",r.getId(),r.getStartDate(),r.getEndDate(),r.getCustomerId())){
                saveReservationDetail(roomId);
                new Alert(Alert.AlertType.CONFIRMATION,"Reservation Done").show();
                if(CrudUtil.execute("UPDATE room SET isAvailable=? WHERE id=?","NotAvailable",txtRoomId.getText())) {
                    System.out.println("Update Room isAvailable");
                    txtRoomId.clear();
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }


    private void saveReservationDetail(String roomId) {
        LocalDate startDate = new ReservationViewFormController().getCheckInDate();
        LocalDate endDate = new ReservationViewFormController().getCheckOutDate();
        long day = ChronoUnit.DAYS.between(startDate, endDate);
        ReservationDetail rv = new ReservationDetail(
                txtReservationId.getText(),String.valueOf(roomId),Double.parseDouble(textCost.getText()),(int)day
        );
        try {
            if(CrudUtil.execute("INSERT INTO reservationDetail VALUES(?,?,?,?)",rv.getReservationId(),rv.getRoomId(),rv.getTotalCost(),rv.getDuration())){
                System.out.println("Reservation Detail also done");
                paymentDetail();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("not Done");
        }
    }

    private void paymentDetail() {
        Payment p = new Payment(
                txtPaymentId.getText(),
                txtReservationId.getText(),
                "visa",
                Double.parseDouble(textCost.getText())
        );
        try {
            if (CrudUtil.execute("INSERT INTO payment VALUES(?,?,?,?)",p.getId(),p.getReservationId(),p.getPaymentType(),p.getTotalPayment())) {
                System.out.println("Payment done");
                //btnSaveDetail.setDisable(false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Payment not Done");
            new Alert(Alert.AlertType.ERROR,e.getMessage());
        }
    }

    private void getRoomCost(String roomId,long day) {
        try {
            ResultSet result = CrudUtil.execute("SELECT costPerNight FROM room WHERE id=?",roomId);
            if(result.next()){
                double cost = result.getDouble("costPerNight");
                System.out.println(cost);
                Double payment =cost*day;
                textCost.setText(String.valueOf(payment));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerName(String newValue){
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Customer WHERE id=?",newValue);
            if(result.next()){
                txtCustomerName.setText(result.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public  void textFields_Key_Released(KeyEvent keyEvent) {
        String roomId = new ReservationViewFormController().getAvailableRoomId();
        System.out.println(new ReservationViewFormController().getAvailableRoomId());
        txtRoomId.setText(roomId);
        LocalDate startDate = new ReservationViewFormController().getCheckInDate();
        LocalDate endDate = new ReservationViewFormController().getCheckOutDate();
        long day = ChronoUnit.DAYS.between(startDate, endDate);
        System.out.println(day);
        getRoomCost(roomId,day);
        }

    public void txtPayment_Key_Released(KeyEvent keyEvent) {
        btnSaveDetail.setDisable(false);
    }

    public void btnOpenOrderOnAction(ActionEvent actionEvent) throws IOException {
        ReservationContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/OrderForm.fxml"));
        ReservationContext.getChildren().add(parent);
    }

    public void btnCheckoutOnAction(ActionEvent actionEvent) throws IOException {
        ReservationContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ViewGuestForm.fxml"));
        ReservationContext.getChildren().add(parent);
    }
}




