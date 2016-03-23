package org.registrator.community.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * This class give opportunity to reset user attempts in 5 minutes after last attempt
 * </p>
 */

@Service
public class CronService {

	@Autowired
	private UserRepository userRepository;


	@Autowired
	private Logger logger;

	
	/**
	 * <p>
	 * reset user attempts to login to zero in 5 minutes from last attempt
	 * </p>
	 * 
	 * @return void
	 */
//	@Scheduled(fixedDelay = 10000)
	@Transactional
	public void checkAllFailAttempts() {
		try {
			List<User> allUsers = userRepository.findAll();
			for (User u : allUsers) {
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				Timestamp lastmodified = (u.getLastModified()!=null)?u.getLastModified():new Timestamp(0);
				long time = timestamp.getTime() - lastmodified.getTime();
				int accountNonLocked = u.getAccountNonLocked();
				if (time > 300000 && accountNonLocked == 0) {
					u.setAccountNonLocked(1);
					u.setAttempts(0);
					logger.info(u.getLogin() + " is unlocked at " + timestamp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to checkAllFailAttempts() with cron expressions " + e);
		}

	}

}
