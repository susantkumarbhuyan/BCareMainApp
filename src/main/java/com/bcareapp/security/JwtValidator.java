package com.bcareapp.security;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bcareapp.bookstore.commonclass.JwtUser;
import com.bcareapp.constants.DateTimeConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtValidator {
	private static final Logger logger = Logger.getLogger(JwtValidator.class);
	private static final String USER_ID = "userId";
	private static final String USER_TYPE = "userType";
	private static final String ROLES = "roles";
	private static final String secret = "0D44A78AA5B3E1E67F5C31019D3EB505E7C5ABA757F319C209EF4648D282E5F6EA36742B0C99769DCB27DFA7986D3720C4EA75D2B4827AEB78960C0BDB243C25";

	@SuppressWarnings("deprecation")
	public String generate(JwtUser jwtUser) {
		Claims claims = Jwts.claims().setSubject(jwtUser.getUsername());
		claims.put(USER_ID, String.valueOf(jwtUser.getUserId()));
		claims.put(USER_TYPE, String.valueOf(jwtUser.getUserType()));
		claims.put(ROLES, jwtUser.getRoles());
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (30 * DateTimeConstants.ONEDAYINMILLI)))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public JwtUser validate(String token) {
		JwtUser jwtUser = null;
		try {
			Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			jwtUser = new JwtUser();
			jwtUser.setUsername(body.getSubject());
			jwtUser.setUserId(Long.parseLong((String) body.get(USER_ID)));
			if (body.get(USER_TYPE) != null) {
				jwtUser.setUserType(Integer.parseInt((String) body.get(USER_TYPE)));
			}
			jwtUser.setRoles((String) body.get(ROLES));
		} catch (Exception e) {
			logger.error("Exception occurred while validating the JWT Token", e);
		}
		return jwtUser;
	}

	public Boolean validateJwtToken(String token, UserDetails userDetails) {
		String username = getUsernameFromToken(token);
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		Boolean isTokenExpired = claims.getExpiration().before(new Date());
		return (username.equals(userDetails.getUsername()) && !isTokenExpired);
	}

	private String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

}
