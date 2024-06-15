package com.example.layeredarchitecture.DAO;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public interface CustomerDAO {
     ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException;

     void saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException ;

     void updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException ;

     void delete (String id) throws SQLException, ClassNotFoundException ;


     String generateNewId () throws SQLException, ClassNotFoundException ;

     boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;
}
