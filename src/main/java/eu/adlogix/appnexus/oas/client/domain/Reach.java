package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Reach {

	OPEN("O"), FIXED("F"), DYNAMIC("D"), DYNAMIC_RECALCULATED_MONTHLY("M"), HOUSE("H");

	private final String code;

	@Override
	public String toString() {
		return code;
	}

	public static Reach fromString(String code) {
		if (code == null)
			return null;

		for (Reach reach : Reach.values()) {
			if (code.equalsIgnoreCase(reach.code)) {
				return reach;
			}
		}
		throw new IllegalArgumentException("Invalid Reach Code:" + code);
	}
}