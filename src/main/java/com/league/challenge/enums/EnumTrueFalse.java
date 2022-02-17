package com.league.challenge.enums;

import java.util.Arrays;

public enum EnumTrueFalse {
	
	TRUE(true, 1L),
	FALSE(false, 0L);
	
	private boolean trueFalse;
	
	private Long oneZero;

	EnumTrueFalse(boolean b, Long i) {
		trueFalse = b;
		oneZero = i;
	}
	
	public static EnumTrueFalse getInstance(boolean trueFalse) {
		return Arrays.asList(EnumTrueFalse.values()).stream()
		.filter(p -> p.isTrueFalse() == trueFalse)
		.findFirst()
		.get();
	}

	public static EnumTrueFalse getInstance(Long oneZero) {
		return Arrays.asList(EnumTrueFalse.values()).stream()
				.filter(p -> p.getOneZero() == oneZero)
				.findFirst()
				.get();
	}
	
	public boolean isTrueFalse() {
		return trueFalse;
	}

	public void setTrueFalse(boolean trueFalse) {
		this.trueFalse = trueFalse;
	}

	public Long getOneZero() {
		return oneZero;
	}

	public void setOneZero(Long oneZero) {
		this.oneZero = oneZero;
	}

}
