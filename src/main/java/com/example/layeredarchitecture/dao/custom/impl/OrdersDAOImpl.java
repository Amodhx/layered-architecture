package com.example.layeredarchitecture.dao.custom.impl;

import com.example.layeredarchitecture.dao.SQLUtil;
import com.example.layeredarchitecture.dao.custom.OrdersDAO;
import com.example.layeredarchitecture.model.OrderDTO;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class OrdersDAOImpl implements OrdersDAO {
    @Override
    public boolean save(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.test("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)",orderDTO.getOrderId(),Date.valueOf(orderDTO.getOrderDate()),orderDTO.getCustomerId());

    }

    @Override
    public ArrayList<OrderDTO> getall() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ResultSet getOrderId(String oid) throws SQLException, ClassNotFoundException {
       return SQLUtil.test("SELECT oid FROM `Orders` WHERE oid=?",oid);
    }

    @Override
    public OrderDTO search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String genarateNewId(){
        try {
            ResultSet rst = SQLUtil.test("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

            return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }

    @Override
    public boolean exits(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }
}
