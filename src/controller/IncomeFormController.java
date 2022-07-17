package controller;

import javafx.scene.control.Label;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncomeFormController {
    public Label textTotalArrivals;
    public Label textTotalOrders;
    public Label textResMonthlyIncome;
    public Label textOrderMonthlyIncome;
    public Label textResAnnuallyIncome;
    public Label textOrderAnnuallyIncome;
    public Label textTotalIncome;
    public Label textTotalResIncome;
    public Label textTotalOrderIncome;

    public void initialize() {
        try {
            ResultSet result = CrudUtil.execute("SELECT SUM(totalCost) FROM reservation INNER JOIN reservationdetail o on reservation.id = o.reservationId");
            ResultSet result6 = CrudUtil.execute("SELECT SUM(totalCost) FROM reservation INNER JOIN reservationdetail o on reservation.id = o.reservationId where startDate > now() - INTERVAL 1 year");
            ResultSet result7 = CrudUtil.execute("SELECT SUM(qty*unitPrice) FROM `order`INNER JOIN orderdetail o on `order`.id = o.orderId where date > now() - INTERVAL 1 year");
            ResultSet result1 = CrudUtil.execute("SELECT SUM(qty*unitPrice) FROM `order`INNER JOIN orderdetail o on `order`.id = o.orderId");
            ResultSet result2 = CrudUtil.execute("SELECT SUM(unitPrice) FROM `order` INNER JOIN orderdetail o on `order`.id = o.orderId where date > now() - INTERVAL 1 month");
            ResultSet result3 = CrudUtil.execute("SELECT SUM(totalCost) FROM reservation INNER JOIN reservationdetail o on reservation.id = o.reservationId where startDate > now() - INTERVAL 1 month");
            ResultSet result4 = CrudUtil.execute("SELECT COUNT(id)FROM reservation");
            ResultSet result5 = CrudUtil.execute("SELECT COUNT(id) FROM `order`");
            while (result4.next()) {
                textTotalArrivals.setText(result4.getString(1));
            }
            while (result5.next()) {
                textTotalOrders.setText(result5.getString(1));
            }
            double total = 0;
            double ototal = 0;
            double rtotal = 0;
            while (result.next()){
               rtotal = Double.parseDouble((result.getString(1)));
               textTotalResIncome.setText(result.getString(1));

            }
            while (result1.next()){
                ototal= Double.parseDouble(result1.getString(1));
                textTotalOrderIncome.setText(result1.getString(1));
            }
            while (result2.next()){
                System.out.println(result2.getString(1));
                 textOrderMonthlyIncome.setText(result2.getString(1));

            }
            System.out.println("-----------------------------");
            while (result3.next()){
                System.out.println(result3.getString(1));
                textResMonthlyIncome.setText(result3.getString(1));

            }
            while (result6.next()){
                textResAnnuallyIncome.setText(result6.getString(1));

            }
            while (result7.next()){
                textOrderAnnuallyIncome.setText(result7.getString(1));
            }
            textTotalIncome.setText(String.valueOf(rtotal+ototal));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
       } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
   }

    }


