package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.utils.EnumUtils;

/**
 * "Day Of Week" values which can be assigned to a {@link Campaign} in Campaign
 * creation, update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum DayOfWeek implements ToStringReturnsEnumCode {

	SUNDAY(0), MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6);

	private final int code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return Integer.toString(code);
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
		return EnumUtils.fromString(code, values(), DayOfWeek.class.getSimpleName());
	}
}