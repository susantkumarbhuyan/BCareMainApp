package com.bcareapp.bookstore.daoimpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.jongo.Aggregate;
import org.jongo.Jongo;
import org.springframework.stereotype.Repository;

import com.bcareapp.bookstore.commonclass.Product;
import com.bcareapp.bookstore.dao.IProductDao;
import com.bcareapp.commonclasses.BCException;
import com.bcareapp.constants.MongoCollectionConstants;
import com.bcareapp.util.CommonsDataUtil;
import com.bcareapp.util.MongoDBUtil;
import com.mongodb.WriteResult;

@Repository
public class ProductDaoImpl implements IProductDao {
	private static final Logger logger = Logger.getLogger(ProductDaoImpl.class.getName());

	public int addAndUpdateProducts(Product prod) throws BCException {
		int n = 0;
		try {
			WriteResult result = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_PRODUCT)
					.update("{productId:#}", prod.getProductId()).upsert().with("{$set:#}", prod);
			n = result.getN();
		} catch (Exception e) {
			logger.error("Exception occurred in ProductService updateProduct", e);
		}
		return n;
	}

	@Override
	public int removeProductById(int productId) throws BCException {
		int n = 0;
		try {
			WriteResult result = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_PRODUCT)
					.remove("{productId:#}", productId);
			n = result.getN();
		} catch (Exception e) {
			throw new BCException("Exception occurred while removing product", e);
		}
		return n;
	}

	@Override
	public int removeAllProduct() throws BCException {
		int n = 0;
		try {
			WriteResult result = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_PRODUCT)
					.remove("{}");
			n = result.getN();
		} catch (Exception e) {
			throw new BCException("Exception occurred while removing all product", e);
		}
		return n;
	}

	@Override
	public List<Product> getProducts(int productId, String category, int minRange, int maxRange, String productName,
			int sortType) throws BCException {
		try {
			Aggregate agg = null;

			if (productId > 0) {
				agg = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_PRODUCT)
						.aggregate("{$match:{'productId':#}}", productId);
			} else if (!CommonsDataUtil.isNullOrEmpty(category) || minRange > 0 || maxRange > 0
					|| !CommonsDataUtil.isNullOrEmpty(productName) || sortType == -1) {
				if (!CommonsDataUtil.isNullOrEmpty(category)) {
					agg = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_PRODUCT)
							.aggregate("{'$match':{'category':#}}", category);
				}
				if (minRange != 0 && maxRange != 0) {
					agg = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_PRODUCT)
							.aggregate("{'$match':{ 'price': { $gt: #, $lt: # } }}", minRange, maxRange);
				}
				if (!CommonsDataUtil.isNullOrEmpty(productName)) {
					agg = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_PRODUCT)
							.aggregate("{'$match':{'productName':{'$regex': #,'$options':'i'}}}", productName);
				}
				if (sortType < 1) {
					agg.and("{'$sort' : {'price': -1}}");
				}
				agg.and("{'$sort' : {'price': 1}}");

			} else {
				agg = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_PRODUCT)
						.aggregate("{ $match : { } }").and("{'$sort' : {'price': 1}}");
			}
			agg.and("{'$project':{productId:1, productName:1,price:1, pages:1, isbnNo:1, language:1,category:1}}");

			return CommonsDataUtil.iteratorToList(agg.as(Product.class).iterator());
		} catch (Exception e) {
			throw new BCException("Exception occurred while removing all product", e);
		}
	}

	@Override
	public Product getProuctbyProductId(long productId) throws BCException {
		try {
			return new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_PRODUCT)
					.findOne("{productId:#}", productId).as(Product.class);

		} catch (Exception e) {
			throw new BCException("Exception occurred while removing all product", e);
		}

	}

}
