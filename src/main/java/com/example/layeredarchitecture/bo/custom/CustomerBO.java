package com.example.layeredarchitecture.bo.custom;

import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO {
     String generateNewId()  throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    boolean existCustomer(String id) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException,ClassNotFoundException;
    boolean saveCustomer(CustomerDTO customerDTO) throws SQLException ,ClassNotFoundException;
    ArrayList<CustomerDTO>  loadAllCustomers() throws SQLException ,ClassNotFoundException;
}
