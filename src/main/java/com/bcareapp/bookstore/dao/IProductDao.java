package com.bcareapp.bookstore.dao;

import java.util.List;

import com.bcareapp.bookstore.commonclass.Product;
import com.bcareapp.commonclasses.BCException;

public interface IProductDao {
	public int addAndUpdateProducts(Product prod) throws BCException;

	public int removeProductById(int productId) throws BCException;

	public int removeAllProduct() throws BCException;

	public List<Product> getProducts(int productId, String category, int minRange, int maxRange, String productName,
			int sortType) throws BCException;

	public Product getProuctbyProductId(long productId) throws BCException;
}
