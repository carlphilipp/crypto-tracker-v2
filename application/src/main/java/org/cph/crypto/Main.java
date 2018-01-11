package org.cph.crypto;

import org.cph.crypto.core.entity.User;

public class Main {
	public static void main(String[] args) {
		User user = new User();
		user.setUsername("carl");
		System.out.print(user.getUsername());
	}
}
