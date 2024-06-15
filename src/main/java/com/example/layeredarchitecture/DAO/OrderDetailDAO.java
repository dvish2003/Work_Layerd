package com.example.layeredarchitecture.DAO;

import com.example.layeredarchitecture.controller.PlaceOrderFormController;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAO {
  private Connection connection = DBConnection.getDbConnection().getConnection();

    public OrderDetailDAO() throws SQLException, ClassNotFoundException {
    }



public boolean addOrderDetails(String orderId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        for (OrderDetailDTO detail : orderDetails) {
            String itemCode = detail.getItemCode();
            BigDecimal UnitPrice = detail.getUnitPrice();
            int qty = detail.getQty();
             Boolean added = saveOrderDetails(orderId,new OrderDetailDTO(itemCode, qty, UnitPrice));



            ItemDTO item = PlaceOrderFormController.findItem(detail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());
            ItemDAOImpl itemDAO = new ItemDAOImpl();
            int Done = itemDAO.updateItem(item.getQtyOnHand(), item.getCode(), item);

            if (added && !(Done > 0)){

                return false;
        }
        }


        return true;
    }

    private Boolean saveOrderDetails(String orderId, OrderDetailDTO orderDetailDTO) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)");

        stm.setString(1, orderId);
        stm.setString(2, orderDetailDTO.getItemCode());
        stm.setBigDecimal(3, orderDetailDTO.getUnitPrice());
        stm.setInt(4, orderDetailDTO.getQty());
        return stm.executeUpdate() == 1;
    }
}



