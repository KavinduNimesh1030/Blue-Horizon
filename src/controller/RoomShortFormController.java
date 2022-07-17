package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Room;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class RoomShortFormController extends Frame {

    public Text lblRoomId;
    public Label lblRoomType;
    public Label lblRoomCapacity;
    public Label lblRoomCost;
    public ImageView RoomImageview;
    public Button closeButton;

    public void initialize(){
        try {
            setData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setData() throws SQLException, ClassNotFoundException {
        Room r = ReservationViewFormController.passData();
        Image imageView = new Image(".\\asserts\\vip.jpg");
        Image imageView2 = new Image(".\\asserts\\Economy.jpg");
        if (r != null) {
            if(r.getType().equals("Vip")){
                RoomImageview.setImage(imageView);
            }else if(r.getType().equals("Economy")){
                RoomImageview.setImage(imageView2);
            }
            lblRoomId.setText(r.getId());
            lblRoomType.setText(r.getType());
            lblRoomCapacity.setText(r.getCapacity());
            lblRoomCost.setText(String.valueOf(r.getCostPerNight()));
        }
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();

    }
}
