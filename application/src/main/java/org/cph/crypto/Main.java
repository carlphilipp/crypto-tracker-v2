package org.cph.crypto;

import org.cph.crypto.core.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		User user = new User();
		user.setEmail("carl@carl.com");
		System.out.print(user.getEmail());

		SpringApplication.run(Main.class, args);
	}
}
