package com.example.layeredarchitecture.dao.custom.impl;

import com.example.layeredarchitecture.dao.SQLUtil;
import com.example.layeredarchitecture.dao.custom.CustomerDAO;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public CustomerDTO search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.test("SELECT * FROM Customer WHERE id=?",id);
        resultSet.next();
        CustomerDTO customerDTO = new CustomerDTO(id + "", resultSet.getString("name"), resultSet.getString("address"));
        return customerDTO;
    }

    @Override
    public String genarateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.test("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
        if (resultSet.next()) {
            String id = resultSet.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }
    @Override
    public boolean exits(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.test("SELECT id FROM Customer WHERE id=? ",id);
        return resultSet.next();

    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.test("DELETE FROM Customer WHERE id=?",id);
    }
    @Override
    public boolean update(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return  SQLUtil.test("UPDATE Customer SET name=?, address=? WHERE id=?",customerDTO.getName(),customerDTO.getAddress(),customerDTO.getId());
    }
    @Override
    public boolean save(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.test("INSERT  into customer values (?,?,?)",customerDTO.getId(),customerDTO.getName(),customerDTO.getAddress());
    }
    @Override
    public ArrayList<CustomerDTO> getall() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.test("SELECT * from Customer");
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
