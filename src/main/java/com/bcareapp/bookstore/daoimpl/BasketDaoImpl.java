package com.bcareapp.bookstore.daoimpl;

import org.apache.log4j.Logger;
import org.jongo.Jongo;
import org.springframework.stereotype.Repository;

import com.bcareapp.bookstore.commonclass.CartItem;
import com.bcareapp.bookstore.dao.IBasketDao;
import com.bcareapp.commonclasses.BCException;
import com.bcareapp.constants.MongoCollectionConstants;
import com.bcareapp.util.MongoDBUtil;
import com.mongodb.WriteResult;

@Repository
public class BasketDaoImpl implements IBasketDao {
	private static final Logger logger = Logger.getLogger(BasketDaoImpl.class.getName());

	@Override
	public int addToCart(CartItem cartItem) throws BCException {
		try {
			int n = 0;
			try {
				WriteResult result = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_CARTITEMS)
						.update("{cartId:#}", cartItem.getCartId()).upsert().with("{$set:#}", cartItem);
				n = result.getN();
			} catch (Exception e) {
				logger.error("Exception occurred in ProductService updateProduct", e);
			}
			return n;
		} catch (Exception e) {
			throw new BCException("Exception occurred while removing all product", e);
		}
	}

}
