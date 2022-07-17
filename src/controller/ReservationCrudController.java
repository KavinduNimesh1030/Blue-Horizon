package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationCrudController {

    public String getReservationId() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT id FROM reservation ORDER BY id DESC LIMIT 1");
        int a = 0;
        String newVersion;
        if (set.next()) {
            String version = set.getString(1);
            int i = (Integer.parseInt(version.substring(2, version.length())) + 1);

            if (i == 100) {
                newVersion = "RE" + i;
            } else {
                newVersion = "RE0" + i;
            }
            return newVersion;
            /*-----D001-> D002----*/
        } else {
            return "D001";

        }
    }


}
