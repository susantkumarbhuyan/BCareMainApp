package com.bcareapp.bookstore.dao;

import com.bcareapp.bookstore.commonclass.CartItem;
import com.bcareapp.commonclasses.BCException;

public interface IBasketDao {

	public int addToCart(CartItem cartItem) throws BCException;

}
