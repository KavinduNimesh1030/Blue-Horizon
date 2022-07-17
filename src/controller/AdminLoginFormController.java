package controller;

import db.PasswordDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.NotificationUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class AdminLoginFormController {
    public TextField txtUserName;
    public TextField pwdPassword;
    public CheckBox checkBox;
    public PasswordField pwdPasswordField;
    private  int attempts;
    public String password;
    int x;


    
    public void adminLoginOnAction(ActionEvent actionEvent) throws IOException {
        attempts++;
        if (pwdPassword.getText().equals("")) {
            pwdPassword.setText(pwdPasswordField.getText());
            new PasswordDb().setPassword(pwdPassword.getText());
        }else if(pwdPasswordField.getText().equals("")) {
            pwdPasswordField.setText(pwdPassword.getText());
            new PasswordDb().setPassword(pwdPasswordField.getText());
        }
        //new PasswordDb().setPassword(pwdPasswordField.getText());
        pwdPassword.setText(pwdPasswordField.getText());
        if (attempts <= 3) {
            if (txtUserName.getText().equals("Admin") && pwdPassword.getText().equals("1234")) {
                    password=pwdPassword.getText();
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,
                        "Login Success",
                        ButtonType.OK,ButtonType.CANCEL);

                Optional<ButtonType> buttonType= alert.showAndWait();
                if(buttonType.get().equals(ButtonType.OK)){
                    URL resource = getClass().getResource("../view/DashBoardForm.fxml");
                    Parent load = FXMLLoader.load(resource);
                    Scene scene = new Scene(load);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    x=1;
                    Image imageView = new Image(".\\asserts\\Correct1.jpg");
                    new NotificationUtil().notification("Login Success","Back to menu",imageView,"MainForm");
                    //new DashBoardFormController().loadData(x);
                }

            } else {
                new Alert(Alert.AlertType.WARNING, "Try again!").show();

            }

        } else {
            txtUserName.setEditable(false);
            pwdPassword.setEditable(false);

        }


    }

    public void checkBoxOnAction(ActionEvent actionEvent) {
        if (checkBox.isSelected()) {
            pwdPassword.setText(pwdPasswordField.getText());
            System.out.println("Select");
            pwdPasswordField.setVisible(false);
            pwdPassword.setVisible(true);
        }else {
            System.out.println("not");
            pwdPasswordField.setText(pwdPassword.getText());
            pwdPasswordField.setVisible(true);
            pwdPassword.setVisible(false);
        }
    }
}
