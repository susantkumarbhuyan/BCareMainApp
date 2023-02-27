package com.bcareapp.bookstore.dao;

import java.util.List;

import com.bcareapp.bookstore.commonclass.Order;
import com.bcareapp.commonclasses.BCException;

public interface IOrderDao {
	public List<Order> getAllOrder() throws BCException;

	public int removeOrdersById(int orderId) throws BCException;

	public int removeAllOrders() throws BCException;
}
