package com.bcareapp.bookstore.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcareapp.bookstore.commonclass.Order;
import com.bcareapp.bookstore.dao.IOrderDao;
import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.commonclasses.BaseResponseListData;
import com.bcareapp.constants.ResponseConstants;

@Service("orderService")
public class OrderService {
	private static final Logger logger = Logger.getLogger(OrderService.class.getName());
	@Autowired
	private IOrderDao orderDao;

	public BaseResponseListData getAllOrder() throws BCException {
		BaseResponseListData response = new BaseResponseListData();
		try {
			List<Order> orderList = orderDao.getAllOrder();
			if (orderList.size() > 0) {
				response.setStatusCode(ResponseConstants.SUCCESS_FOUND_UPDATED);
				response.setStatusMessage(ResponseConstants.SUCESS);
				response.setData(orderList);
			} else {
				response.setStatusCode(ResponseConstants.NOT_FOUND);
				response.setStatusMessage(ResponseConstants.NO_DATAFOUND);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in OrderService getAllOrders", e);
		}
		return response;
	}

	public BaseResponse<String> removeOrdersById(int orderId) throws BCException {
		BaseResponse<String> response = new BaseResponse<>();
		try {
			int result = orderDao.removeOrdersById(orderId);
			if (result > 0 && result == 1) {
				response.setStatusCode(ResponseConstants.ACCEPT_DELETE);
				response.setStatusMessage(ResponseConstants.SUCESS_DELETE_MESSAGE);
			} else {
				response.setStatusCode(ResponseConstants.FAILED);
				response.setStatusMessage(ResponseConstants.NO_DATAFOUND);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in OrderService deleteOrderById", e);
		}
		return response;
	}

	public BaseResponse<String> removeAllOrders() throws BCException {
		BaseResponse<String> response =  new BaseResponse<>();
		try {
			int result = orderDao.removeAllOrders();
			if (result > 0 && result == 1) {
				response.setStatusCode(ResponseConstants.ACCEPT_DELETE);
				response.setStatusMessage(ResponseConstants.SUCESS_DELETE_MESSAGE);
			} else {
				response.setStatusCode(ResponseConstants.FAILED);
				response.setStatusMessage(ResponseConstants.NO_DATAFOUND);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in OrderService deleteAllOrder", e);
		}
		return response;
	}

}
