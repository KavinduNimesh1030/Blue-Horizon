package util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;

public class NotificationUtil implements Loader {
    public void notification(String title, String text, Image image,String path){
        Notifications notificationsBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(new ImageView(image))
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Click ");
                        try {
                            loadUi(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
        //notificationsBuilder.darkStyle();
        notificationsBuilder.show();

    }
    public void loadUi(String Location) throws IOException {
        URL resource = getClass().getResource("../view/"+Location+".fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

}
