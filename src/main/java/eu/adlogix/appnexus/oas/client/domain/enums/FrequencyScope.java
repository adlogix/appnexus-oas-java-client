package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.utils.EnumUtils;

/**
 * "Frequency Scope" values which can be assigned to a {@link Campaign} in
 * Campaign creation, update and retrieval. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum FrequencyScope implements ToStringReturnsEnumCode {

	ZERO(0), SESSION(1), HOURLY(2), DAILY(3), WEEKLY(4), MONTHLY(5), LIFETIME(6);

	private final int code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return Integer.toString(code);
	}

	/**
	 * Returns a {@link FrequencyScope} object which corresponds to the given
	 * OAS code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link FrequencyScope} object
	 */
	public static FrequencyScope fromString(String code) {
		return EnumUtils.fromString(code, values(), FrequencyScope.class.getSimpleName());
	}

}
