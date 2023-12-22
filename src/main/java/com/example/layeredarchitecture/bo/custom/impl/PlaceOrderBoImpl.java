package com.example.layeredarchitecture.bo.custom.impl;

import com.example.layeredarchitecture.bo.custom.PlaceOrderBO;
import com.example.layeredarchitecture.dao.custom.impl.CustomerDAOImpl;
import com.example.layeredarchitecture.dao.custom.impl.ItemDAOImpl;
import com.example.layeredarchitecture.dao.custom.impl.OrderDetailDAOImpl;
import com.example.layeredarchitecture.dao.custom.impl.OrdersDAOImpl;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBoImpl implements PlaceOrderBO {
    private CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    private OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
    OrderDetailDAOImpl orderDetailDAO = new OrderDetailDAOImpl();
    private ItemDAOImpl itemDAO = new ItemDAOImpl();

    @Override
    public ArrayList<ItemDTO> loadAllItems() throws SQLException, ClassNotFoundException {
        return itemDAO.getall();
    }

    @Override
    public ArrayList<CustomerDTO> loadAllCustomer() throws SQLException, ClassNotFoundException {
        return customerDAO.getall();
    }

    @Override
    public String generateNewOrderId() {
        return ordersDAO.genarateNewId();
    }

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException{
        return customerDAO.exits(id);
    }
    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException{
        return itemDAO.exits(code);
    }

    @Override
    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.search(id);
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.search(id);

    }
    @Override
    public boolean PlaceOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
            ResultSet orderId1 = ordersDAO.getOrderId(orderId);
            /*if order id already exist*/
            if (orderId1.next()) {

            }

            connection.setAutoCommit(false);
            boolean b = ordersDAO.save(new OrderDTO(orderId, orderDate, customerId));

            if (!b) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            for (OrderDetailDTO o : orderDetails) {
                boolean b1 = orderDetailDAO.saveOrderDetail(orderId, new OrderDetailDTO(o.getItemCode(), o.getQty(), o.getUnitPrice()));
                if (!b1){
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                ItemDTO item = itemDAO.search(o.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - o.getQty());
                boolean b2 = itemDAO.update(new ItemDTO(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
                if (!b2){
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                connection.commit();
                connection.setAutoCommit(true);
                return true;
            }
        return false;
    }
}
