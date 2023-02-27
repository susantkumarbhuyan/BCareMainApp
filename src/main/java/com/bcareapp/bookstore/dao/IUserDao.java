package com.bcareapp.bookstore.dao;

import java.util.List;

import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.User;

public interface IUserDao {
	public User getUserDetailsByUserName(String username) throws BCException;

	public List<User> getAllUsersList() throws BCException;

	public int removeUserByUserId(int userId) throws BCException;

	public int removeAllUsers() throws BCException;

	public int insertandupdateUser(User user) throws BCException;

	public User getUserByUserId(long userId) throws BCException;
}
