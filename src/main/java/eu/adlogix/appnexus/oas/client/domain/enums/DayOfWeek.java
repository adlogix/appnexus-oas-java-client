package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.Campaign;

/**
 * "Day Of Week" values which can be assigned to a {@link Campaign} in Campaign
 * creation, update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum DayOfWeek {

	SUNDAY("0"), MONDAY("1"), TUESDAY("2"), WEDNESDAY("3"), THURSDAY("4"), FRIDAY("5"), SATURDAY("6");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}

	/**
	 * Returns a {@link DayOfWeek} object which corresponds to the given OAS
	 * code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link DayOfWeek} object
	 */
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