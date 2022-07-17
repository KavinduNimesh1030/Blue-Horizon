package controller;

import db.PasswordDb;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import util.Loader;

import java.io.IOException;

public class RoomContextFormController implements Loader {
    public AnchorPane RoomContext;

    public void initialize() throws IOException {
        String password = new PasswordDb().getPassword();
        System.out.println(password);
        if (password.equals("1234")) {
            System.out.println("ok");
            loadUi("AddRoomForm");
        }else{
            loadUi("ViewRoomForm");
        }
    }

    @Override
    public void loadUi(String Location) throws IOException {
        RoomContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/"+Location+".fxml"));
        RoomContext.getChildren().add(parent);
    }
}
