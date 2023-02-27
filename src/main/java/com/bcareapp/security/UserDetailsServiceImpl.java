package com.bcareapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bcareapp.bookstore.dao.IUserDao;
import com.bcareapp.commonclasses.BCException;
import com.bcareapp.commonclasses.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private IUserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = new User();
		try {
			user = userDao.getUserDetailsByUserName(username);
			if (user == null) {
				new UsernameNotFoundException("User Not Found with username: " + username);
			}

		} catch (BCException e) {
			new BCException("Excceptioon Occured While Loading UserDetails", e);
		}
		return UserDetailsImpl.build(user);
	}
}
