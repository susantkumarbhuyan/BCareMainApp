package com.bcareapp.bookstore.daoimpl;

import java.util.Iterator;
import java.util.List;

import org.jongo.Jongo;
import org.springframework.stereotype.Repository;

import com.bcareapp.bookstore.dao.IUserDao;
import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.User;
import com.bcareapp.constants.MongoCollectionConstants;
import com.bcareapp.util.CommonsDataUtil;
import com.bcareapp.util.MongoDBUtil;
import com.mongodb.WriteResult;

@Repository("userDao")
public class UserDaoImpl implements IUserDao {

	@Override
	public User getUserDetailsByUserName(String username) throws BCException {
		try {
			return new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_USERS)
					.findOne("{emailId:#}", username).as(User.class);
		} catch (Exception e) {
			throw new BCException("Exception occurred while fetching UserDetails", e);
		}

	}

	@Override
	public List<User> getAllUsersList() throws BCException {
		try {
			Iterator<User> itr = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_USERS)
					.find("{}").as(User.class).iterator();
			return CommonsDataUtil.iteratorToList(itr);
		} catch (Exception e) {
			throw new BCException("Exception occurred while removing all product", e);
		}
	}

	@Override
	public int removeUserByUserId(int userId) throws BCException {
		int n = 0;
		try {
			WriteResult result = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_USERS)
					.remove("{userId:#}", userId);
			n = result.getN();
		} catch (Exception e) {
			throw new BCException("Exception occurred while removing product", e);
		}
		return n;
	}

	@Override
	public int removeAllUsers() throws BCException {
		int n = 0;
		try {
			WriteResult result = new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_USERS)
					.remove("{}");
			n = result.getN();
		} catch (Exception e) {
			throw new BCException("Exception occurred while removing all Users", e);
		}
		return n;
	}

	@Override
	public int insertandupdateUser(User user) throws BCException {
		int n = 0;
		try {
			WriteResult result = new Jongo(MongoDBUtil.getDB())
					.getCollection(MongoCollectionConstants.CN_USERS).update("{userId:#}", user.getUserId())
					.upsert().with("{$set:#}", user);
			n = result.getN();
		} catch (Exception e) {
			throw new BCException("Exception occurred while removing all Users", e);
		}
		return n;
	}

	@Override
	public User getUserByUserId(long userId) throws BCException {
		try {
			return new Jongo(MongoDBUtil.getDB()).getCollection(MongoCollectionConstants.CN_USERS)
					.findOne("{userId:#}", userId).as(User.class);
		} catch (Exception e) {
			throw new BCException("Exception occurred while fetching UserDetails", e);
		}
	}
}
