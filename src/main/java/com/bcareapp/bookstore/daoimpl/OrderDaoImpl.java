package com.bcareapp.bookstore.daoimpl;

import java.util.Iterator;
import java.util.List;

import org.jongo.Jongo;
import org.springframework.stereotype.Repository;

import com.bcareapp.bookstore.commonclass.Order;
import com.bcareapp.bookstore.dao.IOrderDao;
import com.bcareapp.commonclasses.BCException;
import com.bcareapp.constants.MongoCollectionConstants;
import com.bcareapp.util.CommonsDataUtil;
import com.bcareapp.util.MongoDBUtil;
import com.mongodb.WriteResult;

@Repository("orderDao")
public class OrderDaoImpl implements IOrderDao {

	public List<Order> getAllOrder() throws BCException {
		try {
			Iterator<Order> iterator = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_ORDERS)
					.find("{}").as(Order.class);
			return CommonsDataUtil.iteratorToList(iterator);
		} catch (Exception e) {
			throw new BCException("Exception occurred while fatching OrderList", e);
		}
	}

	public int removeOrdersById(int orderId) throws BCException {
		int n = 0;
		try {
			WriteResult result = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_ORDERS)
					.remove("{orderId:#}", orderId);
			n = result.getN();
		} catch (Exception e) {
			throw new BCException("Exception occurred while removing Order", e);
		}
		return n;
	}

	@Override
	public int removeAllOrders() throws BCException {
		int n = 0;
		try {
			WriteResult result = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_ORDERS)
					.remove("{}");
			n = result.getN();
		} catch (Exception e) {
			throw new BCException("Exception occurred while removing all Orders", e);
		}
		return n;
	}

}
