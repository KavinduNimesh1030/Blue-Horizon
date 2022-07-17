package controller;

import db.PasswordDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.Loader;
import util.NotificationUtil;

import java.io.IOException;
import java.net.URL;

public class DashBoardFormController implements Loader {

    public Text txtName;
    public Text txtJobType;
    public Text txtHeadName;
    public AnchorPane DashboardContext;
    private int x;

    public void loadData(int x){
       /* this.x=x;
        System.out.println("l"+x);
        if (this.x==2) {
            System.out.println(x);
            txtHeadName.setText("Bordin silva");
            txtJobType.setText("Admin");
            txtName.setText(" Bordin !");

        }else if(this.x==1) {
            System.out.println(x);
            txtHeadName.setText("Sammy Mare");
            txtJobType.setText("Receptionist");
            txtName.setText(" Sammy !");
        }*/

    }

        public  void initialize() throws IOException {
        loadUi("DashboardViewForm");
        }



    public void openAddGuestOnAction(ActionEvent actionEvent) throws IOException {
        Image imageView = new Image(".\\asserts\\Warning.png");
        String password = new PasswordDb().getPassword();
        System.out.println(password);
        if (password.equals("1234")) {
            System.out.println("ok");

            loadUi("AddGuestForm");
        }else{
            System.out.println("not ok");
            //new Alert(Alert.AlertType.ERROR,"Only Admin Can Access").show();
            new NotificationUtil().notification("Accesses Only Admin","Click Hear to login Admin quickly",imageView,"AdminLoginForm");
        }
    }

    public void openMealPlanOnAction(ActionEvent actionEvent) throws IOException {
       loadUi("AddMealForm");
        //loadUi("ViewGuestForm");
    }

    public void openReservationOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ReservationForm");
    }

    public void openOrdersOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("OrderForm");
    }

    public void openRoomsOnAction(ActionEvent actionEvent) throws IOException {
     loadUi("RoomContextForm");

    }

    public void openEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        String password = new PasswordDb().getPassword();
        if (password.equals("1234")) {
            loadUi("AddEmployeeForm");
        }else {
            loadUi("ViewEmployeeForm");
        }
    }

    public void openDashboardOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("DashboardViewForm");

    }

    public void loadUi(String Location) throws IOException {
        DashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/"+Location+".fxml"));
        DashboardContext.getChildren().add(parent);


    }

    public void btnOpenIncomeOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("IncomeForm");
    }
}
