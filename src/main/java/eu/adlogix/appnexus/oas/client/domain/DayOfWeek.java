package eu.adlogix.appnexus.oas.client.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DayOfWeek {

	SUNDAY("0"), MONDAY("1"), TUESDAY("2"), WEDNESDAY("3"), THURSDAY("4"), FRIDAY("5"), SATURDAY("6");

	private final String code;

	@Override
	public String toString() {
		return code;
	}

	public static DayOfWeek fromString(String code) {
		if (code == null)
			return null;

		for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
			if (code.equalsIgnoreCase(dayOfWeek.code)) {
				return dayOfWeek;
			}
		}
		throw new IllegalArgumentException("Invalid DayOfWeek Code:" + code);
	}
}