package controller;

import model.Customer;
import model.Meal;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MealCrudController {
    public static Meal getMeal(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM food WHERE id=?", id);
        if (result.next()) {
            return new Meal(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4)

            );
        }
        return null;
    }
    public static ArrayList<String> getMealCodes() throws SQLException, ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT id FROM food");
        ArrayList<String> codeList = new ArrayList<>();
        while (result.next()) {
            codeList.add(result.getString(1));
        }
        return codeList;
    }
}
