package org.registrator.community.service.impl;

import java.sql.Timestamp;

import org.registrator.community.entity.User;
import org.registrator.community.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("authenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	UserService userService;

	@Autowired
	private Logger logger;

	@Autowired
	private PasswordEncoder userPasswordEncoder;

	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		setPasswordEncoder(userPasswordEncoder);
		super.setUserDetailsService(userDetailsService);
	}

	@Override
	public Authentication authenticate(Authentication authentication) {

		try {
			User user = userService.findUserByLogin(authentication.getName());
			boolean isAccountNonExpired = (user.getAccountNonExpired() == 1) ? true : false;
			Authentication auth = super.authenticate(authentication);

			if (!isAccountNonExpired)
				throw new LockedException("User Account is locked!");
			userService.resetFailAttempts(authentication.getName());
			logger.info(authentication.getName() + " is authentificated successfully");
			return auth;

		} catch (BadCredentialsException e) {
			logger.error(authentication.getName() + " has entered wrong credentials");
			userService.updateFailAttempts(authentication.getName());
			String error = "You have entered wrong login or password.";
			throw new BadCredentialsException(error);

		} catch (LockedException e) {
			String error = "";
			User user = userService.findUserByLogin(authentication.getName());
			if (user != null) {
				Timestamp lastAttempts = user.getLastModified();
				error = "User account is locked! <br><br>Username : " + authentication.getName()
						+ "<br>Last Attempts : " + lastAttempts + "<br>You will be unlocked in 5 minutes ";
			} else {
				error = e.getMessage();
			}
			logger.error(authentication.getName() + " is blocked at : "+user.getLastModified());
			throw new LockedException(error);
		} catch (Exception e) {
			logger.error("Something went wrong while "+authentication.getName()+" was trying to authentificate");
			throw new BadCredentialsException("You have entered wrong login or password.");
		}

	}

}
