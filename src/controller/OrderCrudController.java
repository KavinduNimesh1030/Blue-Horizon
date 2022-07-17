package controller;

import model.Order;
import model.OrderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {
    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO `Order` VALUES(?,?,?)",
                order.getId(), order.getDate(), order.getCustomerId());
    }
    public boolean saveOrderDetails(ArrayList<OrderDetails> details) throws SQLException, ClassNotFoundException {
        for (OrderDetails det:details
        ) {
            boolean isDetailsSaved=CrudUtil.execute("INSERT INTO OrderDetail VALUES(?,?,?,?)",
                    det.getOrderId(),det.getItemCode(),det.getQty(),det.getUnitPrice());

        }
        return true;
    }
    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT id FROM `Order` ORDER BY id DESC LIMIT 1");
        int a=0;
        String newVersion;
        if (set.next()){
            String version = set.getString(1);
            int i = (Integer.parseInt(version.substring(1, version.length()))+1);

            if (i>=10) {
                newVersion = "D0" +  i;
            }else {
                newVersion = "D00" + i;
            }
            System.out.println(newVersion);
            return newVersion;

            /*-----D001-> D002----*/
        }else{
            return "D001";
        }
    }


}
