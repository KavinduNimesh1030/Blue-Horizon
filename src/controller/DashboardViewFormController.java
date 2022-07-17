package controller;

import db.PasswordDb;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class DashboardViewFormController {
    public Text txtName;
    public Text txtHeadName1;
    public Text txtJobType1;
    public ImageView SelectedImageView;

    public void initialize(){
        if (new PasswordDb().getPassword().equals("123")) {
            txtName.setText("Andria");
            txtHeadName1.setText("Andria Silva");
            txtJobType1.setText("Receptionist");
            Image image1 = new Image(DashboardViewFormController.class.getResourceAsStream("..\\asserts\\face2.jpg"));
            SelectedImageView.setImage(image1);
        }
    }
}
