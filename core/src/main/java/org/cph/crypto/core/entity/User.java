package org.cph.crypto.core.entity;

import java.util.List;

public class User {
	private String id;
	private String email;
	private Role role;
	private Currency currency;
	private double liquidityMovement;
	private boolean allowed;
	private double value;
	private double originalValue;
	private double gain;
	private double gainPercentage;
	private List<Position> position;
}
