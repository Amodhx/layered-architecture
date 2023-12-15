package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl {
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Customer WHERE id=?");
        preparedStatement.setString(1,id);
        int executed = preparedStatement.executeUpdate();
        return executed>0;
    }
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Customer SET name=?, address=? WHERE id=?");
        preparedStatement.setString(1,customerDTO.getName());
        preparedStatement.setString(2,customerDTO.getAddress());
        preparedStatement.setString(3,customerDTO.getId());

        int executed = preparedStatement.executeUpdate();
        return executed > 0;
    }
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT  into customer values (?,?,?)");
        preparedStatement.setString(1,customerDTO.getId());
        preparedStatement.setString(2,customerDTO.getName());
        preparedStatement.setString(3,customerDTO.getAddress());

        int executed = preparedStatement.executeUpdate();
        return executed>0;
    }
    public ArrayList<CustomerDTO> getCustomerData() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Customer");
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        while (resultSet.next()){
            CustomerDTO customerDTO = new CustomerDTO(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"));

            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }
}
