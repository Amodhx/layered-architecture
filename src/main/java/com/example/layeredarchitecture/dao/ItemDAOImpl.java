package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl {
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
        pstm.setString(1, itemDTO.getDescription());
        pstm.setBigDecimal(2, itemDTO.getUnitPrice());
        pstm.setInt(3, itemDTO.getQtyOnHand());
        pstm.setString(4, itemDTO.getCode());
        int executed = pstm.executeUpdate();
        return executed > 0 ;
    }
    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)");
        preparedStatement.setString(1,itemDTO.getCode());
        preparedStatement.setString(2,itemDTO.getDescription());
        preparedStatement.setInt(3,itemDTO.getQtyOnHand());
        preparedStatement.setBigDecimal(4,itemDTO.getUnitPrice());
        int executed = preparedStatement.executeUpdate();
        return executed > 0;

    }
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection .getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Item WHERE code=?");
        preparedStatement.setString(1,code);
        int executed = preparedStatement.executeUpdate();
        return executed>0;
    }
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Item");
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<ItemDTO> arrayList = new ArrayList<>();
        while (resultSet.next()){
            ItemDTO itemDTO = new ItemDTO(resultSet.getString(1),resultSet.getString(2),resultSet.getBigDecimal(3),resultSet.getInt(4));
            arrayList.add(itemDTO);
        }
        return arrayList;
    }
}
