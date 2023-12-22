package com.example.layeredarchitecture.dao.custom.impl;

import com.example.layeredarchitecture.dao.SQLUtil;
import com.example.layeredarchitecture.dao.custom.ItemDAO;
import com.example.layeredarchitecture.model.ItemDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ItemDTO search(String id) throws SQLException, ClassNotFoundException {
        ResultSet test = SQLUtil.test("SELECT * FROM Item WHERE code=?", id);
        ItemDTO itemDTO = null;
        while (test.next()){
             itemDTO  = new ItemDTO(test.getString(1),test.getString(2),test.getBigDecimal(3),test.getInt(4));

        }
        return itemDTO;
    }
    @Override
    public String genarateNewId() throws SQLException, ClassNotFoundException {
        ResultSet test = SQLUtil.test("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");
        if (test.next()) {
            String id = test.getString("code");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }
    @Override
    public boolean exits(String code) throws SQLException, ClassNotFoundException{
        ResultSet resultSet = SQLUtil.test("SELECT code FROM Item WHERE code=?",code);
        return resultSet.next();

    }
    @Override
    public boolean update(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.test("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",itemDTO.getDescription(),itemDTO.getUnitPrice(),itemDTO.getQtyOnHand(),itemDTO.getCode());
    }
    @Override
    public boolean save(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
       return SQLUtil.test("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)",itemDTO.getCode(),itemDTO.getDescription(),itemDTO.getQtyOnHand(),itemDTO.getUnitPrice());
    }
    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.test("DELETE FROM Item WHERE code=?",code);
    }
    @Override
    public ArrayList<ItemDTO> getall() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.test("SELECT * FROM Item");
        ArrayList<ItemDTO> arrayList = new ArrayList<>();
        while (resultSet.next()){
            ItemDTO itemDTO = new ItemDTO(resultSet.getString(1),resultSet.getString(2),resultSet.getBigDecimal(3),resultSet.getInt(4));
            arrayList.add(itemDTO);
        }
        return arrayList;
    }
}
