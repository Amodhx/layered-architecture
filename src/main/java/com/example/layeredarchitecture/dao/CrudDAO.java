package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> {


    T search(String id) throws SQLException, ClassNotFoundException;
    String genarateNewId() throws SQLException, ClassNotFoundException;
    boolean exits(String id) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    boolean update(T dto) throws SQLException, ClassNotFoundException;
    boolean save(T dto) throws SQLException, ClassNotFoundException;
    ArrayList<T> getall() throws SQLException, ClassNotFoundException;
}
