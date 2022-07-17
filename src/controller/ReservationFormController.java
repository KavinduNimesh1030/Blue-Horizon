package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import sun.applet.Main;
import util.Loader;

import java.io.IOException;

public class ReservationFormController implements Loader {
    public AnchorPane ReservationContext;
    private static String url;


    public void initialize() throws IOException {
        loadUi("ReservationViewForm");
    }

    @Override
    public void loadUi(String Location) throws IOException {
        ReservationContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/"+Location+".fxml"));
        ReservationContext.getChildren().add(parent);

    }
    public void setUrl(String url) throws IOException {
        //System.out.println(url);
        System.out.println(url);
        loadUi(url);
    }


}
