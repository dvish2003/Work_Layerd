package com.example.layeredarchitecture.DAO;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer");

        ArrayList<CustomerDTO> allCustomer = new ArrayList<>();
        while ( rst.next()){
            CustomerDTO customerDTO = new CustomerDTO(rst.getString("id"),
                    rst.getString("name"),
            rst.getString("address"));
            allCustomer.add(customerDTO);
        }

        return allCustomer;
    }

    public void saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer (id,name, address) VALUES (?,?,?)");
        pstm.setString(1,customerDTO.getId());
        pstm.setString(2, customerDTO.getName());
        pstm.setString(3, customerDTO.getAddress());
        pstm.executeUpdate();

      //  tblCustomers.getItems().add(new CustomerTM(id, name, address));
    }
     public  void updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
         Connection connection = DBConnection.getDbConnection().getConnection();
         PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET name=?, address=? WHERE id=?");
         pstm.setString(1, customerDTO.getName());
         pstm.setString(2, customerDTO.getAddress());
         pstm.setString(3, customerDTO.getId());
         pstm.executeUpdate();
     }

     public  void delete (String id) throws SQLException, ClassNotFoundException {
         Connection connection = DBConnection.getDbConnection().getConnection();
         PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE id=?");
         pstm.setString(1, id);
         pstm.executeUpdate();

     }


     
     /*public  String generateNewId () throws SQLException, ClassNotFoundException {
         Connection connection = DBConnection.getDbConnection().getConnection();
         ResultSet rst = connection.createStatement().executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
        if (rst.next()){
            return rst.getString(1);
        }
        return null;

     }*/
     public  String generateNewId () throws SQLException, ClassNotFoundException {
         Connection connection = DBConnection.getDbConnection().getConnection();
         ResultSet rst = connection.createStatement().executeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
       if (rst.next()){
           return  rst.getString(1);
       }
       return null;

     }




    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT id FROM Customer WHERE id=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }
    public void SearchCustomer(String newValue) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer WHERE id=?");
        pstm.setString(1, newValue + "");
        ResultSet rst = pstm.executeQuery();
        rst.next();
/*
        ArrayList<CustomerDTO> allCustomer = new ArrayList<>();
        while ( rst.next()) {
            CustomerDTO customerDTO = new CustomerDTO(newValue + "", rst.getString("name"), rst.getString("address"));
       allCustomer.add(customerDTO);
        }
        return allCustomer;
*/
    }
    public ArrayList<String> LoadAllCustomerIds() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer");
          ArrayList<String> Ids = new ArrayList<>();

    while (rst.next()){
      //  cmbCustomerId.getItems().add(rst.getString("id"));
    Ids.add(rst.getString("id"));
    }
    return Ids;

    }
    public CustomerDTO findCustomer(String newValue) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();

        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer WHERE id=?");
        pstm.setString(1, newValue + "");
        ResultSet rst = pstm.executeQuery();
        rst.next();
        return  new CustomerDTO(newValue + "", rst.getString("name"), rst.getString("address"));


    }
}
