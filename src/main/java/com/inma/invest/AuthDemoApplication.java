package com.inma.invest;

import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.inma.invest.entity.Role;
import com.inma.invest.entity.RoleName;
import com.inma.invest.repository.RoleRepository;

/**
 * 
 * @author ssatwa
 *
 */

@SpringBootApplication
public class AuthDemoApplication implements ApplicationRunner {

	@Autowired
	private RoleRepository roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(AuthDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		init();
	}

	/**
	 * Intialize default application roles into in-memory db
	 */
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		if (!roleRepo.findByName(RoleName.ROLE_ADMIN).isPresent()) {
			Role role_admin = new Role(RoleName.ROLE_ADMIN);
			roleRepo.save(role_admin);
		}

		if (!roleRepo.findByName(RoleName.ROLE_USER).isPresent()) {
			Role role_user = new Role(RoleName.ROLE_USER);
			roleRepo.save(role_user);
		}

	}

}
