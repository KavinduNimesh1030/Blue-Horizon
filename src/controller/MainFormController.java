package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import util.Loader;

import java.io.IOException;

public class MainFormController implements Loader {
    public AnchorPane context;

    public void recepOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("RecepLoginForm");
    }

    public void AdminOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("AdminLoginForm");
    }
    public void loadUi(String Location) throws IOException {
        context.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/"+Location+".fxml"));
        context.getChildren().add(parent);

    }
}
