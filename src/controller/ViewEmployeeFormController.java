package controller;

import db.PasswordDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Employee;
import model.Room;
import util.CrudUtil;
import util.NotificationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewEmployeeFormController {

    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colTeleNo;
    public TableColumn colPosition;
    public TableView tblEmployeeView;
    public AnchorPane employeeContext;

    public  void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colTeleNo.setCellValueFactory(new PropertyValueFactory("teleNo"));
        colPosition.setCellValueFactory(new PropertyValueFactory("position"));
        try {
            setData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setData() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM employee");
        ObservableList<Employee> obList = FXCollections.observableArrayList();
        while (result.next()){
            obList.add(
                    new Employee(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5)
                    )
            );

        }
        tblEmployeeView.setItems(obList);


    }

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        Image imageView = new Image(".\\asserts\\Warning.png");
        String password = new PasswordDb().getPassword();
        System.out.println(password);
        if (password.equals("1234")) {
            employeeContext.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("../view/ AddEmployeeForm.fxml"));
            employeeContext.getChildren().add(parent);
        }else{
            new NotificationUtil().notification("Accesses Only Admin","Click Hear to login Admin quickly",imageView,"AdminLoginForm");
        }


    }
}
