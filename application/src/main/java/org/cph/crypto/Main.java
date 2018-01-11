package org.cph.crypto;

import org.cph.crypto.core.entity.User;

public class Main {
	public static void main(String[] args) {
		User user = new User();
		user.setEmail("carl@carl.com");
		System.out.print(user.getEmail());
	}
}
