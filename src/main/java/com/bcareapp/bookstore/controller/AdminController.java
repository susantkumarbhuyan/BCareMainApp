package com.bcareapp.bookstore.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcareapp.bookstore.commonclass.Product;
import com.bcareapp.bookstore.service.OrderService;
import com.bcareapp.bookstore.service.ProductService;
import com.bcareapp.bookstore.service.UserService;
import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.commonclasses.BaseResponseListData;
import com.bcareapp.commonclasses.User;
import com.google.gson.Gson;

@RestController
@RequestMapping("/admincontrol")
public class AdminController {
	private static final Logger logger = Logger.getLogger(AdminController.class.getName());

	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;

	@PostMapping("/addandupdateproduct")
	public BaseResponse<String> addAndUpdateProducts(@RequestBody Product product) throws BCException {
		BaseResponse<String> response = null;
		try {
			logger.debug("checkPackageDiscount----> " + new Gson().toJson(product));
			response = productService.addAndUpdateProducts(product);
		} catch (Exception e) {
			logger.error("Exception occurred in ProductService updateProduct", e);
		}
		return response;
	}

	@GetMapping("/removeproduct")
	public BaseResponse<String> removeProductById(int productId) {
		BaseResponse<String> response = null;
		try {
			response = productService.removeProductById(productId);
		} catch (Exception e) {
			logger.error("Exception occurred in ProductService deleteProduct", e);
		}
		return response;
	}

	@GetMapping("/removeallproduct")
	public BaseResponse<String> removeAllProduct() {
		BaseResponse<String> response = null;
		try {
			response = productService.removeAllProduct();

		} catch (Exception e) {
			logger.error("Exception occurred in ProductService deleteAllProduct", e);
		}
		return response;
	}

	@GetMapping("/getallusers")
	public BaseResponseListData getAllUsersList() {
		BaseResponseListData response = null;
		try {
			response = userService.getAllUsersList();
		} catch (Exception e) {
			logger.error("Exception occurred in OrderService getAllOrders", e);
		}
		return response;
	}

	@GetMapping("/getuser")
	public BaseResponse<User> getUserByUsername(String username) {
		BaseResponse<User> response = null;
		try {
			response = userService.getUserByUsername(username);
		} catch (Exception e) {
			logger.error("Exception occurred in UserService viewUserById", e);
		}
		return response;

	}

	@GetMapping("/removeuser")
	public BaseResponse<String> removeUserByUserId(int userId) {
		BaseResponse<String> response = null;
		try {
			response = userService.removeUserByUserId(userId);
		} catch (Exception e) {
			logger.error("Exception occurred in UserService viewUserById", e);
		}
		return response;
	}

	@GetMapping("/removealluser")
	public BaseResponse<String> removeAllUser() {
		BaseResponse<String> response = null;
		try {
			response = userService.removeAllUser();
		} catch (Exception e) {
			logger.error("Exception occurred in UserService deleteAllUser", e);
		}
		return response;
	}

	@GetMapping("/getallorders")
	public BaseResponseListData getAllOrders() {
		BaseResponseListData response = null;
		try {
			response = orderService.getAllOrder();

		} catch (Exception e) {
			logger.error("Exception occurred in OrderService getAllOrders", e);
		}
		return response;
	}

	@GetMapping("/removeorder")
	public BaseResponse<String> removeOrdersById(int orderId) {
		BaseResponse<String> response = null;
		try {
			response = orderService.removeOrdersById(orderId);
		} catch (Exception e) {
			logger.error("Exception occurred in OrderService deleteOrderById", e);
		}
		return response;
	}

	@GetMapping("/removeallorders")
	public BaseResponse<String> removeAllOrders() {
		BaseResponse<String> response = null;
		try {
			response = orderService.removeAllOrders();
		} catch (Exception e) {
			logger.error("Exception occurred in OrderService deleteAllOrder", e);
		}
		return response;
	}

}
