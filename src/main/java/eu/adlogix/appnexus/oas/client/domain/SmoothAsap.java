package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SmoothAsap {

	SMOOTH("S"), ASAP("A");

	private final String code;

	@Override
	public String toString() {
		return code;
	}

	public static SmoothAsap fromString(String code) {
		if (code == null)
			return null;

		for (SmoothAsap smoothAsap : SmoothAsap.values()) {
			if (code.equalsIgnoreCase(smoothAsap.code)) {
				return smoothAsap;
			}
		}
		throw new IllegalArgumentException("Invalid SmoothOrAsap Code:" + code);
	}

}
