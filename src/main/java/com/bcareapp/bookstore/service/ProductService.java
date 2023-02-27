package com.bcareapp.bookstore.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcareapp.bookstore.commonclass.Product;
import com.bcareapp.bookstore.dao.IProductDao;
import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.BaseResponse;
import com.bcareapp.commonclasses.BaseResponseListData;
import com.bcareapp.constants.ResponseConstants;
import com.bcareapp.util.ServerSideScriptsUtil;

@Service("productService")
public class ProductService {
	private static final Logger logger = Logger.getLogger(ProductService.class.getName());
	@Autowired
	private IProductDao productDao;

	public BaseResponse<String> addAndUpdateProducts(Product product) throws BCException {
		BaseResponse<String> response = new BaseResponse<>();
		try {
			if (product.getProductId() == 0) {
				product.setProductId(ServerSideScriptsUtil.callServerSideScript("productId"));
			}
			int result = productDao.addAndUpdateProducts(product);
			if (result > 0 && result == 1) {
				response.setStatusCode(ResponseConstants.SUCCESS_CREATED);
				response.setStatusMessage(ResponseConstants.SUCESS_MESSAGE);
			} else {
				response.setStatusCode(ResponseConstants.FAILED);
				response.setStatusMessage(ResponseConstants.FAILED_MESSAGE);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in ProductService Add and Update Product", e);
		}
		return response;
	}

	public BaseResponse<String> removeProductById(int productId) throws BCException {
		BaseResponse<String> response = new BaseResponse<>();
		try {
			int result = productDao.removeProductById(productId);
			if (result > 0 && result == 1) {
				response.setStatusCode(ResponseConstants.ACCEPT_DELETE);
				response.setStatusMessage(ResponseConstants.SUCESS_DELETE_MESSAGE);
			} else {
				response.setStatusCode(ResponseConstants.FAILED);
				response.setStatusMessage(ResponseConstants.NO_DATAFOUND);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in ProductService deleteProduct", e);
		}
		return response;
	}

	public BaseResponse<String> removeAllProduct() throws BCException {
		BaseResponse<String> response = new BaseResponse<>();
		try {
			int result = productDao.removeAllProduct();
			if (result > 0 && result == 1) {
				response.setStatusCode(ResponseConstants.ACCEPT_DELETE);
				response.setStatusMessage(ResponseConstants.SUCESS_DELETE_MESSAGE);
			} else {
				response.setStatusCode(ResponseConstants.FAILED);
				response.setStatusMessage(ResponseConstants.NO_DATAFOUND);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in ProductService deleteAllProduct", e);
		}
		return response;
	}

	public BaseResponseListData getProducts(int productId, String category, int minRange, int maxRange,
			String productName, int sortType) throws BCException {
		BaseResponseListData response = new BaseResponseListData();
		try {
			List<Product> productList = productDao.getProducts(productId, category, minRange, maxRange, productName,
					sortType);
			if (productList.size() > 0) {
				response.setData(productList);
				response.setStatusCode(ResponseConstants.SUCCESS_CREATED);
				response.setStatusMessage(ResponseConstants.SUCESS_MESSAGE);
			} else {
				response.setStatusCode(ResponseConstants.NOT_FOUND);
				response.setStatusMessage(ResponseConstants.NO_DATAFOUND);
			}
		} catch (Exception e) {
			logger.error("Exception occurred in ProductService deleteAllProduct", e);
		}
		return response;
	}

}
