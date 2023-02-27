package com.bcareapp.bookstore.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcareapp.bookstore.commonclass.CartItem;
import com.bcareapp.bookstore.commonclass.CartRequest;
import com.bcareapp.bookstore.commonclass.PaymentRequest;
import com.bcareapp.bookstore.commonclass.Product;
import com.bcareapp.bookstore.dao.IBasketDao;
import com.bcareapp.bookstore.dao.IProductDao;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.constants.ResponseConstants;
import com.bcareapp.util.ServerSideScriptsUtil;

@Service
public class BasketService {
	private static final Logger logger = Logger.getLogger(BasketService.class.getName());
	@Autowired
	private IBasketDao basketDao;
	@Autowired
	private IProductDao produtDao;

	public BaseResponse<CartItem> addToCart(CartRequest cartRequest) {
		BaseResponse<CartItem> response = new BaseResponse<>();
		try {
			Product product = produtDao.getProuctbyProductId(cartRequest.getProductId());
			CartItem cartItem = new CartItem();
			cartItem.setProductList(List.of(product));
			if (cartRequest.getCartId() == 0l) {
				cartItem.setCartId(ServerSideScriptsUtil.generateUniqueLongVal("cartId"));
			} else {
				cartItem.setCartId(cartRequest.getCartId());
			}
			cartItem.setUserId(cartRequest.getUserId());
			cartItem.setQuantity(cartRequest.getQuantity());
			cartItem.setTotalamount(cartRequest.getQuantity() * product.getPrice());
			int result = basketDao.addToCart(cartItem);
			if (result > 0 && result == 1) {
				response.setData(cartItem);
				response.setStatusCode(ResponseConstants.SUCCESS_CREATED);
				response.setStatusMessage(ResponseConstants.SUCESS_MESSAGE);
			} else {
				response.setStatusCode(ResponseConstants.FAILED);
				response.setStatusMessage(ResponseConstants.FAILED_MESSAGE);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in ProductService updateProduct", e);
		}
		return response;
	}

	public BaseResponse<String> showCartItems() {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseResponse<String> removeCartItem(int cartId) {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseResponse<String> updateCartItem(CartRequest cartRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseResponse<String> addToCheckout(Address address) {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseResponse<String> showCheckout() {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseResponse<String> completeCartPurchase(PaymentRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
