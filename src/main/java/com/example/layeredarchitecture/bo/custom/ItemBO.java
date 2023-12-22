package com.example.layeredarchitecture.bo.custom;

import com.example.layeredarchitecture.model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO {
    String genarateNewId() throws SQLException, ClassNotFoundException;;
    boolean existItem(String code) throws SQLException, ClassNotFoundException;
    boolean updateItem(ItemDTO itemDTO) throws SQLException ,ClassNotFoundException;
    boolean saveItem(ItemDTO itemDTO) throws SQLException,ClassNotFoundException;
    ArrayList<ItemDTO> getAllItems() throws SQLException,ClassNotFoundException;
    boolean deleteItem(String code) throws SQLException,ClassNotFoundException;
}
