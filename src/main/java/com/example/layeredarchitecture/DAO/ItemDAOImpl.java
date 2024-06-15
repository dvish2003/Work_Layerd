package com.example.layeredarchitecture.DAO;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl {

    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Item");

        ArrayList<ItemDTO> allItem = new ArrayList();
        while (rst.next()){
            ItemDTO itemDTO = new ItemDTO
                    (rst.getString("code"),
                    rst.getString("description"),
                    rst.getBigDecimal("unitPrice"),
                    rst.getInt("qtyOnHand")
                    );
            allItem.add(itemDTO);


        }

        return allItem;
    }

    public  void save(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)");
        pstm.setString(1,itemDTO.getCode());
        pstm.setString(2, itemDTO.getDescription());
        pstm.setBigDecimal(3,itemDTO.getUnitPrice());
        pstm.setInt(4,itemDTO.getQtyOnHand());
        pstm.executeUpdate();

    }
    public  void update(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
        pstm.setString(1,itemDTO.getDescription());
        pstm.setBigDecimal(2,itemDTO.getUnitPrice());
        pstm.setInt(3,itemDTO.getQtyOnHand());
        pstm.setString(4,itemDTO.getCode());

        pstm.executeUpdate();
    }
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Item WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();


    }
     public  void delete (String code) throws SQLException, ClassNotFoundException {
         Connection connection = DBConnection.getDbConnection().getConnection();
         PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");
         pstm.setString(1, code);
         pstm.executeUpdate();
     }

     public  String generateNextId() throws SQLException, ClassNotFoundException {
         Connection connection = DBConnection.getDbConnection().getConnection();
         ResultSet rst = connection.createStatement().executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
   if(rst.next()){
       return rst.getString(1);

   }
   return null;
     }

     public ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException {
         Connection connection = DBConnection.getDbConnection().getConnection();
         Statement stm = connection.createStatement();
         ResultSet rst = stm.executeQuery("SELECT * FROM Item");
         ArrayList<String> allIds = new ArrayList<>();

         while (rst.next()) {
             allIds.add(rst.getString("code"));
         }
         return allIds;
     }
     public  ArrayList<ItemDTO> findItem(String code) throws SQLException, ClassNotFoundException {Connection connection = DBConnection.getDbConnection().getConnection();
         PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
         pstm.setString(1, code);
         ResultSet rst = pstm.executeQuery();
         ArrayList<ItemDTO>  arrayList = new ArrayList<>();
         while(rst.next()){
           ItemDTO itemDTO = new ItemDTO
                   (rst.getString("code"),
                           rst.getString("description"),
                           rst.getBigDecimal("unitPrice"),
                           rst.getInt("qtyOnHand")
                   );
           arrayList.add(itemDTO);

       }

         return  arrayList;

     }
     public int updateItem(int qtyOnHand,String code,ItemDTO item) throws SQLException, ClassNotFoundException {

         Connection connection = DBConnection.getDbConnection().getConnection();
             connection.setAutoCommit(false);
             PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
             pstm.setString(1, item.getDescription());
             pstm.setBigDecimal(2, item.getUnitPrice());
             pstm.setInt(3, qtyOnHand);
             pstm.setString(4, code);
             return  pstm.executeUpdate();



}
public ItemDTO findItemCom(String newItemCode) throws SQLException, ClassNotFoundException {
    Connection connection = DBConnection.getDbConnection().getConnection();
    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
    pstm.setString(1, newItemCode + "");
    ResultSet rst = pstm.executeQuery();
    rst.next();

    return new ItemDTO(newItemCode + "", rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));

}
}
/*  public boolean updateItem(int qtyOnHand,String code,ItemDTO item) throws SQLException {
         Connection connection = null;
         try {
             connection = DBConnection.getDbConnection().getConnection();
             connection.setAutoCommit(false);
             PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
             pstm.setString(1, item.getDescription());
             pstm.setBigDecimal(2, item.getUnitPrice());
             pstm.setInt(3, qtyOnHand);
             pstm.setString(4, code);

             if (!(pstm.executeUpdate() > 0)) {
                 connection.rollback();
                 connection.setAutoCommit(true);
                 return false;
             }
             connection.commit();
             connection.setAutoCommit(true);
             return true;
         } catch (SQLException throwables) {
             if (connection != null) {
                 connection.rollback();
                 connection.setAutoCommit(true);
             }
             throwables.printStackTrace();
             return false;
         } catch (ClassNotFoundException e) {
             throw new RuntimeException(e);
         }
     }*/


