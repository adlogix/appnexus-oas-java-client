package eu.adlogix.appnexus.oas.client.domain.enums;

import lombok.AllArgsConstructor;
import eu.adlogix.appnexus.oas.client.domain.Campaign;
import eu.adlogix.appnexus.oas.client.utils.EnumUtils;

/**
 * "Smooth or ASAP" values which can be assigned to {@link Campaign} in Campaign
 * creation, update and retrieval operations. The OAS code is accessible via
 * {@link #toString()}
 */
@AllArgsConstructor
public enum SmoothAsap implements ToStringReturnsEnumCode {

	SMOOTH("S"), ASAP("A");

	private final String code;

	/**
	 * Returns the OAS code
	 */
	@Override
	public String toString() {
		return code;
	}

	/**
	 * Returns a {@link SmoothAsap} object which corresponds to the given OAS
	 * code
	 * 
	 * @param code
	 *            - OAS Code
	 * @return {@link SmoothAsap} object
	 */
	public static SmoothAsap fromString(String code) {
		return EnumUtils.fromString(code, values(), SmoothAsap.class.getSimpleName());
	}

}
