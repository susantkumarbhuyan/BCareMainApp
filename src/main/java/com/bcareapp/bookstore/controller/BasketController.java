package com.bcareapp.bookstore.controller;

import org.apache.log4j.Logger;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcareapp.bookstore.commonclass.CartItem;
import com.bcareapp.bookstore.commonclass.CartRequest;
import com.bcareapp.bookstore.commonclass.PaymentRequest;
import com.bcareapp.bookstore.service.BasketService;
import com.bcareapp.commonclasses.BaseResponse;
@RestController
@RequestMapping("/basket")
public class BasketController {
	private static final Logger logger = Logger.getLogger(BasketController.class.getName());
	@Autowired
	private BasketService basketService;

	@PostMapping("/addtocart")
	public BaseResponse<CartItem> addToCart(@RequestBody CartRequest cartRequest) {
		BaseResponse<CartItem> response = null;
		try {
			response = basketService.addToCart(cartRequest);
		} catch (Exception e) {
			logger.error("Exception occurred in  CartService addCart", e);
		}
		return response;
	}

	@GetMapping("/cart")
	public BaseResponse<String> showCartItems() {
		BaseResponse<String> response = null;
		try {
			response = basketService.showCartItems();
		} catch (Exception e) {
			logger.error("Exception occurred in CartService showCart", e);
		}
		return response;
	}

	@GetMapping("/removecart")
	public BaseResponse<String> removeCartItem(int cartId) {
		BaseResponse<String> response = null;
		try {
			response = basketService.removeCartItem(cartId);
		} catch (Exception e) {
			logger.error("Exception occurred in CartService deleteAllCart", e);
		}
		return response;
	}

	@PostMapping("/cart/update")
	public BaseResponse<String> updateCartItem(@RequestBody CartRequest cartRequest) {
		BaseResponse<String> response = null;
		try {
			response = basketService.updateCartItem(cartRequest);
		} catch (Exception e) {
			logger.error("Exception occurred in CartService updateCart", e);
		}
		return response;
	}

	@PostMapping("/addtocheckout")
	public BaseResponse<String> addToCheckout(@RequestBody Address address) {
		BaseResponse<String> response = null;
		try {
			response = basketService.addToCheckout(address);
		} catch (Exception e) {
			logger.error("Exception occurred in CheckoutService  addCheckout", e);
		}
		return response;
	}

	@PostMapping("/payment")
	public BaseResponse<String> completeCartPurchase(@RequestBody PaymentRequest request) {
		BaseResponse<String> response = null;
		try {
			response = basketService.completeCartPurchase(request);
		} catch (Exception e) {
			logger.error("Exception occurred in CartService updateCart", e);
		}
		return response;
	}

	@GetMapping("/checkout")
	public BaseResponse<String> showCheckout() {
		BaseResponse<String> response = null;
		try {
			response = basketService.showCheckout();
		} catch (Exception e) {
			logger.error("Exception occurred in CheckOutService ShowCheckout", e);
		}
		return response;
	}
}
