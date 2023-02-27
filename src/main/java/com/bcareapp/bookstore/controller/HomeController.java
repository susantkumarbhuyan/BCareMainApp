package com.bcareapp.bookstore.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bcareapp.bookstore.Temp;
import com.bcareapp.bookstore.service.ProductService;
import com.bcareapp.commonclasses.BaseResponseListData;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/home")
public class HomeController {
	private static final Logger logger = Logger.getLogger(HomeController.class.getName());
	@Autowired
	private ProductService productService;

	@GetMapping("/product")
	public BaseResponseListData getProducts(
			@RequestParam(value = "productId", required = false, defaultValue = "0") int productId,
			@RequestParam(value = "category", required = false, defaultValue = "") String category,
			@RequestParam(value = "minRange", required = false, defaultValue = "0") int minRange,
			@RequestParam(value = "maxRange", required = false, defaultValue = "0") int maxRange,
			@RequestParam(value = "productName", required = false, defaultValue = "") String productName,
			@RequestParam(value = "sortType", required = false, defaultValue = "0") Integer sortType) {

		BaseResponseListData response = null;
		try {
			response = productService.getProducts(productId, category, minRange, maxRange, productName, sortType);
		} catch (Exception e) {
			logger.error("Exception occurred in ProductService getAllProducts", e);
		}
		return response;
	}

	@GetMapping("/ttt")
	public String getData() throws JsonParseException, JsonMappingException, IOException {
		String tt = Temp.getDailyDataInMinute();
		return tt;
	}
}
